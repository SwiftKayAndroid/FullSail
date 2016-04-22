package com.swiftkaydevelopment.fullsailcommunicate.adapters;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.swiftkaydevelopment.fullsailcommunicate.R;
import com.swiftkaydevelopment.fullsailcommunicate.fragments.WalkThroughFragment;

import java.util.ArrayList;
import java.util.List;

public class WalkThroughPagerAdapter extends FragmentPagerAdapter {

    private static final int MAX_PAGES = 3;
    private List<WalkThroughFragment> fragments = new ArrayList<>();

    public WalkThroughPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        fragments.add(WalkThroughFragment.newInstance(context.getString(R.string.intro_one), R.drawable.classroom_students_smudged_1));
        fragments.add(WalkThroughFragment.newInstance(context.getString(R.string.intro_two), R.drawable.students_inarow_1));
        fragments.add(WalkThroughFragment.newInstance(null, R.drawable.students_reading_book_1));
    }

    @Override
    public int getCount() {
        return MAX_PAGES;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }
}