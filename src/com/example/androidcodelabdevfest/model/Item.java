package com.example.androidcodelabdevfest.model;

public class Item {
	private String description;
	private String photo;

	public Item() {

	}

	public Item(String description, String photo) {
		this.description = description;
		this.photo = photo;
	}

	// Getters /Setters
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	@Override
	public String toString() {
		return "Item [description=" + description + ", photo=" + photo + "]";
	}
	
}
