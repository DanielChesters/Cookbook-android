package fr.oni.cookbook.listener;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;

import fr.oni.cookbook.R;

public class DeleteDialogOnClickListener implements OnItemLongClickListener {
    private FragmentActivity activity;
    private int deleteDialogTitleId;
    private int deleteDialogTextId;
    private RemoveListener listener;
    private BaseAdapter adapter;

    public DeleteDialogOnClickListener(final FragmentActivity activity,
                                       final int deleteDialogTitleId, final int deleteDialogTextId, final RemoveListener listener,
                                       final BaseAdapter adapter) {
        this.activity = activity;
        this.deleteDialogTitleId = deleteDialogTitleId;
        this.deleteDialogTextId = deleteDialogTextId;
        this.listener = listener;
        this.adapter = adapter;
    }

    public boolean onItemLongClick(final AdapterView<?> adapterView, final View v,
                                   final int position, final long itemID) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(deleteDialogTitleId);
        builder.setMessage(deleteDialogTextId);
        builder.setPositiveButton(R.string.delete_dialog_yes, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.remove(position);
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }

        });
        builder.setNegativeButton(R.string.delete_dialog_no, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }

        });

        builder.show();
        return false;
    }


}
