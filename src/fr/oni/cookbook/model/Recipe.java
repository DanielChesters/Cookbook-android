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
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(title);

		if (description != null && description.length() > 0) {
			builder.append("\n\n");
			builder.append(description);
		}

		if (!getIngredients().isEmpty()){
			builder.append("\n\n");
			for (Ingredient ingredient : ingredients) {
				builder.append(ingredient);
				builder.append("\n");
			}

		}

		if (!getSteps().isEmpty()) {
			builder.append("\n\n");
			for (Step step : steps) {
				builder.append(step);
				builder.append("\n\n");
			}
		}

		return builder.toString();
	}


}
