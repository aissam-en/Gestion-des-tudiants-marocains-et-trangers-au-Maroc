package miniProjetBDR;
//sqlplus admin_bourse/aissam

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Admin_Bourse {
	
	private static Scanner sc = new Scanner(System.in);
	private static Statement stmt = null;
	private static ResultSet result = null;
	private static String sqlString = "";
	private static PreparedStatement pstmt = null;

	// ----------- pour Admin Bourse
	public void admin_bourse(Connection conn) throws SQLException {
		
		boolean continuer = true;
		while (continuer) {
			System.out.printf("%-90s\n"        ,"_____________________________________________________________________________________________");
			System.out.printf("|%-45s %-45s|\n"," "                                            ," ");
			System.out.printf("|%-90s|\n"      ,"                       *** ADMINISTRATEUR DE LA BOURSE AU MAROC ***                        ");
			System.out.printf("|%-45s|%-45s|\n","---------------------------------------------","---------------------------------------------");
			System.out.printf("|%-45s|%-45s|\n","                  Etudiants                  ","                 Universites                 ");
			System.out.printf("|%-45s|%-45s|\n","---------------------------------------------","---------------------------------------------");
			System.out.printf("|%-45s|%-45s|\n","  1. Afficher Les Etudiants "                 ,"   8. Afficher Les Universites ");
			System.out.printf("|%-45s|%-45s|\n","  2. *Afficher Tous Les Infos D'etudiant* "   ,"   9. Afficher Les Bourses ");
			System.out.printf("|%-45s|%-45s|\n","---------------------------------------------","---------------------------------------------");
			System.out.printf("|%-45s|%-45s|\n","  3. Ajouter un Etudiant "                    ,"  10. Ajouter une Universite ");
			System.out.printf("|%-45s|%-45s|\n","  4. Supprimer un Etudiant "                  ,"  11. Supprimer une Universite ");
			System.out.printf("|%-45s|%-45s|\n","  5. Modifier un Etudiant "                   ,"  12. Modifier une Universite ");
			System.out.printf("|%-45s|%-45s|\n","---------------------------------------------","---------------------------------------------");
			System.out.printf("|%-45s|%-45s|\n","  6. Ajouter un Etudiant a Universite "       ," ");
			System.out.printf("|%-45s|%-45s|\n","  7. Ajouter un Etudiant a Bourse "           ," ");
			System.out.printf("|%-45s|%-45s|\n","---------------------------------------------","---------------------------------------------");
			System.out.printf("|%-45s|%-45s|\n","  0. Quitter"                                 ," ");
			System.out.printf("|%-45s|%-45s|\n","_____________________________________________","_____________________________________________");

			System.out.print("\n  Choisir une option ==> ");
            int choix = sc.nextInt();

            switch (choix) {
	        	case 1: 
	        		afficher_etudiant(conn);
	        		break;
	        	case 2: 
	        		System.out.print("\n   Enter ID de l'etudiant: ");
	                int id_etud = sc.nextInt();
	        		etudiant_tout_infos(conn, id_etud);
	        		break;
	        	case 3: 
	        		ajouter_etudiant_(conn);
	        		break;
	        	case 4: 
	        		supprimer_etudiant(conn);
	        		break;
	        	case 5: 
	        		modifier_etudiant(conn);
	        		break;
	        	case 6: 
	        		ajouter_etudiant_universite_(conn);
	        		break;
	        	case 7: 
	        		ajouter_etudiant_bourse_(conn);
	        		break;
	        	case 8: 
	        		afficher_universite(conn);
	        		break;
	        	case 9: 
	        		afficher_bourse(conn);
	        		break;
	        	case 10: 
	        		ajouter_universite_(conn);
	        		break;
	        	case 11: 
	        		supprimer_universite(conn);
	        		break;
	        	case 12: 
	        		modifier_universite(conn);
	        		break;		
	        	case 0: 
	        		continuer = false; 
	        		break;
	        	default: 
	        		System.out.println("\n       *!*!*!*!* Choix invalide *!*!*!*!*");
	        }
		}
	}
	
	public static void afficher_etudiant(Connection conn) throws SQLException {
		stmt = conn.createStatement();
		result = stmt.executeQuery("SELECT ID_ETUDIANT, NOM, PRENOM, NATIONALITE, ETRANGER FROM Etudiants");
		
		System.out.println("\n----------------------------------------------------------------------------------------------");
		System.out.println("                            LES ETUDIANTS MAROCAINES ET ETRANGERS ");
		System.out.println("----------------------------------------------------------------------------------------------");
		System.out.printf("|%-14s|%-20s|%-20s|%-20s|%-14s|\n","  ID_ETUDIANT","  NOM","  PRENOM","  NATIONALITE","  ETRANGER");
		System.out.println("----------------------------------------------------------------------------------------------");
	    while (result.next()) {
            System.out.printf("|  %-12d|  %-18s|  %-18s|  %-18s|  %-12d|\n", result.getInt(1), result.getString(2), result.getString(3), result.getString(4), result.getInt(5));
	    }
        System.out.printf("|%-14s|%-20s|%-20s|%-20s|%-14s|\n\n\n", "______________", "____________________", "____________________", "____________________", "______________");
	};	
	
	public static void afficher_universite(Connection conn) throws SQLException {
		stmt = conn.createStatement();
		result = stmt.executeQuery("SELECT ID_UNIVERSITE, NOM, LOCALISATION FROM UNIVERSITES");
				
		System.out.println("\n---------------------------------------------------------------------------");
		System.out.println("                            LES UNIVERSITES ");
		System.out.println("---------------------------------------------------------------------------");
		System.out.printf("|%-16s|%-35s|%-20s|\n","  ID_UNIVERSITE","  NOM","  LOCALISATION");
		System.out.println("---------------------------------------------------------------------------");
	    while (result.next()) {
            System.out.printf("|  %-14d|  %-33s|  %-18s|\n", result.getInt(1), result.getString(2), result.getString(3));
	    }
        System.out.printf("|%-16s|%-35s|%-20s|\n\n\n", "________________", "___________________________________", "____________________");
	};

	public static void afficher_etudiant_universite(Connection conn) throws SQLException {
		stmt = conn.createStatement();
		result = stmt.executeQuery("SELECT ID_Etudiant, ID_UNIVERSITE FROM ETUDIANTS_UNIVERSITES");
		
		System.out.println("\n-----------------------------------");
		System.out.println("   LES ETUDIANTS_UNIVERSITES ");
		System.out.println("-----------------------------------");
		System.out.printf("|%-16s|%-16s|\n","  ID_ETUDIANT","  ID_UNIVERSITE");
		System.out.println("-----------------------------------");
	    while (result.next()) {
            System.out.printf("|  %-14d|  %-14d|\n", result.getInt(1), result.getInt(2));
	    }
        System.out.printf("|%-16s|%-16s|\n\n\n", "________________", "________________");
	};
	
	public static void afficher_bourse(Connection conn) throws SQLException {
		stmt = conn.createStatement();
		result = stmt.executeQuery("SELECT ID_Bourse, ID_Etudiant, Nom, Montant FROM BOURSES");
				
		System.out.println("\n------------------------------------------------------------------------");
		System.out.println("                          LES BOURSES ");
		System.out.println("------------------------------------------------------------------------");
		System.out.printf("|%-16s|%-16s|%-20s|%-15s|\n","  ID_BOURSE","  ID_ETUDIANT","  NOM","  MONTANT");
		System.out.println("------------------------------------------------------------------------");
	    while (result.next()) {
            System.out.printf("|  %-14d|  %-14d|  %-18s|  %-13d|\n", result.getInt(1), result.getInt(2), result.getString(3), result.getInt(4));
	    }
        System.out.printf("|%-16s|%-16s|%-20s|%-15s|\n\n\n", "________________", "________________", "____________________", "_______________");
	};
	
	public static void etudiant_tout_infos(Connection conn, int p_ID_Etudiant) throws SQLException {
		
		sqlString = "SELECT ID_Etudiant, Nom, Prenom, Nationalite, Etranger FROM Etudiants WHERE ID_Etudiant = ?"; 
		pstmt = conn.prepareStatement(sqlString);
		pstmt.setInt(1, p_ID_Etudiant);
		result = pstmt.executeQuery();
		if (!result.isBeforeFirst()) {
			System.out.println(" *!*!*!*!* L'etudiant de ID N°"+p_ID_Etudiant+" n'exist pas *!*!*!*!*\n\n\n");
		}else {
			while (result.next()) {
				System.out.println("\n----------------------------------------------------------------------");
				System.out.println("            LES INFORMATION DE L'ETUDIANT: "+result.getString(3)+" "+result.getString(2));
				System.out.println("----------------------------------------------------------------------");
				System.out.println("        + Personal Information: ");
				System.out.println("           - ID_ETUDIANT: "+result.getInt(1));
				System.out.println("           - NOM: "+result.getString(2));
				System.out.println("           - PRENOM: "+result.getString(3));
				System.out.println("           - NATIONALITE: "+result.getString(4));
				System.out.print("            ETRANGER: ");
				if(result.getInt(5)==1) {
					System.out.println("Oui");
				}else {
					System.out.println("Non");
				}
			}
						
			sqlString = "SELECT ID_Universite, Nom, Localisation FROM Universites WHERE ID_Universite IN ( SELECT ID_Universite FROM Etudiants_Universites WHERE ID_Etudiant = ? )"; 
			pstmt = conn.prepareStatement(sqlString);
			pstmt.setInt(1, p_ID_Etudiant);
			result = pstmt.executeQuery();
			while (result.next()) {
				System.out.println("        + Universite: ");
				System.out.println("           - ID_UNIVERSITE: "+result.getInt(1));
				System.out.println("           - NOM : "+result.getString(2));
				System.out.println("           - LOCALISATION : "+result.getString(3));
			}
			
		
			sqlString = "SELECT ID_Bourse, Nom, Montant FROM Bourses WHERE ID_Etudiant = ?"; 
			pstmt = conn.prepareStatement(sqlString);
			pstmt.setInt(1, p_ID_Etudiant);
			result = pstmt.executeQuery();
			while (result.next()) {
				System.out.println("        + Bourse: ");
				System.out.println("           - ID_BOURSE: "+result.getInt(1));
				System.out.println("           - NOM : "+result.getString(2));
				System.out.println("           - MONTANT : "+result.getInt(3));
			}
			System.out.println("______________________________________________________________________\n\n\n");
		}
	};	

	//methode 1 ; sans procedure
	public static void ajouter_etudiant(Connection conn) throws SQLException {
		String sqlString = "INSERT INTO ETUDIANTS values(?,?,?,?,?)"; 
		//INSERT INTO Etudiants VALUES(sq_Id_Etudiant.NEXTVAL, 'EN-NAHEL', 'Aissam', 'Marocain', 0);
		pstmt = conn.prepareStatement(sqlString);
		
		Statement stmt_sq_Id_Etudiant = conn.createStatement();
		ResultSet result_sq_Id_Etudiant = stmt_sq_Id_Etudiant.executeQuery("SELECT sq_Id_Etudiant.NEXTVAL FROM dual");

		int sq_Id_Etudiant_nextVal = 0;
		String nom_etud = "";
		String prenom_etud = "";
		String nationalite_etud = "";
		int etranger_etud = 0;
		
		if (result_sq_Id_Etudiant.next()) {
			sq_Id_Etudiant_nextVal = result_sq_Id_Etudiant.getInt(1);
		
			System.out.println("\n\n    - Ajouter un Etudiant - ");
			System.out.println("\n    Entrez les information de l'etudiant : ");
			System.out.print("\n  -Nom: ");
			nom_etud = sc.next();
			sc.nextLine();
			
			System.out.print("\n  -Prenom: ");
			prenom_etud = sc.next();
			sc.nextLine();
			
			System.out.print("\n  -Nationalite: ");
			nationalite_etud = sc.next();
			sc.nextLine();
			
			System.out.print("\n  -Est-ce un étudiant marocain? (oui / non): ");
			String str_maroc = sc.next();
			sc.nextLine();

			if(str_maroc.toLowerCase().equals("non")){
				etranger_etud = 1;
			}
		}
		
		pstmt.setInt(1, sq_Id_Etudiant_nextVal);
		pstmt.setString(2, nom_etud);
		pstmt.setString(3, prenom_etud);
		pstmt.setString(4, nationalite_etud);
		pstmt.setInt(5, etranger_etud);

		pstmt.executeUpdate();  
		

		
		System.out.println("\n         ==> Ajouter Etudiant '"+nom_etud+" "+prenom_etud+"' --> OK");
	}	

	//methode 2 : avec  la procedure INSERT_etudiant(p_Nom, p_Prenom, p_Nationalite, p_Etranger)
	public static void ajouter_etudiant_(Connection conn) throws SQLException {
		String nom_etud = "";
		String prenom_etud = "";
		String nationalite_etud = "";
		int etranger_etud = 0;
		sc.nextLine();

		System.out.println("\n\n    - Ajouter un Etudiant - ");
		System.out.println("\n    Entrez les information de l'etudiant : ");
		System.out.print("\n  -Nom: ");
		nom_etud = sc.nextLine();
		sc.nextLine();
		
		System.out.print("\n  -Prenom: ");
		prenom_etud = sc.next();
		sc.nextLine();
		
		System.out.print("\n  -Nationalite: ");
		nationalite_etud = sc.next();
		sc.nextLine();
		
		System.out.print("\n  -Est-ce un étudiant marocain? (oui / non): ");
		String str_maroc = sc.next();
		sc.nextLine();

		if(str_maroc.toLowerCase().equals("non")){
			etranger_etud = 1;
		}
		
		//INSERT_etudiant(p_Nom, p_Prenom, p_Nationalite, p_Etranger)
		CallableStatement etatAppelable = conn.prepareCall("{call INSERT_etudiant(?,?,?,?)}");

		etatAppelable.setString(1,nom_etud);
		etatAppelable.setString(2,prenom_etud);
		etatAppelable.setString(3,nationalite_etud);
		etatAppelable.setInt(4,etranger_etud);
		
		etatAppelable.execute();
		

		
		System.out.println("\n         ==> Ajouter Etudiant '"+nom_etud+" "+prenom_etud+"' --> OK");
	}	

	//methode ; sans procedure
	public static void supprimer_etudiant(Connection conn) throws SQLException {
		
		afficher_etudiant(conn);
		
		System.out.println("\n\n    - Supprimer un Etudiant - ");
		System.out.print("\n    Entrez l'ID de l'Etudiant: ");
		int id_etud = sc.nextInt();
		
		pstmt = conn.prepareStatement("DELETE FROM ETUDIANTS where ID_ETUDIANT = ?");
		// Lier les variables
		pstmt.setInt(1, id_etud);
		pstmt.executeUpdate();
		

		
		System.out.println("\n         ==> Supprimer Etudiant ID='"+id_etud+"' --> OK");
	}

	//methode ; sans procedure
	public static void modifier_etudiant(Connection conn) throws SQLException {
		
		afficher_etudiant(conn);
		
		System.out.println("\n\n    - Modifier un Etudiant - ");
		System.out.print("\n    Entrez ID de l'etudiant qui sera modifié: ");
		int id_etud = sc.nextInt();
		sc.nextLine();
		
		System.out.print("    -Nom de l'etudiant: ");
		String nom_etud = sc.nextLine();
		sc.nextLine();
		
		System.out.print("    -Prenom de l'etudiant: ");
		String prenom_etud = sc.nextLine();
		sc.nextLine();
		
		System.out.print("    -Nationalite de l'etudiant: ");
		String nationalite_etud = sc.nextLine();
		sc.nextLine();

		int etranger_etud = 0;
		System.out.print("    -Est-ce un étudiant marocain? (oui / non): ");
		String str_maroc = sc.next();
		sc.nextLine();
		if(str_maroc.toLowerCase().equals("non")){
			etranger_etud = 1;
		}
		
		pstmt = conn.prepareStatement("UPDATE ETUDIANTS SET NOM = ? , PRENOM = ? , NATIONALITE = ? , ETRANGER = ? where ID_ETUDIANT = ?");
		
		// Lier les variables
		pstmt.setString(1, nom_etud);
		pstmt.setString(2, prenom_etud);
		pstmt.setString(3, nationalite_etud);
		pstmt.setInt(4, etranger_etud);
		pstmt.setInt(5, id_etud);
		pstmt.executeUpdate();
		
		
		
		System.out.println("\n         ==> Modifier Etudiant ID='"+id_etud+"' --> OK");
	}	

	//methode : avec  la procedure INSERT_etudiant_universite(p_ID_etudiant, p_ID_universite)
	public static void ajouter_etudiant_universite_(Connection conn) throws SQLException {
		
		afficher_etudiant(conn);
		afficher_universite(conn);
		
		System.out.println("\n\n    - Ajouter un Etudiant a Universite - ");
		
		System.out.print("\n  -Entrez le ID de l'etudiant: ");
		int id_etud = sc.nextInt();
		sc.nextLine();
		
		System.out.print("\n  -Entrez le ID de l'universite: ");
		int id_univ = sc.nextInt();
		sc.nextLine();
		
		//INSERT_etudiant_universite(p_ID_etudiant, p_ID_universite)
		CallableStatement etatAppelable = conn.prepareCall("{call INSERT_etudiant_universite(?,?)}");

		etatAppelable.setInt(1,id_etud);
		etatAppelable.setInt(2,id_univ);
		
		etatAppelable.execute();
		

		

		System.out.println("\n         ==> Ajouter Etudiant de ID = '"+id_etud+" a l'Universite ID = "+id_univ+"' --> OK");
	}
	
	//methode : avec  la procedure INSERT_bourse(p_ID_etudiant, p_Nom, p_Montant)
	public static void ajouter_etudiant_bourse_(Connection conn) throws SQLException {

		afficher_etudiant(conn);
		afficher_bourse(conn);		
		
		System.out.println("\n\n    - Ajouter un Etudiant a Une Bourse - ");
		System.out.println("\n    Entrez les information: ");
		
		System.out.print("\n  -ID de l'etudiant: ");
		int id_etud = sc.nextInt();
		sc.nextLine();
		
		System.out.print("\n  -Nom de la Bourse: ");
		String nom_brs = sc.nextLine();
		sc.nextLine();
		
		System.out.print("\n  -Montant de la Bourse: ");
		int montant_brs = sc.nextInt();
		sc.nextLine();
		
		//INSERT_bourse(p_ID_etudiant, p_Nom, p_Montant)
		CallableStatement etatAppelable = conn.prepareCall("{call INSERT_bourse(?,?,?)}");

		etatAppelable.setInt(1,id_etud);
		etatAppelable.setString(2,nom_brs);
		etatAppelable.setInt(3,montant_brs);
		
		etatAppelable.execute();
		

		
		System.out.println("\n         ==> Ajouter Etudiant de ID='"+id_etud+"' a la Bourse '"+nom_brs+"' --> OK");
	}
	
	//methode : avec  la procedure INSERT_universite(p_Nom, p_Localisation)
	public static void ajouter_universite_(Connection conn) throws SQLException {	
		
		System.out.println("\n\n    - Ajouter une Universite - ");
		System.out.println("\n    Entrez les information de l'Universite : ");

		sc.nextLine();
		System.out.print("\n  -Nom: ");
		String nom_univ = sc.nextLine();
		sc.nextLine();
		
		System.out.print("\n  -Localisation: ");
		String loc_univ = sc.nextLine();
		sc.nextLine();
		
		//INSERT_universite(p_Nom, p_Localisation)
		CallableStatement etatAppelable = conn.prepareCall("{call INSERT_universite(?,?)}");

		etatAppelable.setString(1,nom_univ);
		etatAppelable.setString(2,loc_univ);
		
		etatAppelable.execute();
		


		System.out.println("\n         ==> Ajouter Universite "+nom_univ+" --> OK");
	}
	
	//methode ; sans procedure
	public static void supprimer_universite(Connection conn) throws SQLException {
		
		afficher_universite(conn);
		
		System.out.println("\n\n    - Supprimer une Universite - ");
		System.out.print("\n    Entrez l'ID de l'Universite: ");
		int id_univ = sc.nextInt();
		
		pstmt = conn.prepareStatement("DELETE FROM UNIVERSITES where ID_UNIVERSITE = ?");
		// Lier les variables
		pstmt.setInt(1, id_univ);
		pstmt.executeUpdate();
		

		
		System.out.println("\n         ==> Supprimer Universite ID='"+id_univ+"' --> OK");
	}
	
	//methode ; sans procedure
	public static void modifier_universite(Connection conn) throws SQLException {
		
		afficher_universite(conn);
		
		System.out.println("\n\n    - Modifier une Universite - ");
		System.out.print("\n    Entrez ID de l'universite qui sera modifié: ");
		int id_univ = sc.nextInt();
		sc.nextLine();
		
		System.out.print("    -Nom de l'Universite: ");
		String nom_univ = sc.nextLine();
		sc.nextLine();

		System.out.print("    -Localisation de l'Universite: ");
		String loc_univ = sc.nextLine();
		sc.nextLine();
		
		pstmt = conn.prepareStatement("UPDATE UNIVERSITES SET NOM = ? , LOCALISATION = ? where ID_UNIVERSITE = ?");
		
		// Lier les variables
		pstmt.setString(1, nom_univ);
		pstmt.setString(2, loc_univ);
		pstmt.setInt(3, id_univ);
		pstmt.executeUpdate();
		
		System.out.println("\n         ==> Modifier Universite ID='"+id_univ+"' --> OK");
	}
}
