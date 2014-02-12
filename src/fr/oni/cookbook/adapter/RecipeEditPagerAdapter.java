package fr.oni.cookbook.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.util.SparseArray;
import fr.oni.cookbook.fragment.edit.EditIngredientsFragment;
import fr.oni.cookbook.fragment.edit.EditStepsFragment;
import fr.oni.cookbook.fragment.edit.EditTitleFragment;

public class RecipeEditPagerAdapter extends FragmentStatePagerAdapter {

	SparseArray<Fragment> fragArray = new SparseArray<Fragment>();

	public RecipeEditPagerAdapter(FragmentManager fm) {
		super(fm);
		fragArray.put(0, createFragment(EditTitleFragment.class));
		fragArray.put(1, createFragment(EditIngredientsFragment.class));
		fragArray.put(2, createFragment(EditStepsFragment.class));
	}

	@Override
	public Fragment getItem(int i) {
		return fragArray.get(i);
	}

	@Override
	public int getCount() {
		return 3;
	}

	private Fragment createFragment(Class<? extends Fragment> clazz) {
		try {
			Fragment f = clazz.newInstance();
			return f;
		} catch (InstantiationException e) {
			Log.e(this.getClass().getSimpleName(), e.getMessage(), e);
			return null;
		} catch (IllegalAccessException e) {
			Log.e(this.getClass().getSimpleName(), e.getMessage(), e);
			return null;
		}
	}
}
