
package backend.huittmp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

import backend.huittmp.domain.JasenMaksuRepository;
import backend.huittmp.domain.JasenRepository;
import backend.huittmp.domain.JasenRooliRepository;
import backend.huittmp.domain.MaksuRepository;
import backend.huittmp.domain.RooliRepository;
import backend.huittmp.web.AdminController;
import backend.huittmp.web.RooliRestController;
import backend.huittmp.web.UserController;

/**
 * HuittMP-sovelluksen JUnit-testiluokka.
 * Testaa Spring Bootin käynnistymisen ja sovelluksen eri komponenttien alustamisen ilman virheitä.
 * Testit suoritetaan H2-testitietokannalla
 */

@SpringBootTest //Koko sovellus käynnistetään testausta varten
@ActiveProfiles("test")  //H2-testitietokannan asetukset: src\test\resources\application-test.properties
class HuittmpApplicationTests {


	// Injektoidaan Crud-repositoriot ja controlleri luokat 
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

	@Autowired
	private AdminController adminController;

	@Autowired
	private RooliRestController rooliRestController;

	@Autowired 
	private UserController userController;

	// Testaa Sprin boot:n käynnistymisen ilman virheitä
	@Test
	void contextLoads() {
	}


	// Testaa, että Spring Boot alustaa kaikki CRUD-repositoryt onnistuneesti.
	@Test
	void repositorytAlustus() {
    assertThat(jasenMaksuRepository).isNotNull();
    assertThat(jasenRepository).isNotNull();
    assertThat(jasenRooliRepository).isNotNull();
    assertThat(maksuRepository).isNotNull();
    assertThat(rooliRepository).isNotNull();
	}

	// Testaa, että Spring Boot alustaa kaikki controllerit onnistuneesti.
	@Test
	void controlleritAlustus() {
    assertThat(adminController).isNotNull();
    assertThat(rooliRestController).isNotNull();
    assertThat(userController).isNotNull();
	}

}
