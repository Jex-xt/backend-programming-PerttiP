package backend.huittmp.web;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import backend.huittmp.domain.Jasen;
import backend.huittmp.domain.JasenMaksuRepository;
import backend.huittmp.domain.JasenRepository;
import backend.huittmp.domain.JasenRooliRepository;

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


    @GetMapping("/user")
    public String user(Authentication authentication, Model model) {

        String loginEmail = authentication.getName();

        Jasen jasen = jasenRepository.findByEmail(loginEmail).orElseThrow();
        
        model.addAttribute("jasen", jasen);
        model.addAttribute("jasenRoolit",jasenRooliRepository.findByJasenId(jasen.getId()));
        model.addAttribute("jasenMaksut",jasenMaksuRepository.findTop7ByJasenIdOrderByIdDesc(jasen.getId()));
        return "user";
    }
}
