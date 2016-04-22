package com.swiftkaydevelopment.fullsailcommunicate.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;
import com.swiftkaydevelopment.fullsailcommunicate.R;
import com.swiftkaydevelopment.fullsailcommunicate.data.User;
import com.swiftkaydevelopment.fullsailcommunicate.ui.CircleTransform;
import com.swiftkaydevelopment.fullsailcommunicate.utils.AndroidUtils;

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
 * Class Name: ProfileFragment
 * Class Description:
 */
public class ProfileFragment extends BaseFragment implements View.OnClickListener{
    public static final String TAG = "ProfileFragment";
    private static final String ARG_USER = "ARG_USER";
    private static final String ARG_EDIT = "ARG_EDIT";

    private ImageView mProfilePhoto;
    private ImageView mEditProfile;
    private EditText mTvFirstName;
    private EditText mTvMiddleName;
    private EditText mTvLastName;
    private EditText mTvEmailAddress;
    private EditText mTvPhoneNumber;
    private EditText mTvAddress;
    private EditText mAboutMe;
    private LinearLayout mContainer;

    private User mUser;

    private boolean mEditMode = false;

    public static ProfileFragment newInstance(String uid) {
        ProfileFragment frag = new ProfileFragment();
        Bundle b = new Bundle();
        b.putString(ARG_UID, uid);
        frag.setArguments(b);
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mUser = (User) savedInstanceState.getSerializable(ARG_USER);
            mEditMode = savedInstanceState.getBoolean(ARG_EDIT);
        } else {
            //TODO: This is where we will get the User
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.profile_fragment, container, false);
        mProfilePhoto = (ImageView) layout.findViewById(R.id.profileHeaderProfilePicture);
        mTvFirstName = (EditText) layout.findViewById(R.id.profileHeaderFirstname);
        mTvMiddleName = (EditText) layout.findViewById(R.id.profileHeaderMiddleName);
        mTvLastName = (EditText) layout.findViewById(R.id.profileHeaderLastName);
        mTvEmailAddress = (EditText) layout.findViewById(R.id.profileHeaderEmailAddress);
        mTvPhoneNumber = (EditText) layout.findViewById(R.id.profileHeaderPhoneNumber);
        mTvAddress = (EditText) layout.findViewById(R.id.profileHeaderAddress);
        mEditProfile = (ImageView) layout.findViewById(R.id.ivProfileEditProfile);
        mAboutMe = (EditText) layout.findViewById(R.id.profileHeaderAboutMe);
        mContainer = (LinearLayout) layout.findViewById(R.id.profileContainer);
        return layout;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState != null) {
            mEditMode = savedInstanceState.getBoolean(ARG_EDIT);
        }
        toggleEditable();
        if (mUser != null) {
            loadUserInfo();
        } else {
            Picasso.with(getActivity())
                    .load(R.drawable.profile_pic_blank_150)
                    .transform(new CircleTransform())
                    .into(mProfilePhoto);
        }

        mEditProfile.setOnClickListener(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(ARG_USER, mUser);
        super.onSaveInstanceState(outState);
    }

    /**
     * Loads the user's information into the views
     */
    private void loadUserInfo() {
        if (!TextUtils.isEmpty(mUser.profilePictureLocation)) {
            Picasso.with(getActivity())
                    .load(mUser.profilePictureLocation)
                    .placeholder(R.drawable.profile_pic_blank_150)
                    .transform(new CircleTransform())
                    .into(mProfilePhoto);
        } else {
            Picasso.with(getActivity())
                    .load(R.drawable.profile_pic_blank_150)
                    .transform(new CircleTransform())
                    .into(mProfilePhoto);
        }
        mTvFirstName.setText(mUser.firstname);
        mTvMiddleName.setText(mUser.middleName);
        mTvLastName.setText(mUser.lastname);
        mTvPhoneNumber.setText(mUser.phoneNumber);
        mTvAddress.setText(mUser.address);
        mTvEmailAddress.setText(mUser.emailAddress);
    }

    private void toggleEditable() {
        if (mEditMode) {
            mContainer.setBackgroundColor(getActivity().getResources().getColor(R.color.cardview_shadow_start_color));
            mTvFirstName.setFocusableInTouchMode(true);
            mTvFirstName.setFocusable(true);
            mTvLastName.setFocusable(true);
            mTvLastName.setFocusableInTouchMode(true);
            mTvMiddleName.setFocusable(true);
            mTvMiddleName.setFocusableInTouchMode(true);
            mTvPhoneNumber.setFocusable(true);
            mTvPhoneNumber.setFocusableInTouchMode(true);
            mTvAddress.setFocusable(true);
            mTvAddress.setFocusableInTouchMode(true);
            mTvEmailAddress.setFocusable(true);
            mTvEmailAddress.setFocusableInTouchMode(true);
            mAboutMe.setFocusable(true);
            mAboutMe.setFocusableInTouchMode(true);
            mTvFirstName.setHint("First Name");
            mTvLastName.setHint("Middle Name");
            mTvMiddleName.setHint("Last Name");
            mTvPhoneNumber.setHint("xxx xxx xxxx");
            mTvAddress.setHint("1000 Main St Orlando FL, 32828");
            mTvEmailAddress.setHint("someone@somewhere.com");
        } else {
            mContainer.setBackgroundColor(getActivity().getResources().getColor(R.color.white));
            mTvFirstName.setFocusable(false);
            mTvFirstName.setFocusableInTouchMode(false);
            mTvMiddleName.setFocusable(false);
            mTvMiddleName.setFocusableInTouchMode(false);
            mTvLastName.setFocusable(false);
            mTvLastName.setFocusableInTouchMode(false);
            mTvPhoneNumber.setFocusable(false);
            mTvPhoneNumber.setFocusableInTouchMode(false);
            mTvAddress.setFocusable(false);
            mTvAddress.setFocusableInTouchMode(false);
            mTvEmailAddress.setFocusable(false);
            mTvEmailAddress.setFocusableInTouchMode(false);
            mAboutMe.setFocusable(false);
            mTvFirstName.setHint("");
            mTvLastName.setHint("");
            mTvMiddleName.setHint("");
            mTvPhoneNumber.setHint("");
            mTvAddress.setHint("");
            mTvEmailAddress.setHint("");
            AndroidUtils.hideKeyboard(getActivity(), getActivity().getCurrentFocus());
        }
    }

    private void save() {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == mEditProfile.getId()) {
            if (mEditMode) {
                save();
            }
            mEditMode = !mEditMode;
            toggleEditable();
        }
    }
}
