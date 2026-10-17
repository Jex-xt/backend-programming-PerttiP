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

//AdminController-luokka käsittelee ylläpitäjien(Admin) ja käyttäjien http pyyntöjä ja käyttäjien ohjausta oikeisiin Thymeleaf näkymiin

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

    //Näyttää login.html-kirjautumissivun.
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    //Ohjaa kirjautuneen käyttäjän ADMIN tai USER sivulle
    @GetMapping("/aloitus")
    public String aloitus(Authentication authentication) {

    if (authentication.getAuthorities().contains(
        new SimpleGrantedAuthority("ROLE_ADMIN"))) {
        return "redirect:/admin";
    }
    return "redirect:/user";
    }

    //Hakee jäsenet järjestettynä aakkosjärjestykseen ja näyttää admin.html-sivun
    @GetMapping("/admin")
    public String admin(Model model) {
        model.addAttribute("jasenet",jasenRepository.findAllByOrderBySukunimiAscEtunimiAsc());
        return "admin";
    }

    //Hakee jäsenen ID:n mukaan jäsenen tiedot, roolit ja maksumerkinnät ja näyttää jasen.html-sivun
    @GetMapping("/admin/jasen/{id}")
    public String naytajasen(@PathVariable ("id") Long jasenId, Model model) {
        model.addAttribute("jasen",jasenRepository.findById(jasenId).get());

        model.addAttribute("jasenRoolit",jasenRooliRepository.findByJasenId(jasenId));

        model.addAttribute("jasenMaksut",jasenMaksuRepository.findByJasenId(jasenId));
        return "jasen";
    }
    //Luo tyyhjän jasen-olion näyttää lisaa.html-sivun
    @GetMapping("/admin/lisaa")
    public String lisaaUusiJasen(Model model) {
        model.addAttribute("jasen", new Jasen());
        return "lisaa";
    }
    //Vastaan ottaa syötetyt uuden jäsenen tiedot ja tarkistaa syötetyt tiedot. Jos tiedot ovat oiken lisää uuden jäsenen
    @PostMapping("/admin/lisaa")
    public String tallennaUusiJasen(@Valid @ModelAttribute ("jasen") Jasen tiedotJasen, BindingResult bindingResult) {


        if (tiedotJasen.getSalasana() == null || tiedotJasen.getSalasana().length() < 5) {
            bindingResult.rejectValue("salasana", "salasana.lyhyt","Salasanassa pitää olla vähintään 5 merkkiä!");
        }

        //jos jo suoritetuissa tarkastuksissa on virheitä, palautetaan lisaa.html
        if (bindingResult.hasErrors()) {
        return "lisaa";
        }
        //Tarkistaa onko email jo käytössä, jos on palauttaa lisaa.html
        if (jasenRepository.findByEmail(tiedotJasen.getEmail()).isPresent()){ //Tarkistaa onko email jo käytössä, jos on palauttaa lisaa.html
            bindingResult.rejectValue("email", "email.olemassa ","Sähköpostiosoite on jo käytössä!");
            return "lisaa";
        }
       
        //Lisää uuden jäsenen, salaa salasanan ja varmistaa jäsenelle oletuksena USER-käyttöoikeudet
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
    //Hakee jäsenen ID:n mukaan vain jäsenen perustiedot. Roolit ja jäsenmaksut eivät ole muokattavissa tässä sovellus versiossa. 
    @GetMapping("/admin/muokkaa/{id}")
    public String muokkaaJasen(@PathVariable ("id")Long jasenId, Model model) {
        model.addAttribute("jasen",jasenRepository.findById(jasenId).get());
         
        //model.addAttribute("jasenRoolit", jasenRooliRepository.findByJasenId(jasenId));
        //model.addAttribute("jasenMaksut", jasenMaksuRepository.findByJasenId(jasenId));
        return "muokkaa";
    }

    //Vastaan ottaa syötetyt muokatun jäsenen tiedot ja tarkistaa syötetyt tiedot. Jos tiedot ovat oikein päivittää muokatut tiedot ID:n perusteella.
    //@RequestParam vastaanottaa uuden salasanan erikseen, jolloin vanha salasana voidaan halutessa säilyttää.
    @PostMapping("/admin/muokkaa/{id}")
    public String tallennaMuokkaaJasen(@PathVariable("id") Long jasenID,@Valid  @ModelAttribute ("jasen") Jasen tiedotJasen, BindingResult bindingResult, @RequestParam("uusiSalasana") String uusiSalasana){ 

        //Tarkistaa salasana kenttään on kirjoitettu, mutta pituus on alle alle 5 merkkiä lisätään virhe BindingResult-olioon
        if (uusiSalasana != null && !uusiSalasana.isBlank() && uusiSalasana.length() < 5) {
            bindingResult.rejectValue("salasana", "salasana.lyhyt","Salasanassa pitää olla vähintään 5 merkkiä!");
        }
        //jos jo suoritetuissa tarkastuksissa on virheitä, palautetaan muokkaa.html
        if (bindingResult.hasErrors()) { 
        return "muokkaa";
        }

        //Tarkistaa ettei syötetty email ole jo toisen jäsenen käytössä. Saman email osoiteen syöttö sallitaan samalla jäsen ID:llä.
        Jasen samaEmail = jasenRepository.findByEmail(tiedotJasen.getEmail()).orElse(null);
        if (samaEmail != null && !samaEmail.getId().equals(jasenID)) {
            bindingResult.rejectValue("email", "email.kaytossa", "Sähköpostiosoite on jo käytössä!"); tiedotJasen.setId(jasenID);
            return "muokkaa";
         }

        //Luodaan Jasen muuttuja ja sijoitetaan siihen haettu jäsenolio
        Jasen muokattavaJasen = jasenRepository.findById(jasenID).get();
        // Päivittää tietokannasta haetun jäsenen tiedot lomakkeeseen syötetyillä arvoilla
        muokattavaJasen.setEtunimi(tiedotJasen.getEtunimi());
        muokattavaJasen.setSukunimi(tiedotJasen.getSukunimi());
        muokattavaJasen.setEmail(tiedotJasen.getEmail());
        muokattavaJasen.setPuhelinnumero(tiedotJasen.getPuhelinnumero());
        muokattavaJasen.setLiittymispaiva(tiedotJasen.getLiittymispaiva());
        muokattavaJasen.setYllapitaja(tiedotJasen.isYllapitaja());  //Käytetään is getteriä koska kyseessä boolean

        //Tallenttaa myös uuden salasanan salattuna, jos se on myös syötetty.
        if (uusiSalasana != null && !uusiSalasana.isBlank()) {  
            muokattavaJasen.setSalasana(passwordEncoder.encode(uusiSalasana));
        }
        //Tallennetaan muokattu jäsen tietokantaan ja ohjataan admin.html sivulle
        jasenRepository.save(muokattavaJasen);
        return "redirect:/admin";
    }
    //Vastaan ottaa poistettavan jäsen ID: perusteella. Poistaa ensin jäseneen liittyvät tiedot muista tauluista ja lopulta jäsenen tietokannasta
    @PostMapping("/admin/poista/{id}")
    @Transactional
    public String poistaJasen(@PathVariable ("id")Long jasenId) {

        jasenRooliRepository.deleteAll(jasenRooliRepository.findByJasenId(jasenId));

        jasenMaksuRepository.deleteAll(jasenMaksuRepository.findByJasenId(jasenId));

        jasenRepository.deleteById(jasenId);
        return "redirect:/admin";
    }
    
}

