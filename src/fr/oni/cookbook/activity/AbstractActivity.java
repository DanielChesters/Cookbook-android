package fr.oni.cookbook.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBar.TabListener;
import android.support.v7.app.ActionBarActivity;
import fr.oni.cookbook.R;
import fr.oni.cookbook.Utilities;
import fr.oni.cookbook.listener.TabChangeListener;
import fr.oni.cookbook.model.Data;
import fr.oni.cookbook.model.Recipe;

public abstract class AbstractActivity extends ActionBarActivity implements TabListener {

    protected ViewPager viewPager;
    protected Data data;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_activity);
        data = (Data) getApplicationContext();
        this.viewPager = (ViewPager) findViewById(R.id.edit_pager);
        final ActionBar actionBar = getSupportActionBar();

        viewPager.setOnPageChangeListener(new TabChangeListener(actionBar));

        int position = data.getPosition();
        Recipe recipe = data.getRecipes().get(position);

        Utilities.commonSetup(actionBar, recipe, this);
    }

}
