package backend.huittmp.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

//JasenMaksuRepository huolehtii maksumerkintöjen tietokantahauista
public interface JasenMaksuRepository extends CrudRepository<JasenMaksu, Long> {
        //Hakee kaikki jäsenen maksumerkinnät ja palauttaa listan.
        List<JasenMaksu> findByJasenId(Long jasenId);
        
        //Hakee jäsenen 7 uusinta maksumerkintää ja palautaa listan .
        List<JasenMaksu> findTop7ByJasenIdOrderByIdDesc(Long jasenId);
}
