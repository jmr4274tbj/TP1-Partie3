package main;
/*
* Nom de classe : FichierAide
*
* Version       : 2.0
*
* Date          : 2019/03/31
*
* Programmeurs	: Jonathan Martel-Raiche et Rayane Taleb
*/

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class FichierAide {


	public static String getNomFacture() {
		Calendar calendrier = Calendar.getInstance();
		SimpleDateFormat formatDate = new SimpleDateFormat("YYYY_MM_dd-HH_mm_ss");
		return "Facture-du-" + formatDate.format(calendrier.getTime()) + ".txt";
	}
	
	public static boolean fichierConforme(String path) {
		ArrayList<FileContent> listeContenu = new ArrayList<FileContent>();
		listeContenu.add(new FileContent("clients:"));
		listeContenu.add(new FileContent("commandes:"));
		listeContenu.add(new FileContent("plats:"));
		listeContenu.add(new FileContent("fin"));

		boolean conforme = true;
			
			GestionFichier gestionFichier = new GestionFichier();
			if (!gestionFichier.definirLecteur(path, "Erreur lors de l'initialisation du lecteur du fichier d'entrée")) {
				System.out.println("Arret du programme!");
				System.exit(0);
			}
			
			String ligne = null;
			ligne = gestionFichier.lireLigne("Erreur de lecture de premiere ligne du fichier");
			
			while (ligne != null) {
				String ligneRefactorie = getLigneRefactorie(ligne);
				for (FileContent tempContenu : listeContenu) {					
					if (ligneRefactorie.equals(tempContenu.getLigne())) {			
						tempContenu.setPresent(true);
					}

			} 
			ligne = gestionFichier.lireLigne("Erreur de lecture pour chaque ligne du fichier");
		} 
		gestionFichier.fermerTout("Erreur fermeture de la gestion de fichier");

		for (FileContent contenu : listeContenu) { 
			if (!contenu.getPresent()) {
				conforme = false;
			}
		}
		return conforme;
	}
	
	public static boolean clientsVide(String chemin) {
		ArrayList<FileContent> listeContenu = implementerContenuFichier(chemin);
		for (FileContent contenu : listeContenu) { 
			if (contenu.getLigne().equals("clients:")) {
				return contenu.getArrayLignes().size() == 0;
			}
		}
		return true;
	}
	
	public static boolean PlatsVide(String path) {
		ArrayList<FileContent> listeContenu = implementerContenuFichier(path);
		for (FileContent contenu : listeContenu) { 
			if (contenu.getLigne().equals("plats:")) {
				return contenu.getArrayLignes().size() == 0;
			}
		}
		return true;
	}
	
	public static boolean CommandesVide(String path) {
		ArrayList<FileContent> listeContenu = implementerContenuFichier(path);
		for (FileContent contenu : listeContenu) { 
			if (contenu.getLigne().equals("commandes:")) {
				return contenu.getArrayLignes().size() == 0;
			}
		}
		return true;
	}
	
	private static ArrayList<FileContent> implementerContenuFichier(String path) {
		
		ArrayList<FileContent> listeContenu = new ArrayList<FileContent>();
		if(fichierConforme(path)) {
			listeContenu.add(new FileContent("clients:"));
			listeContenu.add(new FileContent("commandes:"));
			listeContenu.add(new FileContent("plats:"));
			listeContenu.add(new FileContent("fin"));
			
			GestionFichier gestionFichier = new GestionFichier();
			if (!gestionFichier.definirLecteur(path, "Erreur de 'initialisation du lecteur de fichier" + "")) {
				System.out.println("Arrêt du programme");
				System.exit(0);
			}
						
			String ligne = null;
			ligne = gestionFichier.lireLigne("Erreur lors de la lecture du fichier");
			
			FileContent currentContent = null;
					
			while (ligne != null) {
				String ligneRefactorie = getLigneRefactorie(ligne);
				for (FileContent tempContenu : listeContenu) {	 
					if (ligneRefactorie.equals(tempContenu.getLigne())) { 	
						tempContenu.setPresent(true);
						currentContent = tempContenu;	
						break;
					}else{				
						if(tempContenu.equals(listeContenu.get(listeContenu.size()-1))) {
							currentContent.getArrayLignes().add(ligne);							
						}
					}
				} 
				ligne = gestionFichier.lireLigne("Erreur lors de la lecture du fichier");
			} 			
			gestionFichier.fermerTout("Erreur de fermeture de la gestion de fichier");
		}
		return listeContenu;
	}

	private static String getLigneRefactorie(String ligne) {
		ligne = ligne.replaceAll(" ", "");
		return ligne.toLowerCase();
	}
	
	
	private static class FileContent {
		
		public ArrayList<String> listeLigne = new ArrayList<String>();
		public String ligne = "";
		public boolean present = false;

		public FileContent(String ligne) {
			setLigne(ligne);
		}

		public String getLigne() {
			return ligne;
		}

		public void setLigne(String ligne) {
			this.ligne = ligne;
		}

		public boolean getPresent() {
			return present;
		}

		public void setPresent(boolean present) {
			this.present = present;
		}
		
		public ArrayList<String> getArrayLignes(){
			return listeLigne;
		}
	}
}
