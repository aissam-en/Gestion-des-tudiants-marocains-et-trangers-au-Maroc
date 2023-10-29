package miniProjetBDR;
//sqlplus admin_bourse/aissam

import java.sql.CallableStatement;
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

public class Admin_Bourse {
	
	private static Statement stmt = null;
	private static ResultSet result = null;
	private static String sqlString = "";
	private static PreparedStatement pstmt = null;


	// Pour afficher Les Informations de tous les etudians
	public static void afficher_etudiant(JFrame frame, Connection conn) throws SQLException {

		JFrame fr = new JFrame("Tous les étudiants");
		fr.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		fr.setSize(797, 411);

		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		JLabel titreLabel = new JLabel("Tous les Étudiants : ");
		titreLabel.setHorizontalAlignment(JLabel.CENTER);
		panel.add(titreLabel, BorderLayout.NORTH);

		String text = "";

		stmt = conn.createStatement();
		result = stmt.executeQuery("SELECT ID_ETUDIANT, NOM, PRENOM, NATIONALITE, ETRANGER FROM Etudiants");

		text += "-------------------------------------------------------------------------------------------\n";
		text += "|  ID_ETUDIANT |  NOM               |  PRENOM            |  NATIONALITÉ     |  ÉTRANGER   |\n";
		text += "-------------------------------------------------------------------------------------------\n";

		while (result.next()) {
		    int id = result.getInt(1);
		    String nom = result.getString(2);
		    String prenom = result.getString(3);
		    String nationalite = result.getString(4);
		    int etranger = result.getInt(5);
		    
		    text += String.format("|  %-12d |  %-16s |  %-16s |  %-16s |  %-8d |\n", id, nom, prenom, nationalite, etranger);
		}

		text += "-------------------------------------------------------------------------------------------\n";

		JTextArea textArea = new JTextArea(text);
		textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
		textArea.setEditable(false);

		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		panel.add(scrollPane, BorderLayout.CENTER);

		fr.setContentPane(panel);
		fr.setVisible(true);
	};	
	
	
	// Pour afficher Les Informations des Universites
	public static void afficher_universite(JFrame frame, Connection conn) throws SQLException {
		JFrame fr = new JFrame("LES UNIVERSITES");
		fr.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		fr.setSize(750, 286);

		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		JLabel titreLabel = new JLabel("LES UNIVERSITES : ");
		titreLabel.setHorizontalAlignment(JLabel.CENTER);
		panel.add(titreLabel, BorderLayout.NORTH);

		String text = "";

		stmt = conn.createStatement();
		result = stmt.executeQuery("SELECT ID_UNIVERSITE, NOM, LOCALISATION FROM UNIVERSITES");

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
	
	
	// Pour afficher Les Informations des Bourses
	public static void afficher_bourse(JFrame frame, Connection conn) throws SQLException {
		JFrame fr = new JFrame("LES BOURSES");
		fr.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		fr.setSize(687, 368);

		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		JLabel titreLabel = new JLabel("LES BOURSES : ");
		titreLabel.setHorizontalAlignment(JLabel.CENTER);
		panel.add(titreLabel, BorderLayout.NORTH);

		String text = "";

		stmt = conn.createStatement();
		result = stmt.executeQuery("SELECT ID_Bourse, ID_Etudiant, Nom, Montant FROM BOURSES");

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
	
	
	// Pour afficher Les Informations d'un Etudiant specifique 
	public static void etudiant_tout_infos(JFrame frame, Connection conn) throws SQLException {
        JFrame fr = new JFrame("Les Information d'un etudiant");
        fr.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        fr.setSize(373, 164);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1));

		JLabel titreLabel = new JLabel("LES INFORMATION D'UN ETUDIANT : ");
		titreLabel.setHorizontalAlignment(JLabel.CENTER);
		
        JLabel idEtudLabel = new JLabel("Enter ID de l'etudiant: ");
		idEtudLabel.setHorizontalAlignment(JLabel.CENTER);
        JTextField idEtudField = new JTextField(10);
		idEtudField.setHorizontalAlignment(JLabel.CENTER);

        JButton chercherButton = new JButton("Afficher Tous les informations");
		chercherButton.setHorizontalAlignment(JLabel.CENTER);

        chercherButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
				
				JFrame fr = new JFrame("LES ETUDIANTS / UNIVERSITES");
				fr.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				fr.setSize(617, 328);

				JPanel panel = new JPanel();
				panel.setLayout(new BorderLayout());
				panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

				JLabel titreLabel = new JLabel("LES INFORMATION D'UN ETUDIANT : ");
				titreLabel.setHorizontalAlignment(JLabel.CENTER);
				panel.add(titreLabel, BorderLayout.NORTH);
		
            	int id_etud = Integer.parseInt(idEtudField.getText());
				String text = "";
				
                sqlString = "SELECT ID_Etudiant, Nom, Prenom, Nationalite, Etranger FROM Etudiants WHERE ID_Etudiant = ?"; 
				
                try {
					pstmt = conn.prepareStatement(sqlString);
				
					pstmt.setInt(1, id_etud);
					result = pstmt.executeQuery();
	                
					if (!result.isBeforeFirst()) {
						JOptionPane.showMessageDialog(fr, "*!*!*!*!* L'etudiant de ID N°"+id_etud+" n'exist pas *!*!*!*!*", "Error", JOptionPane.ERROR_MESSAGE);
					}else {
						while (result.next()) {
							text += "\n----------------------------------------------------------------------\n";
							text += "            LES INFORMATION DE L'ETUDIANT: "+result.getString(3)+" "+result.getString(2)+"\n";
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
					
					
					sqlString = "SELECT ID_Universite, Nom, Localisation FROM Universites WHERE ID_Universite IN ( SELECT ID_Universite FROM Etudiants_Universites WHERE ID_Etudiant = ? )"; 
					pstmt = conn.prepareStatement(sqlString);
					pstmt.setInt(1, id_etud);
					result = pstmt.executeQuery();
					while (result.next()) {
						text += "        + Universite: \n";
						text += "           - ID_UNIVERSITE: "+result.getInt(1)+"\n";
						text += "           - NOM : "+result.getString(2)+"\n";
						text += "           - LOCALISATION : "+result.getString(3)+"\n";
					}
				
				
					sqlString = "SELECT ID_Bourse, Nom, Montant FROM Bourses WHERE ID_Etudiant = ?"; 
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
	
	
	// Pour Ajouter un Etudiant en utilisant la procedure stockee INSERT_etudiant(p_Nom, p_Prenom, p_Nationalite, p_Etranger)
	public static void ajouter_etudiant_(JFrame frame, Connection conn) throws SQLException {
        JFrame fr = new JFrame("Ajouter un Etudiant");
        fr.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        fr.setSize(500, 500);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(9, 1));

		JLabel titre1Label = new JLabel("AJOUTER UN ETUDIANT: ");
		titre1Label.setHorizontalAlignment(JLabel.CENTER);
		
        JLabel titre2Label = new JLabel("Entrez les information de l'etudiant : ");
		titre2Label.setHorizontalAlignment(JLabel.CENTER);
		
        JLabel ajNomLabel = new JLabel("Nom: ");
        JTextField ajNomField = new JTextField(10);

        JLabel ajPrenomLabel = new JLabel("Prenom: ");
        JTextField ajPrenomField = new JTextField(10);

        JLabel ajNationalLabel = new JLabel("Nationalite: ");
        JTextField ajNationalField = new JTextField(10);

        JLabel isEtrangerLabel = new JLabel("Est-ce un étudiant marocain? (oui / non) : ");
        JTextField isEtrangerField = new JTextField(10);
		
        JButton ajouterButton = new JButton("Ajouter");
		ajouterButton.setHorizontalAlignment(JLabel.CENTER);
		
        ajouterButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {				
				String nom = ajNomField.getText();
				String prenom = ajPrenomField.getText();
				String nationalite = ajNationalField.getText();
				String temp = isEtrangerField.getText();		
				int etranger = 0;
				if (temp.toLowerCase().equals("oui")) etranger = 1;
								
                try {
					CallableStatement etatAppelable = conn.prepareCall("{call INSERT_etudiant(?,?,?,?)}");

					etatAppelable.setString(1,nom);
					etatAppelable.setString(2,prenom);
					etatAppelable.setString(3,nationalite);
					etatAppelable.setInt(4,etranger);
					
					etatAppelable.execute();
					
					JOptionPane.showMessageDialog(fr, "Etudian est ajoutee", "Succès", JOptionPane.INFORMATION_MESSAGE);

		        } catch (SQLException e1) {
					e1.printStackTrace();
				}
            }
		});
        
        panel.add(titre1Label);
        panel.add(titre2Label);
		
        panel.add(ajNomLabel);
        panel.add(ajNomField);
        panel.add(ajPrenomLabel);
        panel.add(ajPrenomField);
        panel.add(ajNationalLabel);
        panel.add(ajNationalField);
        panel.add(isEtrangerLabel);
        panel.add(isEtrangerField);
        panel.add(ajouterButton);
		
		fr.setContentPane(panel);
		fr.setVisible(true);
    }
		
	
	// Pour Supprimer un Etudiant
	public static void supprimer_etudiant(JFrame frame, Connection conn) throws SQLException {
		afficher_etudiant(frame, conn);

        JFrame fr = new JFrame("Supprimer un Etudiant");
        fr.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        fr.setSize(473, 178);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1));

		JLabel titre1Label = new JLabel("Supprimer un Etudiant");
		titre1Label.setHorizontalAlignment(JLabel.CENTER);
		

        JLabel titre2Label = new JLabel("Entrez les information de l'Etudiant qui sera supprimer: ");
		titre2Label.setHorizontalAlignment(JLabel.CENTER);
		
        JLabel suppIdLabel = new JLabel("ID : ");
        JTextField suppIdField = new JTextField(10);
		
        JButton supprimerButton = new JButton("Supprimer");
		supprimerButton.setHorizontalAlignment(JLabel.CENTER);
		
        supprimerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {				
				int id_etud = Integer.parseInt(suppIdField.getText());
                try {
					pstmt = conn.prepareStatement("DELETE FROM ETUDIANTS where ID_ETUDIANT = ?");
					pstmt.setInt(1, id_etud);
					
					pstmt.executeUpdate();
								
					JOptionPane.showMessageDialog(fr, "L'Etudiant ID='"+id_etud+"' est supprimee", "Succès", JOptionPane.INFORMATION_MESSAGE);

		        } catch (SQLException e1) {
					e1.printStackTrace();
				}
            }
		});
        
        panel.add(titre1Label);
        panel.add(titre2Label);
		
        panel.add(suppIdLabel);
        panel.add(suppIdField);
		
        panel.add(supprimerButton);
		
		fr.setContentPane(panel);
		fr.setVisible(true);
    }
		
	// Pour Modifier les Information d'un Etudiant
	//methode ; sans procedure
	// OK tm tm
	public static void modifier_etudiant(JFrame frame, Connection conn) throws SQLException {
		//Afficher Les Etudiants enregistrers
		afficher_etudiant(frame, conn);

        JFrame fr = new JFrame("Modifier les infos d'un Etudiant");
        fr.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        fr.setSize(537, 361);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(13, 1));

		JLabel titre1Label = new JLabel("Modifier les infos d'un Etudiant: ");
		titre1Label.setHorizontalAlignment(JLabel.CENTER);
		
        JLabel titre2Label = new JLabel("Entrez les information de l'etudiant qui sera modifié: ");
		titre2Label.setHorizontalAlignment(JLabel.CENTER);
		
        JLabel modIdLabel = new JLabel("ID : ");
        JTextField modIdField = new JTextField(10);

        JLabel modNomLabel = new JLabel("Nom : ");
        JTextField modNomField = new JTextField(10);

        JLabel modPrenomLabel = new JLabel("Prenom : ");
        JTextField modPrenomField = new JTextField(10);

        JLabel modNationalLabel = new JLabel("Nationalite : ");
        JTextField modNationalField = new JTextField(10);

        JLabel isEtrangerLabel = new JLabel("Est-ce un étudiant marocain? (oui / non) : ");
        JTextField isEtrangerField = new JTextField(10);
		
        JButton modifierButton = new JButton("Modifier");
		modifierButton.setHorizontalAlignment(JLabel.CENTER);
        modifierButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {				
				int id_etud = Integer.parseInt(modIdField.getText());
				String nom_etud = modNomField.getText();
				String prenom_etud = modPrenomField.getText();
				String nationalite_etud = modNationalField.getText();
				String temp = isEtrangerField.getText();		
				int etranger_etud = 0;
				if (temp.toLowerCase().equals("oui")) etranger_etud = 1;
								
                try {
					pstmt = conn.prepareStatement("UPDATE ETUDIANTS SET NOM = ? , PRENOM = ? , NATIONALITE = ? , ETRANGER = ? where ID_ETUDIANT = ?");
					
					// Lier les variables
					pstmt.setString(1, nom_etud);
					pstmt.setString(2, prenom_etud);
					pstmt.setString(3, nationalite_etud);
					pstmt.setInt(4, etranger_etud);
					pstmt.setInt(5, id_etud);
					
					pstmt.executeUpdate();
								
					JOptionPane.showMessageDialog(fr, "Etudian ID='"+id_etud+"' est modifiee", "Succès", JOptionPane.INFORMATION_MESSAGE);

		        } catch (SQLException e1) {
					e1.printStackTrace();
				}
            }
		});
        
        panel.add(titre1Label);
        panel.add(titre2Label);
		
        panel.add(modIdLabel);
        panel.add(modIdField);
        panel.add(modNomLabel);
        panel.add(modNomField);
        panel.add(modPrenomLabel);
        panel.add(modPrenomField);
        panel.add(modNationalLabel);
        panel.add(modNationalField);
        panel.add(isEtrangerLabel);
        panel.add(isEtrangerField);
		
        panel.add(modifierButton);
		
		fr.setContentPane(panel);
		fr.setVisible(true);
    }
		

	// Pour Ajouter un Etudiant à Universite par INSERT_etudiant_universite()
	//methode : avec  la procedure INSERT_etudiant_universite(p_ID_etudiant, p_ID_universite)
	// OK tm tm 
	public static void ajouter_etudiant_universite_(JFrame frame, Connection conn) throws SQLException {
		
		//Afficher les Infos des etudiants et des universites
		afficher_etudiant(frame, conn);
		afficher_universite(frame, conn);

        JFrame fr = new JFrame("Ajouter un Etudiant à une Universite");
        fr.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        fr.setSize(461, 209);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1));

		JLabel titreLabel = new JLabel("Ajouter un Etudiant à une Universite: ");
		titreLabel.setHorizontalAlignment(JLabel.CENTER);
		
		JLabel idEtudLabel = new JLabel("Entrez le ID de l'etudiant : ");
        JTextField idEtudField = new JTextField(10);
		
        JLabel idUnivLabel = new JLabel("Entrez le ID de l'universite : ");
        JTextField idUnivField = new JTextField(10);

        JButton ajouterButton = new JButton("Ajouter");
		ajouterButton.setHorizontalAlignment(JLabel.CENTER);
        ajouterButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
				int idEtud = Integer.parseInt(idEtudField.getText());
				int idUniv = Integer.parseInt(idUnivField.getText());
                try {
                	//Appeler la fonction INSERT_etudiant_universite(p_ID_etudiant, p_ID_universite)
					CallableStatement etatAppelable = conn.prepareCall("{call INSERT_etudiant_universite(?,?)}");

					etatAppelable.setInt(1,idEtud);
					etatAppelable.setInt(2,idUniv);
					
					etatAppelable.execute();
					
					JOptionPane.showMessageDialog(fr, "Etudiant ID = '"+idEtud+" est ajoutee à l'Universite ID = "+idUniv, "Succès", JOptionPane.INFORMATION_MESSAGE);

		        } catch (SQLException e1) {
					e1.printStackTrace();
				}
            }
		});
        
        panel.add(titreLabel);
		
        panel.add(idEtudLabel);
        panel.add(idEtudField);
        panel.add(idUnivLabel);
        panel.add(idUnivField);
        panel.add(ajouterButton);
		
		fr.setContentPane(panel);
		fr.setVisible(true);
    }
		
	//methode : avec  la procedure INSERT_bourse(p_ID_etudiant, p_Nom, p_Montant)
	// OK tm tm 
	
	// Pour Ajouter un Etudiant à une Bourse par INSERT_bourse(p_ID_etudiant, p_Nom, p_Montant)
	public static void ajouter_etudiant_bourse_(JFrame frame, Connection conn) throws SQLException {

		afficher_etudiant(frame, conn);
		afficher_bourse(frame, conn);			

        JFrame fr = new JFrame("Ajouter un Etudiant à une Bourse");
        fr.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        fr.setSize(477, 259);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(8, 1));

		JLabel titreLabel = new JLabel("Ajouter un Etudiant à une Bourse: ");
		titreLabel.setHorizontalAlignment(JLabel.CENTER);
		
		JLabel idEtudLabel = new JLabel("Entrez le ID de l'etudiant : ");
        JTextField idEtudField = new JTextField(10);
		
        JLabel nomBourseLabel = new JLabel("Nom de la Bourse : ");
        JTextField nomBourseField = new JTextField(10);
		
        JLabel montBourseLabel = new JLabel("Montant de la Bourse : ");
        JTextField montBourseField = new JTextField(10);

        JButton ajouterButton = new JButton("Ajouter");
		ajouterButton.setHorizontalAlignment(JLabel.CENTER);
        ajouterButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {				
				int id_etud = Integer.parseInt(idEtudField.getText());
				String nom_brs = nomBourseField.getText();
				int montant_brs = Integer.parseInt(montBourseField.getText());
                try {
					//Appelant la fontion INSERT_bourse(p_ID_etudiant, p_Nom, p_Montant)
					CallableStatement etatAppelable = conn.prepareCall("{call INSERT_bourse(?,?,?)}");

					etatAppelable.setInt(1,id_etud);
					etatAppelable.setString(2,nom_brs);
					etatAppelable.setInt(3,montant_brs);
					
					etatAppelable.execute();
					
					JOptionPane.showMessageDialog(fr, "Etudiant ID = '"+id_etud+" est ajoutee à la Bourse '"+nom_brs, "Succès", JOptionPane.INFORMATION_MESSAGE);

		        } catch (SQLException e1) {
					e1.printStackTrace();
				}
            }
		});
        
        panel.add(titreLabel);
		
        panel.add(idEtudLabel);
        panel.add(idEtudField);
        panel.add(nomBourseLabel);
        panel.add(nomBourseField);
        panel.add(montBourseLabel);
        panel.add(montBourseField);
		
        panel.add(ajouterButton);
		
		fr.setContentPane(panel);
		fr.setVisible(true);
    }
		

	// Pour Ajouter une Universite par INSERT_universite(p_Nom, p_Localisation)
	//methode : avec  la procedure INSERT_universite(p_Nom, p_Localisation)
	// OK tm tm *** (not tested)
	public static void ajouter_universite_(JFrame frame, Connection conn) throws SQLException {	
        JFrame fr = new JFrame("Ajouter une Universite");
        fr.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        fr.setSize(504, 241);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1));

		JLabel titreLabel = new JLabel("Ajouter une Universite: ");
		titreLabel.setHorizontalAlignment(JLabel.CENTER);
		
        JLabel nomUnivLabel = new JLabel("Entrez le Nom de l'Universite : ");
        JTextField nomUnivField = new JTextField(10);
		
        JLabel locUnivLabel = new JLabel("Localisation : ");
        JTextField locUnivField = new JTextField(10);

        JButton ajouterButton = new JButton("Ajouter");
		ajouterButton.setHorizontalAlignment(JLabel.CENTER);
        ajouterButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
				String nom_univ = nomUnivField.getText();
				String loc_univ = locUnivField.getText();
                try {
					//INSERT_universite(p_Nom, p_Localisation)
					CallableStatement etatAppelable = conn.prepareCall("{call INSERT_universite(?,?)}");

					etatAppelable.setString(1,nom_univ);
					etatAppelable.setString(2,loc_univ);
					
					etatAppelable.execute();
					
					JOptionPane.showMessageDialog(fr, "Universite "+nom_univ+" est ajoutee", "Succès", JOptionPane.INFORMATION_MESSAGE);

		        } catch (SQLException e1) {
					e1.printStackTrace();
				}
            }
		});
        
        panel.add(titreLabel);
		
        panel.add(nomUnivLabel);
        panel.add(nomUnivField);
        panel.add(locUnivLabel);
        panel.add(locUnivField);
		
        panel.add(ajouterButton);
		
		fr.setContentPane(panel);
		fr.setVisible(true);
    }
		

	// Pour Supprimer une Universite
	//methode ; sans procedure
	// OK tm tm
	public static void supprimer_universite(JFrame frame, Connection conn) throws SQLException {
		
		afficher_universite(frame, conn);

        JFrame fr = new JFrame("Supprimer une Universite");
        fr.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        fr.setSize(480, 153);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1));

		JLabel titre1Label = new JLabel("Supprimer une Universite ");
		titre1Label.setHorizontalAlignment(JLabel.CENTER);

        JLabel titre2Label = new JLabel("Entrez les information de l'Universite qui sera supprimer: ");
		titre2Label.setHorizontalAlignment(JLabel.CENTER);
		
        JLabel suppIdLabel = new JLabel("ID : ");
        JTextField suppIdField = new JTextField(10);
		
        JButton supprimerButton = new JButton("Supprimer");
        supprimerButton.setHorizontalAlignment(JLabel.CENTER);
        supprimerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
				int id_univ = Integer.parseInt(suppIdField.getText());
                try {
					pstmt = conn.prepareStatement("DELETE FROM UNIVERSITES where ID_UNIVERSITE = ?");
					// Lier les variables
					pstmt.setInt(1, id_univ);
					pstmt.executeUpdate();
								
					JOptionPane.showMessageDialog(fr, "Universite ID='"+id_univ+"' est supprimee", "Succès", JOptionPane.INFORMATION_MESSAGE);

		        } catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
		});
        
        panel.add(titre1Label);
        panel.add(titre2Label);
		
        panel.add(suppIdLabel);
        panel.add(suppIdField);
		
        panel.add(supprimerButton);
		
		fr.setContentPane(panel);
		fr.setVisible(true);
    }
		

	// Pour Modifier les information d'une Universite
	//methode ; sans procedure
	// OK tm tm *** (not tested)
	public static void modifier_universite(JFrame frame, Connection conn) throws SQLException {
		
		afficher_universite(frame, conn);

        JFrame fr = new JFrame("Modifier les infos d'une Universite");
        fr.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        fr.setSize(481, 289);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(9, 1));

		JLabel titre1Label = new JLabel("Modifier les infos d'une Universite: ");
		titre1Label.setHorizontalAlignment(JLabel.CENTER);

        JLabel titre2Label = new JLabel("Entrez les information de l'Universite qui sera modifié: ");
		titre2Label.setHorizontalAlignment(JLabel.CENTER);
		
        JLabel modIdLabel = new JLabel("ID : ");
        JTextField modIdField = new JTextField(10);

        JLabel modNomLabel = new JLabel("Nom : ");
        JTextField modNomField = new JTextField(10);

        JLabel modLocLabel = new JLabel("Localisation : ");
        JTextField modLocField = new JTextField(10);
		
        JButton modifierButton = new JButton("Modifier");
		modifierButton.setHorizontalAlignment(JLabel.CENTER);
        modifierButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
				int id_univ = Integer.parseInt(modIdField.getText());
				String nom_univ = modNomField.getText();
				String loc_univ = modLocField.getText();
                try {
					pstmt = conn.prepareStatement("UPDATE UNIVERSITES SET NOM = ? , LOCALISATION = ? where ID_UNIVERSITE = ?");
					// Lier les variables
					pstmt.setString(1, nom_univ);
					pstmt.setString(2, loc_univ);
					pstmt.setInt(3, id_univ);
					
					pstmt.executeUpdate();
								
					JOptionPane.showMessageDialog(fr, "Universite ID='"+id_univ+"' est modifiee", "Succès", JOptionPane.INFORMATION_MESSAGE);

		        } catch (SQLException e1) {
					e1.printStackTrace();
				}
            }
		});
        
        panel.add(titre1Label);
        panel.add(titre2Label);
		
        panel.add(modIdLabel);
        panel.add(modIdField);
        panel.add(modNomLabel);
        panel.add(modNomField);
        panel.add(modLocLabel);
        panel.add(modLocField);
		
        panel.add(modifierButton);
		
		fr.setContentPane(panel);
		fr.setVisible(true);
    }
		

}
