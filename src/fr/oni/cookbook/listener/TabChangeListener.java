package fr.oni.cookbook.listener;

import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBar;

public class TabChangeListener implements OnPageChangeListener {

    private ActionBar actionBar;

    public TabChangeListener(ActionBar actionBar) {
        this.actionBar = actionBar;
    }

    @Override
    public void onPageScrollStateChanged(int position) {
        actionBar.setSelectedNavigationItem(position);
    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
        // do Nothing

    }

    @Override
    public void onPageSelected(int arg0) {
        // do Nothing

    }

}
