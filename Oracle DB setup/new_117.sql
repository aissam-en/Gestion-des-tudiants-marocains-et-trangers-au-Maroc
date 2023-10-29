

Clear Screen
DESC Etudiants_Etrangers
DESC Etudiants

CREATE OR REPLACE TRIGGER TRIGGER_INSERT_etudiant
BEFORE INSERT ON Etudiants_Etrangers
FOR EACH ROW
DECLARE
	n NUMBER;
	etudiant_duplique EXCEPTION;
BEGIN
  SELECT COUNT(*) INTO n FROM Etudiants_Etrangers WHERE Nom = :NEW.Nom AND Prenom = :NEW.Prenom;
  IF n = 0 THEN
	  -- verifier si la ligne inseree est pour un etudiant marocain n'est pad dupliquee
	  -- Inserer la ligne dans la table Etudiants
	  INSERT INTO Etudiants VALUES (:NEW.ID_etudiant, :NEW.Nom, :NEW.Prenom, :NEW.Nationalite, :NEW.Etranger);
  ELSE 
	-- si une ligne avec le même nom et prenom existe deja
	RAISE etudiant_duplique;
  END IF;
EXCEPTION
  WHEN etudiant_duplique THEN
	RAISE_APPLICATION_ERROR(-20001, '!!! L''etudiant existe deja !!!');
END;
/
----Test


SELECT * FROM Etudiants_Etrangers;
SELECT * FROM Etudiants;

INSERT INTO Etudiants_Etrangers VALUES(sq_Id_Etudiant.NEXTVAL, 'Bill', 'Gates', 'Allemand', 1);

INSERT INTO Etudiants_Etrangers VALUES(sq_Id_Etudiant.NEXTVAL, 'Alice', 'Bob', 'Allemand', 1);

SELECT * FROM Etudiants_Etrangers;
SELECT * FROM Etudiants;



Clear Screen
DESC Universites_Etrangers
DESC Universites

CREATE OR REPLACE TRIGGER TRIGGER_INSERT_universite
BEFORE INSERT ON Universites_Etrangers
FOR EACH ROW
DECLARE
	n NUMBER;
	universites_duplique EXCEPTION;
BEGIN
  SELECT COUNT(*) INTO n FROM Universites_Etrangers WHERE Nom = :NEW.Nom;
  IF n = 0 THEN
	-- verifier si aucune universite avec le même nom n'existe deja
    -- Inserer la ligne dans la tables Universites
	INSERT INTO Universites VALUES (:NEW.ID_universite, :NEW.Nom, :NEW.Localisation);
  ELSE 
    -- si une universite avec le même nom existe deja
	RAISE universites_duplique;
  END IF;
EXCEPTION
	WHEN universites_duplique THEN
	  RAISE_APPLICATION_ERROR(-20002, '!!! L''universites "'||:NEW.Nom||'" est deja exist !!!');	 
END;
/

----Test

DESC Universites_Etrangers
DESC Universites

INSERT INTO Universites_Etrangers VALUES(sq_Id_Univetsite.NEXTVAL, 'Université Ibn Zohr', 'Agadir');

INSERT INTO Universites_Etrangers VALUES(sq_Id_Univetsite.NEXTVAL, 'Université Mohamed 6', 'Agadir');

DESC Universites_Etrangers
DESC Universites




Clear Screen
DESC Etud_Univ_Etrangers
DESC Etudiants_Universites

CREATE OR REPLACE TRIGGER TRIGGER_INSERT_etud_univ
BEFORE INSERT ON Etud_Univ_Etrangers
FOR EACH ROW
DECLARE
	n NUMBER;
	etudiant_deja EXCEPTION;
BEGIN
  SELECT COUNT(*) INTO n FROM Etud_Univ_Etrangers WHERE ID_etudiant = :NEW.ID_etudiant;
  IF n = 0 THEN
	-- verifier si l'etudiant n'est pas dupliquee
	-- Inserer la ligne dans la tables Etudiants_Universites
	INSERT INTO Etudiants_Universites VALUES (:NEW.ID_etudiant, :NEW.ID_universite);
  ELSE
	-- si l'etudiant exist deja
	RAISE etudiant_deja;
  END IF;
EXCEPTION
	WHEN etudiant_deja THEN
	  RAISE_APPLICATION_ERROR(-20004, '!!! L''etudiant est deja exist !!!');
END;
/

----Test

Clear Screen
SELECT * FROM Etudiants;
SELECT * FROM Universites;

SELECT * FROM Etud_Univ_Etrangers;
SELECT * FROM Etudiants_Universites;

INSERT INTO Etud_Univ_Etrangers VALUES(, );

INSERT INTO Etud_Univ_Etrangers VALUES(, );
INSERT INTO Etud_Univ_Etrangers VALUES(, );
INSERT INTO Etud_Univ_Etrangers VALUES(, );

SELECT * FROM Etud_Univ_Etrangers;
SELECT * FROM Etudiants_Universites;





Clear Screen
DESC Bourses_Etrangers
DESC Bourses

CREATE OR REPLACE TRIGGER TRIGGER_INSERT_bourses
BEFORE INSERT ON Bourses_Etrangers
FOR EACH ROW
DECLARE
	n NUMBER;
	etudiant_deja EXCEPTION;
BEGIN
  SELECT COUNT(*) INTO n FROM Bourses WHERE ID_etudiant = :NEW.ID_etudiant;
  IF n = 0 THEN
	-- verifier si l'etudiant n'est pas dupliquee
	-- Inserer la ligne dans la table Bourses
	INSERT INTO Bourses VALUES (:NEW.ID_bourse, :NEW.ID_etudiant, :NEW.Nom, :NEW.Montant);
  ELSE
	-- si l'etudiant exist deja
	RAISE etudiant_deja;
  END IF;
EXCEPTION
	WHEN etudiant_deja THEN
	  RAISE_APPLICATION_ERROR(-20005, '!!! L''etudiant est deja exist !!!');
END;
/

----Test
SELECT * FROM Etudiants;

SELECT * FROM Bourses_Etrangers;
SELECT * FROM Bourses;


INSERT INTO Bourses VALUES(sq_Id_Bourse.NEXTVAL, 1, 'Bourses Minhaty', 5000 );

INSERT INTO Bourses VALUES(sq_Id_Bourse.NEXTVAL, , 'Bourses FME', 4000 );
INSERT INTO Bourses VALUES(sq_Id_Bourse.NEXTVAL, , 'Bourses FME', 4000 );
INSERT INTO Bourses VALUES(sq_Id_Bourse.NEXTVAL, , 'Bourses FME', 4000 );

SELECT * FROM Bourses_Etrangers;
SELECT * FROM Bourses;


--------------------------------------------------------
---------------------------------------------------------------
---------------------------------------------------------------
---------------------------------------------------------------
---------------------------------------------------------------

--- Procedure :
--- Une procedure qui insere un nouveau Etudiant Marocain(e):

CLEAR SCREEN

CREATE OR REPLACE PROCEDURE INSERT_etudiant(p_Nom Etudiants_Etrangers.Nom%TYPE, 
                    p_Prenom Etudiants_Etrangers.Prenom%TYPE, 
                    p_Nationalite Etudiants_Etrangers.Nationalite%TYPE) AS					
  n NUMBER := 0;
  etudiant_duplique EXCEPTION;

  BEGIN
	SELECT COUNT(*) INTO n FROM Etudiants_Etrangers WHERE Nom = p_Nom AND Prenom = p_Prenom;
	IF n = 0 THEN
	  -- verifier si l'etudiant n'existe pas
      -- Inserer la ligne dans la table Etudiants_Etrangers
	  INSERT INTO Etudiants_Etrangers VALUES(sq_Id_Etudiant.NEXTVAL, p_Nom, p_Prenom, p_Nationalite, 0);
	  dbms_output.put_line('L''etudiant "'||p_Nom||' '||p_Prenom||'" a ete ajoute');
	ELSE
	  -- si l'etudiant existe deja
	  RAISE etudiant_duplique;
	END IF;
EXCEPTION
	WHEN etudiant_duplique THEN
	  dbms_output.put_line('!!! L''etudiant "'||p_Nom||' '||p_Prenom||'" deja existe !!!');
END;
/


SELECT * FROM Etudiants_Etrangers;
SELECT * FROM Etudiants;

BEGIN
	INSERT_etudiant('EN-NAHEL', 'Aissam', 'Marocain');
END;
/
BEGIN
	INSERT_etudiant('EN-NAHEL', 'Morad', 'Marocain');
END;
/


SELECT * FROM Etudiants_Etrangers;
SELECT * FROM Etudiants;




CLEAR SCREEN
CREATE OR REPLACE PROCEDURE INSERT_universite(p_Nom Universites_Etrangers.Nom%TYPE, 
                        p_Localisation Universites_Etrangers.Localisation%TYPE ) AS					
  n NUMBER := 0;
  universite_duplique EXCEPTION;
	
  BEGIN
    SELECT COUNT(*) INTO n FROM Universites_Etrangers WHERE Nom = p_Nom;
    IF n = 0 THEN
	  -- verifier si l'universite n'existe pas
      -- Inserer la ligne dans la table Universites_Etrangers
      INSERT INTO Universites_Etrangers VALUES(sq_Id_Univetsite.NEXTVAL, p_Nom, p_Localisation);
      dbms_output.put_line('L''Universite "'||p_Nom||'" a ete ajoute');
    ELSE
	  -- si l'universite existe deja
      RAISE universite_duplique;
    END IF;
  EXCEPTION
    WHEN universite_duplique THEN
      dbms_output.put_line('!!! L''Universite "'||p_Nom||'" deja existe !!!');
  END ;
/

SELECT * FROM Universites_Etrangers;
SELECT * FROM Universites;

BEGIN
	INSERT_universite('Universite Hassan 4', 'Meknes');
END;
/
BEGIN
	INSERT_universite('Universite Hassan 1', 'Agadir');
END;
/

SELECT * FROM Universites_Etrangers;
SELECT * FROM Universites;




CLEAR SCREEN
CREATE OR REPLACE PROCEDURE INSERT_etudiant_universite(p_ID_etudiant Etud_Univ_Etrangers.ID_etudiant%TYPE, 
                            p_ID_universite Etud_Univ_Etrangers.ID_universite%TYPE ) AS 
  n NUMBER := 0;
  est_duplique EXCEPTION;
  
  BEGIN
    SELECT COUNT(*) INTO n FROM Etud_Univ_Etrangers WHERE ID_etudiant = p_ID_etudiant AND ID_universite = p_ID_universite;
    IF n = 0 THEN
	  -- verifier si l'etudiant n'est pas enregistre dans une universite
      -- Inserer la ligne dans la table Etud_Univ_Etrangers
      INSERT INTO Etud_Univ_Etrangers VALUES(p_ID_etudiant, p_ID_universite);
      dbms_output.put_line('L''etudiant N°'||p_ID_etudiant||' est enregistre dans l''universite N°'||p_ID_universite);
    ELSE
	  ---- si l'etudiant est enregistre deja dans une universite
      RAISE est_duplique;
    END IF;
  EXCEPTION
    WHEN est_duplique THEN
      dbms_output.put_line('!!! L''etudiant N°'||p_ID_etudiant||' est deja enregistre dans l''universite N°'||p_ID_universite||' !!!');
  END ;
/

SELECT * FROM Etudiants;
SELECT * FROM Universites;

SELECT * FROM Etud_Univ_Etrangers;
SELECT * FROM Etudiants_Universites;

BEGIN
	INSERT_etudiant_universite(, );
	INSERT_etudiant_universite(, );
END;
/

SELECT * FROM Etud_Univ_Etrangers;
SELECT * FROM Etudiants_Universites;





CLEAR SCREEN
CREATE OR REPLACE PROCEDURE INSERT_bourse(p_ID_etudiant Bourses_Etrangers.ID_etudiant%TYPE, 
                      p_Nom Bourses_Etrangers.Nom%TYPE, 
                      p_Montant Bourses_Etrangers.Montant%TYPE ) AS 
  n NUMBER := 0;
  est_duplique EXCEPTION;
  
  BEGIN
    SELECT COUNT(*) INTO n FROM Bourses_Etrangers WHERE Nom = p_Nom AND ID_etudiant = p_ID_etudiant;
    IF n = 0 THEN
	  -- verifier si l'etudiant n'est pas enregistre dans une bourse
      -- Inserer la ligne dans la table Bourses_Etrangers
      INSERT INTO Bourses_Etrangers VALUES(sq_Id_Bourse.NEXTVAL, p_ID_etudiant, p_Nom, p_Montant);
      dbms_output.put_line('L''etudiant N°'||p_ID_etudiant||' est enregistre dans la bourse "'||p_Nom||'".');
    ELSE
	  ---- si l'etudiant est enregistre deja dans une bourse
      RAISE est_duplique;
    END IF;
  EXCEPTION
    WHEN est_duplique THEN
      dbms_output.put_line('!!! L''etudiant N°'||p_ID_etudiant||' est deja enregistre dans la bourse "'||p_Nom||'" !!!');
  END ;
/


SELECT * FROM Etudiants;

SELECT * FROM Bourses_Etrangers;
SELECT * FROM Bourses;

BEGIN
	INSERT_bourse(21, 'Bourseses FME', 3500);
END;
/
BEGIN
	INSERT_bourse(, 'Bourseses ABC',);
END;
/

SELECT * FROM Bourses_Etrangers;
SELECT * FROM Bourses;







-- ////// Action : FIN \\\\\\
