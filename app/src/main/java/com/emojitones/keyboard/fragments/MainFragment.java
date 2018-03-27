package com.emojitones.keyboard.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.emojitones.keyboard.R;
import com.emojitones.keyboard.adapter.PagerAdapter;

public  class MainFragment extends Fragment {

    private ViewPager emojisPager;

    public static Fragment newInstance() {
        return new MainFragment();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.main_fragment, container, false);
        this.emojisPager = (ViewPager) rootView.findViewById(R.id.view_pager);

        TabLayout tablayout = (TabLayout) rootView.findViewById(R.id.tab_layout);

        tablayout.addTab(tablayout.newTab().setIcon(R.drawable.ic_tab_history));
        tablayout.addTab(tablayout.newTab().setIcon(R.drawable.ic_tab_emojitones));

        final ViewPager viewPager = (ViewPager) rootView.findViewById(R.id.view_pager);


        PagerAdapter adapter = new PagerAdapter(getFragmentManager(),tablayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener((tablayout)));

        tablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return rootView;
    }


}