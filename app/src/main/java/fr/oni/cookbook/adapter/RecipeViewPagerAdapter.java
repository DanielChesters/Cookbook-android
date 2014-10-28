package fr.oni.cookbook.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.util.SparseArray;

import fr.oni.cookbook.R;
import fr.oni.cookbook.StringConstant;
import fr.oni.cookbook.fragment.view.ViewIngredientsFragment;
import fr.oni.cookbook.fragment.view.ViewStepsFragment;
import fr.oni.cookbook.fragment.view.ViewTitleFragment;
import fr.oni.cookbook.model.Recipe;

public class RecipeViewPagerAdapter extends FragmentStatePagerAdapter {

  private Context context;
  private SparseArray<Fragment> fragArray = new SparseArray<>();
  private Recipe recipe;

  public RecipeViewPagerAdapter(FragmentManager fm, Recipe recipe, Context context) {
    super(fm);
    this.recipe = recipe;
    this.context = context;
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

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return context.getString(R.string.tab_name_recipe);
            case 1:
                return context.getString(R.string.tab_name_ingredients);
            case 2:
                return context.getString(R.string.tab_name_steps);
            default:
                return super.getPageTitle(position);
        }
    }

  private Fragment createFragment(Class<? extends Fragment> clazz) {
    try {
      Fragment f = clazz.newInstance();
      Bundle data = new Bundle();
      data.putSerializable(StringConstant.KEY_RECIPE, recipe);
      f.setArguments(data);
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
