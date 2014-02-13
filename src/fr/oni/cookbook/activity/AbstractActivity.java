package fr.oni.cookbook.activity;

import java.util.Arrays;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBar.TabListener;
import android.support.v7.app.ActionBarActivity;
import fr.oni.cookbook.R;
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

        actionBarSetup(actionBar, recipe);

        final List<Integer> ids = Arrays.asList(new Integer[] {R.string.tab_name_recipe, R.string.tab_name_ingredients, R.string.tab_name_steps});
        addTabs(actionBar, ids, this);
    }

    private void addTabs(final ActionBar actionBar, final List<Integer> stringIds, final TabListener tabListener) {
        for (final int stringId : stringIds) {
            actionBar.addTab(setTabListener(actionBar, stringId, tabListener));
        }
    }

    private Tab setTabListener(final ActionBar actionBar, final int stringId, final TabListener tabListener) {
        return actionBar.newTab().setText(stringId).setTabListener(tabListener);
    }

    private void actionBarSetup(final ActionBar actionBar, final Recipe recipe) {
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(recipe.getTitle());
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

}
