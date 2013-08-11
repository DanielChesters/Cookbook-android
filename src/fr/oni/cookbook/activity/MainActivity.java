package fr.oni.cookbook.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
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

	static final int EDIT_RECIPE_REQUEST = 1;
	List<Recipe> recipes;
	MainRecipeAdapter recipeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListView listRecipes = (ListView) findViewById(R.id.listRecipes);

        recipes = getRecipes();

        listRecipes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        	public void onItemClick(AdapterView<?> adapterView, View view, int id,long itemID) {
        		Intent intent = new Intent(getApplicationContext(), RecipeViewActivity.class);
        		intent.putExtra("recipe", recipes.get(id));
        		intent.putExtra("position", id);
        		startActivityForResult(intent, EDIT_RECIPE_REQUEST);
    	    }
		});

        recipeAdapter = new MainRecipeAdapter(this, recipes);

		listRecipes.setAdapter(recipeAdapter);
    }


	private List<Recipe> getRecipes() {
		final List<Recipe> recipes = new ArrayList<Recipe>();
		Recipe recipe = new Recipe("Long Test");
		recipe.setDescription("This is a description");

		for (int i = 0; i < 10; i++) {
			recipe.getIngredients().add(new Ingredient("Ingredient " + i));
		}

		recipe.getSteps().add(new Step("Tm1UMPhx0C\nLV5Cp15lER\ncXL3jyctqQ\nsr47tfwuk8\nteh1xGcS8U\nbRuoKzzvpm\n2RjYILdrAY\ncgeD7Syst2\nS4j4OgRpZ8\n5ih2MMVtQ7\nTm1UMPhx0C\nLV5Cp15lER\ncXL3jyctqQ\nsr47tfwuk8\nteh1xGcS8U\nbRuoKzzvpm\n2RjYILdrAY\ncgeD7Syst2\nS4j4OgRpZ8\n5ih2MMVtQ7"));

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
		intent.putExtra("position", -1);
		startActivityForResult(intent, EDIT_RECIPE_REQUEST);
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == EDIT_RECIPE_REQUEST){
			Bundle extras = data.getExtras();
			switch (resultCode) {
				case RESULT_OK:
					Log.d("EDIT", "OK");
					Recipe recipe = (Recipe) extras.getSerializable("recipe");
					int position = extras.getInt("position");
					switch (position) {
						case -1:
							recipes.add(recipe);
							break;

						default:
							recipes.set(position, recipe);
							break;
					}
					recipeAdapter.notifyDataSetChanged();
					break;
				case RESULT_CANCELED:
					Log.d("EDIT", "Cancel");
					break;
				case RESULT_FIRST_USER:
					Log.d("EDIT", "???");
					break;
				default:
					break;
			}
		}

	}

}
