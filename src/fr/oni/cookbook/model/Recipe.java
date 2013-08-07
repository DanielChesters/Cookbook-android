package fr.oni.cookbook.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Recipe implements Serializable{

	private static final long serialVersionUID = -8690849841120320192L;


	public Recipe(String title) {
		this.title = title;
	}

	private String title;
	private String description;
	private List<Ingredient> ingredients;
	private List<Step> steps;


	public List<Ingredient> getIngredients() {
		if (ingredients == null) {
			ingredients = new ArrayList<Ingredient>();
		}
		return ingredients;
	}
	public List<Step> getSteps() {
		if (steps == null) {
			steps = new ArrayList<Step>();
		}
		return steps;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}


}
