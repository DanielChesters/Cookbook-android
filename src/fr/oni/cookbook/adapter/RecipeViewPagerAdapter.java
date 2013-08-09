package fr.oni.cookbook.adapter;

import fr.oni.cookbook.fragment.view.ViewIngredientsFragment;
import fr.oni.cookbook.fragment.view.ViewStepsFragment;
import fr.oni.cookbook.fragment.view.ViewTitleFragment;
import fr.oni.cookbook.model.Recipe;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;

public class RecipeViewPagerAdapter extends FragmentStatePagerAdapter {

	SparseArray<Fragment> fragArray = new SparseArray<Fragment>();
	Recipe recipe;

	public RecipeViewPagerAdapter(FragmentManager fm, Recipe recipe) {
		super(fm);
		this.recipe = recipe;
		fragArray.put(0, createFragment(ViewTitleFragment.class));
		fragArray.put(1, createFragment(ViewIngredientsFragment.class));
		fragArray.put(2, createFragment(ViewStepsFragment.class));
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
			f.setArguments(data);
			return f;
		} catch (InstantiationException e) {
			return null;
		} catch (IllegalAccessException e) {
			return null;
		}
	}
}
