package main;

/*
* Nom de classe : Commande
*
* Description   : Classe qui sert à la gestion d'une commande
*
* Version       : 2.0
*
* Date          : 2019/03/31
*
* Programmeurs	: Jonathan Martel-Raiche et Rayane Taleb
*/

public class Commandes {
	private Client client;
	private Plat plat;
	private int quantite = 0;

	public Commandes(Client client, Plat plat, int quantite) {
		this.client = client;
		this.plat = plat;
		this.quantite = quantite;
	}
	
	public Commandes(Client client, Plat plat, String ligneFichier) {
		this(client, plat, Integer.parseInt(ligneFichier.split(" ")[2]));
	}

	public boolean Contains(Client client) {
		return this.client.Equals(client);
	}

	public Plat getPlat() {
		return this.plat;
	}

	public Client getClient() {
		return this.client;
	}

	public int getQuantite() {
		return this.quantite;
	}

	public double getPrix() {
		return getPlat().getPrix()*getQuantite();
	}
	
	public double getFacture() {
		if (getPrix() == 0)
			return 0;
		return getPrix();
	}
}
