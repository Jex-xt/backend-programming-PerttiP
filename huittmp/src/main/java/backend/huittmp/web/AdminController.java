package backend.huittmp.web;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import backend.huittmp.domain.JasenMaksuRepository;
import backend.huittmp.domain.JasenRepository;
import backend.huittmp.domain.JasenRooliRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import backend.huittmp.domain.Jasen;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;




@Controller 
public class AdminController {

    private final JasenRepository jasenRepository;
    private final JasenRooliRepository jasenRooliRepository;
    private final JasenMaksuRepository jasenMaksuRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminController(JasenRepository jasenRepository, JasenRooliRepository jasenRooliRepository, JasenMaksuRepository jasenMaksuRepository, PasswordEncoder passwordEncoder) {
        this.jasenRepository = jasenRepository;
        this.jasenRooliRepository = jasenRooliRepository;
        this.jasenMaksuRepository = jasenMaksuRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/aloitus")
    public String aloitus(Authentication authentication) {

    if (authentication.getAuthorities().contains(
        new SimpleGrantedAuthority("ROLE_ADMIN"))) {
        return "redirect:/admin";
    }
    return "redirect:/user";
}

    @GetMapping("/admin")
    public String admin(Model model) {
        model.addAttribute("jasenet",jasenRepository.findAllByOrderBySukunimiAscEtunimiAsc());
        return "admin";
    }

    @GetMapping("/admin/jasen/{id}")
    public String naytajasen(@PathVariable ("id") Long jasenId, Model model) {
        model.addAttribute("jasen",jasenRepository.findById(jasenId).get());

        model.addAttribute("jasenRoolit",jasenRooliRepository.findByJasenId(jasenId));

        model.addAttribute("jasenMaksut",jasenMaksuRepository.findByJasenId(jasenId));
        return "jasen";
    }

    @GetMapping("/admin/lisaa")
    public String lisaaUusiJasen(Model model) {
        model.addAttribute("jasen", new Jasen());
        return "lisaa";
    }

    @PostMapping("/admin/lisaa")
    public String tallennaUusiJasen(@Valid @ModelAttribute ("jasen") Jasen tiedotJasen, BindingResult bindingResult) {

        if (tiedotJasen.getSalasana() == null || tiedotJasen.getSalasana().length() < 5) {
            bindingResult.rejectValue("salasana", "salasana.lyhyt","Salasanassa pitää olla vähintään 5 merkkiä!");
        }

        if (bindingResult.hasErrors()) {
        return "lisaa";
        }

        if (jasenRepository.findByEmail(tiedotJasen.getEmail()).isPresent()){ //Tarkistaa onko email jo käytössä, jos on palauttaa lisaa.html
            bindingResult.rejectValue("email", "email.olemassa ","Sähköpostiosoite on jo käytössä!");
            return "lisaa";
        }
       
        Jasen uusiJasen = new Jasen();
        uusiJasen.setEtunimi(tiedotJasen.getEtunimi());
        uusiJasen.setSukunimi(tiedotJasen.getSukunimi());
        uusiJasen.setEmail(tiedotJasen.getEmail());
        uusiJasen.setPuhelinnumero(tiedotJasen.getPuhelinnumero());
        uusiJasen.setLiittymispaiva(tiedotJasen.getLiittymispaiva());
        uusiJasen.setSalasana(passwordEncoder.encode(tiedotJasen.getSalasana()));
        uusiJasen.setYllapitaja(false);
        
        jasenRepository.save(uusiJasen);
        return "redirect:/admin";
    }

    @GetMapping("/admin/muokkaa/{id}")
    public String muokkaaJasen(@PathVariable ("id")Long jasenId, Model model) {
        model.addAttribute("jasen",jasenRepository.findById(jasenId).get());
         
        //model.addAttribute("jasenRoolit", jasenRooliRepository.findByJasenId(jasenId));
        //model.addAttribute("jasenMaksut", jasenMaksuRepository.findByJasenId(jasenId));
        return "muokkaa";
    }
    
    @PostMapping("/admin/muokkaa/{id}")
    public String tallennaMuokkaaJasen(@PathVariable("id") Long jasenID,@Valid  @ModelAttribute ("jasen") Jasen tiedotJasen, BindingResult bindingResult, @RequestParam("uusiSalasana") String uusiSalasana){

        if (uusiSalasana != null && !uusiSalasana.isBlank() && uusiSalasana.length() < 5) {
            bindingResult.rejectValue("salasana", "salasana.lyhyt","Salasanassa pitää olla vähintään 5 merkkiä!");
        }
       
        if (bindingResult.hasErrors()) { 
        return "muokkaa";
        }

        Jasen samaEmail = jasenRepository.findByEmail(tiedotJasen.getEmail()).orElse(null);
        if (samaEmail != null && !samaEmail.getId().equals(jasenID)) {
            bindingResult.rejectValue("email", "email.kaytossa", "Sähköpostiosoite on jo käytössä!"); tiedotJasen.setId(jasenID);
            return "muokkaa";
         }

        Jasen muokattavaJasen = jasenRepository.findById(jasenID).get();

        muokattavaJasen.setEtunimi(tiedotJasen.getEtunimi());
        muokattavaJasen.setSukunimi(tiedotJasen.getSukunimi());
        muokattavaJasen.setEmail(tiedotJasen.getEmail());
        muokattavaJasen.setPuhelinnumero(tiedotJasen.getPuhelinnumero());
        muokattavaJasen.setLiittymispaiva(tiedotJasen.getLiittymispaiva());
        muokattavaJasen.setYllapitaja(tiedotJasen.isYllapitaja());  //Käytetään is getteriä koska kyseessä boolean

        if (uusiSalasana != null && !uusiSalasana.isBlank()) {                  //Tallenttaa myös uuden salasanan, jos se on myös syötetty ja arvo ei ole null tai blank.
            muokattavaJasen.setSalasana(passwordEncoder.encode(uusiSalasana));
        }

        jasenRepository.save(muokattavaJasen);
        return "redirect:/admin";
    }

    @PostMapping("/admin/poista/{id}")
    @Transactional
    public String poistaJasen(@PathVariable ("id")Long jasenId) {

        jasenRooliRepository.deleteAll(jasenRooliRepository.findByJasenId(jasenId));

        jasenMaksuRepository.deleteAll(jasenMaksuRepository.findByJasenId(jasenId));

        jasenRepository.deleteById(jasenId);
        return "redirect:/admin";
    }
    
    @GetMapping("/login")
    public String login() {
        return "login";
    }

}

