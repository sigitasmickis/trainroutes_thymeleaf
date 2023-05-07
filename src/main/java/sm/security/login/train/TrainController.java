package sm.security.login.train;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import sm.security.login.book.Book;

import java.util.List;

@Controller
public class TrainController {

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/trains")
    public String trains(Model model) {
        ResponseEntity<TrainsDTO> trainsDTO = new ResponseEntity<>(
                restTemplate.getForObject("http://localhost:8082//v1/trains", TrainsDTO.class)
                , HttpStatus.OK);
        model.addAttribute("trains", trainsDTO.getBody().getTrainsDTO());
        return "/pages/trainroutes/index";
    }
}
