//admin@huittmp.test
//user@huittmp.test
package backend.huittmp.domain;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * Yhdistyksen jäsenen perustiedot ja käyttäjätunnuksen tiedot.
 * Vastaa tietokannan jasenet-taulua.
 */
@Entity //JPA entiteetti
@Table(name = "jasenet") //Tiedot ovat jasenet taulussa
public class Jasen {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) //id luodaan automaattisesti
    @Column(name = "jasen_id", nullable = false, updatable = false)
    private Long id;

    @NotBlank(message = "Syötä etunimi")
    @Size(max = 30, message = "Etunimi max 30 merkkiä")
    @Column(name = "etunimi", nullable = false)
    private String etunimi;

    @NotBlank(message = "Syötä sukunimi")
    @Size(max = 30, message = "Sukunimi max 30 merkkiä")
    @Column (name = "sukunimi", nullable = false)
    private String sukunimi;

    @NotBlank(message = "Syötä email")
    @Email(message = "Tarkista sähköpostiosoite")
    @Size(max = 50, message = "Sähköposti max 50 merkkiä")
    @Column (name = "email", nullable = false, unique = true)
    private String email;

    @NotBlank(message = "Syötä puhelinumero")
    @Size(max = 15, message = "Puhelinnumero max 15 merkkiä")
    @Column (name ="puhelinnumero", nullable = false)
    private String puhelinnumero;

    @NotNull(message = "Syötä liittymispäivä") 
    @PastOrPresent(message = "Liittymispäivä ei voi olla tulevaisuudessa")
    @Column (name = "liittymispaiva", nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) //// Muuntaa lomakkeen yyyy-MM-dd päivämäärän LocalDate-tyypiksi
    private LocalDate liittymispaiva;

    @Column(name = "salasana")
    private String salasana;

    @Column(name = "yllapitaja", nullable = false)
    private boolean yllapitaja;

    //OneToMany suhde hyödynnetään JUnit testissä
    @OneToMany(mappedBy = "jasen")    // Yhdellä jäsenellä voi olla useita maksumerkintöjä. Suhde määritelty JasenMaksu-luokan jasen-kentässä.
    private List<JasenMaksu> jasenMaksut;


    public Jasen () { //Parametriton konstrukstori JPA varten
    }

    public Jasen(String etunimi, String sukunimi, String email, String puhelinnumero, LocalDate liittymispaiva) { //Parametrillinen konstruktori
    this.etunimi = etunimi;
    this.sukunimi = sukunimi;
    this.email = email;
    this.puhelinnumero = puhelinnumero;
    this.liittymispaiva = liittymispaiva;
    }

    
    public Long getId() {
        return id;
    }

    public String getEtunimi() {
        return etunimi;
    }

    public void setEtunimi(String etunimi) {
        this.etunimi = etunimi;
    }

    public String getSukunimi() {
        return sukunimi;
    }

    public void setSukunimi(String sukunimi) {
        this.sukunimi = sukunimi;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPuhelinnumero() {
        return puhelinnumero;
    }

    public void setPuhelinnumero(String puhelinnumero) {
        this.puhelinnumero = puhelinnumero;
    }

    public LocalDate getLiittymispaiva() {
        return liittymispaiva;
    }

    public void setLiittymispaiva(LocalDate liittymispaiva) {
        this.liittymispaiva = liittymispaiva;
    }

    public String getSalasana() {
        return salasana;
    }

    public void setSalasana(String salasana) {
        this.salasana = salasana;
    }

    public boolean isYllapitaja() {
        return yllapitaja;
    }

    public void setYllapitaja(boolean yllapitaja) {
        this.yllapitaja = yllapitaja;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<JasenMaksu> getJasenMaksut() {
        return jasenMaksut;
    }

    public void setJasenMaksut(List<JasenMaksu> jasenMaksut) {
        this.jasenMaksut = jasenMaksut;
    }

    
 
}


