package com.weiyue.redpacket;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by 姬玉鹏 on 2018/4/6.
 */

public class RedPacktView  extends View{

    private Paint mProgressPaint;

    private Bitmap mRedPack;
    private Bitmap mProgressBg;
    private float currentPosition;
    private int totalPosition;
    private int startColor;
    private int endColor;

    private Bitmap mBmobIcon[]=new Bitmap[2];

    //b爆炸的最大半径
    private float mTotalRadius=0;

    private int mBmobNum=8;
    private float mCurrentBmobProgress;

    private ValueAnimator mProgressAnimator;

    public RedPacktView(Context context) {
        this(context,null);
    }

    public RedPacktView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RedPacktView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mRedPack= BitmapFactory.decodeResource(getResources(),R.drawable.icon_game_red_package_normal);
        mProgressBg= BitmapFactory.decodeResource(getResources(),R.drawable.icon_game_red_package_pb_bg);
        mProgressPaint=new Paint();
        mProgressPaint.setAntiAlias(true);
        mProgressPaint.setDither(true);
        mProgressPaint.setColor(Color.BLUE);
        startColor=Color.parseColor("#ff0000");
        endColor=Color.parseColor("#00ff00");
        Bitmap circle=BitmapFactory.decodeResource(getResources(),R.drawable.icon_red_package_bomb_1);
        Bitmap start=BitmapFactory.decodeResource(getResources(),R.drawable.icon_red_package_bomb_2);
        mBmobIcon[0]=start;
        mBmobIcon[1]=circle;
        mTotalRadius=mProgressBg.getHeight();


    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int size= (int) (Math.max(mRedPack.getHeight(),mRedPack.getWidth())*1.1f);
        setMeasuredDimension(size ,size);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width=getWidth();
        int height=getHeight();
        //画红包
        canvas.drawBitmap(mRedPack,0,0,null);
        //画进度条的背景
        int top= (int) (mRedPack.getHeight()-mProgressBg.getHeight()*0.8f);
        int left= (width - mProgressBg.getWidth() )/2;
        canvas.drawBitmap(mProgressBg,left,top,null);
        //画进度条

        top= (int) (height*0.7f);
        left= (int) (mProgressBg.getWidth()*0.2f);
        int progressHeight= (int) (mProgressBg.getHeight()*0.3f);
        int progressWidth= (int) (mProgressBg.getWidth()*0.78f);
        int currentWidth= (int) (progressWidth*currentPosition/totalPosition);
        RectF rectF=new RectF(left,top,left+currentWidth,top+progressHeight);
        int radius=progressHeight/2;
        Shader shader=new LinearGradient(0,0,currentWidth,0,new int[]{startColor,endColor},new float[]{0,1.0f},Shader.TileMode.CLAMP);
        mProgressPaint.setShader(shader);
        canvas.drawRoundRect(rectF,radius,radius,mProgressPaint);
    }

    public void setTotalPosition(int totalPosition) {
        this.totalPosition = totalPosition;
    }

    private void setCurrentPosition(float currentPosition) {
        this.currentPosition = currentPosition;
        invalidate();
    }

    public void startProgressAnim(int from, int to){
        if (mProgressAnimator==null){
            mProgressAnimator=ValueAnimator.ofFloat(from,to);
            mProgressAnimator.setDuration(600);
            mProgressAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float value= (float) animation.getAnimatedValue();
                    setCurrentPosition(value);
                }
            });
            mProgressAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    executeBombAnim();
                }
            });
        }
        mProgressAnimator.start();

    }

    //执行扩散爆炸动画
    private void executeBombAnim() {
        float preAngle= (float) (2*Math.PI/mBmobNum);
        for (int i=0;i<mBmobNum;i++){
            double angle=i*preAngle;
        }
    }

}
