package backend.huittmp.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface JasenRepository  extends CrudRepository <Jasen, Long>{

    List<Jasen> findAllByOrderBySukunimiAscEtunimiAsc();
    Optional<Jasen> findByEmail(String email);
}
