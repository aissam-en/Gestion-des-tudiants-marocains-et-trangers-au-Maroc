sqlplus system/aissam

create tablespace tbs_mini_prj datafile 'C:\ennahel_aissam\mini_project_BDR\tbs_mini_prj.dbf' size 500M;

create user admin_bourse identified by aissam default tablespace tbs_mini_prj quota unlimited on tbs_mini_prj;
grant all privileges to admin_bourse;

connect admin_bourse/aissam

 -------------------------Création des tables -------------------------
-- drop user admin_bourse;

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

CREATE TABLE Bourses(
  ID_bourse NUMBER primary key,
  ID_etudiant NUMBER,
  Nom VARCHAR2(100),
  Montant NUMBER,
  CONSTRAINT FK_Brs_Etud FOREIGN KEY(ID_etudiant) REFERENCES Etudiants(ID_etudiant)
);

select * from cat;
--select TABLE_NAME from user_tables;

 -------------------------Inserer dans les tables -------------------------
 --Création des séquences 

clear screen

drop sequence sq_Id_Etudiant;
drop sequence sq_Id_Univetsite;
drop sequence sq_Id_Bourse;

 --Création des séquences 
create sequence sq_Id_Etudiant increment by 1 start with 1;

create sequence sq_Id_Univetsite increment by 1 start with 1;

create sequence sq_Id_Bourse increment by 1 start with 1;

delete from Etudiants_Universites;
delete from Bourses;
delete from Universites;
delete from Etudiants;

--insert into Etudiants(ID_etudiant, Nom, Prenom, Nationalite, Etranger)
--drop table Etudiants;
insert into Etudiants values(sq_Id_Etudiant.nextval, 'EN-NAHEL', 'Aissam', 'Marocain', 0);
insert into Etudiants values(sq_Id_Etudiant.nextval, 'Amina', 'Ben-Ali', 'Marocaine', 0);
insert into Etudiants values(sq_Id_Etudiant.nextval, 'Karim', 'Ben-Moussa', 'Marocain', 0);
insert into Etudiants values(sq_Id_Etudiant.nextval, 'Fatima', 'Ben-Mohammed', 'Marocaine', 0);
insert into Etudiants values(sq_Id_Etudiant.nextval, 'Elon', 'Musk', 'Américain', 1);
insert into Etudiants values(sq_Id_Etudiant.nextval, 'John', 'Smith', 'Britannique', 1);
insert into Etudiants values(sq_Id_Etudiant.nextval, 'Mark', 'Zuckerberg', 'Chinois', 1);
set lines 150
select * from Etudiants;
insert into Etudiants values(sq_Id_Etudiant.nextval, 'EN-NAHEL', 'Hamza', 'Marocain', 0);
------ 7 etudients

-- insert into Universites(ID_universite, Nom, Localisation)
--drop table Universites;
insert into Universites values(sq_Id_Univetsite.nextval, 'Université Ibn Zohr', 'Agadir');
insert into Universites values(sq_Id_Univetsite.nextval, 'Université Cadi Ayyad', 'Marrakech');
insert into Universites values(sq_Id_Univetsite.nextval, 'Université Hassan II', 'Casablanca');
insert into Universites values(sq_Id_Univetsite.nextval, 'Université Mohammed V', 'Rabat');
insert into Universites values(sq_Id_Univetsite.nextval, 'Université Moulay Ismail', 'Meknès');
insert into Universites values(sq_Id_Univetsite.nextval, 'Université Al Quaraouiyine', 'Fès');
col Nom for A40
select * from Universites;
------ 6 Universités

--insert into Etudiants_Universites(ID_etudiant, ID_universite)
--drop table Etudiants_Universites;
insert into Etudiants_Universites values(1, 1);
insert into Etudiants_Universites values(2, 2);
insert into Etudiants_Universites values(3, 3);
insert into Etudiants_Universites values(4, 2);
insert into Etudiants_Universites values(5, 4);
insert into Etudiants_Universites values(6, 5);
insert into Etudiants_Universites values(7, 6);
select * from Etudiants_Universites;
------ 

--insert into Bourses(ID_bourse, ID_etudiant, Nom, Montant)
--drop table Bourses;
insert into Bourses values(sq_Id_Bourse.nextval, 1, 'Bourses Minhaty', 1500);
insert into Bourses values(sq_Id_Bourse.nextval, 2, 'Bourse Istihqaq', 3000);
insert into Bourses values(sq_Id_Bourse.nextval, 3, 'Bourses FME', 2000);
insert into Bourses values(sq_Id_Bourse.nextval, 4, 'Bourse AMCI', 4000);
insert into Bourses values(sq_Id_Bourse.nextval, 5, 'Bourses Minhaty', 1500);
insert into Bourses values(sq_Id_Bourse.nextval, 6, 'Bourse AMCI', 4000);
insert into Bourses values(sq_Id_Bourse.nextval, 7, 'Bourses Minhaty', 1500);

select * from Bourses;
------ 

commit;

 -------------------------Cree 2 users (sites) -------------------------
clear screen

connect system/aissam

create user moderateur_bourse_marocaine identified by aissam default tablespace tbs_mini_prj quota unlimited on tbs_mini_prj;

grant create session, create sequence, create table, create procedure, connect, resource, create view, create database link, create snapshot to moderateur_bourse_marocaine; 


create user moderateur_bourse_etranger  identified by aissam default tablespace tbs_mini_prj quota unlimited on tbs_mini_prj;

grant create session, create sequence, create table, create procedure, connect, resource, create view, create database link, create snapshot to moderateur_bourse_etranger; 



connect moderateur_bourse_marocaine/aissam
select * from cat;


connect moderateur_bourse_etranger/aissam
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

connect moderateur_bourse_marocaine/aissam

Copy from admin_bourse/aissam@xe To moderateur_bourse_marocaine/aissam@xe Replace Etudiants_Maroc using select ID_etudiant, Nom, Prenom, Nationalite, Etranger from Etudiants where Etranger = 0;

commit; 

desc Etudiants_Maroc

select * from Etudiants_Maroc;
------------------------------

Copy from admin_bourse/aissam@xe To moderateur_bourse_marocaine/aissam@xe Replace Universites using select ID_universite, Nom, Localisation from Universites;

commit; 

desc Universites

select * from Universites;
------------------------------

--Copy from admin_bourse/aissam@xe To moderateur_bourse_marocaine/aissam@xe Replace Etudiants_Universites_Maroc using select ID_etudiant, ID_universite from Etudiants_Universites where ID_etudiant in (select * from Etudiants_Maroc);
Copy from admin_bourse/aissam@xe To moderateur_bourse_marocaine/aissam@xe Replace Etudiants_Universites_Maroc using select ID_etudiant, ID_universite from Etudiants_Universites where ID_etudiant in (select ID_etudiant from Etudiants where Etranger = 0);

commit; 

desc Etudiants_Universites_Maroc

select * from Etudiants_Universites_Maroc;
------------------------------


Copy from admin_bourse/aissam@xe To moderateur_bourse_marocaine/aissam@xe Replace Bourses_Maroc using select ID_bourse, ID_etudiant, Nom, Montant from Bourses where ID_etudiant in (select ID_etudiant from Etudiants where Etranger = 0);

commit; 

desc Bourses_Maroc

select * from Bourses_Maroc;
------------------------------

-------------------------------------------------------------------
-------------------------------------------------------------------
-------------------------------------------------------------------
-------------------------------------------------------------------

clear screen

connect moderateur_bourse_etranger/aissam

Copy from admin_bourse/aissam@xe To moderateur_bourse_etranger/aissam@xe Replace Etudiants_Etrangers using select ID_etudiant, Nom, Prenom, Nationalite, Etranger from Etudiants where Etranger = 1;

desc Etudiants_Etrangers 

select * from Etudiants_Etrangers;
-----------------------------


Copy from admin_bourse/aissam@xe To moderateur_bourse_etranger/aissam@xe Replace Universites using select ID_universite, Nom, Localisation from Universites;

desc Universites

select * from Universites;
------------------------------


Copy from admin_bourse/aissam@xe To moderateur_bourse_etranger/aissam@xe Replace Etud_Univ_Etrangers using select ID_etudiant, ID_universite from Etudiants_Universites where ID_etudiant in (select ID_etudiant from Etudiants where Etranger = 1);

desc Etud_Univ_Etrangers 

select * from Etud_Univ_Etrangers;
------------------------------


Copy from admin_bourse/aissam@xe To moderateur_bourse_etranger/aissam@xe Replace Bourses_Etrangers using select ID_bourse, ID_etudiant, Nom, Montant from Bourses where ID_etudiant in (select ID_etudiant from Etudiants where Etranger = 1);

desc Bourses_Etrangers 

select * from Bourses_Etrangers;
------------------------------


commit; 

------------------------------------------------------------
------------------------------------------------------------
------------------------------------------------------------
------------------------------------------------------------

------ Création du lien inter-base (database link)
clear screen

connect moderateur_bourse_marocaine/aissam

select * from Etudiants;

create database link dbl_marocaine connect to admin_bourse identified by aissam using 'xe';

col db_link for A30
col host for A20
select * from USER_DB_LINKS;

select * from Etudiants@dbl_marocaine;
--drop database link dbl_marocaine;

-------------------------------------------------------
clear screen

connect moderateur_bourse_etranger/aissam

select * from Etudiants;

create database link dbl_etranger connect to admin_bourse identified by aissam using 'xe';

col db_link for A30
col host for A20
select * from USER_DB_LINKS;

select * from Etudiants@dbl_etranger;

--drop database link dbl_etranger;

-------------------------------------------------------
-------------------------------------------------------
-------------------------------------------------------
-------------------------------------------------------
-------------------------------------------------------


-- Ajout des contraintes de base :
-- 1- Recréer les contraintes clés primaires et clés étrangères :
-- Sur le site 1 moderateur_bourse_marocaine/aissam :
connect moderateur_bourse_marocaine/aissam
alter table Bourses_Maroc add primary key (ID_bourse);
alter table Etudiants_Maroc add primary key (ID_etudiant);
alter table Etudiants_Universites_Maroc add primary key (ID_etudiant, ID_universite);
alter table Universites add primary key (ID_universite);

-- 2- Les Contraintes de Références classiques si la table ‘père’ est sur le même site :
-- Sur le site 1 moderateur_bourse_marocaine/aissam :
Connect moderateur_bourse_marocaine/aissam
alter table Bourses_Maroc add foreign key(ID_etudiant) references Etudiants_Maroc(ID_etudiant);
alter table Etudiants_Universites_Maroc add foreign key (ID_etudiant) references Etudiants_Maroc(ID_etudiant);
alter table Etudiants_Universites_Maroc add foreign key (ID_universite) references Universites(ID_universite);





-- Sur le site 2 moderateur_bourse_etranger/aissam :
connect moderateur_bourse_etranger/aissam
alter table Bourses_Etrangers add primary key (ID_bourse);
alter table Etudiants_Etrangers add primary key (ID_etudiant);
alter table Etud_Univ_Etrangers add primary key (ID_etudiant, ID_universite);
alter table Universites add primary key (ID_universite);

-- 2- Les Contraintes de Références classiques si la table ‘père’ est sur le même site :
-- Sur le site 2 moderateur_bourse_etranger/aissam :
alter table Bourses_Etrangers add foreign key(ID_etudiant) references Etudiants_Etrangers(ID_etudiant);
alter table Etud_Univ_Etrangers add foreign key (ID_etudiant) references Etudiants_Etrangers(ID_etudiant);
alter table Etud_Univ_Etrangers add foreign key (ID_universite) references Universites(ID_universite);





--- 
CREATE OR REPLACE TRIGGER trigger_insert_etudiants
AFTER INSERT ON Etudiants
FOR EACH ROW
BEGIN
  -- Check if the inserted row is for a Marocain student
  IF :NEW.Etranger = 0 THEN
    -- Insert the row into the Etudiants_Maroc table
    INSERT INTO Etudiants_Maroc VALUES (:NEW.ID_etudiant, :NEW.Nom, :NEW.Prenom, :NEW.Nationalite, :NEW.Etranger);
  ELSE
    -- Insert the row into the Etudiants_Etranger table
    INSERT INTO Etudiants_Etranger VALUES (:NEW.ID_etudiant, :NEW.Nom, :NEW.Prenom, :NEW.Nationalite, :NEW.Etranger);
  END IF;
END;
/


insert into Etudiants values(sq_Id_Etudiant.nextval, 'EN-NAHEL', 'Rachid', 'Marocain', 0);


--- 3- Les Contraintes de Références par ‘trigger’ si la table ‘père’ est sur un site distant :
-- Sur le site 1 moderateur_bourse_marocaine/aissam :
connect moderateur_bourse_marocaine/aissam

create or replace trigger ......... --N9EZTHA



--- II- Création des synonymes et vues :
--- 1- Synonymes aux tables :
--- Création sur le site1 moderateur_bourse_marocaine/aissam des synonymes aux tables hébergées sur le central site
--- admin_bourse/aissam, avec teste de bon fonctionnement des synonymes :
--- Sur le site 1 moderateur_bourse_marocaine/aissam :
Connect moderateur_bourse_marocaine/aissam

create synonym Etudiants FOR Etudiants@dbl_marocaine;  ---machi darori ?
select * from Etudiants;

create synonym Universites FOR Universites@dbl_marocaine;  ---HADAAAAA darori ?
select * from Universites;

create synonym Etudiants_Universites FOR Etudiants_Universites@dbl_marocaine;  ---machi darori ?
select * from Etudiants_Universites;

create synonym Bourses FOR Bourses@dbl_marocaine;  ---machi darori ?
select * from Bourses;


--- Création sur le site2 moderateur_bourse_etranger/aissam des synonymes aux tables hébergées sur le central site
--- admin_bourse/aissam, avec teste de bon fonctionnement des synonymes :
--- Sur le site 1 moderateur_bourse_marocaine/aissam :
Connect moderateur_bourse_etranger/aissam

create synonym Etudiants FOR Etudiants@dbl_etranger;  ---machi darori ?
select * from Etudiants;

create synonym Universites FOR Universites@dbl_etranger;  ---HADAAAAA darori ?
select * from Universites;

create synonym Etudiants_Universites FOR Etudiants_Universites@dbl_etranger;  ---machi darori ?
select * from Etudiants_Universites;

create synonym Bourses FOR Bourses@dbl_etranger;  ---machi darori ?
select * from Bourses;




---- 2- Les vues :
---- Création de la vue Etudiants_view qui fait l’union des tables Etudiants_Maroc et Etudiants_Etrangers,
---- et la vue Etudiants_Universites_view qui fait l’union des tables Etudiants_Universites_Maroc et Etud_Univ_Etrangers.
---- Sur le site 1 moderateur_bourse_marocaine/aissam :
Connect moderateur_bourse_marocaine/aissam
--- pass it
CREATE or REPLACE view Etudiants_view as select * from client1 UNION select * from client2;






--- III- Procédure :
--- Une procédure qui insère un nouveau Etudiant Marocain(e):
--- Sur le site moderateur_bourse_marocaine/aissam :
connect moderateur_bourse_marocaine/aissam

create or replace procedure insert_etudiant(p_Nom Etudiants_Maroc.Nom %type, 
												p_Prenom Etudiants_Maroc.Prenom %type, 
												p_Nationalite Etudiants_Maroc.Nationalite %type ) as					
	n Number := 0;
	etudient_duplique exception;
	Begin
		select count(*) into n from Etudiants_Maroc where Nom = p_Nom and Prenom = p_Prenom;
		if n <> 0 then
			raise etudient_duplique;
		end if;
		insert into Etudiants_Maroc values(sq_Id_Etudiant.nextval, p_Nom, p_Prenom, p_Nationalite, 0);
		dbms_output.put_line('L''etudient "'||p_Nom||' '||p_Prenom||'" a ete ajoute');
	Exception
		when etudient_duplique then
			dbms_output.put_line('!!! L''etudient "'||p_Nom||' '||p_Prenom||'" est deja existe !!!');
	End ;
/
--- Une procédure qui insère un nouveau :
--- Sur le site moderateur_bourse_marocaine/aissam :
-- connect moderateur_bourse_marocaine/aissam





--- Une procédure qui insère un nouveau Etudiant Etranger :
--- Sur le site moderateur_bourse_etranger/issam :
connect moderateur_bourse_etranger/aissam

create or replace procedure insert_etudiant(p_Nom Etudiants_Etrangers.Nom %type, 
												p_Prenom Etudiants_Etrangers.Prenom %type, 
												p_Nationalite Etudiants_Etrangers.Nationalite %type ) as					
	n Number := 0;
	etudient_duplique exception;
	Begin
		select count(*) into n from Etudiants_Etrangers where Nom = p_Nom and Prenom = p_Prenom;
		if n <> 0 then
			raise etudient_duplique;
		end if;
		insert into Etudiants_Etrangers values(sq_Id_Etudiant.nextval, p_Nom, p_Prenom, p_Nationalite, 0);
		dbms_output.put_line('L''etudient "'||p_Nom||' '||p_Prenom||'" a ete ajoute');
	Exception
		when etudient_duplique then
			dbms_output.put_line('!!! L''etudient "'||p_Nom||' '||p_Prenom||'" est deja existe !!!');
	End ;
/












































--------- Create Triggers :

clear screen

connect moderateur_bourse_marocaine/aissam

CREATE OR REPLACE TRIGGER trg_maroc
AFTER INSERT OR DELETE ON Etudients_maroc
FOR EACH ROW
BEGIN
  IF inserting THEN
    INSERT INTO admin_bourse.Etudients VALUES (:NEW.column1, :NEW.column2, ...);
  ELSIF deleting THEN
    DELETE FROM admin_bourse.Etudients WHERE column1 = :OLD.column1;
  END IF;
END;
/




connect moderateur_bourse_etranger/aissam

CREATE OR REPLACE TRIGGER trg_etranger
AFTER INSERT OR DELETE ON Etudients_etranger
FOR EACH ROW
BEGIN
  IF inserting THEN
    INSERT INTO admin_bourse.Etudients VALUES (:NEW.column1, :NEW.column2, ...);
  ELSIF deleting THEN
    DELETE FROM admin_bourse.Etudients WHERE column1 = :OLD.column1;
  END IF;
END;
/













































 -------------------------Création des procédures  -------------------------
--insert into Etudiants(ID_etudiant, Nom, Prenom, Nationalite, Etranger)
 -- 
create or replace procedure Ajouter_Etudiant(p_Nom_etud Etudiants.Nom%type,
											 p_Prenom_etud Etudiants.Prenom%type,
											 p_Nationalite_etud Etudiants.Nationalite%type,
											 p_Etranger_etud Etudiants.Etranger%type 
											 ) is									  
tmp_Nom_etud Etudiants.Nom%type;
Etudiant_deja_exist Exception;

BEGIN
	BEGIN
		select Nom into tmp_Nom_etud from Etudiants where Nom = p_Nom_etud;
	EXCEPTION
		when no_data_found THEN tmp_Nom_etud := null;
	END;
	if tmp_Nom_etud is null then 
		insert into Etudiants values( sq_Id_Etudiant.nextval, p_Nom_etud, p_Prenom_etud, p_Nationalite_etud, p_Etranger_etud);
	else raise Etudiant_deja_exist;
	end if;
	DBMS_OUTPUT.PUT_LINE('Client cree');
EXCEPTION
	when Etudiant_deja_exist then DBMS_OUTPUT.PUT_LINE('Etudiant est deja existe');
END;
/
