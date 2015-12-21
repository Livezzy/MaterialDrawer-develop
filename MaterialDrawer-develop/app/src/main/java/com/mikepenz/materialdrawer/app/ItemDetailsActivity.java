package com.mikepenz.materialdrawer.app;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.mikepenz.materialdrawer.app.ui.AboutItemFragment;
import com.mikepenz.materialdrawer.app.ui.PurchaseItemFragment;
import com.mikepenz.materialdrawer.app.ui.TabFragment;

import java.util.ArrayList;

public class ItemDetailsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.itemdetailsactivity);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        showBackButton();

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle("Item details");

        setUpTabs();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.itemdetail, menu);
        updateMenuIconButton(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.search:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setUpTabs() {
        FragmentManager mFragmentManager = getSupportFragmentManager();
        final TabFragment tabFragment = new TabFragment();
        final ArrayList<String> tabTitles = new ArrayList<>();
        tabTitles.add("PURCHASE");
        tabTitles.add("ABOUT");
        tabFragment.setHandler(new TabFragment.TabFragmentHandler() {
            @Override
            public void setAdapter(ViewPager viewPager) {
                viewPager.setAdapter(new TabAdapter(getSupportFragmentManager(), tabTitles));

            }
        });
        android.support.v4.app.FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.add(R.id.frame_container, tabFragment).commit();
    }

    class TabAdapter extends FragmentPagerAdapter {

        private ArrayList<String> tabTitles = new ArrayList<>();

        public TabAdapter(FragmentManager fm, ArrayList<String> tabTitles) {
            super(fm);
            this.tabTitles.clear();
            if (tabTitles != null) {
                for (String str : tabTitles) {
                    this.tabTitles.add(str);
                }
            }
        }

        /**
         * Return fragment with respect to Position .
         */

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    PurchaseItemFragment fragment = new PurchaseItemFragment();
                    return fragment;
                case 1:
                    return new AboutItemFragment();
            }
            return null;
        }

        @Override
        public int getCount() {

            return tabTitles.size();

        }

        /**
         * This method returns the title of the tab according to the position.
         */

        @Override
        public CharSequence getPageTitle(int position) {

            switch (position) {
                case 0:
                    return tabTitles.get(position);
                case 1:
                    return tabTitles.get(position);

            }
            return null;
        }

    }
}
