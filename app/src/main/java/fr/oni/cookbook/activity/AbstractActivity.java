package fr.oni.cookbook.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;

import fr.oni.cookbook.R;
import fr.oni.cookbook.model.Data;
import fr.oni.cookbook.model.Recipe;

public abstract class AbstractActivity extends ActionBarActivity {

    protected ViewPager viewPager;
    protected Data data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_activity);
        data = (Data) getApplicationContext();
        this.viewPager = (ViewPager) findViewById(R.id.edit_pager);
        final ActionBar actionBar = getSupportActionBar();

        int position = data.getPosition();
        Recipe recipe = data.getRecipes().get(position);

        actionBarSetup(actionBar, recipe);
    }

    private void actionBarSetup(final ActionBar actionBar, final Recipe recipe) {
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(recipe.getTitle());
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

}
