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
import fr.oni.cookbook.model.Recipe;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListView listRecipes = (ListView) findViewById(R.id.listRecipes);

        final List<Recipe> recipes = new ArrayList<Recipe>();

        listRecipes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        	public void onItemClick(AdapterView<?> adapterView, View view, int id,long itemID) {
        		Intent intent = new Intent(getApplicationContext(), RecipeViewActivity.class);
        		intent.putExtra("recipe", recipes.get(id));
        		startActivity(intent);
    	    }
		});

        recipes.add(new Recipe("Test 1"));
        recipes.add(new Recipe("Test 2"));
        recipes.add(new Recipe("Test 3"));
        recipes.add(new Recipe("Test 1"));
        recipes.add(new Recipe("Test 2"));
        recipes.add(new Recipe("Test 3"));
        recipes.add(new Recipe("Test 1"));
        recipes.add(new Recipe("Test 2"));
        recipes.add(new Recipe("Test 3"));
        recipes.add(new Recipe("Test 1"));
        recipes.add(new Recipe("Test 2"));
        recipes.add(new Recipe("Test 3"));
        recipes.add(new Recipe("Test 1"));
        recipes.add(new Recipe("Test 2"));
        recipes.add(new Recipe("Test 3"));
        recipes.add(new Recipe("Test 1"));
        recipes.add(new Recipe("Test 2"));
        recipes.add(new Recipe("Test 3"));
        recipes.add(new Recipe("Test 1"));
        recipes.add(new Recipe("Test 2"));
        recipes.add(new Recipe("Test 3"));
        recipes.add(new Recipe("Test 1"));
        recipes.add(new Recipe("Test 2"));
        recipes.add(new Recipe("Test 3"));
        recipes.add(new Recipe("Test 1"));
        recipes.add(new Recipe("Test 2"));
        recipes.add(new Recipe("Test 3"));
        recipes.add(new Recipe("Test 1"));
        recipes.add(new Recipe("Test 2"));
        recipes.add(new Recipe("Test 3"));
        recipes.add(new Recipe("Test 1"));
        recipes.add(new Recipe("Test 2"));
        recipes.add(new Recipe("Test 3"));
        recipes.add(new Recipe("Test 1"));
        recipes.add(new Recipe("Test 2"));
        recipes.add(new Recipe("Test 3"));
        recipes.add(new Recipe("Test 1"));
        recipes.add(new Recipe("Test 2"));
        recipes.add(new Recipe("Test 3"));
        recipes.add(new Recipe("Test 1"));
        recipes.add(new Recipe("Test 2"));
        recipes.add(new Recipe("Test 3"));


        MainRecipeAdapter recipeAdapter = new MainRecipeAdapter(this, recipes);

		listRecipes.setAdapter(recipeAdapter);
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
