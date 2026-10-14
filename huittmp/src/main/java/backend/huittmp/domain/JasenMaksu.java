package backend.huittmp.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

/**
 * Yhdistää jäsenet yhdistyksen vuosittaisiin ja kausittaisiin maksuihin.
 * Tallentaa jäsenkohtaisen maksutilanteen ja mahdollisen talvisäilytys pyörien määrän.
 * Vastaa tietokannan jasen_maksut-taulua.
 */
@Entity
@Table(name = "jasen_maksut", uniqueConstraints = @UniqueConstraint (columnNames = {"jasen_id", "maksu_id"}))   //Tiedot ovat jasen_maksut taulussa. jasen_id ja maksu_id voivat olla identtinen pari vain kerran myös JPA:n luodessa taulut. 
public class JasenMaksu {

    @Id //Pääavain
    @GeneratedValue(strategy = GenerationType.IDENTITY) //id luodaan automaattisesti
    @Column(name = "jasen_maksu_id", nullable = false, updatable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "jasen_id", nullable = false) //Vierasavain
    private Jasen jasen; //Viitataan Jasen-entiteettiin

    @ManyToOne
    @JoinColumn(name = "maksu_id", nullable = false) //Vierasavain
    private Maksu maksu; //Viitataan Maksu-entiteettiin

    @Column(name = "maksettu", nullable = false)
    private boolean maksettu;

    @Column(name= "pyorien_maara") // Pyörien määrä voi olla null tai 0
    private Integer pyorienMaara;   

    public JasenMaksu() {    // JPA:n tarvitsema parametriton konstruktori
    }

    public JasenMaksu (Jasen jasen, Maksu maksu,boolean maksettu, Integer pyorienMaara) {   //Parametrillinen konstruktori
        this.jasen = jasen;
        this.maksu = maksu;
        this.maksettu = maksettu;
        this.pyorienMaara = pyorienMaara;
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

    public Maksu getMaksu() {
        return maksu;
    }

    public void setMaksu(Maksu maksu) {
        this.maksu = maksu;
    }

    public boolean isMaksettu() {
        return maksettu;
    }

    public void setMaksettu(boolean maksettu) {
        this.maksettu = maksettu;
    }

    public Integer getPyorienMaara() {
        return pyorienMaara;
    }

    public void setPyorienMaara(Integer pyorienMaara) {
        this.pyorienMaara = pyorienMaara;
    }

}
