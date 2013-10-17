package org.irmacard.personalisation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Random;

import javax.smartcardio.CardTerminal;
import javax.smartcardio.TerminalFactory;

import net.sourceforge.scuba.smartcards.TerminalCardService;

import org.irmacard.credentials.Attributes;
import org.irmacard.credentials.idemix.IdemixCredentials;
import org.irmacard.credentials.idemix.IdemixPrivateKey;
import org.irmacard.credentials.idemix.spec.IdemixIssueSpecification;
import org.irmacard.credentials.idemix.test.TestSetup;
import org.irmacard.credentials.idemix.util.IssueCredentialInformation;
import org.irmacard.idemix.IdemixService;

public class CredentialLoader {
	public static void main(String[] args) {
		try {
			//Card card = getCardFromDb(args[0]);
			Card card = getCardFromDb("210732163");
			Random rand = new Random();
			
			byte[] credentialPin = new byte[4];
			for(int i = 0; i < 4; i++) {
				credentialPin[i] = (byte) (rand.nextInt(10) + 0x30);
			}
			
			byte[] cardPin = new byte[6];
			for(int i = 0; i < 6; i++) {
				cardPin[i] = (byte) (rand.nextInt(10) + 0x30);
			}
			
		//	issuer.setPin(credentialPin);
			Attributes attributes = new Attributes();
			attributes.add("userID", card.getUserID().getBytes());
			attributes.add("securityHash", hash(card.getUserID(), Integer.toString(card.getCardId())));

			IssueCredentialInformation ici = new IssueCredentialInformation("Surfnet", "root");
			IdemixIssueSpecification spec = ici.getIdemixIssueSpecification();
			IdemixPrivateKey isk = ici.getIdemixPrivateKey();
			
			CardTerminal terminal = TerminalFactory.getDefault().terminals().list().get(0);

			IdemixService is = new IdemixService(new TerminalCardService(terminal));
			IdemixCredentials ic = new IdemixCredentials(is);
			ic.connect();
			is.sendPin(TestSetup.DEFAULT_CRED_PIN);

			ic.issue(spec, isk, attributes, null);

			System.exit(0);
		}
		catch(Exception e) {
			System.err.println("Exception in Credential Loader: " + e.toString());
			System.exit(1);
		}
	}
	
	private static byte[] hash(String... strings) throws NoSuchAlgorithmException {
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		for(String string : strings) {
			digest.update(string.getBytes());
		}
		return digest.digest();
	}
	
	private static Card getCardFromDb(String cardIdString) throws IOException, SQLException {
		URI config = new File(System.getProperty("user.dir")).toURI().resolve("irma_configuration/config.properties.txt");
		
		BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(config)));
		StringBuilder sb = new StringBuilder();
		String line;
		while((line = bufferedReader.readLine()) != null) {
			sb.append(line);
			sb.append("\n");
		}
		bufferedReader.close();
		Properties props = new Properties();
		props.load(new StringReader(sb.toString()));
		
		Connection con = DriverManager.getConnection(props.getProperty("database_url"), props.getProperty("database_username"), props.getProperty("database_password"));
		PreparedStatement stmt = con.prepareStatement("SELECT eduPersonPrincipalName, givenName, surname, email, photo, cardID " +
										"FROM PilotParticipants "+
										"WHERE cardid=?");
		stmt.setInt(1, Integer.parseInt(cardIdString));
		ResultSet result = stmt.executeQuery();
		
		if(result.next()) {
			String userID = result.getString("eduPersonPrincipalName");
			String name = result.getString("givenName") + " " + result.getString("surname");
			String email = result.getString("email");
			int cardId = result.getInt("cardID");
			return new Card(userID, name, email, null, cardId);
		}
		
		return null;
	}
}
