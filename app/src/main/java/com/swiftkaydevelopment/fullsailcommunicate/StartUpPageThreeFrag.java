package com.swiftkaydevelopment.fullsailcommunicate;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by khaines178 on 9/19/15.
 */
public class StartUpPageThreeFrag extends Fragment {

    public StartUpPageThreeFrag() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.fragment_three,container,false);
        return layout;
    }
}
