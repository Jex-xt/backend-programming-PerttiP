package backend.huittmp.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;

/**
 * Yhdistyksen jäsenten vuosittaiset ja kausittaiset maksut.
 * Vastaa tietokannan maksut-taulua.
 */
@Entity 
@Table(name = "maksut", uniqueConstraints = @UniqueConstraint(columnNames = {"maksu_nimi", "maksu_kausi"})) //Tiedot ovat maksut taulussa. Maksun_nimi ja maksu_kausi voivat olla identtinen pari vain kerran myös JPA:n luodessa taulut.
public class Maksu {

    @Id //Pääavain
    @GeneratedValue(strategy = GenerationType.IDENTITY) //id luodaan automaattisesti
    @Column(name = "maksu_id", nullable = false, updatable = false)
    private Long id;
    
    @NotBlank(message = "Syötä maksun nimi")
    @Column(name = "maksu_nimi", nullable = false)
    private String maksuNimi;

    @NotBlank(message = "Syötä maksukausi")
    @Column(name = "maksu_kausi", nullable = false)
    private String maksuKausi;

    public Maksu() {    // JPA:n tarvitsema parametriton konstruktori
    }

    public Maksu(String maksuNimi, String maksuKausi) { //Parametrillinen konstruktori
        this.maksuNimi = maksuNimi;
        this.maksuKausi = maksuKausi;
    }

    public Long getId() {
        return id;
    }

    public String getMaksuNimi() {
        return maksuNimi;
    }

    public void setMaksuNimi(String maksuNimi) {
        this.maksuNimi = maksuNimi;
    }

    public String getMaksuKausi() {
        return maksuKausi;
    }

    public void setMaksuKausi(String maksuKausi) {
        this.maksuKausi = maksuKausi;
    }

    

}
