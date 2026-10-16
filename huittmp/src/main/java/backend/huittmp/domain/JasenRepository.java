package backend.huittmp.domain;

import java.util.List;
import java.util.Optional;

//JasenRepository huolehtii jäsenten tietokantahauista
import org.springframework.data.repository.CrudRepository;

public interface JasenRepository  extends CrudRepository <Jasen, Long>{
    //Hakee kaikki jäsenet sukunimen ja etunimen mukaan aakkosjärjestyksessä
    List<Jasen> findAllByOrderBySukunimiAscEtunimiAsc();

    //Hakee jäsenen emailin perusteella. Käytetään kirjautumisessa
    Optional<Jasen> findByEmail(String email);
}
