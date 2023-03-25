package sm.security.login.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import sm.security.login.Role;

@Controller
public class UsersController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UserRepository repository;

    @GetMapping("/user/new")
    public String createNew(Model model) {
//        logger.info("form for new user submitted");
        model.addAttribute("user", new User());
        return "user/new";
    }

    @PostMapping("/user/create")
    public String createNewUser(User user) {

        user.setRole(Role.USER);
        User saveduser = repository.save(user);
        logger.info("new user created: {}", saveduser);
        return "redirect:/user/login";

    }

    @GetMapping("/user/{id}/view")
    public String showUser(@PathVariable Integer id, Model model) {
        model.addAttribute("user", repository.findById(id).get());
        return "user/user";
    }
}
