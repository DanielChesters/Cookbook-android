package fr.oni.cookbook.task;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import fr.oni.cookbook.R;
import fr.oni.cookbook.StringConstant;
import fr.oni.cookbook.adapter.MainRecipeAdapter;
import fr.oni.cookbook.model.Data;

public class ReadInternalTask extends AsyncTask<Void, Void, String> {
    private MainRecipeAdapter recipeAdapter;
    private Data data;
    private Activity activity;

    public ReadInternalTask(MainRecipeAdapter recipeAdapter, Data data, Activity activity) {
        this.recipeAdapter = recipeAdapter;
        this.data = data;
        this.activity = activity;
    }

    @Override
    protected String doInBackground(Void... params) {
        InputStream inputStream = null;
        String json = "";
        try {
            inputStream = activity.openFileInput(StringConstant.DATA_FILE);
            if (inputStream.available() == 0) {
                inputStream = activity.getResources().openRawResource(R.raw.sample);
            }
        } catch (FileNotFoundException e) {
            inputStream = activity.getResources().openRawResource(R.raw.sample);
        } catch (IOException e) {
            catchIOException(e);
        } finally {
            try {
                if (inputStream != null) {
                    json = TaskUtility.readFromFile(inputStream);
                }
            } catch (IOException e) {
                catchIOException(e);
            }
        }
        return json;
    }

    private void catchIOException(IOException e) {
        Log.e(StringConstant.TAG_DATA_READ, StringConstant.FILE_READ_ERROR + e.toString(), e);
        Toast.makeText(activity.getApplicationContext(), R.string.error_read, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onPostExecute(String json) {
        TaskUtility.readJsonAndUpdateData(json, data, recipeAdapter);
    }
}
