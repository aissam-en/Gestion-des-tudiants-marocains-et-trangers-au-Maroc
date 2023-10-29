package miniProjetBDR;
//sqlplus moderateur_bourse_marocaine/aissam

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Bourses_Etudiants_Marocains {
	
	private static Statement stmt = null;
	private static ResultSet result = null;
	private static String sqlString = "";
	private static PreparedStatement pstmt = null;
	
	// Pour Afficher Les Informations de tous Etudiants Marocains
	public static void afficher_etudiant(JFrame frame, Connection conn) throws SQLException {
		JFrame fr = new JFrame("Tous les étudiants Marocaines");
		fr.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		fr.setSize(797, 411);

		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		JLabel titreLabel = new JLabel("LES ETUDIANTS *MAROCAINES* : ");
		titreLabel.setHorizontalAlignment(JLabel.CENTER);
		panel.add(titreLabel, BorderLayout.NORTH);

		String text = "";

		stmt = conn.createStatement();
		result = stmt.executeQuery("SELECT ID_ETUDIANT, NOM, PRENOM, NATIONALITE, ETRANGER FROM Etudiants_Maroc");

		text += "--------------------------------------------------------------------------------------------\n";
		text += "|  ID_ETUDIANT |  NOM               |  PRENOM            |  NATIONALITÉ     |  ÉTRANGER    |\n";
		text += "--------------------------------------------------------------------------------------------\n";

		while (result.next()) {
		    int id = result.getInt(1);
		    String nom = result.getString(2);
		    String prenom = result.getString(3);
		    String nationalite = result.getString(4);
		    int etranger = result.getInt(5);
		    
		    text += String.format("|  %-12d |  %-16s |  %-16s |  %-16s |  %-8d |\n", id, nom, prenom, nationalite, etranger);
		}

		text += "--------------------------------------------------------------------------------------------\n";

		JTextArea textArea = new JTextArea(text);
		textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
		textArea.setEditable(false);

		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		panel.add(scrollPane, BorderLayout.CENTER);

		fr.setContentPane(panel);
		fr.setVisible(true);
	};	
	
	// Pour Afficher Les Informations des universites
	public static void afficher_universite(JFrame frame, Connection conn) throws SQLException {
		JFrame fr = new JFrame("LES UNIVERSITES");
		fr.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		fr.setSize(750, 286);

		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		JLabel titreLabel = new JLabel("LES UNIVERSITES *MAROCAINES* : ");
		titreLabel.setHorizontalAlignment(JLabel.CENTER);
		panel.add(titreLabel, BorderLayout.NORTH);

		String text = "";

		stmt = conn.createStatement();
		result = stmt.executeQuery("SELECT ID_UNIVERSITE, NOM, LOCALISATION FROM UNIVERSITES_MAROC");

		text += "----------------------------------------------------------------------------------------------\n";
		text += "|  ID_UNIVERSITE |  NOM                                   |          LOCALISATION            |\n";
		text += "----------------------------------------------------------------------------------------------\n";

		while (result.next()) {
		    int id = result.getInt(1);
		    String nom = result.getString(2);
		    String loc = result.getString(3);;
		    
		    text += String.format("|  %-14d|  %-40s|  %-32s|\n", id, nom, loc);
		}

		text += "----------------------------------------------------------------------------------------------\n";

		JTextArea textArea = new JTextArea(text);
		textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
		textArea.setEditable(false);

		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		panel.add(scrollPane, BorderLayout.CENTER);

		fr.setContentPane(panel);
		fr.setVisible(true);

	};	
	
	
	// Pour Afficher Les relations des Etudiants Marocains UNIVERSITES
	public static void afficher_etudiant_universite(JFrame frame, Connection conn) throws SQLException {
		JFrame fr = new JFrame("LES ETUDIANTS / UNIVERSITES");
		fr.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		fr.setSize(385, 318);

		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		JLabel titreLabel = new JLabel("LES ETUDIANTS_UNIVERSITES *MAROCAINES* : ");
		titreLabel.setHorizontalAlignment(JLabel.CENTER);
		panel.add(titreLabel, BorderLayout.NORTH);

		String text = "";

		stmt = conn.createStatement();
		result = stmt.executeQuery("SELECT ID_Etudiant, ID_UNIVERSITE FROM ETUD_UNIV_MAROC");

		text += "-------------------------------------------\n";
		text += "|  ID_Etudiant       |  ID_UNIVERSITE     |\n";
		text += "-------------------------------------------\n";

		while (result.next()) {
		    int id_etud = result.getInt(1);
		    int id_univ = result.getInt(2);
		    
		    text += String.format("|  %-17d|  %-17d |\n", id_etud, id_univ);
		}

		text += "-------------------------------------------\n";

		JTextArea textArea = new JTextArea(text);
		textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
		textArea.setEditable(false);

		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		panel.add(scrollPane, BorderLayout.CENTER);

		fr.setContentPane(panel);
		fr.setVisible(true);
	};	
	
		
	// Pour Afficher Les Informations des Etudiants Marocains et Bourses
	public static void afficher_bourse(JFrame frame, Connection conn) throws SQLException {
		JFrame fr = new JFrame("LES BOURSES");
		fr.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		fr.setSize(687, 368);

		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		JLabel titreLabel = new JLabel("LES BOURSES DES ETUDIANTS *MAROCAINES* ");
		titreLabel.setHorizontalAlignment(JLabel.CENTER);
		panel.add(titreLabel, BorderLayout.NORTH);

		String text = "";

		stmt = conn.createStatement();
		result = stmt.executeQuery("SELECT ID_Bourse, ID_Etudiant, Nom, Montant FROM BOURSES_MAROC");

		text += "---------------------------------------------------------------------------------\n";
		text += "|  ID_BOURSE     |  ID_ETUDIANT   |   NOM                          |  MONTANT   |\n";
		text += "---------------------------------------------------------------------------------\n";

		while (result.next()) {
		    int id_b = result.getInt(1);
		    int id_etud = result.getInt(2);
		    String nom = result.getString(3);
		    int montant = result.getInt(4);
		    
		    text += String.format("|  %-14d |  %-14d |  %-30s |  %-10d|\n", id_b, id_etud, nom, montant);
		}

		text += "---------------------------------------------------------------------------------\n";

		JTextArea textArea = new JTextArea(text);
		textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
		textArea.setEditable(false);

		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		panel.add(scrollPane, BorderLayout.CENTER);

		fr.setContentPane(panel);
		fr.setVisible(true);
	};	
	

	// Pour Afficher Les Informations d'un Etudiant Marocain
	public static void etudiant_tout_infos(JFrame frame, Connection conn) throws SQLException {
        JFrame fr = new JFrame("Les Information d'un etudiant Marocain");
        fr.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        fr.setSize(373, 164);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1));

		JLabel titreLabel = new JLabel("LES INFORMATION D'UN ETUDIANT MAROCAIN: ");
		titreLabel.setHorizontalAlignment(JLabel.CENTER);

        JLabel idEtudLabel = new JLabel("Enter ID de l'etudiant: ");
		idEtudLabel.setHorizontalAlignment(JLabel.CENTER);
        JTextField idEtudField = new JTextField(10);
		idEtudField.setHorizontalAlignment(JLabel.CENTER);

        JButton chercherButton = new JButton("Afficher Tous les informations");
		chercherButton.setHorizontalAlignment(JLabel.CENTER);

        chercherButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            	//Afficher nouveau Fenetre
				JFrame fr = new JFrame("LES ETUDIANTS / UNIVERSITES");
				fr.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				fr.setSize(844, 434);

				JPanel panel = new JPanel();
				panel.setLayout(new BorderLayout());
				panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

				JLabel titreLabel = new JLabel("LES INFORMATION D'UN ETUDIANT : ");
				titreLabel.setHorizontalAlignment(JLabel.CENTER);
				panel.add(titreLabel, BorderLayout.NORTH);
		
            	int id_etud = Integer.parseInt(idEtudField.getText());
				String text = "";
				
                sqlString = "SELECT ID_Etudiant, Nom, Prenom, Nationalite, Etranger FROM Etudiants_Maroc WHERE ID_Etudiant = ?"; 
				
                try {
					pstmt = conn.prepareStatement(sqlString);
				
					pstmt.setInt(1, id_etud);
					result = pstmt.executeQuery();
	                
					if (!result.isBeforeFirst()) {
						JOptionPane.showMessageDialog(fr, "*!*!*!*!* L'etudiant de ID N°"+id_etud+" n'exist pas *!*!*!*!*", "Error", JOptionPane.ERROR_MESSAGE);
					}else {
						while (result.next()) {
							text += "\n----------------------------------------------------------------------\n";
							text += "    MAROC : LES INFORMATION DE L'ETUDIANT: "+result.getString(3)+" "+result.getString(2)+"\n";
							text += "---------------------------------------------------------------------\n";
							text += "        + Personal Information: \n";
							text += "           - ID_ETUDIANT: "+result.getInt(1)+"\n";
							text += "           - NOM: "+result.getString(2)+"\n";
							text += "           - PRENOM: "+result.getString(3)+"\n";
							text += "           - NATIONALITE: "+result.getString(4)+"\n";
							text += "           - ETRANGER: ";
							if(result.getInt(5)==1) {
								text += "Oui\n";
							}else {
								text += "Non\n";
							}
						}	
					}
					
					sqlString = "SELECT ID_Universite, Nom, Localisation FROM Universites_Maroc WHERE ID_Universite IN ( SELECT ID_Universite FROM Etud_Univ_Maroc WHERE ID_Etudiant = ? )"; 
					pstmt = conn.prepareStatement(sqlString);
					pstmt.setInt(1, id_etud);
					result = pstmt.executeQuery();
					while (result.next()) {
						text += "        + Universite: \n";
						text += "           - ID_UNIVERSITE: "+result.getInt(1)+"\n";
						text += "           - NOM : "+result.getString(2)+"\n";
						text += "           - LOCALISATION : "+result.getString(3)+"\n";
					}
				
					sqlString = "SELECT ID_Bourse, Nom, Montant FROM Bourses_Maroc WHERE ID_Etudiant = ?"; 
					pstmt = conn.prepareStatement(sqlString);
					pstmt.setInt(1, id_etud);
					result = pstmt.executeQuery();
					while (result.next()) {
						text += "        + Bourse: \n";
						text += "           - ID_BOURSE: "+result.getInt(1)+"\n";
						text += "           - NOM : "+result.getString(2)+"\n";
						text += "           - MONTANT : "+result.getInt(3)+"\n";
					}
					
					text += "______________________________________________________________________\n\n\n";
					JTextArea textArea = new JTextArea(text);
					textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
					textArea.setEditable(false);
	
					JScrollPane scrollPane = new JScrollPane(textArea);
					scrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
	
					panel.add(scrollPane, BorderLayout.CENTER);
	
					fr.setContentPane(panel);
					fr.setVisible(true);
	            
		        } catch (SQLException e1) {
					e1.printStackTrace();
				}
            }
		});
        
        panel.add(titreLabel);
        panel.add(idEtudLabel);
        panel.add(idEtudField);
        panel.add(chercherButton);
		
		fr.setContentPane(panel);
		fr.setVisible(true);
    }
}
