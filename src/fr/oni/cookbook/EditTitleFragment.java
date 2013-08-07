package fr.oni.cookbook;

import fr.oni.cookbook.model.Recipe;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class EditTitleFragment extends Fragment {

	Recipe recipe;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    Bundle data = getArguments();
	    recipe = (Recipe) data.getSerializable("recipe");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	        Bundle savedInstanceState) {

	    View v = inflater.inflate(R.layout.edit_recipe_title, null);

	    EditText titleEditText = (EditText) v.findViewById(R.id.edit_title_field);
	    EditText descriptionEditText = (EditText) v.findViewById(R.id.edit_description_field);
	    titleEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

				ActionBarActivity activity = (ActionBarActivity) getActivity();
				activity.getSupportActionBar().setTitle(s);

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// Do nothing

			}

			@Override
			public void afterTextChanged(Editable s) {
				// Do nothing
			}
		});

	    titleEditText.setText(recipe.getTitle());
	    descriptionEditText.setText(recipe.getDescription());
	    return v;

	}


}
