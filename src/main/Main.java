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

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class Main {
	
	private static GestionFichier gestionFichier = new GestionFichier();
	private static String nomFicCommandes = "fichier.txt";
	private static ArrayList<Client> listeClients = new ArrayList<Client>();
	private static ArrayList<Plat> listePlats = new ArrayList<Plat>();
	private static ArrayList<Commandes> listeCommandes = new ArrayList<Commandes>();
	private static ArrayList<String> listeErreurs = new ArrayList<String>();
	private static NumberFormat nbFormat = new DecimalFormat("#0.00");
	private static boolean formatOK = false;

	public static void main(String[] args) {
		
		if(!FichierAide.fichierConforme(nomFicCommandes)) {
			listeErreurs.add("Erreur: Le format du fichier est incorrect.");
			formatOK = true;
		}
		if(FichierAide.clientsVide(nomFicCommandes)) {
			listeErreurs.add("Erreur: Le fichier ne contient aucun clients.");
		}
		if(FichierAide.PlatsVide(nomFicCommandes)) {
			listeErreurs.add("Erreur: Le fichier ne contient aucun plats.");
		}
		if(FichierAide.CommandesVide(nomFicCommandes)) {
			listeErreurs.add("Erreur: Le fichier ne contient aucune commandes.");
		}
		
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
			gestionFichier.definirWriter(FichierAide.getNomFacture(), "Erreur de lecture ");

			if (!gestionFichier.ecrire("Les entrées du fichier ne sont pas conformes!",
					"Erreur d'écriture dans le fichier de sortie")) {
				System.exit(0);
			}
		}
		gestionFichier.fermerTout("Erreur fermeture de la gestion de fichier");
		ecrireFactures();
		gestionFichier.fermerTout("Erreur fermeture de la gestion de fichier");
	}
	
	public static void ecrireErreurs(GestionFichier gestionFichier) {
		if(formatOK) {
			gestionFichier.ecrireLigne("Erreur: Format du fichier incorrect", "Erreur de l'écriture dans le fichier");
			gestionFichier.fermerTout("Erreur fermeture de la gestion de fichier");
			System.exit(0);
		}else {
			for(String erreur : listeErreurs) {
				gestionFichier.ecrireLigne(erreur, "Erreur de l'écriture dans le fichier");
				System.out.println(erreur);
			}	
		}
	}
	
	private static void ecrireFactures() {
		gestionFichier.definirWriter(FichierAide.getNomFacture(), "Erreur de création du fichier de sortie");
		System.out.println("Bienvenue chez Barette!\r\n" + "Factures:\n");
		gestionFichier.ecrireLigne("Erreurs et commandes incorrectes:", "Erreur dans l'écriture du de sortie");	
		ecrireErreurs(gestionFichier);	
		gestionFichier.ecrireLigne("\nBienvenue chez Barette!\r", "Erreur dans l'écriture du sortie");
		gestionFichier.ecrireLigne("Facture:", "Erreur dans l'écriture du fichier de sortie");
		
		for (Client client : listeClients) {
			for (Commandes commande : listeCommandes) {
				if (commande.Contains(client) && commande.getFacture() != 0) {
					gestionFichier.ecrire(client.getNom() + ": " + nbFormat.format(commande.getFacture() + calculerTaxes(commande.getFacture())) + "$\n",
							"Erreur de l'écriture dans le fichier");
					System.out.println(client.getNom() + ": " + nbFormat.format(commande.getFacture() + calculerTaxes(commande.getFacture())) + "$");
					break;
				}
			}
		}
	}
	
	public static double calculerTaxes(double montant) {
		double cout;
		double TPS = 0.05;
		double TVQ = 0.10;
		cout = (montant * TPS) + (montant * TVQ);
		return cout;
	}
	
}
