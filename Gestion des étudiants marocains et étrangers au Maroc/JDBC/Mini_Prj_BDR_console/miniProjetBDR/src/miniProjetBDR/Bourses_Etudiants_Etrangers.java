package miniProjetBDR;
//sqlplus moderateur_bourse_etranger/aissam

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Bourses_Etudiants_Etrangers {
	
	private static Scanner sc = new Scanner(System.in);
	private static Statement stmt = null;
	private static ResultSet result = null;
	private static String sqlString = "";
	private static PreparedStatement pstmt = null;

	// ----------- pour Bourses Etudiants Etrangers
	public void bourses_etudiants_etrangers(Connection conn) throws SQLException {

		boolean continuer = true;
		while (continuer) {
			System.out.printf("%-100s\n"       ,"______________________________________________________________________________________________________");
			System.out.printf("|%-50s %-50s|\n"," "                                            ," ");
			System.out.printf("|%-100s|\n"     ,"                               *** LA BOURSE DES ETUDIANTS -ETRANGES- ***                            ");
			System.out.printf("|%-50s|%-50s|\n","--------------------------------------------------","--------------------------------------------------");
			System.out.printf("|%-50s|%-50s|\n","                     Etudiants                    ","                    Universites                   ");
			System.out.printf("|%-50s|%-50s|\n","--------------------------------------------------","--------------------------------------------------");
			System.out.printf("|%-50s|%-50s|\n","  1. Afficher Les Etudiants Etranges"              ,"  4. Afficher Les Universites ");
			System.out.printf("|%-50s|%-50s|\n","  2. Afficher Etudiants Etranges / Universites "   ,"  5. Afficher Les Bourses ");
			System.out.printf("|%-50s|%-50s|\n","--------------------------------------------------","--------------------------------------------------");
			System.out.printf("|%-50s|%-50s|\n","  3. *Afficher Tous Les Infos D'etudiant* "        ," ");
			System.out.printf("|%-50s|%-50s|\n","--------------------------------------------------","--------------------------------------------------");
			System.out.printf("|%-50s|%-50s|\n","  0. Quitter"                                      ," ");
			System.out.printf("|%-50s|%-50s|\n","__________________________________________________","__________________________________________________");
			
			System.out.print("\n  Choisir une option ==> ");
            int choix = sc.nextInt();

            switch (choix) {
	        	case 1: 
	        		afficher_etudiant(conn);
	        		break;
	        	case 2: 
	        		afficher_etudiant_universite(conn);
	        		break;
	        	case 3: 
	        		System.out.print("\n   Enter ID de l'etudiant etrange: ");
	                int id_etud = sc.nextInt();
	        		etudiant_tout_infos(conn, id_etud);
	        		break;
	        	case 4: 
	        		afficher_universite(conn);
	        		break;
	        	case 5: 
	        		afficher_bourse(conn);
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
		result = stmt.executeQuery("SELECT ID_ETUDIANT, NOM, PRENOM, NATIONALITE, ETRANGER FROM Etudiants_Etrangers");
		
		System.out.println("\n----------------------------------------------------------------------------------------------");
		System.out.println("                            LES ETUDIANTS *ETRANGERS* ");
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
		result = stmt.executeQuery("SELECT ID_UNIVERSITE, NOM, LOCALISATION FROM UNIVERSITES_ETRANGERS");
				
		System.out.println("\n---------------------------------------------------------------------------");
		System.out.println("                            LES UNIVERSITES *ETRANGERS* ");
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
		result = stmt.executeQuery("SELECT ID_Etudiant, ID_UNIVERSITE FROM ETUD_UNIV_ETRANGERS");
		
		
		System.out.println("\n---------------------------------------");
		System.out.println(" LES ETUDIANTS_UNIVERSITES *ETRANGERS*");
		System.out.println("---------------------------------------");
		System.out.printf("|%-18s|%-18s|\n","  ID_ETUDIANT","  ID_UNIVERSITE");
		System.out.println("---------------------------------------");
	    while (result.next()) {
            System.out.printf("|  %-16d|  %-16d|\n", result.getInt(1), result.getInt(2));
	    }
        System.out.printf("|%-18s|%-18s|\n\n\n", "__________________", "__________________");
	};
	
	public static void afficher_bourse(Connection conn) throws SQLException {
		stmt = conn.createStatement();
		result = stmt.executeQuery("SELECT ID_Bourse, ID_Etudiant, Nom, Montant FROM BOURSES_ETRANGERS");
				
		System.out.println("\n------------------------------------------------------------------------");
		System.out.println("                  LES BOURSES DES ETUDIANTS *ETRANGERS* ");
		System.out.println("------------------------------------------------------------------------");
		System.out.printf("|%-16s|%-16s|%-20s|%-15s|\n","  ID_BOURSE","  ID_ETUDIANT","  NOM","  MONTANT");
		System.out.println("------------------------------------------------------------------------");
	    while (result.next()) {
            System.out.printf("|  %-14d|  %-14d|  %-18s|  %-13d|\n", result.getInt(1), result.getInt(2), result.getString(3), result.getInt(4));
	    }
        System.out.printf("|%-16s|%-16s|%-20s|%-15s|\n\n\n", "________________", "________________", "____________________", "_______________");
	};
	
	
	public static void etudiant_tout_infos(Connection conn, int p_ID_Etudiant) throws SQLException {
		
		sqlString = "SELECT ID_Etudiant, Nom, Prenom, Nationalite, Etranger FROM Etudiants_Etrangers WHERE ID_Etudiant = ?"; 
		pstmt = conn.prepareStatement(sqlString);
		pstmt.setInt(1, p_ID_Etudiant);
		result = pstmt.executeQuery();
		if (!result.isBeforeFirst()) {
			System.out.println(" *!*!*!*!* L'etudiant etranger de ID N°"+p_ID_Etudiant+" n'exist pas *!*!*!*!*\n\n\n");
		}else {
			while (result.next()) {
				System.out.println("\n-----------------------------------------------------------");
				System.out.println("    ERTANGERS : LES INFORMATION DE L'ETUDIANT: "+result.getString(3)+" "+result.getString(2));
				System.out.println("-----------------------------------------------------------");
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
			
			sqlString = "SELECT ID_Universite, Nom, Localisation FROM Universites_Etrangers WHERE ID_Universite IN ( SELECT ID_Universite FROM Etud_Univ_Etrangers WHERE ID_Etudiant = ? )"; 
			pstmt = conn.prepareStatement(sqlString);
			pstmt.setInt(1, p_ID_Etudiant);
			result = pstmt.executeQuery();
			while (result.next()) {
				System.out.println("        + Universite: ");
				System.out.println("           - ID_UNIVERSITE: "+result.getInt(1));
				System.out.println("           - NOM : "+result.getString(2));
				System.out.println("           - LOCALISATION : "+result.getString(3));
			}
			
		
			sqlString = "SELECT ID_Bourse, Nom, Montant FROM Bourses_Etrangers WHERE ID_Etudiant = ?"; 
			pstmt = conn.prepareStatement(sqlString);
			pstmt.setInt(1, p_ID_Etudiant);
			result = pstmt.executeQuery();
			while (result.next()) {
				System.out.println("        + Bourse: ");
				System.out.println("           - ID_BOURSE: "+result.getInt(1));
				System.out.println("           - NOM : "+result.getString(2));
				System.out.println("           - MONTANT : "+result.getInt(3));
			}
			System.out.println("__________________________________________________________\n\n\n");
		}
	};
}
