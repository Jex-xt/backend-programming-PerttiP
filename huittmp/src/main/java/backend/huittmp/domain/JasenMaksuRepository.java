package backend.huittmp.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface JasenMaksuRepository extends CrudRepository<JasenMaksu, Long> {

        List<JasenMaksu> findByJasenId(Long jasenId);
        List<JasenMaksu> findTop7ByJasenIdOrderByIdDesc(Long jasenId);
}
