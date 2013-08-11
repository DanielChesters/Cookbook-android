package fr.oni.cookbook.fragment.edit;

import fr.oni.cookbook.model.Recipe;
import android.app.Activity;
import android.support.v4.app.Fragment;

public abstract class AbstactEditFragment extends Fragment {
	Recipe recipe;
	OnCompleteListener listener;

	public static interface OnCompleteListener {
		public abstract void onComplete(Recipe recipe);
	}

	@Override
	public void onAttach(Activity activity) {
		try {
			this.listener = (OnCompleteListener)activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString() + " must implement OnCompleteListener");
		}
		super.onAttach(activity);
	}


}
