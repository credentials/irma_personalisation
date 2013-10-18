package org.irmacard.personalisation;

import java.awt.Image;

/**
 * Holds necessary information of cards retreived from the database
 *
 */
public class Card {
	private String userID;
	private String name;
	private String email;
	private Image photo;
	private String cardId;
	private boolean personalised = false;
	
	public Card(String userID, String name, String email, Image photo, String cardId) {
		this.userID = userID;
		this.name = name;
		this.email = email;
		this.photo = photo;
		this.cardId = cardId;
	}

	public String getUserID() {
		return userID;
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

	public String getCardId() {
		return cardId;
	}
	
	public boolean isPersonalised() {
		return personalised;
	}
	
	public void setPersonalised() {
		personalised = true;
	}
}