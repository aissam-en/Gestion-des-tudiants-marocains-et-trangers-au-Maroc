
 ----------------------------- -------------------------
clear screen

sqlplus moderateur_bourse_etranger/aissam
--- connect moderateur_bourse_etranger/aissam
/*
--afficher tous les triggers :
SELECT trigger_name FROM user_triggers;
SELECT trigger_name, table_name, triggering_event FROM user_triggers;
--To delete a trigger in SQL*Plus, you can use the DROP TRIGGER command. The syntax for this command is as follows:
DROP TRIGGER TRIGGER_INSERT_ETUDIANT;
*/

select * from cat;




----------------------------- how to know the service name : -----
								connect system/aissam

								desc v$database;

								select NAME from v$database;

								/*
								SQL> select NAME from v$database;

								NAME
								---------
								XE
								*/

-------------------------------------------------------------------
-------------------------------------------------------------------
-------------------------------------------------------------------
clear screen

Copy from admin_bourse/aissam@xe To moderateur_bourse_etranger/aissam@xe Replace Etudiants_Etrangers using select ID_etudiant, Nom, Prenom, Nationalite, Etranger from Etudiants where Etranger = 1;

desc Etudiants_Etrangers 
set lines 150
select * from Etudiants_Etrangers;
-----------------------------

Copy from admin_bourse/aissam@xe To moderateur_bourse_etranger/aissam@xe Replace Universites_Etrangers using select ID_universite, Nom, Localisation from Universites;

desc Universites_Etrangers
select * from Universites_Etrangers;
------------------------------


Copy from admin_bourse/aissam@xe To moderateur_bourse_etranger/aissam@xe Replace Etud_Univ_Etrangers using select ID_etudiant, ID_universite from Etudiants_Universites where ID_etudiant in (select ID_etudiant from Etudiants where Etranger = 1);

desc Etud_Univ_Etrangers 
select * from Etud_Univ_Etrangers;
------------------------------


Copy from admin_bourse/aissam@xe To moderateur_bourse_etranger/aissam@xe Replace Bourses_Etrangers using select ID_bourse, ID_etudiant, Nom, Montant from Bourses where ID_etudiant in (select ID_etudiant from Etudiants where Etranger = 1);

desc Bourses_Etrangers 
select * from Bourses_Etrangers;

commit; 

------------------------------------------------------------
------------------------------------------------------------
------------------------------------------------------------
------------------------------------------------------------

-- ////// Action 3 : Go to admin_bourse.sql , then complet from Action1 \\\\\\



------ Création du lien inter-base (database link)

-------------------------------------------------------
clear screen
select * from Etudiants;

create database link dbl_etranger_to_admin connect to admin_bourse identified by aissam using 'xe';

col db_link for A30
col host for A20
select * from USER_DB_LINKS;

select * from Etudiants@dbl_etranger_to_admin;

--drop database link dbl_etranger_to_admin;



-------------------------------------------------------
-------------------------------------------------------


--- Création sur le site2 moderateur_bourse_etranger/aissam des synonymes aux tables hébergées sur le central site
--- admin_bourse/aissam, avec teste de bon fonctionnement des synonymes :

create or REPLACE synonym Etudiants FOR Etudiants@dbl_etranger_to_admin; 
select * from Etudiants;

create or REPLACE synonym Universites FOR Universites@dbl_etranger_to_admin; 
select * from Universites;

create or REPLACE synonym Etudiants_Universites FOR Etudiants_Universites@dbl_etranger_to_admin; 
select * from Etudiants_Universites;

create or REPLACE synonym Bourses FOR Bourses@dbl_etranger_to_admin; 
select * from Bourses;



create or REPLACE synonym sq_Id_Etudiant FOR sq_Id_Etudiant@dbl_etranger_to_admin;  
--select * from sq_Id_Etudiant;
---desc sq_Id_Etudiant;

create or REPLACE synonym sq_Id_Univetsite FOR sq_Id_Univetsite@dbl_etranger_to_admin;  
--select * from sq_Id_Univetsite;
---desc sq_Id_Univetsite;

create or REPLACE synonym sq_Id_Bourse FOR sq_Id_Bourse@dbl_etranger_to_admin;  
--select * from sq_Id_Bourse;
--desc sq_Id_Bourse;


-------------------------------------------------------
-------------------------------------------------------
-------------------------------------------------------
--bme :
--IV- Duplication et réplication : materialized view, snapshots :
--Créer une vue matérialisée (mono-table??) Universites_view 
-- sur site moderateur_bourse_etranger/aissam permettant de dupliquer la table Universites de site admin_bourse/aissam. 
-- Avec un 'rafraîchissement' toutes les cinq minutes 1/24/12
------- ERRRRRRRROOOOOOOOOOOORRRRRRRRRR <======
create snapshot Universites_view
refresh fast
start with SYSDATE next SYSDATE+1/24/12
with primary key
as select * from Universites;
--- EROOOOOOOOOOOR :
/*
ERROR at line 1:
ORA-23413: table "ADMIN_BOURSE"."UNIVERSITES" does not have a materialized view log

Omlli kandir f admin_bourse/aissam :
SQL> CREATE MATERIALIZED VIEW LOG ON Universites;
CREATE MATERIALIZED VIEW LOG ON Universites
*
ERROR at line 1:
ORA-00439: feature not enabled: Advanced replication

--- [:-(] M A KH D A M A CH 
--- [:-(] ana khdam b Oracle Database 11g Express Edition Release 11.2.0.2.0 - 64bit Production (Oracle Database 11g Express Edition (XE))
--- o  the "Advanced Replication" feature is only available in the Enterprise Edition of Oracle Database.
*/


------------------------------------------------------------
------------------------------------------------------------


-- Ajout des contraintes de base :
-- 1- Recréer les contraintes clés primaires et clés étrangères :

-- Sur le site 2 moderateur_bourse_etranger/aissam :
alter table Bourses_Etrangers add primary key (ID_bourse);
alter table Etudiants_Etrangers add primary key (ID_etudiant);
alter table Etud_Univ_Etrangers add primary key (ID_etudiant, ID_universite);
alter table Universites_Etrangers add primary key (ID_universite);

-- 2- Les Contraintes de Références classiques si la table ‘père’ est sur le même site :
-- Sur le site 2 moderateur_bourse_etranger/aissam :
--alter table Bourses_Etrangers add foreign key(ID_etudiant) references Etudiants_Etrangers(ID_etudiant);
--alter table Etud_Univ_Etrangers add foreign key (ID_etudiant) references Etudiants_Etrangers(ID_etudiant);
--alter table Etud_Univ_Etrangers add foreign key (ID_universite) references Universites_Etrangers(ID_universite);
--//mli bghit ndir trigger_delete_### tlbo mni bli khass ikon ON delet cascade
SELECT constraint_name, delete_rule FROM all_constraints WHERE table_name = 'BOURSES_ETRANGERS' AND constraint_type = 'R';
alter table Bourses_Etrangers drop constraint SYS_C007803;
alter table Bourses_Etrangers add constraint fk_bourses_etr_etud foreign key(ID_etudiant) references Etudiants_Etrangers(ID_etudiant) on delete cascade;

SELECT constraint_name, delete_rule FROM all_constraints WHERE table_name = 'ETUD_UNIV_ETRANGERS' AND constraint_type = 'R';
alter table Etud_Univ_Etrangers drop constraint SYS_C007804;
alter table Etud_Univ_Etrangers drop constraint SYS_C007805;
alter table Etud_Univ_Etrangers add constraint fk_etud_univ_etr_etud foreign key (ID_etudiant) references Etudiants_Etrangers(ID_etudiant) on delete cascade;
alter table Etud_Univ_Etrangers add constraint fk_etud_univ_etr_univ foreign key (ID_universite) references Universites_Etrangers(ID_universite) on delete cascade;




-- ////// Action 6 : Go to admin_bourse.sql , then complet from Action4 (create TRIGGERs + )\\\\\\





-- ===>
-------!!!!!!! Go to new_117.sql !!!!!!!-------









-------!!!!!!! Dont use those : 
-- 
CREATE OR REPLACE TRIGGER trigger_insert_etudiant
AFTER INSERT ON Etudiants_Etrangers
FOR EACH ROW

temp Number := 0;

BEGIN

  select ID_etudiant into temp from Etudiants where Etudiants.ID_etudiant = :NEW.ID_etudiant;
  IF temp  = 0 THEN
	  -- Check if the inserted row is for a Marocain student
	  INSERT INTO Etudiants VALUES (:NEW.ID_etudiant, :NEW.Nom, :NEW.Prenom, :NEW.Nationalite, :NEW.Etranger);
  ELSE 
	 dbms_output.put_line('!!! L''etudient est deja exist !!!');
END;
/

----Test
insert into Etudiants_Etrangers values(sq_Id_Etudiant.nextval, 'NAHEL', 'Med', 'Marocain', 0); 
--- WACH sq_Id_Etudiant ghatkhdem f Site 1 et Site 2 ??? 7itach hiya kayna f Site 0
select * from Etudiants_Etrangers;
select * from Etudiants;
----------------------------------------------------
----------------------------------------------------
---------

--- hado mn 3ndi(+gpt) :
-- 7utach MATERIALIZED VIEW makhdamach 3ndi , ghaykhsni ndir trigger_insert_universite
CREATE OR REPLACE TRIGGER trigger_insert_universite
AFTER INSERT ON Universites_Etrangers
FOR EACH ROW

n Number := 0;
universites_duplique exception;

BEGIN
  select count(*) into n from Universites_Etrangers where Nom = :NEW.Nom;
  IF n  = 0 THEN
	  INSERT INTO Universites VALUES (:NEW.ID_universite :NEW.Nom, :NEW.Localisation);
  ELSE 
	 raise universites_duplique;
  END IF;
Exception
	when universites_duplique then
		dbms_output.put_line('!!! L''universites "'||:NEW.Nom||'" est deja exist !!!');
END;
/
----Test
insert into Universites_Etrangers values(sq_Id_Univetsite.nextval, 'Universites Ibn Tofayl', 'Sale');
select * from Universites_Etrangers;
select * from Universites;
----------------------------------------------------
----------------------------------------------------



CREATE OR REPLACE TRIGGER trigger_insert_etud_univ
AFTER INSERT ON Etud_Univ_Etrangers
FOR EACH ROW
temp_etranger Number := -1;
BEGIN
  SELECT Etranger into temp_etranger FROM Etudiants WHERE Etudiants.ID_etudiant = :NEW.ID_etudiant;
  -- Check if the inserted row is for a Marocain student
  IF temp_etranger = 0 THEN
    -- Insert the row into the Etudiants_Maroc table
    INSERT INTO Etudiants_Universites VALUES (:NEW.ID_etudiant, :NEW.ID_universite);
  ELSE
	dbms_output.put_line('!!! L''etudient n''exist pas !!!');
  END IF;
END;
/

----Test
insert into Etud_Univ_Etrangers values(1,2);
insert into Etud_Univ_Etrangers values(111,2);
--insert into Etud_Univ_Maroc(ID_etudiant, ID_universite)
select * from Etud_Univ_Etrangers;
select * from Etudiants_Universites;

----------------------------------------------------
----------------------------------------------------

CREATE OR REPLACE TRIGGER trigger_insert_bourses
AFTER INSERT ON Bourses_Etrangers
FOR EACH ROW


temp_etranger Number := -1;
BEGIN
  SELECT Etranger into temp_etranger FROM Etudiants WHERE Etudiants.ID_etudiant = :NEW.ID_etudiant;
  -- Check if the inserted row is for a Marocain student
  IF temp_etranger = 0 THEN
    -- Insert the row into the Etudiants_Maroc table
    INSERT INTO Bourses VALUES (:NEW.ID_bourse, :NEW.ID_etudiant, :NEW.Nom, :NEW.Montant);
  ELSE
	dbms_output.put_line('!!! L''etudient n''exist pas !!!');
  END IF;
END;
/

----Test
insert into Bourses_Etrangers values(sq_Id_Bourse.NEXTVAL, 1, 'Minhaty', 5000 );
insert into Bourses_Etrangers values(sq_Id_Bourse.NEXTVAL, 99, 'Minhaty', 5000 );
--insert into Bourses(ID_bourse, ID_etudiant, Nom, Montant)
select * from Bourses_Etrangers;
select * from Bourses;







---------------------------------------------------------------
---------------------------------------------------------------
---------------------------------------------------------------
---------------------------------------------------------------
---------------------------------------------------------------

--- Procédure :
--- Une procédure qui insère un nouveau Etudiant Marocain(e):

create or replace procedure insert_etudiant_etranger(p_Nom Etudiants_Etranger.Nom%type, 
												     p_Prenom Etudiants_Etranger.Prenom%type, 
												     p_Nationalite Etudiants_Etranger.Nationalite%type ) as					
	n Number := 0;
	etudient_duplique exception;
	
	Begin
		select count(*) into n from Etudiants_Etranger where Nom = p_Nom and Prenom = p_Prenom;
		if n = 0 then
			insert into Etudiants_Etranger values(sq_Id_Etudiant.nextval, p_Nom, p_Prenom, p_Nationalite, 1);
			dbms_output.put_line('L''etudient "'||p_Nom||' '||p_Prenom||'" a ete ajoute');
		ELSE
			raise etudient_duplique;
		end if;
	Exception
		when etudient_duplique then
			dbms_output.put_line('!!! L''etudient "'||p_Nom||' '||p_Prenom||'" est deja existe !!!');
	End ;
/


/*
---- Hadi blach
create or replace procedure insert_Universites(p_Nom Universites.Nom%type, 
											   p_Localisation Universites.Localisation%type ) as					
	n Number := 0;
	universite_duplique exception;
	
	Begin
		select count(*) into n from Universites where Nom = p_Nom;
		if n = 0 then
			insert into Universites values(sq_Id_Univetsite.nextval, p_Nom, p_Localisation);
			dbms_output.put_line('L''Universite "'||p_Nom||'" a ete ajoute');
		ELSE
			raise universite_duplique;
		end if;
	Exception
		when universite_duplique then
			dbms_output.put_line('!!! L''Universite "'||p_Nom||'" est deja existe !!!');
	End ;
/
*/


create or replace procedure insert_etud_univ_etranger(p_ID_etudiant Etud_Univ_Etrangers.ID_etudiant%type, 
											          p_ID_universite Etud_Univ_Etrangers.ID_universite%type ) as					
	n Number := 0;
	est_duplique exception;
	
	Begin
		select count(*) into n from Etud_Univ_Etrangers where ID_etudiant = p_ID_etudiant and ID_universite = p_ID_universite;
		if n = 0 then
			insert into Etud_Univ_Etrangers values(p_ID_etudiant, p_ID_universite);
			dbms_output.put_line('L''etudient N°'||p_ID_etudiant||' est ajoute a l''universite N°'||p_ID_universite);
		ELSE
			raise est_duplique;
		end if;
	Exception
		when est_duplique then
			dbms_output.put_line('!!! L''etudient N°'||p_ID_etudiant||' est deja ajoute dans l''universite N°'||p_ID_universite||' !!!');
	End ;
/



create or replace procedure insert_bourse_etranger(p_ID_etudiant Bourses_Etrangers.ID_etudiant%type, 
												 p_Nom Bourses_Etrangers.Nom%type, 
												 p_Montant Bourses_Etrangers.Montant%type ) as					
	n Number := 0;
	est_duplique exception;
	
	Begin
		select count(*) into n from Bourses_Etrangers where Nom = p_Nom and ID_etudiant = p_ID_etudiant;
		if n = 0 then
			insert into Bourses_Etrangers values(sq_Id_Bourse.nextval, p_ID_etudiant, p_Nom, p_Montant);
			dbms_output.put_line('L''etudient N°'||p_ID_etudiant||' est ajoute a la bourse "'||p_Nom||'".');
		ELSE
			raise est_duplique;
		end if;
	Exception
		when est_duplique then
			dbms_output.put_line('!!! L''etudient N°'||p_ID_etudiant||' est deja ajoute a la bourse "'||p_Nom||'" !!!');
	End ;
/

