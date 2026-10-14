package backend.huittmp.web;

import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import backend.huittmp.domain.Rooli;
import backend.huittmp.domain.RooliRepository;
import jakarta.validation.Valid;

@RestController 
public class RooliRestController {

    private final RooliRepository rooliRepository;

    public RooliRestController (RooliRepository rooliRepository ) {
        this.rooliRepository = rooliRepository;
    }

        @GetMapping("/api/roolit")
        public Iterable<Rooli> getRoolit() {
        return rooliRepository.findAll();
        }

        @GetMapping("/api/roolit/{id}")
        public Optional<Rooli> getRooli(@PathVariable("id") Long id) {
        return rooliRepository.findById(id);
}
        @PostMapping("/api/roolit")
        public Iterable<Rooli> addRooli(@Valid @RequestBody Rooli rooli) {
            rooliRepository.save(rooli);
            return rooliRepository.findAll();
        }

       @PutMapping("/api/roolit/{id}")
        public Rooli muokkaaRooli(@Valid @RequestBody Rooli muokattuRooli, @PathVariable("id") Long id) {
            Rooli rooli = rooliRepository.findById(id).orElseThrow();
            rooli.setRooliNimi(muokattuRooli.getRooliNimi());
			return rooliRepository.save(rooli);
		}

        @DeleteMapping("/api/roolit/{id}")
        public Iterable<Rooli>poistaRooli(@PathVariable("id") Long rooliId) {
            rooliRepository.deleteById(rooliId);
			return rooliRepository.findAll();
        }    

}
