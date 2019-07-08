-- script per creare le tables del database CLIQUE
-- set up application

--      la clausola ENGINE=InnoDB che e' specifica di MySQL
--      senza la quale non e' possibile usare i comandi di
--      definizione dei vincoli di integrita'Â  referenziale
--      FOREIGN KEY

-- libreria (prodotte congiuntamente al DBMS o da enti terzi) che determina 
-- il modo in cui i dati di quella tabella saranno salvati su disco,
-- e ciò sarà determinante per valutare le prestazioni.
-- nnoDB è lo Storage Engine di default.

use CLIQUE_DB;

DROP TABLE IF EXISTS RESTAURANTS;
CREATE TABLE RESTAURANTS
	(COD_REST INTEGER primary key,
	 NAME CHAR(50),
	 ADDRESS CHAR(100),
	 CITY CHAR(50),
	 OWNER CHAR(30)
	)
	ENGINE = InnoDB;

-- inserimento nella table restaurants

INSERT INTO RESTAURANTS VALUES (1, 'La Korte dei Sapori Persi', 'Borgo Calvenzano', 'Pavia', 'orso');
INSERT INTO RESTAURANTS VALUES (2, 'Locanda del Carmine', 'Piazza del carmine 7', 'Pavia', 'tia');
INSERT INTO RESTAURANTS VALUES (3, 'Ristorante Bardelli', 'Lungo Ticino Visconti 2', 'Pavia', 'al');
INSERT INTO RESTAURANTS VALUES (4, 'Torre degli Aquila', 'CorsoStrada Nuova 20', 'Pavia', 'al');


DROP TABLE IF EXISTS OVERVIEW;
CREATE TABLE OVERVIEW
	(
	 RESTAURANT INTEGER primary key,
	 MENU DOUBLE,
	 LOCATION DOUBLE,
	 SERVIZIO DOUBLE,
	 CONTO DOUBLE,
	 CUCINA DOUBLE,
	 MEAN DOUBLE
	)
	ENGINE = InnoDB;


INSERT INTO OVERVIEW VALUES (1,7,8,9,6,8,7.6);
INSERT INTO OVERVIEW VALUES (2,7,8,9,6,8,7.6);
INSERT INTO OVERVIEW VALUES (3,7,8,9,6,8,7.6);
INSERT INTO OVERVIEW VALUES (4,7,8,9,6,8,7.6);


DROP TABLE IF EXISTS USERS;
CREATE TABLE USERS
	(USERNAME CHAR(30) primary key,
     PASSWORD CHAR(10),
	 NAME CHAR(20),
	 SURNAME CHAR(20),
	 USERTYPE CHAR(20)
	)
	ENGINE = InnoDB;


-- inserimento nella table restaurantowners

INSERT INTO USERS VALUES ('orso', 'J19', 'Matteo', 'Orsolini', 'RESTAURANTOWNER');
INSERT INTO USERS VALUES ('tia', 'J19', 'Mattia', 'Bosio', 'RESTAURANTOWNER');
INSERT INTO USERS VALUES ('al', 'ajeje', 'Aldo', 'Baglio', 'RESTAURANTOWNER');
INSERT INTO USERS VALUES ('jack', 'franabile', 'Giacomo', 'Poretti', 'CRITIC');
INSERT INTO USERS VALUES ('seb', 'ferrari', 'Sebastian', 'Vettel', 'CRITIC');
INSERT INTO USERS VALUES ('kimi', 'alfa', 'Kimi', 'Raikkonen', 'CRITIC');

DROP TABLE IF EXISTS MENUENTRY;
CREATE TABLE MENUENTRY
	(
	 DISH_COD INTEGER primary key,
	 DISH CHAR(40),
	 PRICE DOUBLE,
	 RESTAURANT INTEGER,
     DISH_TYPE  CHAR(10)
	)
	ENGINE = InnoDB;

	
-- inserimento nella table menuentry

INSERT INTO MENUENTRY VALUES(21, 'Fiorentina di Scottona piemontese', 45.0, 1,"SECONDO");
INSERT INTO MENUENTRY VALUES(47, 'Tagliata di Angus americano', 25.0, 2,"SECONDO");


DROP TABLE IF EXISTS CRITIQUES;
CREATE TABLE CRITIQUES
	(
	 CRITIQUE_COD  INTEGER,
	 RESTAURANT INTEGER,
	 CRITIC CHAR(30),
	 MENU SMALLINT,
	 LOCATION SMALLINT,
	 SERVIZIO SMALLINT,
	 CONTO SMALLINT,
	 CUCINA DOUBLE,
	 COMMENT CHAR(200),
	 PRIMARY KEY (CRITIQUE_COD),
	 FOREIGN KEY (CRITIC) REFERENCES USERS(USERNAME),
	 FOREIGN KEY (RESTAURANT) REFERENCES RESTAURANTS(COD_REST)
	)
	ENGINE = InnoDB;

-- inserimento nella table critiques

INSERT INTO CRITIQUES VALUES (1, 1, 'jack', 7,8,9,6,8, 'Cibo molto raffinato, ambiente gradevole e personale cordiale');
INSERT INTO CRITIQUES VALUES (2, 2, 'jack', 7,8,9,6,8, 'Cibo molto raffinato, ambiente gradevole e personale cordiale');

-- CREAZIONE TABLE CRITIQUE_DISH
CREATE TABLE CRITIQUE_DISH
(
	CRITIQUE_CODE  INTEGER,
  DISH_CODE INTEGER,
	VOTO_DISH SMALLINT,
    PRIMARY KEY(CRITIQUE_CODE,DISH_CODE),
    foreign key (CRITIQUE_CODE) references CRITIQUES(CRITIQUE_COD),
    foreign key (DISH_CODE) references MENUENTRY(DISH_COD)
)
	engine = InnoDB;
 insert into   CRITIQUE_DISH values (1,21,7);
 insert into CRITIQUE_DISH values (2,47,6);
 