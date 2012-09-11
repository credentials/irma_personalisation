package nl.ru.cs.irma.tests;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import nl.ru.cs.irma.irmawriter.EncryptedLoader;

import org.junit.Test;

public class EncryptedLoaderTest {

	private static final String FILENAME = "config.encrypted";
	private static final char[] PASSWORD = {'a', '5', 'X', '3', '2', 'c', '9'};

	@Test
	public void TestSaveLoad() throws FileNotFoundException, IOException {
		Properties startProperties = new Properties();
		startProperties.load(new FileInputStream("config.properties"));
		
		EncryptedLoader saveLoader = new EncryptedLoader(PASSWORD, FILENAME);
		saveLoader.save(startProperties);
		
		EncryptedLoader loadLoader = new EncryptedLoader(PASSWORD, FILENAME);
		Properties encryptedProperties = loadLoader.load();
		assertEquals(startProperties, encryptedProperties);
	}

}
