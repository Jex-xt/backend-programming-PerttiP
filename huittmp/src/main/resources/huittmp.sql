--Skripti HuittMP-tietokannan taulujen ja datan lisämiseen PostgreSQL kantaan

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
('Talvisäilytysmaksu', '2026-2027'),
('Jäsenmaksu', '2026'),
('Korjaamomaksu', '2026'),
('Talvisäilytysmaksu', '2025-2026');

INSERT INTO jasenet (etunimi, sukunimi, email, puhelinnumero, liittymispaiva)
VALUES
('Matti', 'Meikäläinen', 'matti@example.fi', '0401234567', '2015-01-15'),
('Liisa', 'Virtanen', 'liisa@example.fi', '0501234567', '2019-03-10'),
('Esa', 'Esimerkki', 'esa@example.fi', '0451234567', '2017-05-20'),
('Heli', 'Kopteri', 'heli@example.fi','0441234567', '2024-05-15'),
('Jari', 'Järvinen', 'jari@example.fi','0411234567', '2017-08-21'),
('Kalle', 'Kallenen', 'kalle@example.fi','0416666666', '2016-08-10'),
('Pekka', 'Pekkanen', 'pekka@example.fi','0427777777', '2019-09-01'),
('Jukka', 'Jukkanen', 'jukka@example.fi','0469999999', '2021-02-16'),
('Sirpa', 'Sirpanen', 'sirpa@example.fi','0435555555', '2025-03-17'),
('Janne', 'Jannela', 'janne@example.fi','0448888888', '2026-05-26');

--Testi käyttäjät
INSERT INTO jasenet (etunimi, sukunimi, email, puhelinnumero, liittymispaiva, salasana, yllapitaja)
VALUES
('Testi', 'Admin', 'admin@example.fi', '0444444444','2026-10-16', '$2a$10$oeCGpLsuJi1bxB07ZA9gZ.r0F/fOs/32.lzbGYJI75uJA/bsmPkxG', TRUE),
('Testi', 'User', 'user@example.fi', '0455555555','2023-03-15', '$2a$10$ufHyCSDPGKuv8/iiteY/PO.kjPDd0kQhmSe.LgZHoiMuB482rqBP2', FALSE);

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
(5, 4, '2018-04-01', '2023-03-17'), --Jari kerhoisäntä 2018-2023
(3, 5, '2018-04-01', '2023-03-17'), --Esa Hallituksen varsinainen jäsen 2018-2023 lähtien
(3, 3, '2020-03-12', '2022-03-19'), --Esa rahastonhoitaja 2020-2022
(4, 6, '2024-05-15', '2025-03-09'), --Heli hallituksen varajäsen 2024-2025
(5, 5, '2020-03-12', '2022-03-19'), --Jari hallituksen varsinainen jäsen 2020-2022
(6, 5, '2019-03-16', '2023-03-17'), --Kalle hallituksen varsinainen jäsen
(6, 6, '2023-03-18', '2025-03-14'), --Kalle hallituksen varajäsen
(7, 6, '2025-03-15', NULL), --Pekka hallituksen varajäsen
(9, 5, '2026-03-15', NULL); --Sirpa hallituksen varsinainen jäsen

--Testi Userin roolihistoria
INSERT INTO jasen_roolit (jasen_id, rooli_id, alkupaiva, loppupaiva)
VALUES
(12, 6, '2023-03-18', '2024-03-14'), --Testi User Hallituksen varajäsen
(12, 5, '2024-03-15', '2026-03-14'), --Testi User Hallituksen varsinainen jäsen
(12, 6, '2026-03-15', NULL);         --Testi User Hallituksen varajäsen, nykyinen

INSERT INTO jasen_maksut (jasen_id, maksu_id, maksettu, pyorien_maara)
VALUES
-- Matti
(1, 1, TRUE, NULL), -- Jäsenmaksu 2027
(1, 2, TRUE, NULL), -- Korjaamomaksu 2027
(1, 3, TRUE, 2),    -- Talvisäilytys 2026-2027
(1, 4, TRUE, NULL), -- Jäsenmaksu 2026
(1, 5, TRUE, NULL), -- Korjaamomaksu 2026
(1, 6, TRUE, 2),    -- Talvisäilytys 2025-2026

-- Liisa
(2, 1, TRUE, NULL),
(2, 2, TRUE, NULL),
(2, 3, FALSE, 0),
(2, 4, TRUE, NULL),
(2, 5, TRUE, NULL),

-- Esa
(3, 1, TRUE, NULL),
(3, 2, FALSE, NULL),
(3, 3, TRUE, 1),
(3, 4, TRUE, NULL),
(3, 5, TRUE, NULL),
(3, 6, TRUE, 1),

-- Heli
(4, 1, TRUE, NULL),
(4, 2, TRUE, NULL),
(4, 3, FALSE, 0),
(4, 4, TRUE, NULL),
(4, 5, FALSE, NULL),

-- Jari
(5, 1, TRUE, NULL),
(5, 2, FALSE, NULL),
(5, 3, TRUE, 3),
(5, 4, TRUE, NULL),
(5, 5, TRUE, NULL),
(5, 6, TRUE, 2),

-- Kalle 
(6, 1, TRUE, NULL),
(6, 2, TRUE, NULL),
(6, 4, TRUE, NULL),
(6, 5, TRUE, NULL),

-- Pekka
(7, 1, TRUE, NULL),
(7, 2, FALSE, NULL),
(7, 3, TRUE, 2),
(7, 4, TRUE, NULL),
(7, 5, TRUE, NULL),
(7, 6, TRUE, 2),

-- Jukka
(8, 1, FALSE, NULL),
(8, 4, TRUE, NULL),
(8, 5, TRUE, NULL),

-- Sirpa
(9, 1, TRUE, NULL),
(9, 2, TRUE, NULL),
(9, 4, TRUE, NULL),

-- Janne
(10, 1, FALSE, NULL),
(10, 3, FALSE, 1),
(10, 4, TRUE, NULL);

--Testi Userin maksuhistoria
INSERT INTO jasen_maksut (jasen_id, maksu_id, maksettu, pyorien_maara)
VALUES
(12, 1, TRUE, NULL),  --Jäsenmaksu 2027, maksettu
(12, 2, FALSE, NULL), --Korjaamomaksu 2027, maksamatta
(12, 3, TRUE, 1),     --Talvisäilytys 2026-2027, yksi pyörä
(12, 4, TRUE, NULL);  --Jäsenmaksu 2026, maksettu