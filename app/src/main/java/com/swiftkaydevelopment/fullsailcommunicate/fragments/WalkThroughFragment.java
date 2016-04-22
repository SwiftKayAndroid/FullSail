package com.swiftkaydevelopment.fullsailcommunicate.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.swiftkaydevelopment.fullsailcommunicate.R;

/**
 * Created by Kevin Haines on 9/19/15.
 */
public class WalkThroughFragment extends Fragment {
    public static final String TAG = "wlkThrghFrgment";
    private static final String ARG_MESSAGE = "ARG_MESS";
    private static final String ARG_RES = "ARG_RES";

    private String mMessage;
    private int resId;

    public static WalkThroughFragment newInstance(String message, int res) {
        WalkThroughFragment frag = new WalkThroughFragment();
        Bundle b = new Bundle();
        b.putString(ARG_MESSAGE, message);
        b.putInt(ARG_RES, res);
        frag.setArguments(b);
        return frag;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mMessage = savedInstanceState.getString(ARG_MESSAGE);
            resId = savedInstanceState.getInt(ARG_RES);
        } else if (getArguments() != null) {
            mMessage = getArguments().getString(ARG_MESSAGE);
            resId = getArguments().getInt(ARG_RES);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mMessage = savedInstanceState.getString(ARG_MESSAGE);
            resId = savedInstanceState.getInt(ARG_RES);
        }

        View layout = inflater.inflate(R.layout.walkthrough_fragment, parent, false);
        TextView tvMessage = (TextView) layout.findViewById(R.id.tvWalkthroughMessage);
        RelativeLayout container = (RelativeLayout) layout.findViewById(R.id.walkthroughContainer);
        container.setBackgroundResource(resId);

        if (TextUtils.isEmpty(mMessage)) {
            tvMessage.setVisibility(View.GONE);
        } else {
            tvMessage.setVisibility(View.VISIBLE);
            tvMessage.setText(mMessage);
        }
        return layout;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(ARG_MESSAGE, mMessage);
        super.onSaveInstanceState(outState);
    }
}
