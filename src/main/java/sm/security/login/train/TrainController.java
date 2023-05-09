package sm.security.login.train;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import sm.security.login.city.CitiesDTO;
import sm.security.login.ticket.TicketDTO;
import sm.security.login.ticket.TicketsDTO;
import sm.security.login.user.User;
import sm.security.login.user.UserRepository;

import java.util.HashMap;
import java.util.Map;

@Controller
public class TrainController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    UserRepository userRepository;

    @GetMapping("/trains")
    public String trains(Model model) {
        ResponseEntity<TrainsDTO> trainsDTO = new ResponseEntity<>(
                restTemplate.getForObject("http://localhost:8082//v1//trains", TrainsDTO.class)
                , HttpStatus.OK);

        ResponseEntity<CitiesDTO> citiesDTO = new ResponseEntity<>(
                restTemplate.getForObject("http://localhost:8082//v1//trains//cities", CitiesDTO.class)
                , HttpStatus.OK);
        model.addAttribute("cities", citiesDTO.getBody().getSitiesDTO());
        model.addAttribute("trains", trainsDTO.getBody().getTrainsDTO());
        return "/pages/trainroutes/index";
    }



    @PostMapping("/trains/route")
    public String routeFind(@RequestParam String cityFrom,
                            @RequestParam String cityTo,
                            Model model) {
        logger.info("City from: {}, city to: {}", cityFrom, cityTo);
        ResponseEntity<TrainsDTO> trainsDTOr = new ResponseEntity<>(
                restTemplate.getForObject(
                        "http://localhost:8082//v1//trains//route?from="
                                + cityFrom + "&to=" + cityTo, TrainsDTO.class)
                , HttpStatus.OK);
            model.addAttribute("trains", trainsDTOr.getBody().getTrainsDTO());
            model.addAttribute("notfound", false);
            return "/pages/trainroutes/routes";
    }

    @ExceptionHandler(HttpClientErrorException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleRouteNotFound404(HttpClientErrorException e, Model model) {
        String notFoundCities = e.getMessage().replace("404 :","");
        model.addAttribute("errormessage", notFoundCities);
        model.addAttribute("notfound", true);
        return "/pages/trainroutes/routes";
    }

    @PostMapping("/tickets/buy")
    public String buyTicket(@RequestBody String data, @RequestParam String trainNo, Model model) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<TicketsDTO> requestEntity = new HttpEntity<>(new TicketsDTO(), headers);
        String url = "http://localhost:8082//v1//users//" + getUserId() + "//buy_tickets?trainNo=" + trainNo;
        restTemplate.postForObject(url, requestEntity, TicketsDTO.class);
        return "redirect:/tickets";
    }

    @GetMapping("/tickets")
    public String getAllTickets(Model model) {
        ResponseEntity<TicketsDTO> ticketDTOResponseEntity = new ResponseEntity<>(
                restTemplate.getForObject(
                        "http://localhost:8082//v1//users//" + getUserId() + "//get_tickets", TicketsDTO.class)
                , HttpStatus.OK);
        model.addAttribute("tickets", ticketDTOResponseEntity.getBody().getTicketsDTO());
        return "/pages/trainroutes/tickets";

    }

    private Integer getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        User userPojo = userRepository.findByUsername(currentUserName);
        if (userPojo != null) {
            return userPojo.getId();
        } else {
            throw new RuntimeException("UserId getting error");
        }
    }
}
