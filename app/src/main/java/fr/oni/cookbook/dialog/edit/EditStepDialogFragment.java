package fr.oni.cookbook.dialog.edit;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import fr.oni.cookbook.R;
import fr.oni.cookbook.StringConstant;
import fr.oni.cookbook.model.Data;
import fr.oni.cookbook.model.Recipe;

public class EditStepDialogFragment extends DialogFragment {

    private EditText editText;
    private int positionStep;
    private EditDialogListener listener;

    public void setListener(EditDialogListener listener) {
        this.listener = listener;
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Data data = (Data) getActivity().getApplicationContext();

        LayoutInflater inflater = getActivity().getLayoutInflater();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        @SuppressLint("InflateParams") View v = inflater.inflate(R.layout.edit_step_dialog_layout, null);
        final int position = data.getPosition();
        final Recipe recipe = data.getRecipes().get(position);

        editText = (EditText) v.findViewById(R.id.edit_step_field);
        positionStep = getArguments().getInt(StringConstant.KEY_POSITION_STEP);
        editText.setText(recipe.getSteps().get(positionStep).getOrder());

        builder.setView(v);
        builder.setTitle(R.string.step_edit_title);
        builder.setPositiveButton(R.string.edit_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                recipe.getSteps().get(positionStep).setOrder(editText.getText().toString());
                listener.onCloseDialog();
                EditStepDialogFragment.this.getDialog().dismiss();
            }
        });
        builder.setNegativeButton(R.string.edit_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.onCloseDialog();
                EditStepDialogFragment.this.getDialog().cancel();
            }
        });

        return builder.create();
    }

}
