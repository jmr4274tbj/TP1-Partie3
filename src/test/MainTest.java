package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import main.Client;
import main.GestionFichier;
import main.FichierAide;

@RunWith(MockitoJUnitRunner.class)
class MainTest {

	@Mock
	Client client = new Client("Bernard");

	@Test
	void testClientNom() {
		assertEquals("Bernard", client.getNom());
	}

	@Test
	void testClient() {
		assertEquals(true, client.Equals(client));
	}

	@Test
	void testFichier() {
		assertEquals(true, FichierAide.fichierConforme("./fichierConforme.txt"));
	}

	@Test
	void testFichierExistant() {
		boolean erreur = false;
		erreur = new GestionFichier().definirLecteur("fichierInexistant.txt", "Ce fichier n'existe pas.");
		assertEquals(false, erreur);
	}
	
	@Test
	void testClientsVide() {
		assertEquals(true, FichierAide.clientsVide("./fichierAucunClients.txt"));
	}
	
	@Test
	void testPlatsVide() {
		assertEquals(true, FichierAide.PlatsVide("./fichierAucunPlats.txt"));
	}
	
}
