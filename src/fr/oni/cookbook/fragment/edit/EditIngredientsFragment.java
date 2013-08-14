package fr.oni.cookbook.fragment.edit;

import android.os.Bundle;
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
import fr.oni.cookbook.dialog.edit.EditIngredientDialogFragment;
import fr.oni.cookbook.model.Data;
import fr.oni.cookbook.model.Ingredient;

public class EditIngredientsFragment extends Fragment implements EditDialogListener{

	Data data;
	ArrayAdapter<Ingredient> adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		data = (Data) getActivity().getApplicationContext();
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		ActionBarActivity activity = (ActionBarActivity) getActivity();
		activity.supportInvalidateOptionsMenu();
		ListView v = (ListView) inflater.inflate(R.layout.edit_recipe_ingredients, container, false);
		adapter = new ArrayAdapter<Ingredient>(getActivity(), R.layout.ingredients_edit_list_linear_layout, R.id.ingredient_edit_text, data.getRecipes().get(data.getPosition()).getIngredients());
		v.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
				EditIngredientDialogFragment dialog = new EditIngredientDialogFragment();
				dialog.setListener(EditIngredientsFragment.this);
				Bundle data = new Bundle();
				data.putInt(getString(R.string.key_position_ingredient), pos);
				dialog.setArguments(data);
				dialog.show(getFragmentManager(), getString(R.string.tag_edit_ingredient));
			}

		});
		v.setAdapter(adapter);
		return v;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_add_ingredient:
				addIngredient();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	private void addIngredient() {
		data.getRecipes().get(data.getPosition()).getIngredients().add(new Ingredient(getString(R.string.new_ingredient)));
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		MenuItem item = menu.findItem(R.id.action_add_ingredient);
		item.setVisible(true);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public void onCloseDialog() {
		adapter.notifyDataSetChanged();
	}
}
