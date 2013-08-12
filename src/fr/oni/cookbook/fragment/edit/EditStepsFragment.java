package fr.oni.cookbook.fragment.edit;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import fr.oni.cookbook.R;
import fr.oni.cookbook.dialog.view.ViewStepDialogFragment;
import fr.oni.cookbook.model.Data;
import fr.oni.cookbook.model.Step;

public class EditStepsFragment extends Fragment {

	Data data;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    data = (Data) getActivity().getApplicationContext();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ListView v = (ListView) inflater.inflate(R.layout.edit_recipe_steps, container, false);
		v.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View v, int position, long itemID) {
				DialogFragment dialog = new ViewStepDialogFragment();
				Bundle data = new Bundle();
				data.putSerializable("step", (Step) adapter.getItemAtPosition(position));
				dialog.setArguments(data);
				dialog.show(getFragmentManager(), "step");
			}
		});
		v.setAdapter(new ArrayAdapter<Step>(getActivity(), R.layout.steps_edit_list_linear_layout, R.id.step_edit_text, data.getRecipes().get(data.getPosition()).getSteps()));
		return v;
	}

}
