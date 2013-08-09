package fr.oni.cookbook.fragment.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import fr.oni.cookbook.R;
import fr.oni.cookbook.model.Recipe;

public class ViewTitleFragment extends Fragment {
	Recipe recipe;

	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    Bundle data = getArguments();
	    recipe = (Recipe) data.getSerializable("recipe");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	        Bundle savedInstanceState) {

	    View v = inflater.inflate(R.layout.view_recipe_title, container, false);

	    TextView titleViewText = (TextView) v.findViewById(R.id.view_title_field);
	    TextView descriptionViewText = (TextView) v.findViewById(R.id.view_description_field);

	    titleViewText.setText(recipe.getTitle());
	    descriptionViewText.setText(recipe.getDescription());
	    return v;
	}
}
