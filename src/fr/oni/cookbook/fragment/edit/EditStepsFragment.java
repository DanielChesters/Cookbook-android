package fr.oni.cookbook.fragment.edit;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import fr.oni.cookbook.R;
import fr.oni.cookbook.dialog.edit.EditDialogListener;
import fr.oni.cookbook.dialog.edit.EditStepDialogFragment;
import fr.oni.cookbook.dialog.view.ViewStepDialogFragment;
import fr.oni.cookbook.model.Data;
import fr.oni.cookbook.model.Step;

public class EditStepsFragment extends Fragment implements EditDialogListener {

	Data data;
	private ArrayAdapter<Step> adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    data = (Data) getActivity().getApplicationContext();
	    setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ActionBarActivity activity = (ActionBarActivity) getActivity();
		activity.supportInvalidateOptionsMenu();
		ListView v = (ListView) inflater.inflate(R.layout.edit_recipe_steps, container, false);
		v.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View v, int position, long itemID) {
				EditStepDialogFragment dialog = new EditStepDialogFragment();
				dialog.setListener(EditStepsFragment.this);
				Bundle data = new Bundle();
				data.putSerializable(getString(R.string.key_position_step), position);
				dialog.setArguments(data);
				dialog.show(getFragmentManager(), getString(R.string.tag_edit_step));
			}
		});
		adapter = new ArrayAdapter<Step>(getActivity(), R.layout.steps_edit_list_linear_layout, R.id.step_edit_text, data.getRecipes().get(data.getPosition()).getSteps());
		v.setAdapter(adapter);
		return v;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_add_step:
				addStep();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}

	}

	private void addStep() {
		data.getRecipes().get(data.getPosition()).getSteps().add(new Step(getString(R.string.new_step)));
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		MenuItem item = menu.findItem(R.id.action_add_step);
		item.setVisible(true);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public void onCloseDialog() {
		adapter.notifyDataSetChanged();
	}

}
