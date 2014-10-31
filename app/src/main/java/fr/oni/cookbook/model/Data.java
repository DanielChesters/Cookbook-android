package fr.oni.cookbook.model;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

public class Data extends Application {
    private List<Recipe> recipes = new ArrayList<>();
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
