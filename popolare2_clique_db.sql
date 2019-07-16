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
INSERT INTO RESTAURANTS VALUES (5, 'Ristorante da Lino', 'Via Dei Liguri 28', 'Pavia', 'nico');
INSERT INTO RESTAURANTS VALUES (6, 'Ristorante Botticcella', 'Via Fratelli Marozzi 7', 'Pavia', 'ste');


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
INSERT INTO OVERVIEW VALUES (5,7,8,9,6,8,7.6);
INSERT INTO OVERVIEW VALUES (6,7,8,9,6,8,7.6);



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
INSERT INTO USERS VALUES ('nico', '5689', 'Nicolò', 'Chierico', 'RESTAURANTOWNER');
INSERT INTO USERS VALUES ('ste', '8989', 'Stefano', 'Alberti', 'RESTAURANTOWNER');

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

INSERT INTO MENUENTRY VALUES(1, 'Selezione di salumi misti della corte ', 10.0, 1,"ANTIPASTO");
INSERT INTO MENUENTRY VALUES(2, 'Cannolicchi gratinati', 11.0, 1,"ANTIPASTO");
INSERT INTO MENUENTRY VALUES(3, 'Carpaccio di baccalà ', 16.0, 1,"ANTIPASTO");
INSERT INTO MENUENTRY VALUES(4, 'Ravioli di brasato ', 13.0, 1,"PRIMO");
INSERT INTO MENUENTRY VALUES(5, 'Spaghettone ', 11.0, 1,"PRIMO");
INSERT INTO MENUENTRY VALUES(6, 'Branzino al sale ', 45.0, 1,"SECONDO");
INSERT INTO MENUENTRY VALUES(7, 'Tortino al cioccolato', 6.0, 1,"DOLCE");
INSERT INTO MENUENTRY VALUES(8, 'Ostriche', 19.0, 2,"ANTIPASTO");
INSERT INTO MENUENTRY VALUES(9, 'Piemonte', 18.0, 2,"ANTIPASTO");
INSERT INTO MENUENTRY VALUES(10, 'Caprese', 16.0, 2,"PRIMO");
INSERT INTO MENUENTRY VALUES(11, 'Zuppa alla Pavese', 13.0, 2,"PRIMO");
INSERT INTO MENUENTRY VALUES(12, 'Coniglio in Spagna', 20.0, 2,"SECONDO");
INSERT INTO MENUENTRY VALUES(13, 'Cuore Nero', 8.0, 2,"DOLCE");
INSERT INTO MENUENTRY VALUES(14, 'Puntarelle con acciughe e pinoli', 16.0, 3,"ANTIPASTO");
INSERT INTO MENUENTRY VALUES(15, 'Paccheri con porcini e piovra', 15.0, 3,"PRIMO");
INSERT INTO MENUENTRY VALUES(16, 'Tagliatelle al ragù di anatra', 13.0, 3,"PRIMO");
INSERT INTO MENUENTRY VALUES(17, 'Maialino da latte in porchetta', 20.0, 3,"SECONDO");
INSERT INTO MENUENTRY VALUES(18, 'Sorbetto', 5.0, 3,"DOLCE");
INSERT INTO MENUENTRY VALUES(19, 'Salumi misti', 11.0, 4,"ANTIPASTO");
INSERT INTO MENUENTRY VALUES(20, 'Risi e Bisi', 11.0, 4,"PRIMO");
INSERT INTO MENUENTRY VALUES(21, 'Stinco di vitello', 16.0, 4,"SECONDO");
INSERT INTO MENUENTRY VALUES(22, 'Cantucci e Vin Santo', 8.0, 4,"DOLCE");
INSERT INTO MENUENTRY VALUES(23, 'Serviti Crudi', 20.0, 5,"ANTIPASTO");
INSERT INTO MENUENTRY VALUES(24, 'Tagliatelle al ragu', 15.0, 5,"PRIMO");
INSERT INTO MENUENTRY VALUES(25, 'Serviti fritti', 25.0, 5,"SECONDO");
INSERT INTO MENUENTRY VALUES(26, 'Fragolce e cioccolato', 8.0, 5,"DOLCE");
INSERT INTO MENUENTRY VALUES(27, 'Crudi di Sicilia', 2.50, 6,"ANTIPASTO");
INSERT INTO MENUENTRY VALUES(28, 'Spaghettone di gragnano alle vongole', 14.0, 6,"PRIMO");
INSERT INTO MENUENTRY VALUES(29, 'Polipetti alla griglia', 17.0, 6,"SECONDO");



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
INSERT INTO CRITIQUES VALUES (3, 3, 'kimi', 7,8,9,6,8, 'Cibo molto raffinato, ambiente gradevole e personale cordiale');
INSERT INTO CRITIQUES VALUES (4, 4, 'kimi', 7,8,9,6,8, 'Cibo molto raffinato, ambiente gradevole e personale cordiale');
INSERT INTO CRITIQUES VALUES (5, 5, 'kimi', 7,8,9,6,8, 'Cibo molto raffinato, ambiente gradevole e personale cordiale');
INSERT INTO CRITIQUES VALUES (6, 6, 'kimi', 7,8,9,6,8, 'Cibo molto raffinato, ambiente gradevole e personale cordiale');

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
 insert into   CRITIQUE_DISH values (1,2,8);
 insert into CRITIQUE_DISH values (2,10,8);
 insert into CRITIQUE_DISH values (3,17,8);
 insert into CRITIQUE_DISH values (4,22,8);
 insert into CRITIQUE_DISH values (5,24,8);
 insert into CRITIQUE_DISH values (6,28,8);
 