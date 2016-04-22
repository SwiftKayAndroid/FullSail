package com.swiftkaydevelopment.fullsailcommunicate.activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.swiftkaydevelopment.fullsailcommunicate.BuildConfig;
import com.swiftkaydevelopment.fullsailcommunicate.R;
import com.swiftkaydevelopment.fullsailcommunicate.adapters.WalkThroughPagerAdapter;
import com.swiftkaydevelopment.fullsailcommunicate.managers.AccountManager;
import com.swiftkaydevelopment.fullsailcommunicate.ui.DottedTabView;

public class MainActivity extends AppCompatActivity implements AccountManager.AuthenticationListener {

    private ViewPager mPager;
    private DottedTabView mDottedTabView;
    private Button mBtnNext;
    private ImageView mIvLogo;
    private EditText mEtEmail, mEtPassword;
    private TextView mTvWelcome;
    private WalkThroughPagerAdapter mAdapter;

    int[] startingPos = new int[2];
    int endpos;
    int[] dtvpos = new int[2];
    int ivheight;

    boolean ivmoved = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (BuildConfig.DEBUG) {
            startActivity(HomeActivity.createIntent(this));
            finish();
        }
        setContentView(R.layout.activity_main);
        mDottedTabView = (DottedTabView) findViewById(R.id.dottedView);
        mBtnNext = (Button) findViewById(R.id.btn_intro_next);
        mIvLogo = (ImageView) findViewById(R.id.ivintrologo);
        mTvWelcome = (TextView) findViewById(R.id.tvintrowelcome);
        mEtEmail = (EditText) findViewById(R.id.etintroemail);
        mEtPassword = (EditText) findViewById(R.id.etintropassword);

        mBtnNext.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    mBtnNext.setTextColor(getResources().getColor(R.color.full_sail_orange));
                    return false;
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    mBtnNext.setTextColor(getResources().getColor(R.color.white));

                    if (mPager.getCurrentItem() < 2) {
                        mPager.setCurrentItem(mPager.getCurrentItem() + 1);
                    } else {
                        String email = mEtEmail.getText().toString();
                        if (email.contains("@fullsail.edu")) {
                            if (!TextUtils.isEmpty(mEtPassword.getText().toString())) {
                                AccountManager.instance().authenticateUser(email, mEtPassword.getText().toString());
                            } else {
                                Toast.makeText(MainActivity.this, getString(R.string.password_cant_be_empty), Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(MainActivity.this, getString(R.string.only_fullsail), Toast.LENGTH_LONG).show();
                        }
                    }
                    return true;
                }
                return true;
            }
        });

        mAdapter = new WalkThroughPagerAdapter(getSupportFragmentManager(), this);

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);
        mPager.setCurrentItem(0);
        mDottedTabView.setSelected(0);
        mDottedTabView.setNumberOfDots(3);

        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                if (position == 2 && !ivmoved) {
                    mIvLogo.getLocationOnScreen(startingPos);
                    ivheight = mIvLogo.getHeight();

                    mDottedTabView.getLocationOnScreen(dtvpos);
                    endpos = dtvpos[1] - ivheight - startingPos[1];
                    Animation animation = new TranslateAnimation(0, 0, 0, endpos);
                    animation.setDuration(450);
                    animation.setFillAfter(true);
                    mIvLogo.startAnimation(animation);
                    ivmoved = true;

                    animation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                            mEtEmail.setFocusable(true);
                            mEtEmail.setFocusableInTouchMode(true);
                            mEtEmail.setVisibility(View.VISIBLE);
                            mEtPassword.setVisibility(View.VISIBLE);
                            mEtPassword.setFocusable(true);
                            mEtPassword.setFocusableInTouchMode(true);
                            Animation fadeIn = new AlphaAnimation(0, 1);
                            fadeIn.setInterpolator(new DecelerateInterpolator());
                            fadeIn.setDuration(1300);
                            fadeIn.setFillAfter(true);
                            mEtEmail.setAnimation(fadeIn);
                            mEtPassword.setAnimation(fadeIn);
                            mTvWelcome.setAnimation(fadeIn);
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });

                } else if (position != 2) {
                    if (ivmoved) {
                        mIvLogo.getLocationOnScreen(startingPos);
                        ivheight = mIvLogo.getHeight();

                        mDottedTabView.getLocationOnScreen(dtvpos);
                        endpos = dtvpos[1] - ivheight - startingPos[1];
                        Animation animation = new TranslateAnimation(0, 0, endpos, 0);
                        animation.setDuration(400);
                        animation.setFillAfter(true);
                        mIvLogo.startAnimation(animation);

                        animation.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {
                                Animation fadeOut = new AlphaAnimation(1, 0);
                                fadeOut.setInterpolator(new AccelerateInterpolator());
                                fadeOut.setDuration(500);
                                fadeOut.setFillAfter(true);
                                mEtEmail.setAnimation(fadeOut);
                                mEtPassword.setAnimation(fadeOut);
                                mTvWelcome.setAnimation(fadeOut);
                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
//                                mEtEmail.setVisibility(View.GONE);
//                                mEtPassword.setVisibility(View.GONE);
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });
                        ivmoved = false;
                    }
                }
                mDottedTabView.setSelected(position);

                if (position == 2) {
                    mBtnNext.setText("Login");
                } else {
                    mBtnNext.setText("Next");
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

    @Override
    protected void onPostResume() {
        super.onPostResume();
        AccountManager.instance().addAuthenticationListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        AccountManager.instance().removeAuthenticationListener(this);
    }

    @Override
    public void onAuthenticationSuccessful() {
        startActivity(HomeActivity.createIntent(this));
    }

    @Override
    public void onAuthenticationFailed(int failType) {

    }
}
