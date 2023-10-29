//khdaaaam 
//jme3 had l files f wa7ed lblassa : 
//    MiniProjet_sqlj.sqlj
//    admin_connect.properties
//    etranger_connect.properties
//    maroc_connect.properties
//7el cmd o dir :
//    sqlj MiniProjet_sqlj.sqlj
//    java MiniProjet_sqlj

import java.sql.SQLException ;
import oracle.sqlj.runtime.Oracle;
import java.io.*;

class MiniProjet_sqlj {

	public static void main(String[] args) throws IOException, SQLException {
			
		int n=0, m=0, nbr_ligne;
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		int item_menu = 1;
		
		while (true) {
            System.out.println("\n\n   --- SQLJ --- ");
            System.out.println(" Choisir une option : ");
            System.out.println(" 1. Connectez en tant que *Admin Bourse* ");
            System.out.println(" 2. Bourses Etudiants Marocains ");
            System.out.println(" 3. Bourses Etudiants Etrangers ");
            System.out.println(" 4. Quitter ");

            System.out.print("\nchoix: ");
            item_menu = Integer.parseInt(reader.readLine());
			
			System.out.println("");
			
			switch (item_menu) {
			case 1 :
	            System.out.println("\n   --- SQLJ --- ");
				Oracle.connect(MiniProjet_sqlj.class,"admin_connect.properties");
                System.out.println("  Connection avec Admin Bourse --> OK");
                System.out.println("  -----------------------------------");
                admin_bourse_menu();
				break;
			case 2 :
	            System.out.println("\n   --- SQLJ --- ");
				Oracle.connect(MiniProjet_sqlj.class,"maroc_connect.propertiess");
                System.out.println("  Connection avec Bourses Etudiants Marocains --> OK");
                System.out.println("  --------------------------------------------------");
                maroc_bourse_menu();
				
                break;
			case 3 :
	            System.out.println("\n   --- SQLJ --- ");
				Oracle.connect(MiniProjet_sqlj.class,"etranger_connect.properties");
                System.out.println("  Connection avec Bourses Etudiants Etrangers --> OK");
                System.out.println("  --------------------------------------------------  ");
                etranger_bourse_menu();
				
                break;
			case 4:
                System.out.println("Quitter SQLJ... OK");
                System.exit(0);

            default:
                System.out.println("Choix invalide. Sélectionner une option valide (1, 2, 3 ou 4).");
            }
		

		}

	}
	
	//----- admin_bourse_menu () ======================================================================================================================
	public static void admin_bourse_menu() throws IOException, SQLException {
		int n=0, m=0, nbr_ligne;
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		int item_menu = 1;
		//while(1<=item_menu && item_menu<=5) {
		boolean continuer = true;
		while (continuer) {
			System.out.println("____________________________________________________________________________________________");
			System.out.println("|                                                                                           |");
			System.out.println("|                       *** ADMINISTRATEUR DE LA BOURSE AU MAROC ***                        |");
			System.out.println("|---------------------------------------------|---------------------------------------------|");
			System.out.println("|                  Etudiants                  |                 Universites                 |");
			System.out.println("|---------------------------------------------|---------------------------------------------|");
			System.out.println("|  1. Afficher Les Etudiants                  |   8. Afficher Les Universites               |");
			System.out.println("|  2. *Afficher Tous Les Infos D'etudiant*    |   9. Afficher Les Bourses                   |");
			System.out.println("|---------------------------------------------|---------------------------------------------|");
			System.out.println("|  3. Ajouter un Etudiant                     |  10. Ajouter une Universite                 |");
			System.out.println("|  4. Supprimer un Etudiant                   |  11. Supprimer une Universite               |");
			System.out.println("|  5. Modifier un Etudiant                    |  12. Modifier une Universite                |");
			System.out.println("|---------------------------------------------|---------------------------------------------|");
			System.out.println("|  6. Ajouter un Etudiant a Universite        |                                             |");
			System.out.println("|  7. Ajouter un Etudiant a Bourse            |                                             |");
			System.out.println("|---------------------------------------------|---------------------------------------------|");
			System.out.println("|  0. Quitter                                 |                                             |");
			System.out.println("|_____________________________________________|_____________________________________________|");

			System.out.print("\n  Choisir une option ==> ");
			item_menu = Integer.parseInt(reader.readLine());
			
			System.out.println("");
			
			switch (item_menu) {
				case 1:
					System.out.println("\n     -- 1. Afficher Les Etudiants --\n");		
					#sql iterator iter_a_aff_etud( int ID_ETUDIANT, String NOM, String PRENOM, String NATIONALITE, int ETRANGER );
					iter_a_aff_etud  etudiant_a_aff_etud;  
					#sql etudiant_a_aff_etud = { SELECT ID_ETUDIANT, NOM, PRENOM, NATIONALITE, ETRANGER FROM Etudiants };
					System.out.println("");
					System.out.println("-----------------------------------------------------------");
					System.out.println("|          LES ETUDIANTS MAROCAINES ET ETRANGERS          |");
					System.out.println("-----------------------------------------------------------");
					while (etudiant_a_aff_etud.next()) {
						System.out.println("| - ID_ETUDIANT: "+etudiant_a_aff_etud.ID_ETUDIANT() );
						System.out.println("|     NOM: " +etudiant_a_aff_etud.NOM() );
						System.out.println("|     PRENOM : "+etudiant_a_aff_etud.PRENOM() );
						System.out.println("|     NATIONALITE : "+etudiant_a_aff_etud.NATIONALITE() );
						System.out.println("|     ETRANGER: "+etudiant_a_aff_etud.ETRANGER() );
						System.out.println("| **********************************************************");
					}
					etudiant_a_aff_etud.close();     
					break;			
					
				case 2:
					System.out.println("\n     -- 2. *Afficher Tous Les Infos D'un Etudiant* --\n");	
					System.out.print("\n  Saisir ID_ETUDIANT: ");
					int id_etud = Integer.parseInt(reader.readLine());
					//les infos Personel
					#sql iterator iter_info_person( int ID_Etudiant, String Nom, String Prenom, String Nationalite, int Etranger );
					iter_info_person  etudiant_info_person;  
					#sql etudiant_info_person = { SELECT ID_Etudiant, Nom, Prenom, Nationalite, Etranger FROM Etudiants WHERE ID_Etudiant = :id_etud };
					System.out.println("");
					System.out.println("--------------------------------------------------------------------------");
					System.out.println("|                 LES INFORMATION DE L'ETUDIANT: ");
					//System.out.print("|                 LES INFORMATION DE L'ETUDIANT: "); #sql :Nom; System.out.print(""); #sql :Prenom; System.out.println("");
					System.out.println("--------------------------------------------------------------------------");
					while (etudiant_info_person.next()) {
						System.out.println("|        + Personal Information: ");
						System.out.println("|           - ID_ETUDIANT: "+etudiant_info_person.ID_Etudiant() );
						System.out.println("|           - NOM: " +etudiant_info_person.Nom() );
						System.out.println("|           - PRENOM : "+etudiant_info_person.Prenom() );
						System.out.println("|           - NATIONALITE : "+etudiant_info_person.Nationalite() );
						System.out.println("|           - ETRANGER: "+etudiant_info_person.Etranger() );
					}
					etudiant_info_person.close();   
					
					//les infos de l universite
					#sql iterator iter_info_univ( int ID_Universite, String Nom, String Localisation );
					iter_info_univ  universite;  
					#sql universite = { SELECT ID_Universite, Nom, Localisation FROM Universites WHERE ID_Universite IN ( SELECT ID_Universite FROM Etudiants_Universites WHERE ID_Etudiant = :id_etud ) };
					while (universite.next()) {
						System.out.println("|        + Universite: ");
						System.out.println("|           - ID_UNIVERSITE: "+universite.ID_Universite() );
						System.out.println("|           - NOM: " +universite.Nom() );
						System.out.println("|           - LOCALISATION : "+universite.Localisation() );
					}
					universite.close();   
					
					// les infos de labourse
					#sql iterator iter_info_bourse( int ID_Bourse, String Nom, int Montant );
					iter_info_bourse  bourse;  
					#sql bourse = { SELECT ID_Bourse, Nom, Montant FROM Bourses WHERE ID_Etudiant = :id_etud };
					while (bourse.next()) {
						System.out.println("|        + Bourse: ");
						System.out.println("|           - ID_BOURSE: "+bourse.ID_Bourse() );
						System.out.println("|           - NOM: " +bourse.Nom() );
						System.out.println("|           - MONTANT : "+bourse.Montant() );
						System.out.println("| **********************************************************");
					}
					bourse.close();   
					break;			

				case 3:
					System.out.println("\n     -- 3. Ajouter un Etudiant --\n");		
					
					String nom_etud = "";
					String prenom_etud = "";
					String nationalite_etud = "";
					int etranger_etud = 0;
					
					System.out.println("\n\n    - Ajouter un Etudiant - ");
					System.out.println("\n    Entrez les information de l'etudiant : ");
					System.out.print("\n  -Nom: ");
					nom_etud = reader.readLine();
					
					System.out.print("\n  -Prenom: ");
					prenom_etud = reader.readLine();
					
					System.out.print("\n  -Nationalite: ");
					nationalite_etud = reader.readLine();
					
					System.out.print("\n  -Est-ce un étudiant marocain? (oui / non): ");
					String str_maroc = reader.readLine();

					if(str_maroc.toLowerCase().equals("non")){
						etranger_etud = 1;
					}
					
					#sql{ call INSERT_etudiant(:nom_etud, :prenom_etud, :nationalite_etud,:etranger_etud) };
					
					System.out.println("\n         ==> Ajouter Etudiant '"+nom_etud+" "+prenom_etud+"' --> OK");
					
					break;
					
				case 4:
					System.out.println("\n     -- 4. Supprimer un Etudiant --\n");	
					
					//Afficher laliste des etudiants
					#sql iterator iter_a_sup_etud( int ID_ETUDIANT, String NOM, String PRENOM, String NATIONALITE, int ETRANGER );
					iter_a_sup_etud  etudiant_a_sup_etud;  
					#sql etudiant_a_sup_etud = { SELECT ID_ETUDIANT, NOM, PRENOM, NATIONALITE, ETRANGER FROM Etudiants };
					System.out.println("");
					System.out.println("-----------------------------------------------------------");
					System.out.println("|          LES ETUDIANTS MAROCAINES ET ETRANGERS          |");
					System.out.println("-----------------------------------------------------------");
					while (etudiant_a_sup_etud.next()) {
						System.out.println("| - ID_ETUDIANT: "+etudiant_a_sup_etud.ID_ETUDIANT() );
						System.out.println("|     NOM: " +etudiant_a_sup_etud.NOM() );
						System.out.println("|     PRENOM : "+etudiant_a_sup_etud.PRENOM() );
						System.out.println("|     NATIONALITE : "+etudiant_a_sup_etud.NATIONALITE() );
						System.out.println("|     ETRANGER: "+etudiant_a_sup_etud.ETRANGER() );
						System.out.println("| **********************************************************");
					}
					etudiant_a_sup_etud.close();     
					
					//supprimer un etudiant	
					
					System.out.print("\n\n    Entrez l'ID de l'Etudiant: ");
					id_etud = Integer.parseInt(reader.readLine());
					
					#sql{ DELETE FROM ETUDIANTS where ID_ETUDIANT = :id_etud };
					
					System.out.println("\n         ==> Supprimer Etudiant ID='"+id_etud+"' --> OK");
					
					break;
					
				case 5:
					System.out.println("\n     -- 5. Modifier un Etudiant --\n");	
					
					//Afficher la liste des etudiants
					#sql iterator iter_a_mod_etude( int ID_ETUDIANT, String NOM, String PRENOM, String NATIONALITE, int ETRANGER );
					iter_a_mod_etude  etudiant_a_mod_etude;  
					#sql etudiant_a_mod_etude = { SELECT ID_ETUDIANT, NOM, PRENOM, NATIONALITE, ETRANGER FROM Etudiants };
					System.out.println("");
					System.out.println("-----------------------------------------------------------");
					System.out.println("|          LES ETUDIANTS MAROCAINES ET ETRANGERS          |");
					System.out.println("-----------------------------------------------------------");
					while (etudiant_a_mod_etude.next()) {
						System.out.println("| - ID_ETUDIANT: "+etudiant_a_mod_etude.ID_ETUDIANT() );
						System.out.println("|     NOM: " +etudiant_a_mod_etude.NOM() );
						System.out.println("|     PRENOM : "+etudiant_a_mod_etude.PRENOM() );
						System.out.println("|     NATIONALITE : "+etudiant_a_mod_etude.NATIONALITE() );
						System.out.println("|     ETRANGER: "+etudiant_a_mod_etude.ETRANGER() );
						System.out.println("| **********************************************************");
					}
					etudiant_a_mod_etude.close();     
					
					//modifier un etudiant	
					System.out.print("\n    Entrez ID de l'etudiant qui sera modifié: ");
					int id_etud_mod = Integer.parseInt(reader.readLine());
					
					System.out.print("    -Nom de l'etudiant: ");
					String nom_etud_mod = reader.readLine();
					
					System.out.print("    -Prenom de l'etudiant: ");
					String prenom_etud_mod = reader.readLine();
					
					System.out.print("    -Nationalite de l'etudiant: ");
					String nationalite_etud_mod = reader.readLine();

					int etranger_etud_mod = 0;
					System.out.print("    -Est-ce un étudiant marocain? (oui / non): ");
					String str_maroc_mod = reader.readLine();
					if(str_maroc_mod.toLowerCase().equals("non")){
						etranger_etud_mod = 1;
					}
					#sql{ UPDATE ETUDIANTS SET NOM = :nom_etud_mod , PRENOM = :prenom_etud_mod , NATIONALITE = :nationalite_etud_mod , ETRANGER = :etranger_etud_mod where ID_ETUDIANT = :id_etud_mod };
					System.out.println("\n         ==> Modifier Etudiant ID='"+id_etud_mod+"' --> OK");
					break;
					
				case 6:
					System.out.println("\n     -- 6. Ajouter un Etudiant a Universite --\n");	
					
					//Afficher la liste des etudiants
					#sql iterator iter_a_ajt_etud_univ_1( int ID_ETUDIANT, String NOM, String PRENOM, String NATIONALITE, int ETRANGER );
					iter_a_ajt_etud_univ_1  etudiant_a_ajt_etud_univ_1;  
					#sql etudiant_a_ajt_etud_univ_1 = { SELECT ID_ETUDIANT, NOM, PRENOM, NATIONALITE, ETRANGER FROM Etudiants };
					System.out.println("");
					System.out.println("-----------------------------------------------------------");
					System.out.println("|          LES ETUDIANTS MAROCAINES ET ETRANGERS          |");
					System.out.println("-----------------------------------------------------------");
					while (etudiant_a_ajt_etud_univ_1.next()) {
						System.out.println("| - ID_ETUDIANT: "+etudiant_a_ajt_etud_univ_1.ID_ETUDIANT() );
						System.out.println("|     NOM: " +etudiant_a_ajt_etud_univ_1.NOM() );
						System.out.println("|     PRENOM : "+etudiant_a_ajt_etud_univ_1.PRENOM() );
						System.out.println("|     NATIONALITE : "+etudiant_a_ajt_etud_univ_1.NATIONALITE() );
						System.out.println("|     ETRANGER: "+etudiant_a_ajt_etud_univ_1.ETRANGER() );
						System.out.println("| **********************************************************");
					}
					etudiant_a_ajt_etud_univ_1.close();     
					
					//Afficher la liste des universities
					#sql iterator iter_a_ajt_etud_univ_2( int ID_UNIVERSITE, String NOM, String LOCALISATION);
					iter_a_ajt_etud_univ_2  univ_a_ajt_etud_univ_2;  
					#sql univ_a_ajt_etud_univ_2 = { SELECT ID_UNIVERSITE, NOM, LOCALISATION FROM Universites };
					System.out.println("");
					System.out.println("-----------------------------------------------------------");
					System.out.println("|                     LES UNIVERSITES                      |");
					System.out.println("-----------------------------------------------------------");
					while (univ_a_ajt_etud_univ_2.next()) {
						System.out.println("| - ID_UNIVERSITE: "+univ_a_ajt_etud_univ_2.ID_UNIVERSITE() );
						System.out.println("|     NOM: " +univ_a_ajt_etud_univ_2.NOM() );
						System.out.println("|     LOCALISATION : "+univ_a_ajt_etud_univ_2.LOCALISATION() );
						System.out.println("| **********************************************************");
					}
					univ_a_ajt_etud_univ_2.close();     
					
					//ajouter etudiant a universite
					System.out.println("\n\n    - Ajouter un Etudiant a Universite - ");
					
					System.out.print("\n  -Entrez le ID de l'etudiant: ");
					int id_etud_aj_univ = Integer.parseInt(reader.readLine());
					
					System.out.print("\n  -Entrez le ID de l'universite: ");
					int id_univ_aj_univ = Integer.parseInt(reader.readLine());
					
					#sql{ call INSERT_etudiant_universite(:id_etud_aj_univ, :id_univ_aj_univ) };

					System.out.println("\n         ==> Ajouter Etudiant de ID = '"+id_etud_aj_univ+" a l'Universite de ID = "+id_univ_aj_univ+"' --> OK");
					break;	
					
				case 7:
					System.out.println("\n     -- 7. Ajouter un Etudiant a Bourse --\n");	
					
					//Afficher la liste des etudiants
					#sql iterator iter_a_ajt_etud_brs_1( int ID_ETUDIANT, String NOM, String PRENOM, String NATIONALITE, int ETRANGER );
					iter_a_ajt_etud_brs_1 etudiant_a_ajt_etud_brs_1;  
					#sql etudiant_a_ajt_etud_brs_1 = { SELECT ID_ETUDIANT, NOM, PRENOM, NATIONALITE, ETRANGER FROM Etudiants };
					System.out.println("");
					System.out.println("-----------------------------------------------------------");
					System.out.println("|          LES ETUDIANTS MAROCAINES ET ETRANGERS          |");
					System.out.println("-----------------------------------------------------------");
					while (etudiant_a_ajt_etud_brs_1.next()) {
						System.out.println("| - ID_ETUDIANT: "+etudiant_a_ajt_etud_brs_1.ID_ETUDIANT() );
						System.out.println("|     NOM: " +etudiant_a_ajt_etud_brs_1.NOM() );
						System.out.println("|     PRENOM : "+etudiant_a_ajt_etud_brs_1.PRENOM() );
						System.out.println("|     NATIONALITE : "+etudiant_a_ajt_etud_brs_1.NATIONALITE() );
						System.out.println("|     ETRANGER: "+etudiant_a_ajt_etud_brs_1.ETRANGER() );
						System.out.println("| **********************************************************");
					}
					etudiant_a_ajt_etud_brs_1.close();     
					
					//Afficher la liste des bourses
					#sql iterator iter_a_ajt_etud_brs_2( int ID_Bourse, int ID_Etudiant, String Nom, int Montant);
					iter_a_ajt_etud_brs_2 bourse_a_ajt_etud_brs_2;  
					#sql bourse_a_ajt_etud_brs_2 = { SELECT ID_Bourse, ID_Etudiant, Nom, Montant FROM BOURSES };
					System.out.println("");
					System.out.println("-----------------------------------------------------------");
					System.out.println("|                       LES BOURSES                        |");
					System.out.println("-----------------------------------------------------------");
					while (bourse_a_ajt_etud_brs_2.next()) {
						System.out.println("| - ID_BOURSE: "+bourse_a_ajt_etud_brs_2.ID_Bourse() );
						System.out.println("|     ID_ETUDIANT: " +bourse_a_ajt_etud_brs_2.ID_Etudiant() );
						System.out.println("|     NOM: " +bourse_a_ajt_etud_brs_2.Nom() );
						System.out.println("|     MONTANT : "+bourse_a_ajt_etud_brs_2.Montant() );
						System.out.println("| **********************************************************");
					}
					bourse_a_ajt_etud_brs_2.close();    
					
					
					//ajouter etudiant a bourse
					System.out.println("\n\n    - Ajouter un Etudiant a Bourse - ");
					
					System.out.println("\n    Entrez les information: ");
					
					System.out.print("\n  -ID de l'etudiant: ");
					int id_etud_aj_brs = Integer.parseInt(reader.readLine());
					
					System.out.print("\n  -Nom de la Bourse: ");
					String nom_brs_aj_brs = reader.readLine();
					
					System.out.print("\n  -Montant de la Bourse: ");
					int montant_brs_aj_brs = Integer.parseInt(reader.readLine());

					#sql{ call INSERT_bourse(:id_etud_aj_brs, :nom_brs_aj_brs, :montant_brs_aj_brs) };

					System.out.println("\n         ==> Ajouter Etudiant de ID='"+id_etud_aj_brs+"' a la Bourse '"+nom_brs_aj_brs+"' --> OK");
					break;
								
				case 8:
					System.out.println("\n     -- 8. Afficher Les Universites --\n");		
					#sql iterator iter_a_aff_univ( int ID_UNIVERSITE, String NOM, String LOCALISATION);
					iter_a_aff_univ  universite_a_aff_univ;  
					#sql universite_a_aff_univ = { SELECT ID_UNIVERSITE, NOM, LOCALISATION FROM Universites };
					System.out.println("");
					System.out.println("-----------------------------------------------------------");
					System.out.println("|                     LES UNIVERSITES                      |");
					System.out.println("-----------------------------------------------------------");
					while (universite_a_aff_univ.next()) {
						System.out.println("| - ID_UNIVERSITE: "+universite_a_aff_univ.ID_UNIVERSITE() );
						System.out.println("|     NOM: " +universite_a_aff_univ.NOM() );
						System.out.println("|     LOCALISATION : "+universite_a_aff_univ.LOCALISATION() );
						System.out.println("| **********************************************************");
					}
					universite_a_aff_univ.close();     
					break;	
								
				case 9:
					System.out.println("\n     -- 9. Afficher Les Bourses --\n");		
					#sql iterator iter_a_aff_brs( int ID_Bourse, int ID_Etudiant, String Nom, int Montant);
					iter_a_aff_brs  bourse_a_aff_brs;  
					#sql bourse_a_aff_brs = { SELECT ID_Bourse, ID_Etudiant, Nom, Montant FROM BOURSES };
					System.out.println("");
					System.out.println("-----------------------------------------------------------");
					System.out.println("|                       LES BOURSES                        |");
					System.out.println("-----------------------------------------------------------");
					while (bourse_a_aff_brs.next()) {
						System.out.println("| - ID_BOURSE: "+bourse_a_aff_brs.ID_Bourse() );
						System.out.println("|     ID_ETUDIANT: " +bourse_a_aff_brs.ID_Etudiant() );
						System.out.println("|     NOM: " +bourse_a_aff_brs.Nom() );
						System.out.println("|     MONTANT : "+bourse_a_aff_brs.Montant() );
						System.out.println("| **********************************************************");
					}
					bourse_a_aff_brs.close();     
					break;			
								
				case 10:
					System.out.println("\n     -- 10. Ajouter une Universite --\n");		
					
					System.out.println("\n    Entrez les information de l'Universite : ");

					System.out.print("\n  -Nom: ");
					String nom_univ = reader.readLine();
					
					System.out.print("\n  -Localisation: ");
					String loc_univ = reader.readLine();

					#sql{ call INSERT_universite(:nom_univ, :loc_univ) };

					System.out.println("\n         ==> Ajouter Universite "+nom_univ+" --> OK");
					break;
					
				case 11:
					System.out.println("\n     -- 11. Supprimer une Universite --\n");	
					
					//Afficher laliste des Universites
					#sql iterator iter_a_sup_univ( int ID_UNIVERSITE, String NOM, String LOCALISATION);
					iter_a_sup_univ  universite_a_sup_univ;  
					#sql universite_a_sup_univ = { SELECT ID_UNIVERSITE, NOM, LOCALISATION FROM Universites };
					System.out.println("");
					System.out.println("-----------------------------------------------------------");
					System.out.println("|                     LES UNIVERSITES                      |");
					System.out.println("-----------------------------------------------------------");
					while (universite_a_sup_univ.next()) {
						System.out.println("| - ID_UNIVERSITE: "+universite_a_sup_univ.ID_UNIVERSITE() );
						System.out.println("|     NOM: " +universite_a_sup_univ.NOM() );
						System.out.println("|     LOCALISATION : "+universite_a_sup_univ.LOCALISATION() );
						System.out.println("| **********************************************************");
					}
					universite_a_sup_univ.close(); 
					
					//supprimer une universite	
					
					System.out.print("\n    Entrez l'ID de l'Universite: ");
					int id_univ = Integer.parseInt(reader.readLine());
							
					#sql{ DELETE FROM UNIVERSITES where ID_UNIVERSITE = :id_univ };
					
					System.out.println("\n         ==> Supprimer Universite ID='"+id_univ+"' --> OK");
					break;
					
				case 12:
					System.out.println("\n     -- 12. Modifier une Universite --\n");	
					
					//Afficher la liste des Universites
					#sql iterator iter_a_mod_univ( int ID_UNIVERSITE, String NOM, String LOCALISATION);
					iter_a_mod_univ universite_a_mod_univ;  
					#sql universite_a_mod_univ = { SELECT ID_UNIVERSITE, NOM, LOCALISATION FROM Universites };
					System.out.println("");
					System.out.println("-----------------------------------------------------------");
					System.out.println("|                     LES UNIVERSITES                      |");
					System.out.println("-----------------------------------------------------------");
					while (universite_a_mod_univ.next()) {
						System.out.println("| - ID_UNIVERSITE: "+universite_a_mod_univ.ID_UNIVERSITE() );
						System.out.println("|     NOM: " +universite_a_mod_univ.NOM() );
						System.out.println("|     LOCALISATION : "+universite_a_mod_univ.LOCALISATION() );
						System.out.println("| **********************************************************");
					}
					universite_a_mod_univ.close(); 
					
					//modifier une universite	
					System.out.print("\n    Entrez ID de l'universite qui sera modifié: ");
					int id_univ_mod_univ = Integer.parseInt(reader.readLine());
					
					System.out.print("    -Nom de l'Universite: ");
					String nom_univ_mod_univ = reader.readLine();

					System.out.print("    -Localisation de l'Universite: ");
					String loc_univ_mod_univ = reader.readLine();
									
					#sql{ UPDATE UNIVERSITES SET NOM = :nom_univ_mod_univ , LOCALISATION = :loc_univ_mod_univ where ID_UNIVERSITE = :id_univ_mod_univ };
					
					System.out.println("\n         ==> Modifier Universite ID='"+id_univ_mod_univ+"' --> OK");
					break;
				
				case 0:
					continuer = false; 
					break;
					
				default: 
					System.out.println("\n       *!*!*!*!* Choix invalide *!*!*!*!*");
			} //fin de switch
		}  //fin de while
	} //fin de admin_bourse_menu()
	


	
	
	//----- maroc_bourse_menu () ======================================================================================================================
	public static void maroc_bourse_menu() throws IOException, SQLException {
		int n=0, m=0, nbr_ligne;
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		int item_menu = 1;
		//while(1<=item_menu && item_menu<=5) {
		boolean continuer = true;
		while (continuer) {
			System.out.println("______________________________________________________________________________________________________");
			System.out.println("|                                                                                                     |");
			System.out.println("|                           *** LA BOURSE DES ETUDIANTS -MAROCAINES- ***                              |");
			System.out.println("|--------------------------------------------------|--------------------------------------------------|");
			System.out.println("|                     Etudiants                    |                    Universites                   |");
			System.out.println("|--------------------------------------------------|--------------------------------------------------|");
			System.out.println("|  1. Afficher Les Etudiants Marocaines            |  4. Afficher Les Universites                     |");
			System.out.println("|  2. Afficher Etudiants Marocaines / Universites  |  5. Afficher Les Bourses                         |");
			System.out.println("|--------------------------------------------------|--------------------------------------------------|");
			System.out.println("|  3. *Afficher Tous Les Infos D'etudiant*         |                                                  |");
			System.out.println("|--------------------------------------------------|--------------------------------------------------|");
			System.out.println("|  0. Quitter                                      |                                                  |");
			System.out.println("|__________________________________________________|__________________________________________________|");
			
			System.out.print("\n  Choisir une option ==> ");
			item_menu = Integer.parseInt(reader.readLine());
			
			System.out.println("");
			
			switch (item_menu) {
				case 1:
					System.out.println("\n     -- 1. Afficher Les Etudiants Marocaines --\n");
					
					#sql iterator iter_m_aff_etud( int ID_ETUDIANT, String NOM, String PRENOM, String NATIONALITE, int ETRANGER );
					iter_m_aff_etud  etudiant_m_aff_etud;  
					#sql etudiant_m_aff_etud = { SELECT ID_ETUDIANT, NOM, PRENOM, NATIONALITE, ETRANGER FROM Etudiants_Maroc };
					System.out.println("");
					System.out.println("-----------------------------------------------------------");
					System.out.println("|                LES ETUDIANTS *MAROCAINES*                |");
					System.out.println("-----------------------------------------------------------");
					while (etudiant_m_aff_etud.next()) {
						System.out.println("| - ID_ETUDIANT: "+etudiant_m_aff_etud.ID_ETUDIANT() );
						System.out.println("|     NOM: " +etudiant_m_aff_etud.NOM() );
						System.out.println("|     PRENOM : "+etudiant_m_aff_etud.PRENOM() );
						System.out.println("|     NATIONALITE : "+etudiant_m_aff_etud.NATIONALITE() );
						System.out.println("|     ETRANGER: "+etudiant_m_aff_etud.ETRANGER() );
						System.out.println("| **********************************************************");
					}
					etudiant_m_aff_etud.close();     
					break;
					
				case 2:
					System.out.println("\n     -- 2. Afficher Etudiants Marocaines / Universites --\n");	
					
					#sql iterator iter_m_aff_etud_univ( int ID_ETUDIANT, int ID_UNIVERSITE );
					iter_m_aff_etud_univ  etu_univ_m_aff_etud_univ;  
					#sql etu_univ_m_aff_etud_univ = { SELECT ID_ETUDIANT, ID_UNIVERSITE FROM ETUD_UNIV_MAROC };
					
					System.out.println("");
					System.out.println("-----------------------------------------------------------");
					System.out.println("|          LES ETUDIANTS_UNIVERSITES *MAROCAINES*          |");
					System.out.println("-----------------------------------------------------------");
					while (etu_univ_m_aff_etud_univ.next()) {
						System.out.println("| - ID_ETUDIANT: "+etu_univ_m_aff_etud_univ.ID_ETUDIANT() );
						System.out.println("| - ID_UNIVERSITE: "+etu_univ_m_aff_etud_univ.ID_UNIVERSITE() );
						System.out.println("| **********************************************************");
					}
					etu_univ_m_aff_etud_univ.close();     
					break;
				
				case 3:
					System.out.println("\n     -- 3. *Afficher Tous Les Infos D'un Etudiant* --\n");	
					System.out.print("\n  Saisir ID_ETUDIANT: ");
					int id_etud = Integer.parseInt(reader.readLine());
					//les infos Personel
					#sql iterator iter_info_person( int ID_Etudiant, String Nom, String Prenom, String Nationalite, int Etranger );
					iter_info_person  etudiant_info_person;  
					#sql etudiant_info_person = { SELECT ID_Etudiant, Nom, Prenom, Nationalite, Etranger FROM ETUDIANTS_MAROC WHERE ID_Etudiant = :id_etud };
					System.out.println("");
					System.out.println("--------------------------------------------------------------------------");
					System.out.println("|                 MAROC : LES INFORMATION DE L'ETUDIANT: ");
					//System.out.print("|                 MAROC : LES INFORMATION DE L'ETUDIANT: "); #sql :Nom; System.out.print(""); #sql :Prenom; System.out.println("");
					System.out.println("--------------------------------------------------------------------------");
					while (etudiant_info_person.next()) {
						System.out.println("|        + Personal Information: ");
						System.out.println("|           - ID_ETUDIANT: "+etudiant_info_person.ID_Etudiant() );
						System.out.println("|           - NOM: " +etudiant_info_person.Nom() );
						System.out.println("|           - PRENOM : "+etudiant_info_person.Prenom() );
						System.out.println("|           - NATIONALITE : "+etudiant_info_person.Nationalite() );
						System.out.println("|           - ETRANGER: "+etudiant_info_person.Etranger() );
					}
					etudiant_info_person.close();   
					
					//les infos de l universite
					#sql iterator iter_info_univ( int ID_Universite, String Nom, String Localisation );
					iter_info_univ  universite_info_univ;  
					#sql universite_info_univ = { SELECT ID_Universite, Nom, Localisation FROM UNIVERSITES_MAROC WHERE ID_Universite IN ( SELECT ID_Universite FROM ETUD_UNIV_MAROC WHERE ID_Etudiant = :id_etud ) };
					while (universite_info_univ.next()) {
						System.out.println("|        + Universite: ");
						System.out.println("|           - ID_UNIVERSITE: "+universite_info_univ.ID_Universite() );
						System.out.println("|           - NOM: " +universite_info_univ.Nom() );
						System.out.println("|           - LOCALISATION : "+universite_info_univ.Localisation() );
					}
					universite_info_univ.close();   
					
					// les infos de labourse
					#sql iterator iter_info_bourse( int ID_Bourse, String Nom, int Montant );
					iter_info_bourse  bourse_info_bourse;  
					#sql bourse_info_bourse = { SELECT ID_Bourse, Nom, Montant FROM BOURSES_MAROC WHERE ID_Etudiant = :id_etud };
					while (bourse_info_bourse.next()) {
						System.out.println("|        + Bourse: ");
						System.out.println("|           - ID_BOURSE: "+bourse_info_bourse.ID_Bourse() );
						System.out.println("|           - NOM: " +bourse_info_bourse.Nom() );
						System.out.println("|           - MONTANT : "+bourse_info_bourse.Montant() );
						System.out.println("| **********************************************************");
					}
					bourse_info_bourse.close();   
					break;
				
				case 4:
					System.out.println("\n     -- 4. Afficher Les Universites  --\n");	
					#sql iterator iter_m_aff_univ( int ID_UNIVERSITE, String NOM, String LOCALISATION);
					iter_m_aff_univ  univ_m_aff_univ;  
					#sql univ_m_aff_univ = { SELECT ID_UNIVERSITE, NOM, LOCALISATION FROM UNIVERSITES_MAROC };
					System.out.println("");
					System.out.println("-----------------------------------------------------------");
					System.out.println("|               LES UNIVERSITES *MAROCAINES*               |");
					System.out.println("-----------------------------------------------------------");
					while (univ_m_aff_univ.next()) {
						System.out.println("| - ID_UNIVERSITE: "+univ_m_aff_univ.ID_UNIVERSITE() );
						System.out.println("|     NOM: " +univ_m_aff_univ.NOM() );
						System.out.println("|     LOCALISATION : "+univ_m_aff_univ.LOCALISATION() );
						System.out.println("| **********************************************************");
					}
					univ_m_aff_univ.close();     
					break;	
				
				case 5:
					System.out.println("\n     -- 5. Afficher Les Bourses --\n");
					#sql iterator iter_m_aff_brs( int ID_Bourse, int ID_Etudiant, String Nom, int Montant);
					iter_m_aff_brs  bourse;  
					#sql bourse = { SELECT ID_Bourse, ID_Etudiant, Nom, Montant FROM BOURSES_MAROC };
					System.out.println("");
					System.out.println("-----------------------------------------------------------");
					System.out.println("|         LES BOURSES DES ETUDIANTS *MAROCAINES*           |");
					System.out.println("-----------------------------------------------------------");
					while (bourse.next()) {
						System.out.println("| - ID_BOURSE: "+bourse.ID_Bourse() );
						System.out.println("|     ID_ETUDIANT: " +bourse.ID_Etudiant() );
						System.out.println("|     NOM: " +bourse.Nom() );
						System.out.println("|     MONTANT : "+bourse.Montant() );
						System.out.println("| **********************************************************");
					}
					bourse.close();     
					break;
				
				case 0:
					continuer = false; 
					break;
					
				default: 
					System.out.println("\n       *!*!*!*!* Choix invalide *!*!*!*!*");
			} //fin de switch
		}  //fin de while
	} //fin de maroc_bourse_menu()


	
	
	//----- etranger_bourse_menu () ======================================================================================================================
	public static void etranger_bourse_menu() throws IOException, SQLException {
		int n=0, m=0, nbr_ligne;
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		int item_menu = 1;
		//while(1<=item_menu && item_menu<=5) {
		boolean continuer = true;
		while (continuer) {
			System.out.println("______________________________________________________________________________________________________");
			System.out.println("|                                                                                                     |");
			System.out.println("|                              *** LA BOURSE DES ETUDIANTS -ETRANGES- ***                             |");
			System.out.println("|--------------------------------------------------|--------------------------------------------------|");
			System.out.println("|                     Etudiants                    |                    Universites                   |");
			System.out.println("|--------------------------------------------------|--------------------------------------------------|");
			System.out.println("|  1. Afficher Les Etudiants Etranges              |  4. Afficher Les Universites                     |");
			System.out.println("|  2. Afficher Etudiants Etranges / Universites   |  5. Afficher Les Bourses                         |");
			System.out.println("|--------------------------------------------------|--------------------------------------------------|");
			System.out.println("|  3. *Afficher Tous Les Infos D'etudiant*         |                                                  |");
			System.out.println("|--------------------------------------------------|--------------------------------------------------|");
			System.out.println("|  0. Quitter                                      |                                                  |");
			System.out.println("|__________________________________________________|__________________________________________________|");
			
			System.out.print("\n  Choisir une option ==> ");
			item_menu = Integer.parseInt(reader.readLine());
			
			System.out.println("");
			
			switch (item_menu) {
				case 1:
					System.out.println("\n     -- 1. Afficher Les Etudiants Etranges --\n");
					
					#sql iterator iter_e_aff_etud( int ID_ETUDIANT, String NOM, String PRENOM, String NATIONALITE, int ETRANGER );
					iter_e_aff_etud  etudiant_e_aff_etud;  
					#sql etudiant_e_aff_etud = { SELECT ID_ETUDIANT, NOM, PRENOM, NATIONALITE, ETRANGER FROM ETUDIANTS_ETRANGERS };
					System.out.println("");
					System.out.println("-----------------------------------------------------------");
					System.out.println("|                 LES ETUDIANTS *ETRANGERS*                |");
					System.out.println("-----------------------------------------------------------");
					while (etudiant_e_aff_etud.next()) {
						System.out.println("| - ID_ETUDIANT: "+etudiant_e_aff_etud.ID_ETUDIANT() );
						System.out.println("|     NOM: " +etudiant_e_aff_etud.NOM() );
						System.out.println("|     PRENOM : "+etudiant_e_aff_etud.PRENOM() );
						System.out.println("|     NATIONALITE : "+etudiant_e_aff_etud.NATIONALITE() );
						System.out.println("|     ETRANGER: "+etudiant_e_aff_etud.ETRANGER() );
						System.out.println("| **********************************************************");
					}
					etudiant_e_aff_etud.close();     
					break;
					
				case 2:
					System.out.println("\n     -- 2. Afficher Etudiants Etranges / Universites --\n");	
					
					#sql iterator iter_e_aff_etud_univ( int ID_ETUDIANT, int ID_UNIVERSITE );
					iter_e_aff_etud_univ  etu_univ_e_aff_etud_univ;  
					#sql etu_univ_e_aff_etud_univ = { SELECT ID_ETUDIANT, ID_UNIVERSITE FROM ETUD_UNIV_ETRANGERS };
					
					System.out.println("");
					System.out.println("-----------------------------------------------------------");
					System.out.println("|           LES ETUDIANTS_UNIVERSITES *ETRANGERS*          |");
					System.out.println("-----------------------------------------------------------");
					while (etu_univ_e_aff_etud_univ.next()) {
						System.out.println("| - ID_ETUDIANT: "+etu_univ_e_aff_etud_univ.ID_ETUDIANT() );
						System.out.println("| - ID_UNIVERSITE: "+etu_univ_e_aff_etud_univ.ID_UNIVERSITE() );
						System.out.println("| **********************************************************");
					}
					etu_univ_e_aff_etud_univ.close();     
					break;
					
				case 3:
					System.out.println("\n     -- 3. *Afficher Tous Les Infos D'un Etudiant* --\n");	
					System.out.print("\n  Saisir ID_ETUDIANT: ");
					int id_etud = Integer.parseInt(reader.readLine());
					//les infos Personel
					#sql iterator iter_info_person( int ID_Etudiant, String Nom, String Prenom, String Nationalite, int Etranger );
					iter_info_person  etudiant_info_person;  
					#sql etudiant_info_person = { SELECT ID_Etudiant, Nom, Prenom, Nationalite, Etranger FROM ETUDIANTS_ETRANGERS WHERE ID_Etudiant = :id_etud };
					System.out.println("");
					System.out.println("--------------------------------------------------------------------------");
					System.out.println("|                 ETRANGER : LES INFORMATION DE L'ETUDIANT: ");
					//System.out.print("|                 ETRANGER : LES INFORMATION DE L'ETUDIANT: "); #sql :Nom; System.out.print(""); #sql :Prenom; System.out.println("");
					System.out.println("--------------------------------------------------------------------------");
					while (etudiant_info_person.next()) {
						System.out.println("|        + Personal Information: ");
						System.out.println("|           - ID_ETUDIANT: "+etudiant_info_person.ID_Etudiant() );
						System.out.println("|           - NOM: " +etudiant_info_person.Nom() );
						System.out.println("|           - PRENOM : "+etudiant_info_person.Prenom() );
						System.out.println("|           - NATIONALITE : "+etudiant_info_person.Nationalite() );
						System.out.println("|           - ETRANGER: "+etudiant_info_person.Etranger() );
					}
					etudiant_info_person.close();   
					
					//les infos de l universite
					#sql iterator iter_info_univ( int ID_Universite, String Nom, String Localisation );
					iter_info_univ  universite_info_univ;  
					#sql universite_info_univ = { SELECT ID_Universite, Nom, Localisation FROM UNIVERSITES_ETRANGERS WHERE ID_Universite IN ( SELECT ID_Universite FROM ETUD_UNIV_ETRANGERS WHERE ID_Etudiant = :id_etud ) };
					while (universite_info_univ.next()) {
						System.out.println("|        + Universite: ");
						System.out.println("|           - ID_UNIVERSITE: "+universite_info_univ.ID_Universite() );
						System.out.println("|           - NOM: " +universite_info_univ.Nom() );
						System.out.println("|           - LOCALISATION : "+universite_info_univ.Localisation() );
					}
					universite_info_univ.close();   
					
					// les infos de labourse
					#sql iterator iter_info_bourse( int ID_Bourse, String Nom, int Montant );
					iter_info_bourse  bourse_info_bourse;  
					#sql bourse_info_bourse = { SELECT ID_Bourse, Nom, Montant FROM BOURSES_ETRANGERS WHERE ID_Etudiant = :id_etud };
					while (bourse_info_bourse.next()) {
						System.out.println("|        + Bourse: ");
						System.out.println("|           - ID_BOURSE: "+bourse_info_bourse.ID_Bourse() );
						System.out.println("|           - NOM: " +bourse_info_bourse.Nom() );
						System.out.println("|           - MONTANT : "+bourse_info_bourse.Montant() );
						System.out.println("| **********************************************************");
					}
					bourse_info_bourse.close();   
					break;	
				
				case 4:
					System.out.println("\n     -- 4. Afficher Les Universites  --\n");	
					#sql iterator iter_e_aff_univ( int ID_UNIVERSITE, String NOM, String LOCALISATION);
					iter_e_aff_univ  universite_e_aff_univ;  
					#sql universite_e_aff_univ = { SELECT ID_UNIVERSITE, NOM, LOCALISATION FROM UNIVERSITES_ETRANGERS };
					System.out.println("");
					System.out.println("-----------------------------------------------------------");
					System.out.println("|                LES UNIVERSITES *ETRANGERS*               |");
					System.out.println("-----------------------------------------------------------");
					while (universite_e_aff_univ.next()) {
						System.out.println("| - ID_UNIVERSITE: "+universite_e_aff_univ.ID_UNIVERSITE() );
						System.out.println("|     NOM: " +universite_e_aff_univ.NOM() );
						System.out.println("|     LOCALISATION : "+universite_e_aff_univ.LOCALISATION() );
						System.out.println("| **********************************************************");
					}
					universite_e_aff_univ.close();     
					break;	
				
				case 5:
					System.out.println("\n     -- 5. Afficher Les Bourses --\n");
					#sql iterator iter_e_aff_brs( int ID_Bourse, int ID_Etudiant, String Nom, int Montant);
					iter_e_aff_brs bourse_e_aff_brs;  
					#sql bourse_e_aff_brs = { SELECT ID_Bourse, ID_Etudiant, Nom, Montant FROM BOURSES_ETRANGERS };
					System.out.println("");
					System.out.println("-----------------------------------------------------------");
					System.out.println("|          LES BOURSES DES ETUDIANTS *ETRANGERS*           |");
					System.out.println("-----------------------------------------------------------");
					while (bourse_e_aff_brs.next()) {
						System.out.println("| - ID_BOURSE: "+bourse_e_aff_brs.ID_Bourse() );
						System.out.println("|     ID_ETUDIANT: " +bourse_e_aff_brs.ID_Etudiant() );
						System.out.println("|     NOM: " +bourse_e_aff_brs.Nom() );
						System.out.println("|     MONTANT : "+bourse_e_aff_brs.Montant() );
						System.out.println("| **********************************************************");
					}
					bourse_e_aff_brs.close();     
					break;	
				
				case 0:
					continuer = false; 
					break;
					
				default: 
					System.out.println("\n       *!*!*!*!* Choix invalide *!*!*!*!*");
			} //fin de switch
		}  //fin de while
	} //fin de etranger_bourse_menu()


	

}
