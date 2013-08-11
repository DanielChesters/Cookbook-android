package fr.oni.cookbook.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBar.TabListener;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import fr.oni.cookbook.R;
import fr.oni.cookbook.adapter.RecipeEditPagerAdapter;
import fr.oni.cookbook.model.Data;
import fr.oni.cookbook.model.Recipe;

public class EditActivity extends ActionBarActivity implements TabListener {

	ViewPager viewPager;
	RecipeEditPagerAdapter recipeEditPagerAdapter;

	Data data;

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// Do nothing
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		viewPager.setCurrentItem(tab.getPosition());

	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// Do nothing
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    getMenuInflater().inflate(R.menu.edit_menu, menu);
	    return super.onCreateOptionsMenu(menu);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_activity);
		data = (Data) getApplicationContext();
		this.viewPager = (ViewPager) findViewById(R.id.edit_pager);
		final ActionBar actionBar = getSupportActionBar();

		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				actionBar.setSelectedNavigationItem(position);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// do nothing
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// do nothing
			}
		});

		int position = data.getPosition();
		Recipe recipe = data.getRecipes().get(position);

		recipeEditPagerAdapter = new RecipeEditPagerAdapter(getSupportFragmentManager(), recipe, position);

		viewPager.setAdapter(recipeEditPagerAdapter);

		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(recipe.getTitle());
		actionBar.setDisplayHomeAsUpEnabled(true);

		Tab tabRecipe = actionBar.newTab().setText(getString(R.string.tab_name_recipe)).setTabListener(this);
		Tab tabIngredients = actionBar.newTab().setText(getString(R.string.tab_name_ingredients)).setTabListener(this);
		Tab tabSteps = actionBar.newTab().setText(getString(R.string.tab_name_steps)).setTabListener(this);

		actionBar.addTab(tabRecipe);
		actionBar.addTab(tabIngredients);
		actionBar.addTab(tabSteps);

	}

	private void onSave() {
		finish();
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_save:
				onSave();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}

	}
}
