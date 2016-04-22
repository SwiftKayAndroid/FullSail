package com.swiftkaydevelopment.fullsailcommunicate.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.swiftkaydevelopment.fullsailcommunicate.R;
import com.swiftkaydevelopment.fullsailcommunicate.activities.CourseActivity;
import com.swiftkaydevelopment.fullsailcommunicate.data.Course;
import com.swiftkaydevelopment.fullsailcommunicate.data.CourseItem;
import com.swiftkaydevelopment.fullsailcommunicate.managers.CourseManager;

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
 * Class Name: CoursesFragment
 * Class Description:
 */
public class CoursesFragment extends BaseFragment implements View.OnClickListener, CourseManager.CourseManagerListener{
    public static final String TAG = "CoursesFragment";
    private static final String ARG_COURSES = "ARG_COURSES";

    private LinearLayout mCoursesContainer;
    private ArrayList<Course> mCourses = new ArrayList<>();

    public static CoursesFragment newInstance(String uid) {
        CoursesFragment frag = new CoursesFragment();
        Bundle b = new Bundle();
        b.putString(ARG_UID, uid);
        frag.setArguments(b);
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            CourseManager.instance().getCurrentCourses(uid);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.courses_fragment, container, false);
        mCoursesContainer = (LinearLayout) layout.findViewById(R.id.coursesContainer);
        return layout;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState != null) {
            mCourses = (ArrayList) savedInstanceState.getSerializable(ARG_COURSES);
        }
        reinitializeCoursesViews();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(ARG_COURSES, mCourses);
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

    /**
     * Clears and re-Adds all the course views
     * Better than a RecyclerView since the max number of courses will
     * always be two we don't need the overhead from a RecyclerView.
     */
    private void reinitializeCoursesViews() {
        mCoursesContainer.removeAllViews();
        for (Course course : mCourses) {
            addCourseView(course);
        }
    }

    /**
     * Adds a new View containing course information
     * to the cardview
     * @param course
     */
    private void addCourseView(Course course) {
        View itemView = LayoutInflater.from(getActivity())
                .inflate(R.layout.course_item, null);
        TextView courseTitle = (TextView) itemView.findViewById(R.id.courseItemCourseName);
        courseTitle.setText(course.courseName);
        itemView.setTag(course);
        itemView.setOnClickListener(this);
        mCoursesContainer.addView(itemView);
    }

    @Override
    public void onGetCourses(ArrayList<Course> courses) {
        Log.w(TAG, "onGetCourses");
        mCourses = courses;
        reinitializeCoursesViews();
    }

    @Override
    public void onGetAssignments(ArrayList<CourseItem> assignments) {

    }

    @Override
    public void onClick(View v) {
        if (v.getTag() instanceof Course) {
            getActivity().startActivity(CourseActivity.createIntent(getActivity(), (Course) v.getTag()));
        }
    }
}
