package backend.huittmp;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import backend.huittmp.domain.Jasen;
import backend.huittmp.domain.JasenMaksu;
import backend.huittmp.domain.JasenMaksuRepository;
import backend.huittmp.domain.JasenRepository;
import backend.huittmp.domain.JasenRooli;
import backend.huittmp.domain.JasenRooliRepository;
import backend.huittmp.domain.Maksu;
import backend.huittmp.domain.MaksuRepository;
import backend.huittmp.domain.Rooli;
import backend.huittmp.domain.RooliRepository;

/**
 * HuittMP-sovelluksen JPA-repositoryjen JUnit-testiluokka.
 * Testaa tietokannan CRUD-toimintoja ja JPA-entiteettien välisiä suhteita.
 * Testit suoritetaan H2-testitietokannalla.
 */

@DataJpaTest // Testaa JPA-entiteettejä ja repositoryjen tietokantatoimintoja
@ActiveProfiles("test")  //H2-testitietokannan asetukset: src\test\resources\application-test.properties
public class RepositoryTests {

    @Autowired
	private JasenMaksuRepository jasenMaksuRepository;

	@Autowired
	private JasenRepository jasenRepository;

	@Autowired
	private JasenRooliRepository jasenRooliRepository;

	@Autowired
	private MaksuRepository maksuRepository;

	@Autowired
	private RooliRepository rooliRepository;

    // Testataan löytyykö testikannan jasenet taulusta jasen sähköpostiosoitteella.
    @Test
    public void etsiJasenEmaililla() {
        Optional<Jasen> jasen = jasenRepository.findByEmail("matti@example.fi");
        assertThat(jasen).isPresent();
        assertThat(jasen.get().getEtunimi()).isEqualTo("Matti");
        assertThat(jasen.get().getSukunimi()).isEqualTo("Meikäläinen");
    }

    // Testataan löytyykö maksut taulusta ID:llä 2 Korjaamomaksu.
    @Test
    public void etsiMaksu() {
        Maksu maksu = maksuRepository.findById(2L).orElse(null);
        assertThat(maksu).isNotNull();
        assertThat(maksu.getMaksuNimi()).isEqualTo("Korjaamomaksu");
    }

    // Testataan löytyykö roolit taulusta ID:llä 1 Puheenjohtaja.
    @Test
    public void etsiRooli() {
        Rooli rooli = rooliRepository.findById(1L).orElse(null);
        assertThat(rooli).isNotNull();
        assertThat(rooli.getRooliNimi()).isEqualTo("Puheenjohtaja");
        }
    
     // Testataan kuinka monta roolia löytyy jasen_roolit taulun historiasta rooli ID:llä 3.
    @Test
        public void etsiJasenRoolit() {
        List<JasenRooli> roolit = jasenRooliRepository.findByRooliId(3L);
        assertThat(roolit).hasSize(2);
        }

    // Testataan kuinka monta maksua löytyy jasen_maksut taulun historiasta jäsen ID:llä 1.
    @Test
        public void etsiJasenMaksut() {
        List<JasenMaksu> maksut = jasenMaksuRepository.findByJasenId(1L);
        assertThat(maksut).hasSize(3);
        }
    /**
     * Testataan, että jäsenen maksumerkinnät löytyvät OneToMany-suhteen kautta.
     * Yhdellä Jasen-entiteetillä voi olla monta JasenMaksu-entiteettiä.
     * Suhde on määritelty Jasen-entiteetissä.
     */
    @Test
        public void testaaJasenOneToMany() {
        Jasen jasen = jasenRepository.findById(3L).orElse(null);
        assertThat(jasen).isNotNull();
        assertThat(jasen.getEtunimi()).isEqualTo("Esa");
        assertThat(jasen.getJasenMaksut()).hasSize(3);
        }

    /**
     * Testataan, että jäsenen roolimerkinnät löytyvät ManyToOne-suhteen kautta.
     * Yhdellä Jasen-entiteetillä voi olla monta JasenRooli-entiteettiä.
     * Suhde on määritelty JasenRooli-entiteetissä.
     */
    @Test
        public void testaaJasenRooliManyToOne() {
        JasenRooli jasenRooli = jasenRooliRepository.findById(1L).orElse(null);

        assertThat(jasenRooli).isNotNull();
        assertThat(jasenRooli.getJasen().getEtunimi()).isEqualTo("Matti");
        assertThat(jasenRooli.getRooli().getRooliNimi()).isEqualTo("Puheenjohtaja");
    } 

    /**
     * Testataan, että jäsenen maksumerkinnät löytyvät ManyToOne-suhteen kautta.
     * Yhdellä Jasen-entiteetillä voi olla monta JasenMaksu-entiteettiä.
     * Suhde on määritelty JasenMaksu-entiteetissä.
     */
    @Test
    public void testaaJasenMaksuManyToOne() {
        JasenMaksu jasenMaksu = jasenMaksuRepository.findById(1L).orElse(null);

        assertThat(jasenMaksu).isNotNull();
        assertThat(jasenMaksu.getJasen().getEtunimi()).isEqualTo("Matti");
        assertThat(jasenMaksu.getMaksu().getMaksuNimi()).isEqualTo("Jäsenmaksu");
    }

    // Testataan uuden jäsenen luonti jasenet tauluun    
    @Test 
        public void luoJasen() {
            Jasen jasen = new Jasen();
            jasen.setEtunimi("Testi");
            jasen.setSukunimi("Testaaja");
            jasen.setEmail("testi@example.fi");
            jasen.setPuhelinnumero("0401234566");
            jasen.setLiittymispaiva(LocalDate.of(2026, 10, 15));

            Jasen uusi = jasenRepository.save(jasen);
            assertThat(uusi.getId()).isNotNull();
            assertThat(uusi.getSukunimi()).isEqualTo("Testaaja");
            assertThat(jasenRepository.findByEmail("testi@example.fi")).isPresent();
        }
        
    // Testataan olemassa olevan jäsenen sukunimen muuttaminen.
    @Test
    public void muokkaaJasen() {
        Jasen jasen = jasenRepository.findById(4L).orElse(null);
        assertThat(jasen).isNotNull();
        assertThat(jasen.getSukunimi()).isEqualTo("Kopteri");

        jasen.setSukunimi("Hopperi");
        jasenRepository.save(jasen);
        assertThat(jasen.getSukunimi()).isEqualTo("Hopperi");
    }

    // Testataan luodun jäsenen poistaminen.
    @Test
    public void poistaJasen() {
        Jasen jasen = new Jasen();
        jasen.setEtunimi("Testi");
        jasen.setSukunimi("Poistaja");
        jasen.setEmail("poisto@example.fi");
        jasen.setPuhelinnumero("0401234567");
        jasen.setLiittymispaiva(LocalDate.now());

        Jasen uusi = jasenRepository.save(jasen);
        assertThat(uusi.getId()).isNotNull();

        jasenRepository.deleteById(uusi.getId());
        assertThat(jasenRepository.findById(uusi.getId())).isEmpty();
        }
  
}
