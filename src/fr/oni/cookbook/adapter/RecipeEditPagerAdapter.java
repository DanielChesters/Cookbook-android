package fr.oni.cookbook.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import fr.oni.cookbook.fragment.edit.EditTitleFragment;
import fr.oni.cookbook.model.Recipe;

public class RecipeEditPagerAdapter extends FragmentStatePagerAdapter {

	SparseArray<Fragment> fragArray = new SparseArray<Fragment>();
	Recipe recipe;
	int position;

	public RecipeEditPagerAdapter(FragmentManager fm, Recipe recipe, int position) {
		super(fm);
		this.recipe = recipe;
		this.position = position;
		fragArray.put(0, createFragment(EditTitleFragment.class));
		fragArray.put(1, createFragment(EditTitleFragment.class));
		fragArray.put(2, createFragment(EditTitleFragment.class));
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
			Bundle data = new Bundle();
			data.putSerializable("recipe", recipe);
			data.putInt("position", position);
			f.setArguments(data);
			return f;
		} catch (InstantiationException e) {
			return null;
		} catch (IllegalAccessException e) {
			return null;
		}
	}
}
