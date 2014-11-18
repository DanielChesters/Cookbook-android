package fr.oni.cookbook.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.shamanland.fab.ShowHideOnScroll;

import fr.oni.cookbook.BuildConfig;
import fr.oni.cookbook.R;
import fr.oni.cookbook.adapter.MainRecipeAdapter;
import fr.oni.cookbook.model.Data;
import fr.oni.cookbook.model.Recipe;
import fr.oni.cookbook.task.ReadImportTask;
import fr.oni.cookbook.task.ReadInternalTask;
import fr.oni.cookbook.task.WriteExportTask;
import fr.oni.cookbook.task.WriteInternalTask;

public class MainActivity extends ActionBarActivity {

    private static final int EDIT_RECIPE_REQUEST = 1;
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

        final RecyclerView listRecipes = (RecyclerView) findViewById(R.id.listRecipes);

        listRecipes.setLayoutManager(new LinearLayoutManager(this));
        listRecipes.setItemAnimator(new DefaultItemAnimator());
        recipeAdapter = new MainRecipeAdapter(data.getRecipes());

        if (data.getRecipes().isEmpty()) {
            new ReadInternalTask(recipeAdapter, data, this).execute();
        }

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
        importIntent.setType("*/*");
        importIntent.setAction(Intent.ACTION_GET_CONTENT);
        importIntent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(Intent.createChooser(importIntent, getString(R.string.import_chooser)), IMPORT_RECIPES_REQUEST);
    }

    private void openExportRecipes() {
        Intent exportIntent = new Intent();
        exportIntent.setType("application/json");
        exportIntent.setAction(Intent.ACTION_CREATE_DOCUMENT);
        exportIntent.addCategory(Intent.CATEGORY_OPENABLE);
        exportIntent.putExtra(Intent.EXTRA_TITLE, "export.json");
        startActivityForResult(Intent.createChooser(exportIntent, getString(R.string.export_chooser)), EXPORT_RECIPES_REQUEST);
    }

    private void saveRecipes() {
        new WriteInternalTask(this, data).execute();
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
                    importData(jsonImportFileUri);
                    break;
                case EXPORT_RECIPES_REQUEST:
                    Uri jsonExportFileUri = intent.getData();
                    exportData(jsonExportFileUri);
                    break;
                default:
                    recipeAdapter.notifyDataSetChanged();
                    saveRecipes();
                    break;
            }

        }
    }

    private void importData(Uri jsonImportFileUri) {
        new ReadImportTask(recipeAdapter, data, this).execute(jsonImportFileUri);
    }

    private void exportData(Uri jsonExportFileUri) {
        new WriteExportTask(this, data).execute(jsonExportFileUri);
    }
}
