package com.swiftkaydevelopment.fullsailcommunicate.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Kevin Haines on 10/19/15.
 */
public abstract class BaseActivity extends AppCompatActivity {
    public static final String TAG = "baseactivity";
    protected static final String ARG_UID = "ARG_UID";
    protected SharedPreferences prefs;
    protected String uid;

    protected abstract int getLayoutResource();
    protected abstract Context getContext();

    private String initializeUser() {
        String KEY = "uid";
        if (prefs != null) {
            return prefs.getString(KEY, null);
        }
        err("prefs is null");
        return null;
    }

    protected abstract void createActivity(Bundle savedInstanceState);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getLayoutResource() != 0) {
            setContentView(getLayoutResource());
        }

        prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        uid = initializeUser();
         if (uid == null || uid.isEmpty()) {
             err("uid is null");
         }
        createActivity(savedInstanceState);
        checkForNullUID();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void checkForNullUID(){
        if (uid == null || uid.isEmpty()) {
            err("WARNING UID IS NULL...WARNING UID IS NULL....WARNING...WARNING..");
        }
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
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }
}
