package main;

/*
* Nom de classe : GestionFichier
*
* Description   : Classe qui sert à la gestion de fichier
*
* Version       : 2.0
*
* Date          : 2019/03/31
*
* Programmeurs	: Jonathan Martel-Raiche et Rayane Taleb
*/

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class GestionFichier {
	private BufferedReader lecture;
	private BufferedWriter ecriture;
	
	public boolean definirLecteur(String fichier, String erreur) {
		boolean ok = false;
		try {
			lecture = new BufferedReader(new FileReader(fichier));
			ok = true;
		} catch (FileNotFoundException e) { journalErreur(erreur); }
		return ok;
	}
	
	public boolean definirWriter(String fichier, String erreur){
		boolean ok = false;
		try {
			ecriture = new BufferedWriter(new FileWriter(fichier));
			ok = true;
		} catch (IOException e) { journalErreur(erreur); }
		return ok;
	}
	
	public String lireLigne(String erreur) {
		String ligne = null;
		try {
			ligne = lecture.readLine();
		} catch (Exception e) {
			if(e.getMessage().equals("Lecture fermé")) {
				return null;
			 } else { journalErreur(erreur); }
		}
		return ligne;
	}
	
	public boolean ecrire(String ligne, String erreur){
		boolean ok = false;
		try {
			ecriture.write(ligne);
			ok = true;
		} catch (Exception e) { journalErreur(erreur); }
		return ok;
	}
	
	public void ecrireLigne(String ligne, String erreur){
		try {
			ecriture.write(ligne + "\n");
		} catch (Exception e) { journalErreur(erreur); }
	}
	
	public void fermerTout(String erreur) {
		boolean probleme = false;
		if(lecture != null) {
			try {
				lecture.close();
			} catch (Exception e) { probleme = true; }
		}
		if(ecriture != null) {
			try {
				ecriture.close();
			} catch (Exception e) { probleme = true; }			
		}	
		if(probleme) {
			journalErreur(erreur);
		}	
	}
	
	private void journalErreur(String erreur) {
		System.err.println(erreur);
		if(ecriture != null) {
			ecrireLigne(erreur, "");
		}
	}
	
}
