package com.swiftkaydevelopment.fullsailcommunicate.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Kevin Haines on 10/20/15.
 */
public abstract class BaseFragment extends Fragment {
    protected static String TAG = "findme-basefragment";
    public static final String ARG_UID = "ARG_UID";
    protected String uid;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            uid = savedInstanceState.getString(ARG_UID);
        } else {
            uid = getArguments().getString(ARG_UID);
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState != null) {
            uid = savedInstanceState.getString(ARG_UID);
        }
        checkForNullUID();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(ARG_UID, uid);
        super.onSaveInstanceState(outState);
    }

    protected void err(String message){
        Log.e(TAG, message);
    }
    protected void warn(String message){
        Log.w(TAG, message);
    }
    protected  void log(String message){
        Log.i(TAG, message);
    }
    protected void toast(String message){
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    private void checkForNullUID(){
        if (TextUtils.isEmpty(uid)) {
            warn("WARNING UID IS NULL...WARNING UID IS NULL....WARNING...WARNING..");
        }
    }
}
