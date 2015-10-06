package com.swiftkaydevelopment.fullsailcommunicate;

import java.util.Locale;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {




    MyAdapter mAdapter;
    ViewPager mPager;
    DottedTabView dtv;

    Button btnnext;
    ImageView ivlogo;

    Button login;
    EditText etemail, etpassword;
    TextView tvwelcome;

    int[] startingPos = new int[2];
    int endpos;
    int[] dtvpos = new int[2];
    int ivheight;

    boolean ivmoved = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dtv = (DottedTabView) findViewById(R.id.dottedView);
        btnnext = (Button) findViewById(R.id.btn_intro_next);
        ivlogo = (ImageView) findViewById(R.id.ivintrologo);
        tvwelcome = (TextView) findViewById(R.id.tvintrowelcome);
        etemail = (EditText) findViewById(R.id.etintroemail);
        etpassword = (EditText) findViewById(R.id.etintropassword);
        login = (Button) findViewById(R.id.btnlogin);




                btnnext.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_DOWN){

                    btnnext.setTextColor(getResources().getColor(R.color.full_sail_orange));
                    return false;
                }
                if(event.getAction() == MotionEvent.ACTION_UP){
                    btnnext.setTextColor(getResources().getColor(R.color.white));

                    if(mPager.getCurrentItem() < 2) {
                        mPager.setCurrentItem(mPager.getCurrentItem() + 1);

                    }else{
                        //this is where we will proceed to a choice activity to choose login or register
                    }
                    return true;
                }
                return true;
            }
        });

        //initialize the adapter
        mAdapter = new MyAdapter(getSupportFragmentManager());

        //initialize viewpager
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);
        mPager.setCurrentItem(0);
        dtv.setSelected(0);

        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                if(position == 2 && !ivmoved) {
                    ivlogo.getLocationOnScreen(startingPos);
                    ivheight = ivlogo.getHeight();

                    dtv.getLocationOnScreen(dtvpos);
                    endpos = dtvpos[1] - ivheight - startingPos[1] ;
                    Log.d(VarHolder.TAG,"starting down animation. starting x: " + Integer.toString(startingPos[0]) +
                            " starting y: " + startingPos[1] + " dtv x: " + dtvpos[0] + " dtv y: " + dtvpos[1] + " endpos: " + endpos +
                    " height: " + ivheight);
                    Animation animation = new TranslateAnimation(0, 0, 0, endpos);
                    animation.setDuration(450);
                    animation.setFillAfter(true);
                    ivlogo.startAnimation(animation);
                    ivmoved = true;

                    animation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                            etemail.setFocusable(true);
                            etpassword.setFocusable(true);
                            Animation fadeIn = new AlphaAnimation(0, 1);
                            fadeIn.setInterpolator(new DecelerateInterpolator()); // add this
                            fadeIn.setDuration(1300);
                            fadeIn.setFillAfter(true);
                            login.setAnimation(fadeIn);
                            etemail.setAnimation(fadeIn);
                            etpassword.setAnimation(fadeIn);
                            tvwelcome.setAnimation(fadeIn);
                            //login.startAnimation(fadeIn);
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });

                }else if(position != 2){
                    if(ivmoved){
                        ivlogo.getLocationOnScreen(startingPos);
                        ivheight = ivlogo.getHeight();

                        dtv.getLocationOnScreen(dtvpos);
                        endpos = dtvpos[1] - ivheight - startingPos[1] ;
                        Log.d(VarHolder.TAG, "starting down animation. starting x: " + Integer.toString(startingPos[0]) +
                                " starting y: " + startingPos[1] + " dtv x: " + dtvpos[0] + " dtv y: " + dtvpos[1] + " endpos: " + endpos +
                                " height: " + ivheight);
                        Animation animation = new TranslateAnimation(0, 0, endpos, 0);
                        animation.setDuration(400);
                        animation.setFillAfter(true);
                        ivlogo.startAnimation(animation);

                        animation.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {
                                etemail.setFocusable(false);
                                etpassword.setFocusable(false);
                                Animation fadeOut = new AlphaAnimation(1, 0);
                                fadeOut.setInterpolator(new AccelerateInterpolator()); // and this
                                //fadeOut.setStartOffset();
                                fadeOut.setDuration(500);
                                fadeOut.setFillAfter(true);
                                login.setAnimation(fadeOut);
                                etemail.setAnimation(fadeOut);
                                etpassword.setAnimation(fadeOut);
                                tvwelcome.setAnimation(fadeOut);
                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {

                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });







                        ivmoved = false;
                    }


                }


                dtv.setSelected(position);

                if(position == 2){
                    btnnext.setText("Register");
                }else{
                    btnnext.setText("Next");
                }



            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }




}
