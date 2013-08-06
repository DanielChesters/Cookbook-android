package fr.oni.cookbook.model;

import java.util.ArrayList;
import java.util.List;

public class Recipe {

	public Recipe(String title) {
		this.title = title;
	}

	private String title;
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


}
