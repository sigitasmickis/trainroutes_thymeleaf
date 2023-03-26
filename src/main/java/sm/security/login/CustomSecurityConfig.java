package sm.security.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import sm.security.login.user.CustomUserDetailsService;

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
                .authorizeHttpRequests().requestMatchers("/error", "", "/", "/index", "/user/new", "/user/create", "h2-console/**").permitAll()
                .and()
                .authorizeHttpRequests().requestMatchers("/user/login", "user/home").hasAuthority("USER")
//                .and()
//                .authorizeHttpRequests().requestMatchers("/admin/**").hasAuthority("ADMIN")
                .and()
                .authorizeHttpRequests().requestMatchers("/home").hasAnyAuthority( "USER")
//                .and()
//                .authorizeHttpRequests().requestMatchers("/home/**", "/topics/**/edit").hasAnyAuthority("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/user/login")
                .usernameParameter("username")
                .loginProcessingUrl("/user/login")
                .defaultSuccessUrl("/index")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/user/logout")
                .logoutSuccessUrl("/")
                .permitAll();

        return http.build();
    }

}
