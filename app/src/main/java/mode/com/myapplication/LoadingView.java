package mode.com.myapplication;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.shapes.Shape;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by 姬玉鹏 on 2018/3/6.
 */

public class LoadingView extends LinearLayout {

    int ANIM_DURATION=500;

    ShapeView shape;
    TextView shadom;
    boolean isDestroy=false;

    public LoadingView(Context context) {
        this(context,null);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        inflate(context,R.layout.item,this);
        shape=findViewById(R.id.shape);
        shadom=findViewById(R.id.shadom);
        startFullAnim();
    }

    public void startFullAnim(){
        if (isDestroy)return;
        ObjectAnimator fullAnim=ObjectAnimator.ofFloat(shape,"translationY",0,dip2px(80));
        fullAnim.setDuration(ANIM_DURATION);
        fullAnim.setInterpolator(new AccelerateInterpolator());
        ObjectAnimator shadomAnim=ObjectAnimator.ofFloat(shadom,"scaleX",1f,0.2f);
        shadomAnim.setDuration(ANIM_DURATION);
        AnimatorSet set=new AnimatorSet();
        set.playTogether(fullAnim,shadomAnim);
        set.start();
        set.addListener(new AnimatorListenerAdapter(){
            @Override
            public void onAnimationEnd(Animator animation) {
                shape.exchangeShape();
                startUpAnim();
            }
        });
    }

    private void startUpAnim() {
        if (isDestroy)return;
        ObjectAnimator upAnim=ObjectAnimator.ofFloat(shape,"translationY",dip2px(80),0);
        upAnim.setDuration(ANIM_DURATION);
        upAnim.setInterpolator(new DecelerateInterpolator());
        ObjectAnimator shadomAnim=ObjectAnimator.ofFloat(shadom,"scaleX",0.2f,1f);
        shadomAnim.setDuration(ANIM_DURATION);
        AnimatorSet set=new AnimatorSet();
        set.playTogether(upAnim,shadomAnim);
        set.addListener(new AnimatorListenerAdapter(){
            @Override
            public void onAnimationEnd(Animator animation) {
                startFullAnim();
            }

            @Override
            public void onAnimationStart(Animator animation) {
                startRotation();
            }
        });
        set.start();
    }

    private void startRotation() {
        ObjectAnimator animator=null;
        switch (shape.getPosition()){
            case 1:
                break;
            case 2:
                animator=ObjectAnimator.ofFloat(shape,"rotation",0,120);
                break;
            case 3:
                animator=ObjectAnimator.ofFloat(shape,"rotation",0,100);
                break;
        }
        if (animator!=null){
            animator.setDuration(ANIM_DURATION);
            animator.start();
        }
    }


    private int dip2px(int dip){
        return  (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dip,getResources().getDisplayMetrics());
    }

    public void stopAnim(){
        shape.clearAnimation();
        shadom.clearAnimation();
        isDestroy=true;
        ViewGroup parent= (ViewGroup) getParent();
        if(parent!=null){
            parent.removeView(this);//从父布局中移除掉自身。
            removeAllViews();//移除掉自己内部的所有view。
        }
    }


}
