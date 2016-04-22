package com.swiftkaydevelopment.fullsailcommunicate.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.swiftkaydevelopment.fullsailcommunicate.R;
import com.swiftkaydevelopment.fullsailcommunicate.constants.AppConstants;
import com.swiftkaydevelopment.fullsailcommunicate.data.MockData;
import com.swiftkaydevelopment.fullsailcommunicate.fragments.AssignmentViewFragment;
import com.swiftkaydevelopment.fullsailcommunicate.fragments.AttendanceFragment;
import com.swiftkaydevelopment.fullsailcommunicate.fragments.GradesFragment;
import com.swiftkaydevelopment.fullsailcommunicate.fragments.HomeDemoFragment;
import com.swiftkaydevelopment.fullsailcommunicate.fragments.ProfileFragment;
import com.swiftkaydevelopment.fullsailcommunicate.fragments.ScheduleFragment;
import com.swiftkaydevelopment.fullsailcommunicate.ui.CircleTransform;

/**
 * Created by Kevin Haines on 12/31/15.
 * Copyright (C) 2015 Kevin Haines
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p>
 * Package Name: com.swiftkaydevelopment.fullsailcommunicate.activities
 * Project: Full Sail Communicate
 * Class Name:
 * Class Description:
 */
public class HomeActivity extends BaseActivity {
    private static final String TAG = "HomeActivity";
    private static final String ARG_CURRENT_PAGE = "ARG_CURRENT_PAGE";

    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private int mCurrentPage = -1;

    public static Intent createIntent(Context context) {
        Intent i = new Intent(context, HomeActivity.class);
        return i;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.homepage;
    }

    @Override
    protected Context getContext() {
        return this;
    }

    @Override
    protected void createActivity(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mCurrentPage = savedInstanceState.getInt(ARG_CURRENT_PAGE);
        }

        mToolbar = (Toolbar) findViewById(R.id.toolbarNavigation);
        mToolbar.setNavigationIcon(R.mipmap.ic_menu_white_24dp);
        setSupportActionBar(mToolbar);

        initializeDrawer();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(ARG_CURRENT_PAGE, mCurrentPage);
        super.onSaveInstanceState(outState);
    }

    public void initializeDrawer(){
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.nvMainNavigation);
        View headerView = mNavigationView.inflateHeaderView(R.layout.drawerheader);

        setUpHeader(headerView);

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                selectMenuItem(menuItem.getItemId());
                mDrawerLayout.closeDrawers();
                return false;
            }
        });

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close) {

            @Override
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        mDrawerLayout.setDrawerListener(drawerToggle);

        selectMenuItem(mCurrentPage != -1 ? mCurrentPage : mNavigationView.getMenu().findItem(R.id.navMenuHome).getItemId());
    }

    private void selectMenuItem(int itemId) {
        Fragment fragment = null;
        String tag = null;
        mCurrentPage = itemId;

        switch (itemId) {
            case R.id.navMenuHome:
                fragment = getSupportFragmentManager().findFragmentByTag(HomeDemoFragment.TAG);
                if (fragment == null) {
                    fragment = HomeDemoFragment.newInstance();
                }
                tag = HomeDemoFragment.TAG;
                break;
            case R.id.navMenuCourses:
                fragment = getSupportFragmentManager().findFragmentByTag(AssignmentViewFragment.TAG);
                if (fragment == null) {
                    fragment = AssignmentViewFragment.newInstance();
                }
                tag = AssignmentViewFragment.TAG;

                break;
            case R.id.navMenuProfile:
                fragment = getSupportFragmentManager().findFragmentByTag(ProfileFragment.TAG);
                if (fragment == null) {
                    fragment = ProfileFragment.newInstance(uid);
                }
                tag = ProfileFragment.TAG;
                break;
            case R.id.navMenuGrades:
                fragment = getSupportFragmentManager().findFragmentByTag(GradesFragment.TAG);
                if (fragment == null) {
                    fragment = GradesFragment.newInstance(uid);
                }
                tag = GradesFragment.TAG;

                break;
            case R.id.navMenuSchedule:
                fragment = getSupportFragmentManager().findFragmentByTag(ScheduleFragment.TAG);
                if (fragment == null) {
                    fragment = ScheduleFragment.newInstance(uid);
                }
                tag = ScheduleFragment.TAG;
                break;
            case R.id.navMenuAttendance:
                fragment = getSupportFragmentManager().findFragmentByTag(AttendanceFragment.TAG);
                if (fragment == null) {
                    fragment = AttendanceFragment.newInstance(uid);
                }
                tag = AttendanceFragment.TAG;
                break;
            case R.id.navMenuFeedback:
                break;
            default:
                Log.e(TAG, "invalid menu position");
                break;
        }

        if (fragment != null && !TextUtils.isEmpty(tag)) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_container, fragment, tag)
                    .addToBackStack(null)
                    .commit();
        } else {
            Log.e(TAG, "error loading fragment");
        }
    }

    private View setUpHeader(View header) {
        TextView tvName = (TextView) header.findViewById(R.id.tvDrawerHeaderName);
        ImageView ivusersphoto = (ImageView) header.findViewById(R.id.ivDrawerHeaderProfilePicture);

        tvName.setText(prefs.getString(AppConstants.PREFERENCE_NAMES.PREF_FIRSTNAME, MockData.FIRSTNAME) +
                " " + prefs.getString(AppConstants.PREFERENCE_NAMES.PREF_LASTNAME, MockData.LASTNAME));
        String imgLoc = prefs.getString(AppConstants.PREFERENCE_NAMES.PREF_PRO_PIC_LOC, MockData.PROFILE_PICTURE_LOCATION);
        if (!TextUtils.isEmpty(imgLoc)) {
            Picasso.with(this)
                    .load(imgLoc)
                    .transform(new CircleTransform())
                    .into(ivusersphoto);
        } else {
            Picasso.with(this)
                    .load(R.drawable.profile_pic_blank_150)
                    .transform(new CircleTransform())
                    .into(ivusersphoto);
        }

        tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDrawerLayout != null) {
                    mDrawerLayout.closeDrawers();
                }

                ProfileFragment fragment = (ProfileFragment) getSupportFragmentManager().findFragmentByTag(ProfileFragment.TAG);
                if (fragment == null) {
                    fragment = ProfileFragment.newInstance(uid);
                }
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_container, fragment, ProfileFragment.TAG)
                        .addToBackStack(null)
                        .commit();
            }
        });

        ivusersphoto.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mDrawerLayout != null) {
                    mDrawerLayout.closeDrawers();
                }

                ProfileFragment fragment = (ProfileFragment) getSupportFragmentManager().findFragmentByTag(ProfileFragment.TAG);
                if (fragment == null) {
                    fragment = ProfileFragment.newInstance(uid);
                }
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_container, fragment, ProfileFragment.TAG)
                        .addToBackStack(null)
                        .commit();
            }
        });
        return header;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigation_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            mDrawerLayout.openDrawer(GravityCompat.START);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
