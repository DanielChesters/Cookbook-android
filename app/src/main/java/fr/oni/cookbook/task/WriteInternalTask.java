package fr.oni.cookbook.task;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import fr.oni.cookbook.R;
import fr.oni.cookbook.StringConstant;
import fr.oni.cookbook.model.Data;

public class WriteInternalTask extends AsyncTask<Void, Void, Void> {
    Activity activity;
    Data data;
    boolean doToast;

    public WriteInternalTask(Activity activity, Data data) {
        this(activity, data, true);
    }

    public WriteInternalTask(Activity activity, Data data, boolean doToast) {
        this.activity = activity;
        this.data = data;
        this.doToast = doToast;
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            FileOutputStream outputStream = activity.openFileOutput(StringConstant.DATA_FILE, Context.MODE_PRIVATE);
            TaskUtility.writeToFile(outputStream, data);
        } catch (FileNotFoundException e) {
            Log.e(StringConstant.TAG_DATA_EXPORT, StringConstant.FILE_WRITE_ERROR + e.toString(), e);
            Toast.makeText(activity.getApplicationContext(), R.string.file_not_found, Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Log.e(StringConstant.TAG_DATA_EXPORT, StringConstant.FILE_WRITE_ERROR + e.toString(), e);
            Toast.makeText(activity.getApplicationContext(), R.string.error_write, Toast.LENGTH_LONG).show();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if (doToast) {
            Toast.makeText(activity.getApplicationContext(), R.string.action_save_recipes_confirm, Toast.LENGTH_LONG).show();
        }
    }
}
