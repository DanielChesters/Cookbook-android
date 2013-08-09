package fr.oni.cookbook.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import fr.oni.cookbook.R;
import fr.oni.cookbook.adapter.MainRecipeAdapter;
import fr.oni.cookbook.model.Ingredient;
import fr.oni.cookbook.model.Recipe;
import fr.oni.cookbook.model.Step;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListView listRecipes = (ListView) findViewById(R.id.listRecipes);

        final List<Recipe> recipes = getRecipes();

        listRecipes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        	public void onItemClick(AdapterView<?> adapterView, View view, int id,long itemID) {
        		Intent intent = new Intent(getApplicationContext(), RecipeViewActivity.class);
        		intent.putExtra("recipe", recipes.get(id));
        		startActivity(intent);
    	    }
		});

        MainRecipeAdapter recipeAdapter = new MainRecipeAdapter(this, recipes);

		listRecipes.setAdapter(recipeAdapter);
    }


	private List<Recipe> getRecipes() {
		final List<Recipe> recipes = new ArrayList<Recipe>();
		Recipe recipe = new Recipe("Long Test");
		recipe.setDescription("This is a description");

		for (int i = 0; i < 10; i++) {
			recipe.getIngredients().add(new Ingredient("Ingredient " + i));
		}

		for (int i = 0; i < 10; i++) {
			recipe.getSteps().add(new Step("Step " + i));
		}

		recipes.add(recipe);

		for (int i = 0; i < 10; i++) {
			recipes.add(new Recipe("Test " + + i));
		}

		return recipes;
	}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_add:
			addRecipe();
			return true;

		case R.id.action_settings:
			Toast.makeText(getApplicationContext(), "Setting", Toast.LENGTH_SHORT).show();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}


	private void addRecipe() {
		Intent intent = new Intent(getApplicationContext(), EditActivity.class);
		intent.putExtra("recipe", new Recipe("New Recipe"));
		startActivity(intent);
	}



}
