package fr.oni.cookbook.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBar.TabListener;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import fr.oni.cookbook.R;
import fr.oni.cookbook.fragment.edit.EditTitleFragment;
import fr.oni.cookbook.model.Recipe;

public class EditActivity extends ActionBarActivity implements TabListener {

	//TODO Maybe change List to Map
	List<Fragment> fragList = new ArrayList<Fragment>();
	Recipe recipe;

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// Do nothing
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		Fragment f = null;
		if (fragList.size() > tab.getPosition()) {
			f = fragList.get(tab.getPosition());
		}
		Bundle data = new Bundle();
		data.putSerializable("recipe", recipe);

        if (f == null) {
			switch (tab.getPosition()) {
				case 0:
					f = new EditTitleFragment();
					break;
				case 1:
					//TODO create the EditIngredientsFragment
					f = new EditTitleFragment();
					break;
				case 2:
					//TODO create the EditStepsFragment
					f = new EditTitleFragment();
					break;

				default:
					break;
			}
			fragList.add(f);
		}
		f.setArguments(data);
        ft.replace(android.R.id.content, f);

	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		 if (fragList.size() > tab.getPosition()) {
	            ft.remove(fragList.get(tab.getPosition()));
	        }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    getMenuInflater().inflate(R.menu.edit_menu, menu);
	    return super.onCreateOptionsMenu(menu);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		recipe = (Recipe) getIntent().getExtras().getSerializable("recipe");

		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(recipe.getTitle());

		Tab tabRecipe = actionBar.newTab().setText(getString(R.string.tab_name_recipe)).setTabListener(this);
		Tab tabIngredients = actionBar.newTab().setText(getString(R.string.tab_name_ingredients)).setTabListener(this);
		Tab tabSteps = actionBar.newTab().setText(getString(R.string.tab_name_steps)).setTabListener(this);

		actionBar.addTab(tabRecipe);
		actionBar.addTab(tabIngredients);
		actionBar.addTab(tabSteps);

	}

}
