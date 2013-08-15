package fr.oni.cookbook.dialog.view;

import fr.oni.cookbook.R;
import fr.oni.cookbook.model.Step;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class ViewStepDialogFragment extends DialogFragment {
	Step step;


	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		step = (Step) getArguments().get("step");
		builder.setTitle(R.string.step_view_title);
		builder.setMessage(step.getOrder());
		builder.setPositiveButton(R.string.step_view_ok, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				ViewStepDialogFragment.this.getDialog().cancel();
			}
		});

		return builder.create();
	}

}
