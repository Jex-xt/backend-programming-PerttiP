package backend.huittmp.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JasenRooliRepository extends JpaRepository<JasenRooli, Long> {

        List<JasenRooli> findByJasenId(Long jasenId);
}