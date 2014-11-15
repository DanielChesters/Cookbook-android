package fr.oni.cookbook.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.List;

import fr.oni.cookbook.BuildConfig;
import fr.oni.cookbook.R;
import fr.oni.cookbook.StringConstant;
import fr.oni.cookbook.adapter.MainRecipeAdapter;
import fr.oni.cookbook.model.Data;
import fr.oni.cookbook.model.Recipe;

public class MainActivity extends ActionBarActivity {

    private static final int EDIT_RECIPE_REQUEST = 1;
    private static final int VIEW_RECIPE_REQUEST = 2;
    private static final int EXPORT_RECIPES_REQUEST = 3;
    private static final int IMPORT_RECIPES_REQUEST = 4;
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
            InputStream inputStream;
            try {
                inputStream = openFileInput(StringConstant.DATA_FILE);
            } catch (FileNotFoundException e) {
                inputStream = getResources().openRawResource(R.raw.sample);
            }
            if (inputStream != null) {
                new ReadTask().execute(inputStream);
            }
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

            case R.id.action_export_recipes:
                openExportRecipes();
                return true;

            case R.id.action_import_recipes:
                openImportRecipes();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openImportRecipes() {
        Intent importIntent = new Intent();
        importIntent.setType("application/json");
        importIntent.setAction(Intent.ACTION_GET_CONTENT);
        importIntent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(Intent.createChooser(importIntent, "Test"), IMPORT_RECIPES_REQUEST);
    }

    private void openExportRecipes() {
        Intent exportIntent = new Intent();
        exportIntent.setType("application/json");
        exportIntent.setAction(Intent.ACTION_CREATE_DOCUMENT);
        exportIntent.addCategory(Intent.CATEGORY_OPENABLE);
        exportIntent.putExtra(Intent.EXTRA_TITLE, "export.json");
        startActivityForResult(Intent.createChooser(exportIntent, "Test"), EXPORT_RECIPES_REQUEST);
    }

    private void saveRecipes() {
        try {
            FileOutputStream outputStream = openFileOutput(StringConstant.DATA_FILE, Context.MODE_PRIVATE);
            new SaveTask().execute(outputStream);
            Toast.makeText(getApplicationContext(), R.string.action_save_recipes_confirm, Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Log.e(StringConstant.TAG_DATA_WRITE, StringConstant.FILE_WRITE_ERROR + e.toString(), e);
        }

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
            switch (requestCode) {
                case IMPORT_RECIPES_REQUEST:
                    Uri jsonImportFileUri = intent.getData();
                    Log.d("onActivityResult", "Uri: " + jsonImportFileUri.toString());
                    importData(jsonImportFileUri);
                    break;
                case EXPORT_RECIPES_REQUEST:
                    Uri jsonExportFileUri = intent.getData();
                    exportData(jsonExportFileUri);
                    break;
                default:
                    recipeAdapter.notifyDataSetChanged();
                    try {
                        FileOutputStream outputStream = openFileOutput(StringConstant.DATA_FILE, Context.MODE_PRIVATE);
                        new SaveTask().execute(outputStream);
                    } catch (IOException e) {
                        Log.e(StringConstant.TAG_DATA_WRITE, StringConstant.FILE_WRITE_ERROR + e.toString(), e);
                    }

                    break;
            }

        }
    }

    private void importData(Uri jsonImportFileUri) {
        try {
            ParcelFileDescriptor importFileDescriptor = getBaseContext().getContentResolver().openFileDescriptor(jsonImportFileUri, "r");
            FileInputStream inputStream = new FileInputStream(importFileDescriptor.getFileDescriptor());
            new ReadTask().execute(inputStream);
        } catch (IOException e) {
            Log.e(StringConstant.TAG_DATA_IMPORT, StringConstant.FILE_READ_ERROR + e.toString(), e);
        }
    }

    private void exportData(Uri jsonExportFileUri) {
        try {
            ParcelFileDescriptor exportFileDescriptor = getBaseContext().getContentResolver().openFileDescriptor(jsonExportFileUri, "rwt");
            FileOutputStream exportFileOutputStream = new FileOutputStream(exportFileDescriptor.getFileDescriptor());
            new SaveTask().execute(exportFileOutputStream);
        } catch (IOException e) {
            Log.e(StringConstant.TAG_DATA_EXPORT, StringConstant.FILE_WRITE_ERROR + e.toString(), e);
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
        if (recipes != null && !recipes.isEmpty()) {
            data.getRecipes().clear();
            data.getRecipes().addAll(recipes);
        }
    }

    private void writeToFile(OutputStream outputStream, String data) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            outputStreamWriter.write(data);
            outputStreamWriter.close();
            outputStream.close();
        } catch (IOException e) {
            Log.e(StringConstant.TAG_DATA_WRITE, StringConstant.FILE_WRITE_ERROR + e.toString(), e);
        }
    }

    private String readFromFile(InputStream inputStream) {

        String ret = "";

        try {
            if (inputStream != null) {
                int sizeFile = inputStream.available();
                byte[] buffer = new byte[sizeFile];
                inputStream.read(buffer);
                inputStream.close();
                ret = new String(buffer, Charset.defaultCharset());
            }
        } catch (FileNotFoundException e) {
            Log.e(StringConstant.TAG_DATA_READ, StringConstant.FILE_READ_NOT_FOUND + e.toString(), e);
        } catch (IOException e) {
            Log.e(StringConstant.TAG_DATA_READ, StringConstant.FILE_READ_ERROR + e.toString(), e);
        }

        return ret;
    }

    private class ReadTask extends AsyncTask<InputStream, Void, String> {

        @Override
        protected String doInBackground(InputStream... params) {
            return readFromFile(params[0]);
        }

        @Override
        protected void onPostExecute(String json) {
            stringToData(json);
            recipeAdapter.notifyDataSetChanged();
        }

    }

    private class SaveTask extends AsyncTask<OutputStream, Void, Void> {

        @Override
        protected Void doInBackground(OutputStream... params) {
            writeToFile(params[0], dataToString());
            return null;
        }

    }

}
