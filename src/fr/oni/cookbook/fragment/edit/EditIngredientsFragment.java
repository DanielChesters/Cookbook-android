package fr.oni.cookbook.fragment.edit;

import fr.oni.cookbook.R;
import fr.oni.cookbook.model.Data;
import fr.oni.cookbook.model.Ingredient;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class EditIngredientsFragment extends Fragment {

	Data data;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		data = (Data) getActivity().getApplicationContext();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ListView v = (ListView) inflater.inflate(R.layout.edit_recipe_ingredients, container, false);
		v.setAdapter(new ArrayAdapter<Ingredient>(getActivity(), R.layout.ingredients_edit_list_linear_layout, R.id.ingredient_edit_text, data.getRecipes().get(data.getPosition()).getIngredients()));
		return v;
	}


}
