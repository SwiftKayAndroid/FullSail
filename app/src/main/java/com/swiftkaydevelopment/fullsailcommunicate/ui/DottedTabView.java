package com.swiftkaydevelopment.fullsailcommunicate.ui;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kevin Haines on 9/18/15.
 */
public class DottedTabView extends LinearLayout {

    private List<Dot> mDots = new ArrayList<>();;
    private Context mContext;
    int dotColor = Color.WHITE;
    int selected;

    int padding = 15;

    public DottedTabView(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    public DottedTabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    public DottedTabView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    private void init(){
        this.setBackgroundColor(Color.TRANSPARENT);
        this.setDividerPadding(5);
        padding = this.getWidth() / 9;
    }

    public void setNumberOfDots(int num) {
        for (int i = 0; i < num; i++) {
            addDot();
        }
    }

    public void setDotColor(int colorRes){
        dotColor = colorRes;

        for(Dot dot : mDots){
            dot.setPaintColor(dotColor);
        }
        resetView();
    }

    public void setPadding(int p){
        this.padding = p;

        for(Dot dot : mDots){
            dot.setPadding(padding, padding, padding, padding);
        }
        resetView();
    }

    public void addDot(){
        Dot dot = new Dot(mContext);
        dot.setPaintColor(dotColor);
        dot.setPadding(padding, padding, padding, padding);

        mDots.add(dot);
        resetView();
    }

    public void setSelected(int pos){
        selected = pos;
        resetView();
    }

    private void resetView(){
        this.removeAllViews();

        for(Dot dot : mDots){
            dot.setPadding(padding,padding,padding,padding);
            if (mDots.indexOf(dot) == selected) {
                dot.setSelected(true);
            } else {
                dot.setSelected(false);
            }

            this.addView(dot);
        }
        invalidate();
    }
}
