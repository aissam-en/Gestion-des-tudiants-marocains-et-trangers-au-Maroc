package miniProjetBDR;

import java.sql.*;
import java.util.Scanner;

public class Main {

	private static Scanner sc = new Scanner(System.in);
	private static String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private static String user = "";
	private static String passwd = "";
	private static Connection conn = null;
	
	// ----------- Connection Avec BD
	public static Connection connexion(String p_url, String p_user, String p_passwd, Connection p_conn) {
		try {
			// chargement des pilotes Oracle :
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			// établissement de la connexion :
			p_conn = DriverManager.getConnection(p_url, p_user, p_passwd);
			
            System.out.println("Connection ... ");
            System.out.println("Connection --> OK");
		}
		// gestion des exceptions
		catch (SQLException e) {
            System.out.println("ERROR de Connection");
            e.printStackTrace();
		}
		return p_conn;
	}
	
	// ----------- fonction de loop infinie pour choisir 
	public static void menu() throws SQLException {
		while (true) {
            System.out.println("\n\n   --- JDBC --- ");
            System.out.println(" Choisir une option : ");
            System.out.println(" 1. Connectez en tant que *Admin Bourse* ");
            System.out.println(" 2. Bourses Etudiants Marocains ");
            System.out.println(" 3. Bourses Etudiants Etrangers ");
            System.out.println(" 4. Quitter ");

            System.out.print("\nchoix: ");
            int choice = sc.nextInt();
            
            switch(choice) {
                case 1:
                	System.out.println(" --- Admin Bourse --- ");
                	System.out.println("     (admin_bourse/aissam) ");
                	
                    System.out.print(" User: ");
                    user = sc.next();
                    
                    System.out.print(" Password: ");
                    passwd = sc.next();
                    
                    conn = connexion(url, user, passwd, conn);
                    System.out.println("  Connection avec Admin Bourse --> OK");
                    System.out.println("  -----------------------------------");

                    Admin_Bourse ad = new Admin_Bourse(); ad.admin_bourse(conn);
                    
               		conn.close(); 
            		
                    break;
                case 2:
                	System.out.println(" --- Bourses Etudiants Marocains --- ");
                	System.out.println("     (moderateur_bourse_marocaine/aissam) ");
                	
                    System.out.print(" User: ");
                    user = sc.next();
                    
                    System.out.print(" Password: ");
                    passwd = sc.next();
                    
                    conn = connexion(url, user, passwd, conn);
                    System.out.println("  Connection avec Bourses Etudiants Marocains --> OK");
                    System.out.println("  --------------------------------------------------");

                    Bourses_Etudiants_Marocains bem = new Bourses_Etudiants_Marocains();
                    bem.bourses_etudiants_marocains(conn);
            		
            		conn.close();             		
            		
                    break;
                case 3:
                	System.out.println(" --- Bourses Etudiants Etrangers --- ");
                	System.out.println("     (moderateur_bourse_etranger/aissam) ");
                	
                    System.out.print("User: ");
                    user = sc.next();
                    
                    System.out.print("Password: ");
                    passwd = sc.next();
                    
                    conn = connexion(url, user, passwd, conn);
                    System.out.println("  Connection avec Bourses Etudiants Etrangers --> OK");
                    System.out.println("  --------------------------------------------------  ");

                    Bourses_Etudiants_Etrangers bee = new Bourses_Etudiants_Etrangers();
                    bee.bourses_etudiants_etrangers(conn);
                    
            		conn.close(); 
            		
                    break;
                case 4:
            		conn.close(); 
            		
                    System.out.println("Quitter ... OK");
                    sc.close();
                    System.exit(0);

                default:
                    System.out.println("Choix invalide. Sélectionner une option valide (1, 2, 3 ou 4).");
            }
        }
	}

	public static void main(String[] args) throws SQLException {
		menu();
	}
}

