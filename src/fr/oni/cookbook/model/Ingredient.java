package fr.oni.cookbook.model;

import java.io.Serializable;

public class Ingredient implements Serializable {
	private static final long serialVersionUID = -878175105293313375L;

	private String name;
	private String quantity;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public Ingredient(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return name;
	}
}
