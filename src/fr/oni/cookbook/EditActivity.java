package fr.oni.cookbook;

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

public class EditActivity extends ActionBarActivity implements TabListener {

	List<Fragment> fragList = new ArrayList<Fragment>();

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// Do nothing
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		Fragment f = null;
        EditFragment ef = null;

        if (fragList.size() > tab.getPosition())
                fragList.get(tab.getPosition());

        if (f == null) {
            ef = new EditFragment();
            Bundle data = new Bundle();
            data.putInt("idx",  tab.getPosition());
            ef.setArguments(data);
            fragList.add(ef);
        }
        else {
        	ef = (EditFragment) f;
        }


        ft.replace(android.R.id.content, ef);

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

		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle("New Recipe");

		Tab tabRecipe = actionBar.newTab().setText("Recipe").setTabListener(this);
		Tab tabIngedients = actionBar.newTab().setText("Ingedients").setTabListener(this);
		Tab tabSteps = actionBar.newTab().setText("Steps").setTabListener(this);

		actionBar.addTab(tabRecipe);
		actionBar.addTab(tabIngedients);
		actionBar.addTab(tabSteps);
	}

}
