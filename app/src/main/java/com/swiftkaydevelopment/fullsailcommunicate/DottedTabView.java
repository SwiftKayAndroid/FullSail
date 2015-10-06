package com.swiftkaydevelopment.fullsailcommunicate;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by khaines178 on 9/18/15.
 */
public class DottedTabView extends LinearLayout {

    List<Dot> dots;
    Context context;
    int dotColor = Color.WHITE;
    int selected;

    int padding = 15;

    public DottedTabView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public DottedTabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public DottedTabView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init(){
        Log.d(VarHolder.TAG,"Initializing DottedTabView");
        dots = new ArrayList<Dot>();

        this.setBackgroundColor(Color.TRANSPARENT);
        this.setDividerPadding(5);
        padding = this.getWidth()/9;
        addDot();
        addDot();
        addDot();


    }

    public void setDotColor(int colorRes){
        Log.d(VarHolder.TAG,"Setting dot color to " + getResources().getResourceName(colorRes));
        dotColor = colorRes;



        for(int i = 0;i < dots.size();i++){
            dots.get(i).setPaintColor(dotColor);
        }
        resetView();

    }

    public void setPadding(int p){
        Log.d(VarHolder.TAG,"Setting dot padding to " + Integer.toString(p));
        this.padding = p;

        for(int i = 0;i < dots.size();i++){
            dots.get(i).setPadding(padding,padding,padding,padding);
        }
        resetView();


    }

    public void addDot(){

        Log.d(VarHolder.TAG,"Adding dot.");
        Dot dot = new Dot(context);
        dot.setPaintColor(dotColor);
        dot.setPadding(padding, padding, padding, padding);

        dots.add(dot);
        Log.d(VarHolder.TAG, "Dot added. number of dots: " + Integer.toString(dots.size()));
        resetView();

    }
    public void setSelected(int pos){

        selected = pos;
        resetView();
    }

    public void removeDot(){
        Log.d(VarHolder.TAG,"Removing dot.");
        if(dots.size() > 0){
            dots.remove(0);
        }
        Log.d(VarHolder.TAG, "Dot removed. number of dots: " + Integer.toString(dots.size()));
        resetView();

    }

    private void resetView(){
        Log.d(VarHolder.TAG, "Resetting View");
        this.removeAllViews();

        for(int i = 0;i< dots.size();i++){
            Dot dot = dots.get(i);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            params.weight = 1;

            params.gravity = Gravity.CENTER;
            dot.setLayoutParams(params);
            dot.setPadding(padding,padding,padding,padding);
            if(i == selected){
                dot.setSelected(true);
            }else{
                dot.setSelected(false);
            }

            this.addView(dot);
        }



        invalidate();
    }




}
