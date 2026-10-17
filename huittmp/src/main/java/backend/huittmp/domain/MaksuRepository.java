package backend.huittmp.domain;

import org.springframework.data.repository.CrudRepository;

// MaksuRepository huolehtii maksutyyppien ja maksukausien tietokantakäsittelystä.
public interface MaksuRepository extends CrudRepository<Maksu, Long> {

}
