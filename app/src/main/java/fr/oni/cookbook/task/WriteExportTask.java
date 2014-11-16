package fr.oni.cookbook.task;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.widget.Toast;

import java.io.FileOutputStream;

import fr.oni.cookbook.R;
import fr.oni.cookbook.StringConstant;
import fr.oni.cookbook.model.Data;

public class WriteExportTask extends AsyncTask<Uri, Void, Void> {
    private Activity activity;
    private Data data;

    @Override
    protected void onPostExecute(Void aVoid) {
        Toast.makeText(activity.getApplicationContext(), R.string.action_export_recipes_confirm, Toast.LENGTH_LONG).show();
    }

    public WriteExportTask(Activity activity, Data data) {
        this.activity = activity;
        this.data = data;
    }

    @Override
    protected Void doInBackground(Uri... params) {
        try {
            ParcelFileDescriptor exportFileDescriptor = activity.getBaseContext().getContentResolver().openFileDescriptor(params[0], "rwt");
            FileOutputStream exportFileOutputStream = new FileOutputStream(exportFileDescriptor.getFileDescriptor());
            TaskUtility.writeToFile(exportFileOutputStream, data);
            exportFileOutputStream.close();
            exportFileDescriptor.close();
        } catch (java.io.IOException e) {
            Log.e(StringConstant.TAG_DATA_WRITE, StringConstant.FILE_WRITE_ERROR + e.toString(), e);
            Toast.makeText(activity.getApplicationContext(), R.string.action_export_recipes_error, Toast.LENGTH_LONG).show();
        }
        return null;
    }
}
