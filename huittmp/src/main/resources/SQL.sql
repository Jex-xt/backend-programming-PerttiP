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

INSERT INTO roolit (rooli_nimi)
VALUES
('Puheenjohtaja'),
('Sihteeri'),
('Rahastonhoitaja'),
('Kerhoisäntä'),
('Hallituksen varsinainen jäsen'),
('Hallituksen varajäsen');

INSERT INTO maksut (maksu_nimi, maksu_kausi)
VALUES
('Jäsenmaksu', '2027'),
('Korjaamomaksu', '2027'),
('Talvisäilytysmaksu', '2026-2027');

INSERT INTO jasenet (etunimi, sukunimi, email, puhelinnumero, liittymispaiva)
VALUES
('Matti', 'Meikäläinen', 'matti@example.fi', '0401234567', '2015-01-15'),
('Liisa', 'Virtanen', 'liisa@example.fi', '0501234567', '2019-03-10'),
('Esa', 'Esimerkki', 'esa@example.fi', '0451234567', '2017-05-20'),
('Heli', 'Kopteri', 'heli@example.fi','0441234567', '2024-05-15'),
('Jari', 'Järvinen', 'jari@example.fi','0411234567', '2017-08-21');


INSERT INTO jasen_roolit (jasen_id, rooli_id, alkupaiva, loppupaiva)
VALUES
(1, 1, '2018-04-01', NULL), --Matti puheenjohtaja kannan alusta lähtien
(2, 2, '2023-03-18', NULL), --Liisa Sihteeri 2023 lähtien
(2, 5, '2022-03-20', NULL), --Liisa hallituksen jäsen 2022 lähtien
(2, 3, '2022-03-20', NULL), --Liisa rahastonhoitaja 2022 lähtien
(3, 4, '2023-03-18', NULL), --Esa kerhoisäsntä 2023 lähtien
(4, 5, '2025-03-10', NULL), --Heli hallituksen varsinainen jäsen 2025 lähtien
(5, 5, '2026-03-15', NULL), --Jari hallituksen varsinainen jäsen 2026 lähtien
(3, 6, '2023-03-18', NULL), -- Esa Hallituksen varajäsen 2023 lähtien
(2, 6, '2020-03-12', '2022-03-19'), --Liisa hallituksen varajäsen 2020-2022
(5, 4, '2018-04-01', '2023-03-18'), --Jari kerhoisäntä 2018-2023
(3, 5, '2018-04-01', '2023-03-17'), --Esa Hallituksen varsinainen jäsen 2018-2023 lähtien
(3, 3, '2020-03-12', '2022-03-19'), --Esa rahastonhoitaja 2020-2022
(4, 6, '2024-05-15', '2025-03-09'), --Heli hallituksen varajäsen 2024-2025
(5, 5, '2020-03-12', '2022-03-19'); --Jari hallituksen varsinainen jäsen 2020-2022

INSERT INTO jasen_maksut (jasen_id, maksu_id, maksettu, pyorien_maara)
VALUES
--Matti
(1, 1, TRUE, NULL), --Jäsenmaksu
(1, 2, TRUE, NULL), --Korjaamomaksu
(1, 3, TRUE, 2), --Talvisäilytysmaksu, Talvisäilytyksessä olevien pyörien määrä

--Liisa
(2, 1, TRUE, NULL),
(2, 2, TRUE, NULL),
(2, 3, FALSE, 0),

--Esa
(3, 1, TRUE, NULL),
(3, 2, FALSE, NULL),
(3, 3, TRUE, 1),

--Heli
(4, 1, TRUE, NULL),
(4, 2, TRUE, NULL),
(4, 3, FALSE, 0),

--Jari
(5, 1, TRUE, NULL),
(5, 2, FALSE, NULL),
(5, 3, TRUE, 3);

