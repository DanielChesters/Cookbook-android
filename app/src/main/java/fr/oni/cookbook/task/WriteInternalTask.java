package fr.oni.cookbook.task;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import fr.oni.cookbook.R;
import fr.oni.cookbook.StringConstant;
import fr.oni.cookbook.model.Data;

public class WriteInternalTask extends AsyncTask<Void, Void, Void> {
    Activity activity;
    Data data;

    public WriteInternalTask(Activity activity, Data data) {
        this.activity = activity;
        this.data = data;
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            FileOutputStream outputStream = activity.openFileOutput(StringConstant.DATA_FILE, Context.MODE_PRIVATE);
            TaskUtility.writeToFile(outputStream, data);
        } catch (FileNotFoundException e) {
            Log.e(StringConstant.TAG_DATA_WRITE, StringConstant.FILE_WRITE_ERROR + e.toString(), e);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        Toast.makeText(activity.getApplicationContext(), R.string.action_save_recipes_confirm, Toast.LENGTH_LONG).show();
    }
}
