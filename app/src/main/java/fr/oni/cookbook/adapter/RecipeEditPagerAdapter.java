package fr.oni.cookbook.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.util.SparseArray;

import fr.oni.cookbook.R;
import fr.oni.cookbook.fragment.edit.EditIngredientsFragment;
import fr.oni.cookbook.fragment.edit.EditStepsFragment;
import fr.oni.cookbook.fragment.edit.EditTitleFragment;

public class RecipeEditPagerAdapter extends FragmentStatePagerAdapter {

    private Context context;
    private SparseArray<Fragment> fragArray = new SparseArray<>();

    public RecipeEditPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
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
            return clazz.newInstance();
        } catch (InstantiationException e) {
            Log.e(this.getClass().getSimpleName(), e.getMessage(), e);
            return null;
        } catch (IllegalAccessException e) {
            Log.e(this.getClass().getSimpleName(), e.getMessage(), e);
            return null;
        }
    }
}
