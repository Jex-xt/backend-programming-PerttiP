package backend.huittmp;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;

//SecurityTests-luokka testaa käyttäjien kirjautumista ja käyttöoikeuksien toimivuutta.
//Testit suoritetaan H2-testitietokannalla
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class SecurityTests {

    @Autowired
    private MockMvc mockMvc;

    //Testataan user-käyttäjän kirjautuminen oikealla salasanalla
    @Test
        public void userLogin() throws Exception {
            mockMvc.perform(formLogin()
                .user("user@example.fi")
                .password("user"))
                .andExpect(authenticated());
        }

    //Testataan user-käyttäjän kirjautuminen väärällä salasanalla
    @Test
        public void userLoginFalsePass() throws Exception {
            mockMvc.perform(formLogin()
                .user("user@example.fi")
                .password("admin"))
                .andExpect(unauthenticated());
        }  

    // Testataan ADMIN-käyttäjän kirjautuminen oikealla salasanalla.
    @Test
        public void adminLogin() throws Exception {
            mockMvc.perform(formLogin()
                .user("admin@example.fi")
                .password("admin"))
                .andExpect(authenticated());
        }

    //Testataan user-käyttäjän pääsyn estäminen admin sivustoille
    @Test
        public void userEiPaaseAdminSivulle() throws Exception {
            mockMvc.perform(get("/admin")
                .with(user("user@example.fi").roles("USER")))
                .andExpect(status().isForbidden());
        }

     // Testataan admin-käyttäjän pääsy ylläpitosivulle.
    @Test
    public void adminPaaseeAdminSivulle() throws Exception {
        mockMvc.perform(get("/admin")
            .with(user("admin@example.fi").roles("ADMIN")))
            .andExpect(status().isOk());
    }
    
    // Testataan user-käyttäjän pääsy omalle sivulle.
    @Test
    public void userPaaseeUserSivulle() throws Exception {
        mockMvc.perform(get("/user")
            .with(user("user@example.fi").roles("USER")))
            .andExpect(status().isOk());
    }


}
