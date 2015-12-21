package com.mikepenz.materialdrawer.app.ui;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;
import com.mikepenz.materialdrawer.app.R;
import com.mikepenz.materialdrawer.app.contracts.Constants;

public class TabFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabFragmentHandler _handler;
    private Typeface mTypeface;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /**
         *Inflate tab_layout and setup Views.
         */
        View view = inflater.inflate(R.layout.tab_layout, null);
//        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);


        // Bind the tabs to the ViewPager
        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) view.findViewById(R.id.tabs);

        /**
         * Now , this is a workaround ,
         * The setupWithViewPager dose't works without the runnable .
         * Maybe a Support Library Bug .
         */
        if (_handler != null) {
            _handler.setAdapter(viewPager);
        }

        tabs.setViewPager(viewPager);
//        tabs.setTabPaddingLeftRight(80);
        mTypeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/" + Constants.defaultNormalFont);
        tabs.setTypeface(mTypeface, Typeface.NORMAL);

//        mTypeface = Typeface.create(getContext().getString(R.string.roboto_regular), Typeface.NORMAL);
//        tabs.setTypeface(mTypeface, Typeface.NORMAL);

        //tab layout default one...
//        tabLayout.post(new Runnable() {
//            @Override
//            public void run() {
//                tabLayout.setupWithViewPager(viewPager);
//            }
//        });

//        int tabChildCount = tabLayout.getChildCount();
//        Log.i("test", "onCreateView childcount " + tabChildCount);
//
        return view;

    }

    public void setHandler(TabFragmentHandler handler) {
        _handler = handler;
    }

    public interface TabFragmentHandler {
        public void setAdapter(ViewPager viewPager);
    }
}