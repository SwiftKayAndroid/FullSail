package com.swiftkaydevelopment.fullsailcommunicate;

import android.content.Context;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;

/**
 * Created by khaines178 on 9/19/15.
 */
public class SimpleAnimator {
    Context context;

    public SimpleAnimator(Context context) {
        this.context = context;
    }




    //-----------------------------------------------FADE ANIMATIONS-->>
    public void fadeIn(View view,boolean fill,int duration){

        Animation fadeIn = new AlphaAnimation(0, 1);            //initialize animation object
        fadeIn.setInterpolator(new DecelerateInterpolator());
        fadeIn.setDuration(duration);                           //set length of animation
        fadeIn.setFillAfter(fill);                              //should animation keep alpha after animation is complete
        view.setAnimation(fadeIn);                              //sets and starts the animation
    }
    public void fadeIn(View[] views,boolean fill,int duration){

        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator());
        fadeIn.setDuration(duration);
        fadeIn.setFillAfter(fill);

        for(int i = 0;i< views.length;i++) {

            views[i].setAnimation(fadeIn);
        }
    }


    //---------------------------------------------------TRANSLATION ANIMATIONS-->>
}
