package org.irmacard.personalisation.test;

import javax.smartcardio.CardException;

import net.sourceforge.scuba.smartcards.CardServiceException;

import org.irmacard.personalisation.IrmaIssuer;
import org.junit.Test;

import credentials.Attributes;
import credentials.CredentialsException;

public class CredentialTest {
	@Test
	public void credentialIssue() throws CardException, CredentialsException, CardServiceException {
		IrmaIssuer ii = new IrmaIssuer();
		
		ii.issue(getSurfnetAttributes());
	}
	
    private Attributes getSurfnetAttributes() {
        // Return the attributes that have been revealed during the proof
        Attributes attributes = new Attributes();

		attributes.add("userID", "s654321@ru.nl".getBytes());
		attributes.add("securityHash", "DEADBEEF".getBytes()); // FIXME: Don't care for now
		
		return attributes;
	}

}
