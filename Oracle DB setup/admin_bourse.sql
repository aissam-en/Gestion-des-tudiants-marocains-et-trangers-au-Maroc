sqlplus system/aissam

drop user moderateur_bourse_marocaine CASCADE; 
drop user moderateur_bourse_etranger CASCADE;
drop user admin_bourse CASCADE;

CREATE tablespace tbs_mini_prj datafile 'C:\ennahel_aissam\mini_project_BDR\tbs_mini_prj.dbf' size 500M;

CREATE USER admin_bourse identIFied by aissam default tablespace tbs_mini_prj quota unlimited on tbs_mini_prj;
grant all privileges to admin_bourse;

CREATE user moderateur_bourse_marocaine identIFied by aissam default tablespace tbs_mini_prj quota unlimited on tbs_mini_prj;
grant CREATE session, CREATE sequence, CREATE table, CREATE procedure, connect, resource, CREATE view, CREATE database link, CREATE synonym, CREATE snapshot to moderateur_bourse_marocaine; 

CREATE user moderateur_bourse_etranger  identIFied by aissam default tablespace tbs_mini_prj quota unlimited on tbs_mini_prj;
grant CREATE session, CREATE sequence, CREATE table, CREATE procedure, connect, resource, CREATE view, CREATE database link, CREATE synonym, CREATE snapshot to moderateur_bourse_etranger; 


---sqlplus system/aissam
---grant CREATE synonym to moderateur_bourse_marocaine; 

---sqlplus system/aissam
---grant CREATE SYNONYM to moderateur_bourse_etranger; 

 -------------------------Creation des tables -------------------------
connect admin_bourse/aissam
SELECT * FROM cat;
clear screen


DROP TABLE Etudiants_Universites;
DROP TABLE Bourses;
DROP TABLE Universites;
DROP TABLE Etudiants;

CREATE TABLE Etudiants(
  ID_etudiant NUMBER primary key,
  Nom VARCHAR2(20),
  Prenom VARCHAR2(20),
  Nationalite VARCHAR2(20),
  Etranger NUMBER(1) CHECK(Etranger IN(0, 1))
);

CREATE TABLE Universites(
  ID_universite NUMBER primary key,
  Nom VARCHAR2(100),
  Localisation VARCHAR2(30)
);

CREATE TABLE Etudiants_Universites(
  ID_etudiant NUMBER,
  ID_universite NUMBER,
  CONSTRAINT PK_Etudiant_Universite PRIMARY KEY(ID_etudiant, ID_universite),
  CONSTRAINT FK_EtudUniv_Etudiants FOREIGN KEY(ID_etudiant) REFERENCES Etudiants(ID_etudiant),
  CONSTRAINT FK_EtudUniv_Universite FOREIGN KEY(ID_universite) REFERENCES Universites(ID_universite)
);
--// update: I forget "ON delete cascade" 
alter table Etudiants_Universites drop constraint FK_EtudUniv_Etudiants;
alter table Etudiants_Universites drop constraint FK_EtudUniv_Universite;
alter table Etudiants_Universites add CONSTRAINT FK_EtudUniv_Etudiants FOREIGN KEY (ID_etudiant) REFERENCES Etudiants(ID_etudiant) ON DELETE CASCADE;
alter table Etudiants_Universites add CONSTRAINT FK_EtudUniv_Universite FOREIGN KEY (ID_universite) REFERENCES Universites(ID_universite) ON DELETE CASCADE;




CREATE TABLE Bourses(
  ID_bourse NUMBER primary key,
  ID_etudiant NUMBER,
  Nom VARCHAR2(100),
  Montant NUMBER,
  CONSTRAINT FK_Brs_Etud FOREIGN KEY(ID_etudiant) REFERENCES Etudiants(ID_etudiant)
);
--// update: I forget "ON delete cascade" 
alter table Bourses drop constraint FK_Brs_Etud;
alter table Bourses add CONSTRAINT FK_Brs_Etud FOREIGN KEY (ID_etudiant) REFERENCES Etudiants(ID_etudiant) ON DELETE CASCADE;



SELECT * FROM cat;
--SELECT TABLE_NAME FROM user_tables;

 -------------------------Inserer dans les tables -------------------------
 --Creation des sequences 
clear screen

drop sequence sq_Id_Etudiant;
drop sequence sq_Id_Univetsite;
drop sequence sq_Id_Bourse;

 --Creation des sequences 
CREATE sequence sq_Id_Etudiant increment by 1 start with 1;

CREATE sequence sq_Id_Univetsite increment by 1 start with 1;

CREATE sequence sq_Id_Bourse increment by 1 start with 1;

delete FROM Etudiants_Universites;
delete FROM Bourses;
delete FROM Universites;
delete FROM Etudiants;

--INSERT INTO Etudiants(ID_etudiant, Nom, Prenom, Nationalite, Etranger)
--drop table Etudiants;
INSERT INTO Etudiants VALUES(sq_Id_Etudiant.NEXTVAL, 'EN-NAHEL', 'Aissam', 'Marocain', 0);
INSERT INTO Etudiants VALUES(sq_Id_Etudiant.NEXTVAL, 'Amina', 'Ben-Ali', 'Marocaine', 0);
INSERT INTO Etudiants VALUES(sq_Id_Etudiant.NEXTVAL, 'Karim', 'Ben-Moussa', 'Marocain', 0);
INSERT INTO Etudiants VALUES(sq_Id_Etudiant.NEXTVAL, 'Fatima', 'Ben-Mohammed', 'Marocaine', 0);
INSERT INTO Etudiants VALUES(sq_Id_Etudiant.NEXTVAL, 'Elon', 'Musk', 'Americain', 1);
INSERT INTO Etudiants VALUES(sq_Id_Etudiant.NEXTVAL, 'John', 'Smith', 'Britannique', 1);
INSERT INTO Etudiants VALUES(sq_Id_Etudiant.NEXTVAL, 'Mark', 'Zuckerberg', 'Chinois', 1);
set lines 150
SELECT * FROM Etudiants;

------ 7 etudiants

-- INSERT INTO Universites(ID_universite, Nom, Localisation)
--drop table Universites;
INSERT INTO Universites VALUES(sq_Id_Univetsite.NEXTVAL, 'Universite Ibn Zohr', 'Agadir');
INSERT INTO Universites VALUES(sq_Id_Univetsite.NEXTVAL, 'Universite Cadi Ayyad', 'Marrakech');
INSERT INTO Universites VALUES(sq_Id_Univetsite.NEXTVAL, 'Universite Hassan II', 'Casablanca');
INSERT INTO Universites VALUES(sq_Id_Univetsite.NEXTVAL, 'Universite Mohammed V', 'Rabat');
INSERT INTO Universites VALUES(sq_Id_Univetsite.NEXTVAL, 'Universite Moulay Ismail', 'Meknes');
INSERT INTO Universites VALUES(sq_Id_Univetsite.NEXTVAL, 'Universite Al Quaraouiyine', 'Fes');
col Nom for A40
SELECT * FROM Universites;
------ 6 Universites

--INSERT INTO Etudiants_Universites(ID_etudiant, ID_universite)
--drop table Etudiants_Universites;
INSERT INTO Etudiants_Universites VALUES(1, 1);
INSERT INTO Etudiants_Universites VALUES(2, 2);
INSERT INTO Etudiants_Universites VALUES(3, 3);
INSERT INTO Etudiants_Universites VALUES(4, 2);
INSERT INTO Etudiants_Universites VALUES(5, 4);
INSERT INTO Etudiants_Universites VALUES(6, 5);
INSERT INTO Etudiants_Universites VALUES(7, 6);
SELECT * FROM Etudiants_Universites;
------ 

--INSERT INTO Bourses(ID_bourse, ID_etudiant, Nom, Montant)
--drop table Bourses;
INSERT INTO Bourses VALUES(sq_Id_Bourse.NEXTVAL, 1, 'Bourses Minhaty', 1500);
INSERT INTO Bourses VALUES(sq_Id_Bourse.NEXTVAL, 2, 'Bourse Istihqaq', 3000);
INSERT INTO Bourses VALUES(sq_Id_Bourse.NEXTVAL, 3, 'Bourses FME', 2000);
INSERT INTO Bourses VALUES(sq_Id_Bourse.NEXTVAL, 4, 'Bourse AMCI', 4000);
INSERT INTO Bourses VALUES(sq_Id_Bourse.NEXTVAL, 5, 'Bourses Minhaty', 1500);
INSERT INTO Bourses VALUES(sq_Id_Bourse.NEXTVAL, 6, 'Bourse AMCI', 4000);
INSERT INTO Bourses VALUES(sq_Id_Bourse.NEXTVAL, 7, 'Bourses Minhaty', 1500);
SELECT * FROM Bourses;
------ 

commit;


----------------------------- how to know the service name : -----
								connect system/aissam

								DESC v$database;

								SELECT NAME FROM v$database;

								/*
								SQL> SELECT NAME FROM v$database;

								NAME
								---------
								XE
								*/

-------------------------------------------------------------------
-------------------------------------------------------------------
-------------------------------------------------------------------

-- ////// Action 1 : Go to moderateur_bourses_marocaine.sql , and complete \\\\\\


------ Creation du lien inter-base (database link)
clear screen

SELECT * FROM Etudiants_Maroc;

CREATE database link dbl_marocaine_to_maroc connect to moderateur_bourse_marocaine identIFied by aissam using 'xe';

col db_link for A30
col host for A20
SELECT * FROM USER_DB_LINKS;

SELECT * FROM Etudiants_Maroc@dbl_marocaine_to_maroc;
--drop database link dbl_marocaine_to_maroc;

------ Creation du lien inter-base (database link)
clear screen

SELECT * FROM Etudiants_Etrangers;

CREATE database link dbl_etranger_to_etranger connect to moderateur_bourse_etranger identIFied by aissam using 'xe';

---col db_link for A30
---col host for A20
SELECT * FROM USER_DB_LINKS;

SELECT * FROM Etudiants_Etrangers@dbl_etranger_to_etranger;
--drop database link dbl_etranger_to_etranger;



--- Creation des synonymes et vues :
--- Synonymes aux tables :

CREATE OR REPLACE synonym Etudiants_Maroc FOR moderateur_bourse_marocaine.Etudiants_Maroc@dbl_marocaine_to_maroc;
SELECT * FROM Etudiants_Maroc;

CREATE OR REPLACE synonym Universites_Maroc FOR moderateur_bourse_marocaine.Universites_Maroc@dbl_marocaine_to_maroc;
SELECT * FROM Universites_Maroc;

CREATE OR REPLACE synonym Etud_Univ_Maroc FOR moderateur_bourse_marocaine.Etud_Univ_Maroc@dbl_marocaine_to_maroc;
SELECT * FROM Etud_Univ_Maroc;

CREATE OR REPLACE synonym Bourses_Maroc FOR moderateur_bourse_marocaine.Bourses_Maroc@dbl_marocaine_to_maroc;
SELECT * FROM Bourses_Maroc;


CREATE OR REPLACE synonym Etudiants_Etrangers FOR moderateur_bourse_etranger.Etudiants_Etrangers@dbl_etranger_to_etranger;
SELECT * FROM Etudiants_Etrangers;

CREATE OR REPLACE synonym Universites_Etrangers FOR moderateur_bourse_etranger.Universites_Etrangers@dbl_etranger_to_etranger;
SELECT * FROM Universites_Etrangers;

CREATE OR REPLACE synonym Etud_Univ_Etrangers FOR moderateur_bourse_etranger.Etud_Univ_Etrangers@dbl_etranger_to_etranger;
SELECT * FROM Etud_Univ_Etrangers;

CREATE OR REPLACE synonym Bourses_Etrangers FOR moderateur_bourse_etranger.Bourses_Etrangers@dbl_etranger_to_etranger;
SELECT * FROM Bourses_Etrangers;




---------------------------------------------------------------
---------------------------------------------------------------
---------------------------------------------------------------
---------------------------------------------------------------
---------------------------------------------------------------

-- ////// Action 4 : Go to moderateur_bourse_marocaine.sql , then complet from Action2 \\\\\\


---------------------------------------------------------------
---------------------------------------------------------------
---------------------------------------------------------------
---------------------------------------------------------------
---------------------------------------------------------------

-- 

Clear Screen
DESC Etudiants
DESC Etudiants_Maroc
DESC Etudiants_Etrangers


CREATE OR REPLACE TRIGGER TRIGGER_INSERT_etudiant
BEFORE INSERT ON Etudiants
FOR EACH ROW
DECLARE
	n NUMBER;
	etudiant_duplique EXCEPTION;
BEGIN
  SELECT COUNT(*) INTO n FROM Etudiants WHERE Nom = :NEW.Nom AND Prenom = :NEW.Prenom;
  IF n = 0 THEN
	IF :NEW.Etranger = 0 THEN
	  -- verifier si la ligne inseree est pour un etudiant marocain
	  -- Inserer la ligne dans la table Etudiants_Maroc
	  INSERT INTO Etudiants_Maroc VALUES (:NEW.ID_etudiant, :NEW.Nom, :NEW.Prenom, :NEW.Nationalite, :NEW.Etranger);
	ELSE
	  -- si la ligne inseree est pour un etudiant etranger
	  -- Inserer la ligne dans la table Etudiants_Etranger
	  INSERT INTO Etudiants_Etrangers VALUES (:NEW.ID_etudiant, :NEW.Nom, :NEW.Prenom, :NEW.Nationalite, :NEW.Etranger);
	END IF;
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

SELECT * FROM Etudiants;
SELECT * FROM Etudiants_Maroc;
SELECT * FROM Etudiants_Etrangers;

INSERT INTO Etudiants VALUES(sq_Id_Etudiant.NEXTVAL, 'EN-NAHEL', 'Aissam', 'Marocain', 0);

INSERT INTO Etudiants VALUES(sq_Id_Etudiant.NEXTVAL, 'EN-NAHEL', 'Hassan', 'Marocain', 0);

SELECT * FROM Etudiants;
SELECT * FROM Etudiants_Maroc;
SELECT * FROM Etudiants_Etrangers;

INSERT INTO Etudiants VALUES(sq_Id_Etudiant.NEXTVAL, 'Bill', 'Gates', 'Allemand', 1);

SELECT * FROM Etudiants;
SELECT * FROM Etudiants_Maroc;
SELECT * FROM Etudiants_Etrangers;







Clear Screen
DESC Universites
DESC Universites_Maroc
DESC Universites_Etrangers

CREATE OR REPLACE TRIGGER TRIGGER_INSERT_universite
BEFORE INSERT ON Universites
FOR EACH ROW
DECLARE
	n NUMBER;
	universites_duplique EXCEPTION;
BEGIN
  SELECT COUNT(*) INTO n FROM Universites WHERE Nom = :NEW.Nom;
  IF n = 0 THEN
	-- verifier si aucune universite avec le même nom n'existe deja
    -- Inserer la ligne dans les tables Universites_Maroc et Universites_Etrangers
	INSERT INTO Universites_Maroc VALUES (:NEW.ID_universite, :NEW.Nom, :NEW.Localisation);
	INSERT INTO Universites_Etrangers VALUES (:NEW.ID_universite, :NEW.Nom, :NEW.Localisation);
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
SELECT * FROM Universites;
SELECT * FROM Universites_Maroc;
SELECT * FROM Universites_Etrangers;

INSERT INTO Universites VALUES(sq_Id_Univetsite.NEXTVAL, 'Université Ibn Zohr', 'Fes');

INSERT INTO Universites VALUES(sq_Id_Univetsite.NEXTVAL, 'Université Hassan 3', 'Fes');

SELECT * FROM Universites;
SELECT * FROM Universites_Maroc;
SELECT * FROM Universites_Etrangers;

/*
delete from Universites where nom = 'Universites Ibn Tofayl';
delete from Universites where nom = 'Universite Ibn Zohr';
delete from Universites where nom = 'Universites Hassan 3';
 
delete from Universites_Maroc where nom = 'Universites Ibn Tofayl';
delete from Universites_Maroc where nom = 'Universite Ibn Zohr';
delete from Universites_Maroc where nom = 'Universites Hassan 3';
 
delete from Universites_Etrangers where nom = 'Universites Ibn Tofayl';
delete from Universites_Etrangers where nom = 'Universite Ibn Zohr';
delete from Universites_Etrangers where nom = 'Universites Hassan 3';

delete from Universites where ID_UNIVERSITE = 8;
delete from Universites_Maroc where ID_UNIVERSITE = 8;
delete from Universites_Etrangers where ID_UNIVERSITE = 8;
*/






Clear Screen
DESC Etudiants_Universites
DESC Etud_Univ_Maroc
DESC Etud_Univ_Etrangers

CREATE OR REPLACE TRIGGER TRIGGER_INSERT_etud_univ
BEFORE INSERT ON Etudiants_Universites
FOR EACH ROW
DECLARE
	n NUMBER;
	temp_etranger NUMBER := -1;
	etudiant_n_exist EXCEPTION;
	etudiant_deja EXCEPTION;
BEGIN
  SELECT COUNT(*) INTO n FROM Etudiants_Universites WHERE ID_etudiant = :NEW.ID_etudiant;
  IF n = 0 THEN
	SELECT Etranger INTO temp_etranger FROM Etudiants WHERE Etudiants.ID_etudiant = :NEW.ID_etudiant;
	IF temp_etranger = 0 THEN
	  -- verifier si l'etudiant exist et de nationalite marocaine
	  -- Inserer la ligne dans la table Etud_Univ_Maroc
	  INSERT INTO Etud_Univ_Maroc VALUES (:NEW.ID_etudiant, :NEW.ID_universite);
	ELSIF temp_etranger = 1 THEN
	  -- si l'etudiant est etranger
	  -- Inserer la ligne dans la table Etud_Univ_Etrangers
	  INSERT INTO Etud_Univ_Etrangers VALUES (:NEW.ID_etudiant, :NEW.ID_universite);
    ELSE
	  -- si l'etudiant n'exist pas
	  RAISE etudiant_n_exist;
    END IF;
  ELSE
	-- si l'etudiant exist deja
	RAISE etudiant_deja;
  END IF;
EXCEPTION
	WHEN etudiant_n_exist THEN
	  RAISE_APPLICATION_ERROR(-20003, '!!! L''etudiant n''exist pas!!!');
	WHEN etudiant_deja THEN
	  RAISE_APPLICATION_ERROR(-20004, '!!! L''etudiant est deja exist !!!');
END;
/

----Test

Clear Screen
SELECT * FROM Etudiants;
SELECT * FROM Universites;

SELECT * FROM Etudiants_Universites;
SELECT * FROM Etud_Univ_Maroc;
SELECT * FROM Etud_Univ_Etrangers;

SELECT * FROM Etudiants

INSERT INTO Etudiants_Universites VALUES(55, 22);

INSERT INTO Etudiants_Universites VALUES(1, 1);
INSERT INTO Etudiants_Universites VALUES(11, 22);
INSERT INTO Etudiants_Universites VALUES(12, 22);

SELECT * FROM Etudiants_Universites;
SELECT * FROM Etud_Univ_Maroc;
SELECT * FROM Etud_Univ_Etrangers;


/*
DELETE from Etudiants_Universites WHERE ID_ETUDIANT=11;
DELETE from Etudiants_Universites WHERE ID_ETUDIANT=12;
DELETE from Etud_Univ_Maroc WHERE ID_ETUDIANT=11;
DELETE from Etud_Univ_Etrangers WHERE ID_ETUDIANT=12;
*/


----------------------------------------------------
----------------------------------------------------


Clear Screen
DESC Bourses
DESC Bourses_Maroc
DESC Bourses_Etrangers

CREATE OR REPLACE TRIGGER TRIGGER_INSERT_bourses
BEFORE INSERT ON Bourses
FOR EACH ROW
DECLARE
	n NUMBER;
	temp_etranger NUMBER;
	etudiant_deja EXCEPTION;
BEGIN
  SELECT COUNT(*) INTO n FROM Bourses WHERE ID_etudiant = :NEW.ID_etudiant;
  IF n = 0 THEN
	  SELECT Etranger INTO temp_etranger FROM Etudiants WHERE Etudiants.ID_etudiant = :NEW.ID_etudiant;
	  IF temp_etranger = 0 THEN
		-- verifier si l'etudiant exist et de nationalite marocaine
		-- Inserer la ligne dans la table Bourses_Maroc
		INSERT INTO Bourses_Maroc VALUES (:NEW.ID_bourse, :NEW.ID_etudiant, :NEW.Nom, :NEW.Montant);
	  ELSE
		-- si l'etudiant est etranger
		-- Inserer la ligne dans la table Bourses_Etrangers
		INSERT INTO Bourses_Etrangers VALUES (:NEW.ID_bourse, :NEW.ID_etudiant, :NEW.Nom, :NEW.Montant);
	  END IF;
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

SELECT * FROM Bourses;
SELECT * FROM Bourses_Maroc;
SELECT * FROM Bourses_Etrangers;


INSERT INTO Bourses VALUES(sq_Id_Bourse.NEXTVAL, 1, 'Bourses Minhaty', 5000 );

INSERT INTO Bourses VALUES(sq_Id_Bourse.NEXTVAL, 9, 'Bourses Minhaty', 5000 );
INSERT INTO Bourses VALUES(sq_Id_Bourse.NEXTVAL, 11, 'Bourses Minhaty', 5000 );
INSERT INTO Bourses VALUES(sq_Id_Bourse.NEXTVAL, 12, 'Bourses Minhaty', 5000 );

SELECT * FROM Bourses;
SELECT * FROM Bourses_Maroc;
SELECT * FROM Bourses_Etrangers;



/*
delete from Bourses where ID_BOURSE = 22;
delete from Bourses where ID_BOURSE = 23;
delete from Bourses where ID_BOURSE = 24;
delete from Bourses_Maroc where ID_BOURSE = 22;
delete from Bourses_Maroc where ID_BOURSE = 23;
delete from Bourses_Etrangers where ID_BOURSE = 24;


*/
---------------------------------------------------------------
---------------------------------------------------------------
---------------------------------------------------------------
---------------------------------------------------------------
---------------------------------------------------------------


CREATE OR REPLACE TRIGGER TRIGGER_DELETE_etudiant
BEFORE DELETE ON Etudiants
FOR EACH ROW
BEGIN
  IF :OLD.Etranger = 0 THEN
    -- supprimer la ligne dans la table Etudiants_Maroc
    DELETE FROM Etudiants_Maroc WHERE ID_etudiant = :OLD.ID_etudiant;
  ELSE
    -- supprimer la ligne dans la table Etudiants_Etrangers
    DELETE FROM Etudiants_Etrangers WHERE ID_etudiant = :OLD.ID_etudiant;
  END IF;
END;
/
-- DARORI MN ON DELETE CASCADE ikono f defenition dyal tables foreign key
select * from ETUDIANTS;
delete from ETUDIANTS where ID_ETUDIANT = 21;
commit;
--select * from Etudiants_Maroc;
--select * from Etudiants_Etrangers;


---------------------------------------------------------------


CREATE OR REPLACE TRIGGER TRIGGER_DELETE_universite
AFTER DELETE ON Universites
FOR EACH ROW
BEGIN
  -- supprimer la ligne dans la table Universites_Maroc
  DELETE FROM Universites_Maroc WHERE ID_universite = :OLD.ID_universite;
  -- supprimer la ligne dans la table Universites_Etrangers
  DELETE FROM Universites_Etrangers WHERE ID_universite = :OLD.ID_universite;
END;
/

select * from Universites;
DELETE FROM Universites WHERE ID_UNIVERSITE = 6;
COMMIT;

select * from Universites;
-- select * from Universites_Maroc;
-- select * from Universites_Etrangers;





---------------------------------------------------------------


select * from Bourses;

CREATE OR REPLACE TRIGGER TRIGGER_DELETE_bourse
AFTER DELETE ON Bourses
FOR EACH ROW
BEGIN
  -- supprimer la ligne dans la table Bourses_Maroc
  DELETE FROM Bourses_Maroc WHERE ID_Bourse = :OLD.ID_Bourse;
  -- supprimer la ligne dans la table Bourses_Etrangers
  DELETE FROM Bourses_Etrangers WHERE ID_Bourse = :OLD.ID_Bourse;
END;
/

DELETE FROM Bourses WHERE ID_Bourse = 32;
commit;

select * from Bourses;
-- select * from Bourses_Maroc;
-- select * from Bourses_Etrangers;
-- SCREENED






CREATE OR REPLACE TRIGGER TRIGGER_DELETE_etud_univ
AFTER DELETE ON Etudiants_Universites
FOR EACH ROW
BEGIN
  -- supprimer la ligne dans la table Etud_Univ_Maroc
  DELETE FROM Etud_Univ_Maroc WHERE ID_etudiant = :OLD.ID_etudiant;
  -- supprimer la ligne dans la table Etud_Univ_Etrangers
  DELETE FROM Etud_Univ_Etrangers WHERE ID_etudiant = :OLD.ID_etudiant;
END;
/


select * from Etudiants_Universites;
DELETE FROM Etudiants_Universites WHERE ID_etudiant = 11;
commit;

select * from Etudiants_Universites;
-- select * from Etud_Univ_Maroc;
-- select * from Etud_Univ_Etrangers;






--- Procedure :

CLEAR SCREEN

CREATE OR REPLACE PROCEDURE INSERT_etudiant(p_Nom Etudiants.Nom%TYPE, 
                    p_Prenom Etudiants.Prenom%TYPE, 
                    p_Nationalite Etudiants.Nationalite%TYPE, 
                    p_Etranger Etudiants.Etranger%TYPE ) AS					
  n NUMBER := 0;
  etudiant_duplique EXCEPTION;

  BEGIN
	SELECT COUNT(*) INTO n FROM Etudiants WHERE Nom = p_Nom AND Prenom = p_Prenom;
	IF n = 0 THEN
	  -- verifier si l'etudiant n'existe pas
      -- Inserer la ligne dans la table Etudiants
	  INSERT INTO Etudiants VALUES(sq_Id_Etudiant.NEXTVAL, p_Nom, p_Prenom, p_Nationalite, p_Etranger);
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


SELECT * FROM Etudiants;
SELECT * FROM Etudiants_Maroc;
SELECT * FROM Etudiants_Etrangers;

BEGIN
	INSERT_etudiant('EN-NAHEL', 'Med', 'Marocain', 0);
	INSERT_etudiant('Steve', 'Jobs', 'American', 1);
END;
/

SELECT * FROM Etudiants;
SELECT * FROM Etudiants_Maroc;
SELECT * FROM Etudiants_Etrangers;



CLEAR SCREEN
CREATE OR REPLACE PROCEDURE INSERT_universite(p_Nom Universites.Nom%TYPE, 
                        p_Localisation Universites.Localisation%TYPE ) AS					
  n NUMBER := 0;
  universite_duplique EXCEPTION;
	
  BEGIN
    SELECT COUNT(*) INTO n FROM Universites WHERE Nom = p_Nom;
    IF n = 0 THEN
	  -- verifier si l'universite n'existe pas
      -- Inserer la ligne dans la table Universites
      INSERT INTO Universites VALUES(sq_Id_Univetsite.NEXTVAL, p_Nom, p_Localisation);
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

SELECT * FROM Universites;
SELECT * FROM Universites_Maroc;
SELECT * FROM Universites_Etrangers;

BEGIN
	INSERT_universite('Universite Hassan 4', 'Meknes');
END;
/

SELECT * FROM Universites;
SELECT * FROM Universites_Maroc;
SELECT * FROM Universites_Etrangers;




CLEAR SCREEN
CREATE OR REPLACE PROCEDURE INSERT_etudiant_universite(p_ID_etudiant Etudiants_Universites.ID_etudiant%TYPE, 
                            p_ID_universite Etudiants_Universites.ID_universite%TYPE ) AS 
  n NUMBER := 0;
  est_duplique EXCEPTION;
  
  BEGIN
    SELECT COUNT(*) INTO n FROM Etudiants_Universites WHERE ID_etudiant = p_ID_etudiant AND ID_universite = p_ID_universite;
    IF n = 0 THEN
	  -- verifier si l'etudiant n'est pas enregistre dans une universite
      -- Inserer la ligne dans la table Etudiants_Universites
      INSERT INTO Etudiants_Universites VALUES(p_ID_etudiant, p_ID_universite);
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
SELECT * FROM Etudiants_Universites;
SELECT * FROM Etud_Univ_Maroc;
SELECT * FROM Etud_Univ_Etrangers;
SELECT * FROM Universites;

BEGIN
	INSERT_etudiant_universite(21, 23);
	INSERT_etudiant_universite(22, 23);
END;
/

SELECT * FROM Etudiants_Universites;
SELECT * FROM Etud_Univ_Maroc;
SELECT * FROM Etud_Univ_Etrangers;




CLEAR SCREEN
CREATE OR REPLACE PROCEDURE INSERT_bourse(p_ID_etudiant Bourses.ID_etudiant%TYPE, 
                      p_Nom Bourses.Nom%TYPE, 
                      p_Montant Bourses.Montant%TYPE ) AS 
  n NUMBER := 0;
  est_duplique EXCEPTION;
  
  BEGIN
    SELECT COUNT(*) INTO n FROM Bourses WHERE Nom = p_Nom AND ID_etudiant = p_ID_etudiant;
    IF n = 0 THEN
	  -- verifier si l'etudiant n'est pas enregistre dans une bourse
      -- Inserer la ligne dans la table Bourses
      INSERT INTO Bourses VALUES(sq_Id_Bourse.NEXTVAL, p_ID_etudiant, p_Nom, p_Montant);
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
SELECT * FROM Bourses;
SELECT * FROM Bourses_Maroc;
SELECT * FROM Bourses_Etrangers;

BEGIN
	INSERT_bourse(21, 'Bourseses FME', 3500);
	INSERT_bourse(22, 'Bourseses FME', 3500);
END;
/

SELECT * FROM Bourses;
SELECT * FROM Bourses_Maroc;
SELECT * FROM Bourses_Etrangers;


-- ////// Action 7 : Go to moderateur_bourse_marocaine.sql , then complet from Action5 \\\\\\

