package backend.huittmp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//WebSecurityConfig-luokka Määrittää sovelluksen kirjautumisen, käyttöoikeudet ja tietoturva-asetukset.
@Configuration
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(authorize -> authorize
            .requestMatchers("/css/**").permitAll() //css tiedostot sallitaan kaikille
            .requestMatchers("/admin/**").hasRole("ADMIN") //admin alkuiset osoitteet vaativat ADMIN käyttöikeuden
            .requestMatchers("/api/**").hasRole("ADMIN") //REST pyynnöt vaativat ADMIN kirjautumisen
            .requestMatchers("/user", "/user/**").hasAnyRole("USER", "ADMIN") //user-osoitteet vaativat joko USER tai ADMIn käyttöoikeuden
            .anyRequest().authenticated())

            .csrf(csrf -> csrf.ignoringRequestMatchers("/api/**"))  //Ohittaa CSRF tarkistuksen REST rajapinnassa POST, PUT ja DELETE.  
            .formLogin(form -> form
                .loginPage("/login") //Määrittää kirjautumissivun osoitteen
                .defaultSuccessUrl("/aloitus", true)    //Ohjaa käyttäjän onnistunee kirjautumisen jälkeen aloitus /aloitus metodiin
                .permitAll())
                
            .logout(logout -> logout.permitAll());  //Sallitaan uloskirjautuminen

        return http.build();
    }
    //Salasanojen käsittelyssä käytetään BCrypt-algoritmia
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
