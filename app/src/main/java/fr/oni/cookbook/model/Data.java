package fr.oni.cookbook.model;

import java.util.ArrayList;
import java.util.List;

import android.app.Application;

public class Data extends Application {
  private List<Recipe> recipes = new ArrayList<Recipe>();
  private int position;

  public int getPosition() {
    return position;
  }

  public void setPosition(int position) {
    this.position = position;
  }

  public List<Recipe> getRecipes() {
    return recipes;
  }

  public void setRecipes(List<Recipe> recipes) {
    this.recipes = recipes;
  }
}
