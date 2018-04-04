package com.weiyue.lovecustomlayout;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.ActionBarOverlayLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.Random;

/**
 * Created by 姬玉鹏 on 2018/3/31.
 */

public class LoveLayout extends RelativeLayout {


    private int mLayoutWidth,mLayoutHeight;

    private int mIvWidth,mIvHeight;

    private Random mRandom;

    private Interpolator[] mInterpolators;

    public LoveLayout(Context context) {
        this(context,null);
    }

    public LoveLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LoveLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mRandom=new Random();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mLayoutWidth=MeasureSpec.getSize(widthMeasureSpec);
        mLayoutHeight=MeasureSpec.getSize(heightMeasureSpec);
        Drawable drawable= ContextCompat.getDrawable(getContext(),R.drawable.ic_launcher_round);
        mIvWidth=drawable.getIntrinsicWidth();
        mIvHeight=drawable.getIntrinsicHeight();
        mInterpolators=new Interpolator[]{new AccelerateDecelerateInterpolator(),new AccelerateInterpolator(),new DecelerateInterpolator(),new LinearInterpolator()};
    }

    public void addLove(){
        ImageView loveIv=new ImageView(getContext());
        loveIv.setImageResource(R.drawable.ic_launcher_round);
        RelativeLayout.LayoutParams params=new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(CENTER_HORIZONTAL);
        params.addRule(ALIGN_PARENT_BOTTOM);
        loveIv.setLayoutParams(params);
        addView(loveIv);
        startAnim(loveIv);
    }

    private void startAnim(final ImageView loveIv) {
        AnimatorSet allAnimSet=new AnimatorSet();
        AnimatorSet innerSet=new AnimatorSet();
        ObjectAnimator  alphaAnim=ObjectAnimator.ofFloat(loveIv,"alpha",0.3f,1.0f);
        ObjectAnimator  scaleXAnim=ObjectAnimator.ofFloat(loveIv,"scaleX",0.3f,1.0f);
        ObjectAnimator  scaleYAnim=ObjectAnimator.ofFloat(loveIv,"scaleY",0.3f,1.0f);
        innerSet.playTogether(alphaAnim,scaleXAnim,scaleYAnim);
        innerSet.setDuration(500);
        allAnimSet.playSequentially(innerSet,getBeiZerAnim(loveIv));
        allAnimSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                removeView(loveIv);
            }
        });
        allAnimSet.start();
    }

    private Animator getBeiZerAnim(final ImageView loveIv) {
        PointF p0=new PointF(mLayoutWidth/2-mIvWidth/2,mLayoutHeight-mIvHeight);
        PointF p1=getPointF(2);
        PointF p2=getPointF(1);
        PointF p3=new PointF(mRandom.nextInt(mLayoutWidth-mIvWidth),0);
        LoveTypeEvaluator typeEvaluator=new LoveTypeEvaluator(p1,p2);
        ValueAnimator animator=ObjectAnimator.ofObject(typeEvaluator,p0,p3);
        animator.setDuration(4000);
        animator.setInterpolator(mInterpolators[mRandom.nextInt(mInterpolators.length-1)]);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                PointF pointF= (PointF) valueAnimator.getAnimatedValue();
                loveIv.setX(pointF.x);
                loveIv.setY(pointF.y);
                float t=valueAnimator.getAnimatedFraction();
                loveIv.setAlpha(1-t+0.2f);
            }
        });
        return animator;
    }

    private PointF getPointF(int index) {
        int y=mRandom.nextInt(mLayoutHeight/2)+(index-1)*(mLayoutHeight/2);
        Log.d("lalala","index="+index+"  y="+y);
        return new PointF(mRandom.nextInt(mLayoutWidth)-mIvWidth,y);
    }

}
