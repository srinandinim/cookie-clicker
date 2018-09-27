package com.example.srina.cookieclicker;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ImageView mainCookie, grandma, barn;
    ConstraintLayout constraintLayout;
    TextView scoreCount, cookiesNeeded;
    int score;
    int grandmaCount = 30;
    double grandmaHorizontalBias = 0.00;
    boolean animateGrandma = true;
    int barnCount = 50;
    double barnHorizontalBias = 0.03;
    boolean animateBarn = true;
    ScaleAnimation scaleIn = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
    ScaleAnimation scaleOut = new ScaleAnimation(1.0f, 0.0f, 1.0f, 0.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,0.5f);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainCookie = (ImageView) findViewById(R.id.mainCookie);
        scoreCount = (TextView) findViewById(R.id.score);
        cookiesNeeded = (TextView) findViewById(R.id.cookiesNeeded);
        constraintLayout = (ConstraintLayout) findViewById(R.id.layout);
        grandma = (ImageView) findViewById(R.id.grandma);
        barn = (ImageView) findViewById(R.id.barn);
        scaleIn.setDuration(300);
        scaleOut.setDuration(300);

        grandma.setVisibility(View.INVISIBLE);
        grandma.setClickable(false);
        barn.setVisibility(View.INVISIBLE);
        barn.setClickable(false);

        final ScaleAnimation scale = new ScaleAnimation(1.0f, 0.95f, 1.0f, 0.95f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scale.setDuration(200);

        mainCookie.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                view.startAnimation(scale);
                cookieClick();
            }
        });

        grandma.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                score = score - grandmaCount;
                grandmaCount+=15;
                if (score >= grandmaCount){
                    grandma.setClickable(true);
                    grandma.setVisibility(View.VISIBLE);
                }
                else {
                    grandma.startAnimation(scaleOut);
                    grandma.setClickable(false);
                    grandma.setVisibility(View.INVISIBLE);
                    animateGrandma = true;
                }
                if (score < barnCount){
                    if (animateBarn == false){
                        barn.startAnimation(scaleOut);
                    }
                    barn.setClickable(false);
                    barn.setVisibility(View.INVISIBLE);
                    animateBarn = true;
                }
                scoreCount.setText(score + " cookies");
                addGrandma(grandmaHorizontalBias);
                grandmaHorizontalBias+=0.15;
                new GrandmaThread().start();
            }
        });

        barn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                score = score - barnCount;
                barnCount += 25;
                if (score >= barnCount){
                    barn.setClickable(true);
                    barn.setVisibility(View.VISIBLE);
                }
                else {
                    barn.startAnimation(scaleOut);
                    barn.setClickable(false);
                    barn.setVisibility(View.INVISIBLE);
                    animateBarn = true;
                }
                if (score < grandmaCount){
                    if (animateGrandma == false){
                        grandma.startAnimation(scaleOut);
                    }
                    grandma.setClickable(false);
                    grandma.setVisibility(View.INVISIBLE);
                    animateGrandma = true;
                }
                scoreCount.setText(score + " cookies");
                addBarn (barnHorizontalBias);
                barnHorizontalBias+=0.15;
                new BarnThread().start();
            }
        });

    }

    public void addGrandma(double grandmaHorizontalBias){
        ImageView grandmaAddition = new ImageView(this);
        grandmaAddition.setImageResource(R.drawable.granny);
        grandmaAddition.setId(View.generateViewId());
        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
        grandmaAddition.setLayoutParams(params);
        constraintLayout.addView(grandmaAddition);
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(constraintLayout);
        constraintSet.connect(grandmaAddition.getId(), ConstraintSet.TOP, scoreCount.getId(), ConstraintSet.BOTTOM);
        constraintSet.connect(grandmaAddition.getId(), ConstraintSet.BOTTOM, constraintLayout.getId(), ConstraintSet.BOTTOM);
        constraintSet.connect(grandmaAddition.getId(), ConstraintSet.RIGHT, constraintLayout.getId(), ConstraintSet.RIGHT);
        constraintSet.connect(grandmaAddition.getId(), ConstraintSet.LEFT, constraintLayout.getId(), ConstraintSet.LEFT);
        constraintSet.setHorizontalBias(grandmaAddition.getId(), (float)grandmaHorizontalBias);
        constraintSet.setVerticalBias(grandmaAddition.getId(), 0.8f);
        constraintSet.applyTo(constraintLayout);
    }

    public void addBarn (double barnHorizontalBias){
        ImageView barnAddition = new ImageView(this);
        barnAddition.setImageResource(R.drawable.farmsmale);
        barnAddition.setId(View.generateViewId());
        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
        barnAddition.setLayoutParams(params);
        constraintLayout.addView(barnAddition);
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(constraintLayout);
        constraintSet.connect(barnAddition.getId(), ConstraintSet.TOP, scoreCount.getId(), ConstraintSet.BOTTOM);
        constraintSet.connect(barnAddition.getId(), ConstraintSet.BOTTOM, constraintLayout.getId(), ConstraintSet.BOTTOM);
        constraintSet.connect(barnAddition.getId(), ConstraintSet.RIGHT, constraintLayout.getId(), ConstraintSet.RIGHT);
        constraintSet.connect(barnAddition.getId(), ConstraintSet.LEFT, constraintLayout.getId(), ConstraintSet.LEFT);
        constraintSet.setHorizontalBias(barnAddition.getId(), (float)barnHorizontalBias);
        constraintSet.setVerticalBias(barnAddition.getId(), 0.95f);
        constraintSet.applyTo(constraintLayout);
    }

    public void cookieClick() {
        final TextView plusOne = new TextView(this);
        plusOne.setId(View.generateViewId());
        plusOne.setText("+1");
        score++;
        scoreCount.setText(score + " cookies");
        plusOne.setTextColor(Color.parseColor("Black"));

        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
        plusOne.setLayoutParams(params);
        constraintLayout.addView(plusOne);
        final ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(constraintLayout);

        constraintSet.connect(plusOne.getId(), ConstraintSet.TOP, scoreCount.getId(), ConstraintSet.BOTTOM);
        constraintSet.connect(plusOne.getId(), ConstraintSet.BOTTOM, constraintLayout.getId(), ConstraintSet.BOTTOM);
        constraintSet.connect(plusOne.getId(), ConstraintSet.RIGHT, constraintLayout.getId(), ConstraintSet.RIGHT);
        constraintSet.connect(plusOne.getId(), ConstraintSet.LEFT, constraintLayout.getId(), ConstraintSet.LEFT);

        constraintSet.setHorizontalBias(plusOne.getId(), 0.5f);
        constraintSet.setVerticalBias(plusOne.getId(), 0.5f);
        constraintSet.applyTo(constraintLayout);

        TranslateAnimation translateAnimation = new TranslateAnimation(((float)Math.random()*25f)+5, ((float)Math.random()*25f)+5, 0f, -250f);
        translateAnimation.setDuration(800);
        plusOne.startAnimation(translateAnimation);
        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation animation) {}
            public void onAnimationEnd(Animation animation) {
                constraintLayout.removeView(plusOne);
            }
            public void onAnimationRepeat(Animation animation) {}
        });
        if (score >= grandmaCount && animateGrandma == true){
            animateGrandma = false;
            grandma.setVisibility(View.VISIBLE);
            grandma.setClickable(true);
            grandma.startAnimation(scaleIn);
        }
        if (score >= barnCount && animateBarn == true){
            animateBarn = false;
            barn.setVisibility(View.VISIBLE);
            barn.setClickable(true);
            barn.startAnimation(scaleIn);
        }

    }

    public synchronized void add(int i) {
        score += i;
    }

    public class GrandmaThread extends Thread {
        public void run() {
            while (true) {
                try {
                    Thread.sleep(2500);
                } catch (Exception e) {
                }
                int increment = 1;
                add(increment);
                if (score >= grandmaCount){
                    grandma.post(new Runnable() {
                        public void run() {
                            grandma.setVisibility(View.VISIBLE);
                            grandma.setClickable(true);
                            if (animateGrandma)
                                grandma.startAnimation(scaleIn);
                            animateGrandma = false;
                        }
                    });
                }
                scoreCount.post(new Runnable() {
                    public void run() {
                        scoreCount.setText(score + " cookies");
                    }
                });
            }
        }
    }

    public class BarnThread extends Thread {
        public void run() {
            while (true) {
                try {
                    Thread.sleep(1500);
                } catch (Exception e) {
                }
                int increment = 1;
                add(increment);
                if (score >= barnCount){
                    grandma.post(new Runnable() {
                        public void run() {
                            barn.setVisibility(View.VISIBLE);
                            barn.setClickable(true);
                            if (animateBarn)
                                barn.startAnimation(scaleIn);
                            animateBarn = false;
                        }
                    });
                }
                scoreCount.post(new Runnable() {
                    public void run() {
                        scoreCount.setText(score + " cookies");
                    }
                });
            }
        }
    }
}