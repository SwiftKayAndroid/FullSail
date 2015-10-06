package com.swiftkaydevelopment.fullsailcommunicate;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MyAdapter extends FragmentPagerAdapter {

    StartUpPageOneFrag one;
    StartUpPageTwoFrag two;
    StartUpPageThreeFrag three;

        public MyAdapter(FragmentManager fm) {
            super(fm);

        }

        @Override
        public int getCount() {

            return 3;
        }

        @Override
        public Fragment getItem(int position) {

            if (position == 0) {
                if(one != null){
                    return one;
                }else {
                    return new StartUpPageOneFrag();
                }

            }else if(position == 1){
                if(two != null){
                    return two;
                }else {
                    return new StartUpPageTwoFrag();
                }
            }else{
                if(three != null){
                    return three;
                }else {
                    return new StartUpPageThreeFrag();
                }
            }


        }
    }