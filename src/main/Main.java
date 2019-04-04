package main;

/*
* Nom de classe : Main
*
* Description   : Classe qui permet de lire un fichier et de créer un fichier de facture
*
* Version       : 2.0
*
* Date          : 2019/03/31
*
* Programmeurs	: Jonathan Martel-Raiche et Rayane Taleb
*/

import java.util.ArrayList;

public class Main {
	
	private static GestionFichier gestionFichier = new GestionFichier();
	private static String nomFicCommandes = "fichier.txt";
	private static ArrayList<Client> listeClients = new ArrayList<Client>();
	private static ArrayList<Plat> listePlats = new ArrayList<Plat>();
	private static ArrayList<Commandes> listeCommandes = new ArrayList<Commandes>();

	public static void main(String[] args) {
		
		boolean lecteur = gestionFichier.definirLecteur(nomFicCommandes, "Erreur de l'initialisation du lecteur de fichier");

		if(!lecteur) {
			System.err.println("Arret du programme");
			System.exit(0);
		}

		try {
			String ancien = null;
			String ligne = null;
			while ((ligne = gestionFichier.lireLigne("Erreur de lecture dans le fichier")) != null) {
				if (ligne.endsWith(":")) {
					ancien = ligne.replace(" :", "");
				} else if (ancien.equals("Clients")) {
					listeClients.add(new Client(ligne));
				} else if (ancien.equals("Plats")) {
					listePlats.add(new Plat(ligne));
				} else if (ancien.equals("Commandes")) {
					for (Client client : listeClients) {
						if (client.getNom().equals(ligne.split(" ")[0])) {
							for (Plat plat : listePlats) {
								if (plat.Equals(ligne.split(" ")[1])) {
									listeCommandes.add(new Commandes(client, plat, ligne));
									break;
								}
							}
						}
					}
				}
			}

			System.out.println("");
		} catch (Exception e) {
			gestionFichier.definirWriter("facture", "Erreur de lecture ");

			if (!gestionFichier.ecrire("Les entrées du fichier ne sont pas conformes!",
					"Erreur d'écriture dans le fichier de sortie")) {
				System.exit(0);
			}
		}
		gestionFichier.fermerTout("Erreur fermeture de la gestion de fichier");
		ecrireFactures();
		gestionFichier.fermerTout("Erreur fermeture de la gestion de fichier");
	}
	
	private static void ecrireFactures() {
		gestionFichier.definirWriter("facture", "Erreur de création du fichier de sortie");
		System.out.println("Bienvenue chez Barette!\r\n" + "Factures:\n");
		gestionFichier.ecrireLigne("Erreurs et commandes incorrectes:", "Erreur dans l'écriture du de sortie");		
		gestionFichier.ecrireLigne("\nBienvenue chez Barette!\r", "Erreur dans l'écriture du sortie");
		gestionFichier.ecrireLigne("Facture:", "Erreur dans l'écriture du fichier de sortie");
		
		for (Client client : listeClients) {
			for (Commandes commande : listeCommandes) {
				if (commande.Contains(client) && commande.getFacture() != 0) {
					gestionFichier.ecrire(client.getNom() + ": " + commande.getFacture() + "$\n",
							"Erreur de l'écriture dans le fichier");
					System.out.println(client.getNom() + ": " + commande.getFacture() + "$");
					break;
				}
			}
		}
	}
	
	
}
