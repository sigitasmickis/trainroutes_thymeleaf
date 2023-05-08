package sm.security.login.train;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import sm.security.login.book.Book;
import sm.security.login.city.CitiesDTO;

import java.util.List;

@Controller
public class TrainController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    RestTemplate restTemplate;

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

//    @PostMapping("/trains/route")
//    public ModelAndView redirectRouteSearch(@RequestParam String cityFrom,
//                                            @RequestParam String cityTo,
//                                            HttpServletRequest request) {
//        return  new ModelAndView("redirect:/trains/route?cityFrom="
//                + cityFrom + "&to=" + cityTo);
//    }


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
            return "/pages/trainroutes/tickets";
    }

    @ExceptionHandler(HttpClientErrorException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleRouteNotFound404(HttpClientErrorException e, Model model) {
        String notFoundCities = e.getMessage().replace("404 :","");
        model.addAttribute("errormessage", notFoundCities);
        model.addAttribute("notfound", true);
        return "/pages/trainroutes/tickets";
    }
}
