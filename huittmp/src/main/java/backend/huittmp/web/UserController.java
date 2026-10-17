package backend.huittmp.web;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import backend.huittmp.domain.Jasen;
import backend.huittmp.domain.JasenMaksuRepository;
import backend.huittmp.domain.JasenRepository;
import backend.huittmp.domain.JasenRooliRepository;

//UserController-luokka hakee kirjautuneen käyttäjän tiedot ja välittää ne Thymeleaf-näkymään.

@Controller 
public class UserController {

    private final JasenRepository jasenRepository;
    private final JasenRooliRepository jasenRooliRepository;
    private final JasenMaksuRepository jasenMaksuRepository;

    public UserController(JasenRepository jasenRepository, JasenRooliRepository jasenRooliRepository, JasenMaksuRepository jasenMaksuRepository) {
        this.jasenRepository = jasenRepository;
        this.jasenRooliRepository = jasenRooliRepository;
        this.jasenMaksuRepository = jasenMaksuRepository;
    }

    //Hakee kirjautuneen käyttäjän tiedot, roolit ja 7 viimeisintä maksumerkintää ja näyttää user.html-sivun.
    @GetMapping("/user")
    public String user(Authentication authentication, Model model) {

        //Hakee kirjautuneen käyttäjän sähköpostiosoitteen Spring Securityltä.
        String loginEmail = authentication.getName();

        //Hakee käyttäjän tiedot emailin perusteella.
        Jasen jasen = jasenRepository.findByEmail(loginEmail).orElseThrow();

        //Välittää jäsenen perustiedot, roolit ja maksumerkinnät Thymeleafille.
        model.addAttribute("jasen", jasen);
        model.addAttribute("jasenRoolit",jasenRooliRepository.findByJasenId(jasen.getId()));
        model.addAttribute("jasenMaksut",jasenMaksuRepository.findTop7ByJasenIdOrderByIdDesc(jasen.getId()));
        return "user";
    }
}
