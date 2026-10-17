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

// RooliRestController tarjoaa http-pyynöille REST-rajapinnan roolien hakemiseen, lisäämiseen, muokkaamiseen ja poistamiseen

@RestController 
public class RooliRestController {

    private final RooliRepository rooliRepository;

    //RooliRepository käyttöön kostruktorin kautta
    public RooliRestController (RooliRepository rooliRepository ) {
        this.rooliRepository = rooliRepository;
    }
        //Haetaan kaikki roolit tietokannasta
        @GetMapping("/api/roolit")
        public Iterable<Rooli> getRoolit() {
        return rooliRepository.findAll();
        }

        //Haetaan yksittäinen rooli ID:n perusteella
        @GetMapping("/api/roolit/{id}")
        public Optional<Rooli> getRooli(@PathVariable("id") Long id) {
        return rooliRepository.findById(id);
        }

        //Lisätään uusi rooli tietokantaan ja palauttaa lopuksi kaikki roolit
        @PostMapping("/api/roolit")
        public Iterable<Rooli> addRooli(@Valid @RequestBody Rooli rooli) {
            rooliRepository.save(rooli);
            return rooliRepository.findAll();
        }

        //Haetaan rooli ID:n perusteella, sekä nimi muokataan ja tallennetaan tietokantaan
        @PutMapping("/api/roolit/{id}")
        public Rooli muokkaaRooli(@Valid @RequestBody Rooli muokattuRooli, @PathVariable("id") Long id) {
            Rooli rooli = rooliRepository.findById(id).orElseThrow();
            rooli.setRooliNimi(muokattuRooli.getRooliNimi());
			return rooliRepository.save(rooli);
		}

        //Poistetaan rooli ID:n perusteella ja palauttaa lopuksi kaikki roolit
        @DeleteMapping("/api/roolit/{id}")
        public Iterable<Rooli>poistaRooli(@PathVariable("id") Long rooliId) {
            rooliRepository.deleteById(rooliId);
			return rooliRepository.findAll();
        }    

}
