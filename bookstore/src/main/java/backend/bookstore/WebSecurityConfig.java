package backend.bookstore;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration 
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests( authorize -> authorize // http pyyntöjen käyttöoikeuksien määritys
            .requestMatchers("/css/**").permitAll() //css toimii ennen kirjautumista
            .requestMatchers("/api/**").permitAll()
            .requestMatchers("/h2-console/**").permitAll()
            .requestMatchers("/delete/**").hasAuthority("ADMIN") //delete vaatii afmin oikeudet
            .anyRequest().authenticated())  //Kaikki pyynnöt vaatii kirjautumisen
            .httpBasic(Customizer.withDefaults())
            .headers(headers -> 
					headers.frameOptions(frameOptions -> frameOptions 
						.disable())) // for h2console
        .formLogin(formlogin -> formlogin
            .loginPage("/login") 
            .defaultSuccessUrl("/booklist", true) //Onnistunut login ohjaa/booklist sivulle
            .permitAll())
         .logout(logout -> logout.permitAll())   
         .csrf(csrf -> csrf.disable());   
        return http.build();
        }
    
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
}    
    /* @Bean
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
        }     */
    }
