package fr.oni.cookbook;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class EditFragment extends Fragment {
	int index;

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    Bundle data = getArguments();
	    index = data.getInt("idx");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	        Bundle savedInstanceState) {

	    View v = inflater.inflate(R.layout.edit_recipe, null);
	    TextView tv = (TextView) v.findViewById(R.id.msg);
	    tv.setText("Fragment " + (index + 1));

	    return v;

	}


}
