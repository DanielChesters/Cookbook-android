package fr.oni.cookbook;

import java.util.Arrays;
import java.util.List;

import fr.oni.cookbook.model.Recipe;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBar.TabListener;

public final class Utilities {

    private Utilities() {
        //Nothing
    }

    public static void commonSetup(final ActionBar actionBar, final Recipe recipe, final TabListener tabListener) {
        Utilities.actionBarSetup(actionBar, recipe);

        final List<Integer> ids = Arrays.asList(new Integer[] {R.string.tab_name_recipe, R.string.tab_name_ingredients, R.string.tab_name_steps});
        Utilities.addTabs(actionBar, ids, tabListener);
    }

    private static void addTabs(final ActionBar actionBar, final List<Integer> stringIds, final TabListener tabListener) {
        for (final int stringId : stringIds) {
            actionBar.addTab(Utilities.setTabListener(actionBar, stringId, tabListener));
        }
    }

    private static Tab setTabListener(final ActionBar actionBar, final int stringId, final TabListener tabListener) {
        return actionBar.newTab().setText(stringId).setTabListener(tabListener);
    }

    private static void actionBarSetup(final ActionBar actionBar, final Recipe recipe) {
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(recipe.getTitle());
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
}
