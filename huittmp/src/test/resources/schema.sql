--H2-testitietokannan taulujen luontiskripti

DROP TABLE IF EXISTS jasen_roolit;
DROP TABLE IF EXISTS jasen_maksut;
DROP TABLE IF EXISTS jasenet;
DROP TABLE IF EXISTS roolit;
DROP TABLE IF EXISTS maksut;

CREATE TABLE jasenet (
   jasen_id BIGSERIAL PRIMARY KEY,
   etunimi VARCHAR(100) NOT NULL,
   sukunimi VARCHAR(100) NOT NULL,
   email VARCHAR(100) NOT NULL UNIQUE,
   puhelinnumero VARCHAR (50) NOT NULL,
   liittymispaiva DATE NOT NULL,
   salasana VARCHAR(255),
   yllapitaja BOOLEAN NOT NULL DEFAULT FALSE --Onko admin oikeudet
);

CREATE TABLE roolit (
    rooli_id BIGSERIAL PRIMARY KEY,
    rooli_nimi VARCHAR(100) NOT NULL UNIQUE --Jäsenen roolin nimi Esim Pj, sihteeri, hallituksen jäsen tai kerhoisäntä
);

CREATE TABLE maksut (
    maksu_id BIGSERIAL PRIMARY KEY,
    maksu_nimi VARCHAR(100) NOT NULL,
    maksu_kausi VARCHAR(100) NOT NULL,
    UNIQUE (maksu_nimi, maksu_kausi) --Estää tuplakirjauksen samalla id yhdistelmällä
);

CREATE TABLE jasen_roolit (
    jasen_rooli_id BIGSERIAL PRIMARY KEY,
    jasen_id BIGINT NOT NULL,
    rooli_id BIGINT NOT NULL,
    alkupaiva DATE NOT NULL,
    loppupaiva DATE,

    FOREIGN KEY (jasen_id) REFERENCES jasenet(jasen_id),
    FOREIGN KEY (rooli_id) REFERENCES roolit(rooli_id)
);

CREATE TABLE jasen_maksut (
    jasen_maksu_id BIGSERIAL PRIMARY KEY,
    jasen_id BIGINT NOT NULL,
    maksu_id BIGINT NOT NULL,
    maksettu Boolean NOT NULL DEFAULT FALSE, --asettaa oletuksena uudelle jäsenelle sopivan arvon
    pyorien_maara INTEGER DEFAULT NULL, --Jäsenellä talvisälytyksessä olevien pyörien määrä

    FOREIGN KEY (jasen_id) REFERENCES jasenet(jasen_id),
    FOREIGN KEY (maksu_id) REFERENCES maksut(maksu_id),
    UNIQUE (jasen_id, maksu_id) --Estää tuplakirjauksen samalla id yhdistelmällä
);