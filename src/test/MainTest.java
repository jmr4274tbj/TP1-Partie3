package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import main.Client;

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

}
