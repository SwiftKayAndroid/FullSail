package com.swiftkaydevelopment.fullsailcommunicate.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.swiftkaydevelopment.fullsailcommunicate.R;
import com.swiftkaydevelopment.fullsailcommunicate.adapters.SingleCourseAdapter;
import com.swiftkaydevelopment.fullsailcommunicate.data.Course;
import com.swiftkaydevelopment.fullsailcommunicate.data.CourseItem;
import com.swiftkaydevelopment.fullsailcommunicate.managers.CourseManager;

import java.util.ArrayList;

/**
 * Created by Kevin Haines on 1/5/16.
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
 * Class Name: SingleCourseFragment
 * Class Description:
 */
public class SingleCourseFragment extends BaseFragment implements CourseManager.CourseManagerListener{
    public static final String TAG = "SingleCourseFragment";
    private static final String ARG_COURSE = "ARG_COURSE";
    private static final String ARG_COURSE_ITEMS = "ARG_COURSE_ITEMS";

    private Course mCourse;
    private SingleCourseAdapter mAdapter;
    private RecyclerView mRecyclerView;
    ArrayList<CourseItem> mCourseItems = new ArrayList<>();

    public static SingleCourseFragment newInstance(String uid, Course course) {
        SingleCourseFragment frag = new SingleCourseFragment();
        Bundle b = new Bundle();
        b.putString(ARG_UID, uid);
        b.putSerializable(ARG_COURSE, course);
        frag.setArguments(b);
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null && getArguments() != null) {
            mCourse = (Course) getArguments().getSerializable(ARG_COURSE);
            CourseManager.instance().getAssignments(uid, mCourse);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.single_course_fragment, container, false);
        mRecyclerView = (RecyclerView) layout.findViewById(R.id.singleCourseRecyclerView);
        return layout;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (mAdapter == null) {
            mAdapter = new SingleCourseAdapter(mCourseItems);
        }

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(ARG_COURSE, mCourse);
        outState.putSerializable(ARG_COURSE_ITEMS, mCourseItems);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
        CourseManager.instance().addListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        CourseManager.instance().removeListener(this);
    }

    @Override
    public void onGetCourses(ArrayList<Course> courses) {

    }

    @Override
    public void onGetAssignments(ArrayList<CourseItem> assignments) {
        Log.w(TAG, "onGetAssignments");
        mAdapter.setAssignments(assignments);
    }
}
