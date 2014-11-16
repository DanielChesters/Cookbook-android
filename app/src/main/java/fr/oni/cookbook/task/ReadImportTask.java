package fr.oni.cookbook.task;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import fr.oni.cookbook.R;
import fr.oni.cookbook.StringConstant;
import fr.oni.cookbook.adapter.MainRecipeAdapter;
import fr.oni.cookbook.model.Data;

public class ReadImportTask extends AsyncTask<Uri, Void, String> {
    private MainRecipeAdapter recipeAdapter;
    private Data data;
    private Activity activity;

    public ReadImportTask(MainRecipeAdapter recipeAdapter, Data data, Activity activity) {
        this.recipeAdapter = recipeAdapter;
        this.data = data;
        this.activity = activity;
    }

    @Override
    protected String doInBackground(Uri... params) {
        InputStream inputStream = null;
        String json = "";
        try {
            ParcelFileDescriptor importFileDescriptor = activity.getBaseContext().getContentResolver().openFileDescriptor(params[0], "r");
            inputStream = new FileInputStream(importFileDescriptor.getFileDescriptor());
            json = TaskUtility.readFromFile(inputStream);
            inputStream.close();
            importFileDescriptor.close();
        } catch (FileNotFoundException e) {
            Log.e(StringConstant.TAG_DATA_IMPORT, StringConstant.FILE_READ_NOT_FOUND + e.toString(), e);
            Toast.makeText(activity.getApplicationContext(), R.string.file_not_found, Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Log.e(StringConstant.TAG_DATA_IMPORT, StringConstant.FILE_READ_ERROR + e.toString(), e);
            Toast.makeText(activity.getApplicationContext(), R.string.action_import_recipes_error, Toast.LENGTH_LONG).show();
        }
        return json;
    }

    @Override
    protected void onPostExecute(String json) {
        TaskUtility.readJsonAndUpdateData(json, data, recipeAdapter);
        new WriteInternalTask(activity, data, false).execute();
        Toast.makeText(activity.getApplicationContext(), R.string.action_import_recipes_confirm, Toast.LENGTH_LONG).show();
    }
}
