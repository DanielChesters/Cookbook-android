package fr.oni.cookbook.dialog.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import fr.oni.cookbook.R;
import fr.oni.cookbook.model.Data;
import fr.oni.cookbook.model.Recipe;

public class DeleteRecipeConfirmDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final Data data = (Data) getActivity().getApplicationContext();
        Recipe recipe = data.getRecipes().get(data.getPosition());
        builder.setTitle(getString(R.string.delete_dialog_title) + recipe.getTitle());
        builder.setMessage(R.string.delete_dialog_text);
        builder.setPositiveButton(R.string.delete_dialog_yes, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                data.getRecipes().remove(data.getPosition());
                data.setPosition(-1);
                getActivity().setResult(Activity.RESULT_OK);
                getActivity().finish();
            }

        });
        builder.setNegativeButton(R.string.delete_dialog_no, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                DeleteRecipeConfirmDialogFragment.this.getDialog().cancel();

            }

        });
        return builder.create();
    }


}
