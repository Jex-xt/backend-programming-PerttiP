package backend.huittmp.domain;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Yhdistää jäsenet yhdistyksen eri rooleihin.
 * Tallentaa jäsenen roolin sekä sen alkamis- ja päättymispäivän.
 * Vastaa tietokannan jasen_roolit-taulua.
 */
@Entity
@Table(name = "jasen_roolit")   //Tiedot ovat jasen_roolit taulussa.
public class JasenRooli {

    @Id //Pääavain
    @GeneratedValue(strategy = GenerationType.IDENTITY) //id luodaan automaattisesti
    @Column(name = "jasen_rooli_id", nullable = false, updatable = false)
    private Long id;

    @ManyToOne  //Useampi jasen_roolit-taulun merkintä voi viitata samaan Jasen olioon.
    @JoinColumn(name = "jasen_id", nullable = false)  //Vierasavain 
    private Jasen jasen; //Viitataan Jasen-entiteettiin

    @ManyToOne  //Useampi jasen_roolit-taulun merkintä voi viitata samaan Rooli olioon.
    @JoinColumn (name ="rooli_id", nullable = false)  //Vierasavain 
    private Rooli rooli; //Viitataan Rooli-entiteettiin

    @Column(name = "alkupaiva", nullable = false)
    private LocalDate alkupaiva;

    @Column(name = "loppupaiva")
    private LocalDate loppupaiva;

    public JasenRooli() {  // JPA:n tarvitsema parametriton konstruktori
    }

    public JasenRooli(Jasen jasen, Rooli rooli, LocalDate alkupaiva, LocalDate loppupaiva) {    //Parametrillinen konstruktori
        this.jasen = jasen;
        this.rooli = rooli;
        this.alkupaiva = alkupaiva;
        this.loppupaiva = loppupaiva;
    }

    public Long getId() {
        return id;
    }

    public Jasen getJasen() {
        return jasen;
    }

    public void setJasen(Jasen jasen) {
        this.jasen = jasen;
    }

    public Rooli getRooli() {
        return rooli;
    }

    public void setRooli(Rooli rooli) {
        this.rooli = rooli;
    }

    public LocalDate getAlkupaiva() {
        return alkupaiva;
    }

    public void setAlkupaiva(LocalDate alkupaiva) {
        this.alkupaiva = alkupaiva;
    }

    public LocalDate getLoppupaiva() {
        return loppupaiva;
    }

    public void setLoppupaiva(LocalDate loppupaiva) {
        this.loppupaiva = loppupaiva;
    } 

}
