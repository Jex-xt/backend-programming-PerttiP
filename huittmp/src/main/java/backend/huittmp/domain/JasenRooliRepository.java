package backend.huittmp.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

// JasenRooliRepository huolehtii jäsenten roolimerkintöjen tietokantahauista.
public interface JasenRooliRepository extends JpaRepository<JasenRooli, Long> {
        //Hakee jäsenen roolimerkinnät ja palauttaa ne listana.
        List<JasenRooli> findByJasenId(Long jasenId);

        //Hakee kaikki tiettyyn rooliin liittyvät merkinnät. Käytetään JPA-testauksessa.
        List<JasenRooli> findByRooliId(Long rooliId);
        
}