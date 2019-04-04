package main;

/*
* Nom de classe : MaClasse
*
* Description   : Classe qui sert à la gestion d'un client
*
* Version       : 2.0
*
* Date          : 2019/03/31
*
* Programmeurs	: Jonathan Martel-Raiche et Rayane Taleb
*/ 

public class Client {
	private String nom = null;

	public Client(String name) {
		this.nom = name;
	}

	public String getNom() {
		return nom;
	}

	public boolean Equals(Client client) {
		return nom.equals(client.getNom());
	}

}
