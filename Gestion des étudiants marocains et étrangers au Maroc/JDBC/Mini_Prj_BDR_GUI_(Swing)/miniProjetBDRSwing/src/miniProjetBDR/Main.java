package miniProjetBDR;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class Main {
	
	private static String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private static String user = "";
	private static String passwd = "";
	private static Connection conn = null;
	
	// -----------> Connection Avec BD
	public static Connection connexion(String p_url, String p_user, String p_passwd, Connection p_conn) {
		try {
			// chargement des pilotes Oracle :
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			// établissement de la connexion :
			p_conn = DriverManager.getConnection(p_url, p_user, p_passwd);
			
            System.out.println("Connection ... ");
            System.out.println("Connection --> OK");
		}
		// gestion des exceptions de connection
		catch (SQLException e) {
            System.out.println("ERROR de Connection");
            e.printStackTrace();
		}
		return p_conn;
	}
		
		
	private static String choix = "";
    public static void main(String[] args) {
        // Jframe principale
        JFrame frame = new JFrame("JDBC Projet - Gestion des etudiant au Maroc");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(760, 110);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        //  Ajouter un text au Jpanel 
        JLabel titleLabel = new JLabel("Connectez en tant que : ");
        panel.add(titleLabel);

        // Creation des 3 buttons
        JButton button1 = new JButton("Admin Bourse");
        JButton button2 = new JButton("Moderateur Etudiants Marocains");
        JButton button3 = new JButton("Moderateur Etudiants Etrangers");
        JButton quitButton = new JButton("Quitter");

        // action listeners pour les 3 buttons
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Le button 1 -----> clicked");
                choix = "1";
                //Afficher la fenetre de connection
                showLoginDialog(frame, choix);
            }
        });

        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Le button 2 -----> clicked");
                choix = "2";
                //Afficher la fenetre de connection
                showLoginDialog(frame, choix);
            }
        });

        button3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Le button 3 -----> clicked");
                choix = "3";
                //Afficher la fenetre de connection
                showLoginDialog(frame, choix);
            }
        });

        quitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Le button Quitter -----> clicked");
                //sortir le prg
                System.exit(0);
            }
        });

        // Ajouter les 3 buttons au Jpanel
        panel.add(button1);
        panel.add(button2);
        panel.add(button3);
        panel.add(quitButton);

        // Ajouter Jpanel à Jframe
        frame.add(panel);

        frame.setVisible(true);
    }

    private static void showLoginDialog(JFrame frame, String choix) {
    	
        //la fenetre de connection à la Base de donnees
    	
        JFrame connectionFrame = new JFrame("Connection à BD");
        connectionFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        connectionFrame.setSize(462, 150);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        
        String titre_Choix =  "";
        if (choix == "1" ) titre_Choix = "Admin Bourse";
        if (choix == "2" ) titre_Choix = "Moderateur Etudiants Marocains";
        if (choix == "3" ) titre_Choix = "Moderateur Etudiants Etrangers";
        JLabel titreLabel = new JLabel("Connectez en tant que \""+titre_Choix+"\"");
        titreLabel.setHorizontalAlignment(JLabel.CENTER);

        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField(30);
        JLabel passwordLabel = new JLabel("Password:");
        JTextField passwordField = new JTextField(30);

        JButton connectButton = new JButton("Connection");

        connectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {            	
            	user = usernameField.getText();
            	passwd = passwordField.getText();                   
            	conn = connexion(url, user, passwd, conn);
                
                // verifier si username et password sont correct, puis afficher le menue selon le choix               
                if (user.equals("admin_bourse") && passwd.equals("aissam")) {                    
                	Admin_Menu(frame, conn);
                    connectionFrame.dispose(); // fermer la fenetre de connection à BD
                }else if (user.equals("moderateur_bourse_marocaine") && passwd.equals("aissam")) {                   
                	Marocains_Menu(frame, conn);
                    connectionFrame.dispose(); 
                } else if (user.equals("moderateur_bourse_etranger") && passwd.equals("aissam")) {                   
                	Etrangers_Menu(frame, conn);
                    connectionFrame.dispose(); 
                } else {
                    JOptionPane.showMessageDialog(connectionFrame, "le username et le password sont incorrect", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        panel.add(titreLabel);
        
        //pour l'affichage :
        if (choix == "1" ) panel.add(new JLabel("                                                         "));
        if (choix == "2" ) panel.add(new JLabel("                      "));
        if (choix == "3" ) panel.add(new JLabel("                      "));
        
        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(connectButton);

        connectionFrame.add(panel);

        connectionFrame.setVisible(true);
    }


    private static void Admin_Menu(JFrame frame, Connection conn) {
        // Le Menu de l'Administrateur
    	
        JFrame connectedMenuFrame = new JFrame("*** ADMINISTRATEUR DE LA BOURSE AU MAROC ***");
        connectedMenuFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        connectedMenuFrame.setSize(500, 300);

        // Diviser la fenetre en 2 parties
        //une partie en haut et une partie en bas
        //la partie en haut est diviser en 2 partie: partie a gauche et partie a droite
        
        // La partite a gauche
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout(7, 1));
        
        leftPanel.setBorder(BorderFactory.createTitledBorder("Etudiants"));
        
        JButton adminButton1 = new JButton("1. Afficher Les Etudiants");
        adminButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("adminButton1 est -----> clicked");
                try {
					Admin_Bourse.afficher_etudiant(frame, conn);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
            }
        });
        leftPanel.add(adminButton1);
        
        JButton adminButton2 = new JButton("2. *Afficher Tous Les Infos D'etudiant*");
        adminButton2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("adminButton2 est -----> clicked");
                try {
					Admin_Bourse.etudiant_tout_infos(frame, conn);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
            }
        });
        leftPanel.add(adminButton2);
        
        
        JButton adminButton3 = new JButton("3. Ajouter un Etudiant");
        adminButton3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("adminButton3 est -----> clicked");
                try {
					Admin_Bourse.ajouter_etudiant_(frame, conn);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
            }
        });
        leftPanel.add(adminButton3);
        
        
        JButton adminButton4 = new JButton("4. Supprimer un Etudiant");
        adminButton4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("adminButton4 est -----> clicked");
                try {
					Admin_Bourse.supprimer_etudiant(frame, conn);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
            }
        });
        leftPanel.add(adminButton4);
        
        
        JButton adminButton5 = new JButton("5. Modifier un Etudiant");
        adminButton5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("adminButton5 est -----> clicked");
                try {
					Admin_Bourse.modifier_etudiant(frame, conn);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
            }
        });
        leftPanel.add(adminButton5);
        
        
        JButton adminButton6 = new JButton("6. Ajouter un Etudiant à une Universite");
        adminButton6.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("adminButton6 est -----> clicked");
                try {
					Admin_Bourse.ajouter_etudiant_universite_(frame, conn);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
            }
        });
        leftPanel.add(adminButton6);
        
        
        JButton adminButton7 = new JButton("7. Ajouter un Etudiant à une Bourse");
        adminButton7.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("adminButton7 est -----> clicked");
                try {
					Admin_Bourse.ajouter_etudiant_bourse_(frame, conn);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
            }
        });
        leftPanel.add(adminButton7);
        
        
        

     // La partie a droite
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new GridLayout(5, 1));
        rightPanel.setBorder(BorderFactory.createTitledBorder("Universites"));
        
        JButton adminButton8 = new JButton("8. Afficher Les Universites");
        adminButton8.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("adminButton8 est -----> clicked");
                try {
					Admin_Bourse.afficher_universite(frame, conn);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
            }
        });
        rightPanel.add(adminButton8);
        
        
        JButton adminButton9 = new JButton("9. Afficher Les Bourses");
        adminButton9.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("adminButton9 est -----> clicked");
                try {
					Admin_Bourse.afficher_bourse(frame, conn);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
            }
        });
        rightPanel.add(adminButton9);
        
        
        JButton adminButton10 = new JButton("10. Ajouter une Universite");
        adminButton10.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("adminButton10 est -----> clicked");
                try {
					Admin_Bourse.ajouter_universite_(frame, conn);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
            }
        });
        rightPanel.add(adminButton10);
        
        
        JButton adminButton11 = new JButton("11. Supprimer une Universite");
        adminButton11.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("adminButton11 est -----> clicked");
                try {
					Admin_Bourse.supprimer_universite(frame, conn);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
            }
        });
        rightPanel.add(adminButton11);
        
        
        JButton adminButton12 = new JButton("12. Modifier une Universite");
        adminButton12.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("adminButton12 est -----> clicked");
                try {
					Admin_Bourse.modifier_universite(frame, conn);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
            }
        });
        rightPanel.add(adminButton12);
        

        // La button "Déconnexion" pour Déconnexion de la BD et fermer la fenetre de l'Admin, et retour au Menu principale
        JButton deconnectionButton = new JButton("Déconnexion");
        deconnectionButton.setForeground(Color.RED);
        deconnectionButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	try {
            		//Déconnexion from la BD
					conn.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				} 
                connectedMenuFrame.dispose(); // Fermer la fenetre
                frame.setVisible(true); 
            }
        });

        // Centrer le button "Déconnexion" dans la pannel en bas
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.add(deconnectionButton);

        // Jpanel en haut qui contiens la partie droite et gauche
        JPanel upPanel = new JPanel(new BorderLayout());
        upPanel.add(leftPanel, BorderLayout.WEST);
        upPanel.add(rightPanel, BorderLayout.EAST);

        //La Jpannel principale de la fenetre de l'Admin
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(upPanel, BorderLayout.NORTH);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        connectedMenuFrame.add(mainPanel);

        connectedMenuFrame.setVisible(true);
    }
    
    
    private static void Marocains_Menu(JFrame frame, Connection conn) {
        // Menu pour les etudiants marocains (La meme structure que de l'Admin)
    	
        JFrame connectedMenuFrame = new JFrame("*** LES ETUDIANTS -MAROCAINES- ***");
        connectedMenuFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        connectedMenuFrame.setSize(600, 300);

        //la partie gauche 
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout(7, 1));
        leftPanel.setBorder(BorderFactory.createTitledBorder("Etudiants"));
        
        JButton marocButton1 = new JButton("1. Afficher Les Etudiants Marocaines");
        marocButton1.addActionListener(new ActionListener() {
              public void actionPerformed(ActionEvent e) {
                  System.out.println("marocButton1 est ----> clicked");
                  try {
                	  Bourses_Etudiants_Marocains.afficher_etudiant(frame, conn);
  				} catch (SQLException e1) {
  					e1.printStackTrace();
  				}
              }
          });
          leftPanel.add(marocButton1);


        JButton marocButton2 = new JButton("2. Afficher Etudiants Marocaines / Universites");
        marocButton2.addActionListener(new ActionListener() {
              public void actionPerformed(ActionEvent e) {
                  System.out.println("marocButton2 est ----> clicked");
                  try {
                	  Bourses_Etudiants_Marocains.afficher_etudiant_universite(frame, conn);
  				} catch (SQLException e1) {
  					e1.printStackTrace();
  				}
              }
          });
          leftPanel.add(marocButton2);
          
          
        JButton marocButton3 = new JButton("3. *Afficher Tous Les Infos D'etudiant*");
        marocButton3.addActionListener(new ActionListener() {
              public void actionPerformed(ActionEvent e) {
                  System.out.println("marocButton3 est ----> clicked");
                  
                  try {
                	  Bourses_Etudiants_Marocains.etudiant_tout_infos(frame, conn);
  				} catch (SQLException e1) {
  					e1.printStackTrace();
  				}
              }
          });
          leftPanel.add(marocButton3);

          
          
     // La partie droite
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new GridLayout(5, 1));
        rightPanel.setBorder(BorderFactory.createTitledBorder("Universites"));
        
        JButton marocButton4 = new JButton("4. Afficher Les Universites"); 
        marocButton4.addActionListener(new ActionListener() {
              public void actionPerformed(ActionEvent e) {
                  System.out.println("marocButton4 est ----> clicked");
                  try {
                	  Bourses_Etudiants_Marocains.afficher_universite(frame, conn);
  				} catch (SQLException e1) {
  					e1.printStackTrace();
  				}
              }
          });
        rightPanel.add(marocButton4);
          
          
        JButton marocButton5 = new JButton("5. Afficher Les Bourses"); 
        marocButton5.addActionListener(new ActionListener() {
              public void actionPerformed(ActionEvent e) {
                  System.out.println("marocButton5 est ----> clicked");
                  try {
                	  Bourses_Etudiants_Marocains.afficher_bourse(frame, conn);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
              }
          });
        rightPanel.add(marocButton5);
          
          

        // "Déconnexion" button
        JButton deconnectionButton = new JButton("Déconnexion");
        deconnectionButton.setForeground(Color.RED);
        deconnectionButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	try {
            		//
					conn.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				} 
                connectedMenuFrame.dispose(); 
                frame.setVisible(true); 
            }
        });

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.add(deconnectionButton);

        JPanel upPanel = new JPanel(new BorderLayout());
        upPanel.add(leftPanel, BorderLayout.WEST);
        upPanel.add(rightPanel, BorderLayout.EAST);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(upPanel, BorderLayout.NORTH);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        connectedMenuFrame.add(mainPanel);

        connectedMenuFrame.setVisible(true);
    }
    
    
    private static void Etrangers_Menu(JFrame frame, Connection conn) {
        // Menu pour les etudiant etrangers
    	
        JFrame connectedMenuFrame = new JFrame("*** LES ETUDIANTS -ETRANGES- ***");
        connectedMenuFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        connectedMenuFrame.setSize(600, 300);

        //La partie Gauche
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout(7, 1));
        leftPanel.setBorder(BorderFactory.createTitledBorder("Etudiants"));
        
        JButton etrangerButton1 = new JButton("1. Afficher Les Etudiants Etranges");
        etrangerButton1.addActionListener(new ActionListener() {
              public void actionPerformed(ActionEvent e) {
                  System.out.println("etrangerButton1 est -----> clicked");
                  try {
                	  Bourses_Etudiants_Etrangers.afficher_etudiant(frame, conn);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
              }
          });
          leftPanel.add(etrangerButton1);


        JButton etrangerButton2 = new JButton("2. Afficher Etudiants Etranges / Universites");
        etrangerButton2.addActionListener(new ActionListener() {
              public void actionPerformed(ActionEvent e) {
                  System.out.println("etrangerButton2 est -----> clicked");
                  try {
                	  Bourses_Etudiants_Etrangers.afficher_etudiant_universite(frame, conn);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
              }
          });
          leftPanel.add(etrangerButton2);
          
          
        JButton etrangerButton3 = new JButton("3. *Afficher Tous Les Infos D'etudiant*");
        etrangerButton3.addActionListener(new ActionListener() {
              public void actionPerformed(ActionEvent e) {
                  System.out.println("etrangerButton3 est -----> clicked");
                  try {
                	  Bourses_Etudiants_Etrangers.etudiant_tout_infos(frame, conn);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
              }
          });
          leftPanel.add(etrangerButton3);

          
          
        //Lapartie Droite
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new GridLayout(5, 1));
        rightPanel.setBorder(BorderFactory.createTitledBorder("Universites"));
        
        JButton etrangerButton4 = new JButton("4. Afficher Les Universites"); 
        etrangerButton4.addActionListener(new ActionListener() {
              public void actionPerformed(ActionEvent e) {
                  System.out.println("etrangerButton4 est -----> clicked");
                  try {
                	  Bourses_Etudiants_Etrangers.afficher_universite(frame, conn);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
              }
          });
        rightPanel.add(etrangerButton4);
          
          
        JButton etrangerButton5 = new JButton("5. Afficher Les Bourses"); 
        etrangerButton5.addActionListener(new ActionListener() {
              public void actionPerformed(ActionEvent e) {
                  System.out.println("etrangerButton5 est -----> clicked");
                  try {
                	  Bourses_Etudiants_Etrangers.afficher_bourse(frame, conn);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
              }
          });
        rightPanel.add(etrangerButton5);
          
          

        //"Déconnexion" button 
        JButton deconnectionButton = new JButton("Déconnexion");
        deconnectionButton.setForeground(Color.RED);
        deconnectionButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	try {
					conn.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				} 
                connectedMenuFrame.dispose();
                frame.setVisible(true);
            }
        });

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.add(deconnectionButton);

        JPanel upPanel = new JPanel(new BorderLayout());
        upPanel.add(leftPanel, BorderLayout.WEST);
        upPanel.add(rightPanel, BorderLayout.EAST);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(upPanel, BorderLayout.NORTH);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        connectedMenuFrame.add(mainPanel);

        connectedMenuFrame.setVisible(true);
    }
}

