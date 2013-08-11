package fr.oni.cookbook.fragment.edit;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import fr.oni.cookbook.R;
import fr.oni.cookbook.model.Recipe;

public class EditTitleFragment extends AbstactEditFragment {
	EditText titleEditText;
	EditText descriptionEditText;

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

	    titleEditText = (EditText) v.findViewById(R.id.edit_title_field);
	    descriptionEditText = (EditText) v.findViewById(R.id.edit_description_field);
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

	@Override
	public void onPause() {
		recipe.setTitle(titleEditText.getText().toString());
		recipe.setDescription(descriptionEditText.getText().toString());
		listener.onComplete(recipe);
		super.onPause();
	}


}
