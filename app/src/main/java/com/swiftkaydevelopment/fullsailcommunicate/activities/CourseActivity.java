package com.swiftkaydevelopment.fullsailcommunicate.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.swiftkaydevelopment.fullsailcommunicate.R;
import com.swiftkaydevelopment.fullsailcommunicate.data.Course;
import com.swiftkaydevelopment.fullsailcommunicate.fragments.SingleCourseFragment;

/**
 * Created by Kevin Haines on 1/3/16.
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
 * Package Name: com.swiftkaydevelopment.fullsailcommunicate.activities
 * Project: Full Sail Communicate
 * Class Name: CourseActivity
 * Class Description:
 */
public class CourseActivity extends BaseActivity {
    public static final String TAG = "CourseActivity";
    private static final String ARG_COURSE = "ARG_COURSE";
    private Course mCourse;

    public static Intent createIntent(Context context, @NonNull Course course) {
        Intent i = new Intent(context, CourseActivity.class);
        i.putExtra(ARG_COURSE, course);
        return i;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_course;
    }

    @Override
    protected Context getContext() {
        return this;
    }

    @Override
    protected void createActivity(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mCourse = (Course) savedInstanceState.getSerializable(ARG_COURSE);
        } else if (getIntent() != null && getIntent().getExtras() != null) {
            mCourse = (Course) getIntent().getExtras().getSerializable(ARG_COURSE);
        } else {
            throw new IllegalArgumentException("You must create intent using createIntent method");
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.coursesToolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_arrow_left_white_24dp);
        toolbar.setTitle(mCourse.courseName);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        SingleCourseFragment coursesFragment = (SingleCourseFragment) getSupportFragmentManager().findFragmentByTag(SingleCourseFragment.TAG);
        if (coursesFragment == null) {
            coursesFragment = SingleCourseFragment.newInstance(uid, mCourse);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_container, coursesFragment, SingleCourseFragment.TAG)
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(ARG_COURSE, mCourse);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
