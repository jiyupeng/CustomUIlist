package com.weiyue.loadinganim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;

/**
 * Created by 姬玉鹏 on 2018/4/8.
 */

public class LoadingView extends View {

    private int rx;
    private int ry;
    private Paint mPaint;
    private int smallRadius;
    private int bigRadius;
    private float mCurrentPre;
    private int state;
    private int mToalRadius;
    private AnimState mAnimState;

    private int[] colors={
            Color.BLACK,Color.YELLOW,Color.BLUE,Color.CYAN,
            Color.DKGRAY,Color.GRAY,Color.GREEN,Color.MAGENTA
    };


    public LoadingView(Context context) {
        this(context,null);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint=new Paint();
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width=MeasureSpec.getSize(widthMeasureSpec);
        int height=MeasureSpec.getSize(heightMeasureSpec);
        rx=width/2;
        ry=height/2;
        mToalRadius= (int) Math.sqrt(rx*rx+ry*ry);
        bigRadius=width/5;
        smallRadius=bigRadius/8;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mAnimState==null) {
            mAnimState = new RotationState();
        }
        mAnimState.draw(canvas);
    }


    class RotationState implements AnimState{

        public RotationState() {
            ValueAnimator animator=ValueAnimator.ofFloat(0f,(float) (2*Math.PI));
            animator.setDuration(2000);
            animator.setInterpolator(new LinearInterpolator());
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mCurrentPre= (float) animation.getAnimatedValue();
                    invalidate();
                }
            });
            animator.start();
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mAnimState=new ScaleAnim();
                }
            });
        }

        @Override
        public void draw(Canvas canvas) {
            canvas.drawColor(Color.WHITE);
            float pre= (float) (2*Math.PI/8);
            for (int i=0;i<8;i++){
                mPaint.setColor(colors[i]);
                float angle=i*pre+mCurrentPre;
                canvas.drawCircle((float) (rx+bigRadius*Math.cos(angle)),(float) (ry+bigRadius*Math.sin(angle)),smallRadius,mPaint);
            }}
        }
    class ScaleAnim implements AnimState {

        public ScaleAnim() {
            ValueAnimator animator=ValueAnimator.ofInt(bigRadius,0);
            animator.setDuration(800);
            animator.setInterpolator(new AnticipateInterpolator(6f));
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    bigRadius= (int) animation.getAnimatedValue();
                    invalidate();
                }
            });
            animator.start();
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mAnimState=new ExpendState();
                }
            });
        }

        @Override
        public void draw(Canvas canvas) {
            canvas.drawColor(Color.WHITE);
            float pre= (float) (2*Math.PI/8);
            for (int i=0;i<8;i++){
                mPaint.setColor(colors[i]);
                float angle=i*pre+mCurrentPre;
                canvas.drawCircle((float) (rx+bigRadius*Math.cos(angle)),(float) (ry+bigRadius*Math.sin(angle)),smallRadius,mPaint);
            }}
        }


    class ExpendState implements  AnimState{

        public ExpendState() {
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setColor(Color.WHITE);
            mPaint.setStrokeWidth(20);
            ValueAnimator animator=ValueAnimator.ofInt(0,mToalRadius);
            animator.setDuration(2000);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    bigRadius= (int) animation.getAnimatedValue();
                    invalidate();
                }
            });
            animator.start();
        }

        @Override
        public void draw(Canvas canvas) {
            int strokeWidth=mToalRadius-bigRadius;
            mPaint.setStrokeWidth(strokeWidth);
            float realradiu=strokeWidth/2+bigRadius;
            Log.d("realradiu","realradiu="+realradiu);
            canvas.drawCircle(rx,ry,realradiu,mPaint);
        }
    }

    interface  AnimState{
        void draw(Canvas canvas);
    }


}
