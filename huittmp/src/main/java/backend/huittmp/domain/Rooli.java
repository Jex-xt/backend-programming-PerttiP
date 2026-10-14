package backend.huittmp.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

/**
 * Yhdistyksen jäsenten roolit, kuten puheenjohtaja, sihteeri ja rahastonhoitaja.
 * Vastaa tietokannan roolit-taulua.
 */
@Entity 
@Table(name = "roolit") //Tiedot ovat roolit taulussa
public class Rooli {

    @Id //Pääavain
    @GeneratedValue(strategy = GenerationType.IDENTITY) //id luodaan automaattisesti
    @Column(name = "rooli_id", nullable = false, updatable = false)
    private Long id;

    @NotBlank(message = "Syötä roolin nimi")
    @Column(name = "rooli_nimi", nullable = false, unique = true)
    private String rooliNimi;

    public Rooli () {   //Parametriton konstrukstori JPA varten
    }
    
    public Rooli(String rooliNimi) {    //Parametrillinen konstruktori
        this.rooliNimi = rooliNimi;
    }

    public Long getId() {
        return id;
    }

    public String getRooliNimi() {
        return rooliNimi;
    }

    public void setRooliNimi(String rooliNimi) {
        this.rooliNimi = rooliNimi;
    }
    
}

