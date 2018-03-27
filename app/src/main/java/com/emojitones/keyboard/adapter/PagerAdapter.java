package com.emojitones.keyboard.adapter;



import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.emojitones.keyboard.fragments.EmojitonesFragment;
import com.emojitones.keyboard.fragments.HistoryFragment;

public class PagerAdapter extends FragmentStatePagerAdapter{

    int mNumofTabs;
    Context mcontext;

    public PagerAdapter(FragmentManager fm, int mNumofTabs)
    {
        super(fm);
        this.mNumofTabs = mNumofTabs;
    }


    @Override
    public Fragment getItem(int position){
        switch (position)
        {
            case 0:
                HistoryFragment historyFragment = new HistoryFragment();
                return historyFragment;
            case 1:
                EmojitonesFragment emojitonesFragment = new EmojitonesFragment();
                return  emojitonesFragment;
                default:
                    return null;
        }

    }

    @Override
    public int getCount()
    {
        return  mNumofTabs;
    }
}