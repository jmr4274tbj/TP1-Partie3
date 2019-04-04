package main;

/*
* Nom de classe : Plats
*
* Description   : Classe qui sert à la gestion d'un plat
*
* Version       : 2.0
*
* Date          : 2019/03/31
*
* Programmeurs	: Jonathan Martel-Raiche et Rayane Taleb
*/

public class Plat{
	private String nom = null;
	private double prix = 0.0;

	public Plat(String nom, double prix) {
		this.nom = nom;
		this.prix = prix;
	}
	public Plat(String ligneFichier) {
		this(ligneFichier.split(" ")[0], Double.parseDouble(ligneFichier.split(" ")[1]));
	}
	
	public double getPrix() {
		return this.prix;
	}

	public boolean Equals(Object arg) {
		return nom.equals(arg.toString());
	}
}
