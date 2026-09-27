package backend.bookstore;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration 
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests( authorize -> authorize // http pyyntöjen käyttöoikeuksien määritys
            .requestMatchers("/delete/**").hasRole("ADMIN") //delete vaatii afmin oikeudet
            .anyRequest().authenticated())  //Kaikki pyynnöt vaatii kirjautumisen
        .formLogin(formlogin -> formlogin
            .loginPage("/login") 
            .defaultSuccessUrl("/booklist", true) //Onnistunut login ohjaa/booklist sivulle
            .permitAll()
    );
        return http.build();
        }

    @Bean
    public UserDetailsService userDetailsService() {
        
        List<UserDetails> users = new ArrayList<>();
        
        UserDetails user1 = User.withDefaultPasswordEncoder()
            .username("admin")
            .password("admin")
            .roles("ADMIN")
            .build();
            
        UserDetails user2 =  User.withDefaultPasswordEncoder()
            .username("user")
            .password("user")
            .roles("USER")
            .build();

        users.add(user1);
        users.add(user2);
        return new InMemoryUserDetailsManager(users);
        }    
    }
