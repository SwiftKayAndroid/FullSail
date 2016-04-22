package com.swiftkaydevelopment.fullsailcommunicate.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.swiftkaydevelopment.fullsailcommunicate.R;
import com.swiftkaydevelopment.fullsailcommunicate.adapters.GradesAdapter;
import com.swiftkaydevelopment.fullsailcommunicate.data.Grade;

import java.util.ArrayList;

/**
 * Created by Kevin Haines on 1/2/16.
 * Copyright (C) 2015 Kevin Haines
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p/>
 * Package Name: com.swiftkaydevelopment.fullsailcommunicate.fragments
 * Project: Full Sail Communicate
 * Class Name: GradesFragment
 * Class Description:
 */
public class GradesFragment extends BaseFragment {
    public static final String TAG = "GradesFragment";
    private static final String ARG_GRADES= "ARG_GRADES";

    private RecyclerView mRecyclerView;
    private TextView mStartDate;
    private TextView mStatus;
    private TextView mGpa;

    private GradesAdapter mAdapter;
    private ArrayList<Grade> mGrades = new ArrayList<>();

    public static GradesFragment newInstance(String uid) {
        GradesFragment frag = new GradesFragment();
        Bundle b = new Bundle();
        b.putString(ARG_UID, uid);
        frag.setArguments(b);
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.grades_fragment, container, false);
        mRecyclerView = (RecyclerView) layout.findViewById(R.id.recyclerView);
        mGpa = (TextView) layout.findViewById(R.id.gradesGpa);
        mStartDate = (TextView) layout.findViewById(R.id.gradesStartDate);
        mStatus = (TextView) layout.findViewById(R.id.attendanceStatus);
        return layout;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState != null) {
            mGrades = (ArrayList) savedInstanceState.getSerializable(ARG_GRADES);
        }

        if (mAdapter == null) {
            mAdapter = new GradesAdapter(mGrades);
        }
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(ARG_GRADES, mGrades);
        super.onSaveInstanceState(outState);
    }
}
