package sm.security.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import sm.security.login.user.CustomUserDetailsService;
import sm.security.login.user.User;
import sm.security.login.user.UserRepository;

import java.util.List;

@SpringBootApplication
public class App implements CommandLineRunner {

//    @Autowired
//    PasswordEncoder passwordEncoder;

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Autowired
    private UserRepository repository;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        repository.saveAll(List.of(
                new User("min", passwordEncoder().encode("min"), Role.USER),
                new User("john",  passwordEncoder().encode("depp"), Role.USER),
                new User("leg",  passwordEncoder().encode("leg"), Role.ADMIN),
                new User("mark",  passwordEncoder().encode("ive"), Role.USER)
        ));

    }


}
