package fr.oni.cookbook.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shamanland.fab.ShowHideOnScroll;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import fr.oni.cookbook.BuildConfig;
import fr.oni.cookbook.R;
import fr.oni.cookbook.StringConstant;
import fr.oni.cookbook.adapter.MainRecipeAdapter;
import fr.oni.cookbook.model.Data;
import fr.oni.cookbook.model.Ingredient;
import fr.oni.cookbook.model.Recipe;
import fr.oni.cookbook.model.Step;

public class MainActivity extends ActionBarActivity {

    private static final int EDIT_RECIPE_REQUEST = 1;
    private static final int VIEW_RECIPE_REQUEST = 2;
    private Data data;
    private MainRecipeAdapter recipeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog()
                    .build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());
        }

        setContentView(R.layout.activity_main);
        data = (Data) getApplicationContext();

        final ListView listRecipes = (ListView) findViewById(R.id.listRecipes);

        recipeAdapter = new MainRecipeAdapter(this, data.getRecipes());

        if (data.getRecipes().isEmpty()) {
            new ReadTask().execute();
        }

        listRecipes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int id, long itemID) {
                Intent intent = new Intent(getApplicationContext(), RecipeViewActivity.class);
                data.setPosition(id);
                startActivityForResult(intent, VIEW_RECIPE_REQUEST);
            }
        });

        listRecipes.setAdapter(recipeAdapter);
        View fab = findViewById(R.id.fabListRecipes);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRecipe();
            }
        });
        listRecipes.setOnTouchListener(new ShowHideOnScroll(fab));
    }

    private List<Recipe> getSamplesRecipes() {
        final List<Recipe> recipes = new ArrayList<>();
        Recipe recipe = new Recipe("Long Test");
        recipe.setDescription("This is a description");

        for (int i = 0; i < 10; i++) {
            recipe.getIngredients().add(new Ingredient("Ingredient " + i));
        }

        recipe
                .getSteps()
                .add(
                        new Step(
                                "Tm1UMPhx0C\nLV5Cp15lER\ncXL3jyctqQ\nsr47tfwuk8\nteh1xGcS8U\nbRuoKzzvpm\n2RjYILdrAY\ncgeD7Syst2\nS4j4OgRpZ8\n5ih2MMVtQ7\nTm1UMPhx0C\nLV5Cp15lER\ncXL3jyctqQ\nsr47tfwuk8\nteh1xGcS8U\nbRuoKzzvpm\n2RjYILdrAY\ncgeD7Syst2\nS4j4OgRpZ8\n5ih2MMVtQ7"));

        for (int i = 0; i < 10; i++) {
            recipe.getSteps().add(new Step("Step " + i));
        }

        recipes.add(recipe);

        for (int i = 0; i < 10; i++) {
            recipes.add(new Recipe("Test " + +i));
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
            case R.id.action_save_recipes:
                saveRecipes();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveRecipes() {
        new SaveTask().execute();
        Toast
                .makeText(getApplicationContext(), R.string.action_save_recipes_confirm, Toast.LENGTH_LONG)
                .show();
    }

    private void addRecipe() {
        Recipe recipe = new Recipe(getString(R.string.new_recipe));
        data.getRecipes().add(recipe);
        data.setPosition(data.getRecipes().indexOf(recipe));
        Intent intent = new Intent(getApplicationContext(), EditActivity.class);

        startActivityForResult(intent, EDIT_RECIPE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (resultCode == RESULT_OK) {
            recipeAdapter.notifyDataSetChanged();
            writeToFile(dataToString());
        }
    }

    private String dataToString() {
        Gson gson = new Gson();
        return gson.toJson(data.getRecipes());
    }

    private void stringToData(String json) {
        Gson gson = new Gson();
        Type collectionType = new TypeToken<List<Recipe>>() {
            // Nothing
        }.getType();
        List<Recipe> recipes = gson.fromJson(json, collectionType);
        data.getRecipes().addAll(recipes);
    }

    private void writeToFile(String data) {
        try {
            OutputStreamWriter outputStreamWriter =
                    new OutputStreamWriter(openFileOutput(StringConstant.DATA_FILE, Context.MODE_PRIVATE),
                            Charset.defaultCharset());
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        } catch (IOException e) {
            Log.e(StringConstant.TAG_DATA_WRITE, StringConstant.FILE_WRITE_ERROR + e.toString(), e);
        }
    }

    private String readFromFile() {

        String ret = "";

        try {
            InputStream inputStream = openFileInput(StringConstant.DATA_FILE);

            if (inputStream != null) {
                InputStreamReader inputStreamReader =
                        new InputStreamReader(inputStream, Charset.defaultCharset());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString;
                StringBuilder stringBuilder = new StringBuilder();

                while ((receiveString = bufferedReader.readLine()) != null) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                inputStreamReader.close();
                bufferedReader.close();
                ret = stringBuilder.toString();
            }
        } catch (FileNotFoundException e) {
            Log.e(StringConstant.TAG_DATA_READ, StringConstant.FILE_READ_NOT_FOUND + e.toString(), e);
        } catch (IOException e) {
            Log.e(StringConstant.TAG_DATA_READ, StringConstant.FILE_READ_ERROR + e.toString(), e);
        }

        return ret;
    }

    private class ReadTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            return readFromFile();
        }

        @Override
        protected void onPostExecute(String json) {
            data.getRecipes().clear();

            if (json.isEmpty()) {
                data.getRecipes().addAll(getSamplesRecipes());
            } else {
                stringToData(json);
            }

            recipeAdapter.notifyDataSetChanged();
        }

    }

    private class SaveTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            writeToFile(dataToString());
            return null;
        }

    }

}
