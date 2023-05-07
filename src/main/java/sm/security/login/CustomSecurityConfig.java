package sm.security.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
//@Order(1)
public class CustomSecurityConfig {


    @Autowired
    DaoAuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain filterChain1(HttpSecurity http) throws Exception {

        http.authenticationProvider(authenticationProvider);

        http
                .csrf().disable()
                .authorizeHttpRequests().requestMatchers("/error", "", "/", "/index", "/user/new", "/user/create").permitAll()
                .and()
                .authorizeHttpRequests().requestMatchers("/user/login", "user/home").hasAuthority("USER")
                .and()
                .authorizeHttpRequests().requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                .and()
                .authorizeHttpRequests().requestMatchers("/error","", "/trains","/trains/**").hasAnyAuthority( "USER")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/user/login")
                .usernameParameter("username")
                .loginProcessingUrl("/user/login")
                .defaultSuccessUrl("/trains")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/user/logout")
                .logoutSuccessUrl("/")
                .permitAll();

        return http.build();
    }

}
