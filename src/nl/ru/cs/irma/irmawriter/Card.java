package nl.ru.cs.irma.irmawriter;

import java.awt.Image;

/**
 * Holds necessary information of cards retreived from the database
 *
 */
public class Card {
	private String name;
	private String email;
	private Image photo;
	private int cardId;
	private boolean personalised = false;
	
	public Card(String name, String email, Image photo, int cardId) {
		this.name = name;
		this.email = email;
		this.photo = photo;
		this.cardId = cardId;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public Image getPhoto() {
		return photo;
	}

	public int getCardId() {
		return cardId;
	}
	
	public boolean isPersonalised() {
		return personalised;
	}
	
	public void setPersonalised() {
		personalised = true;
	}
}