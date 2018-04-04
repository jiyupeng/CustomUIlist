package mode.com.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by 姬玉鹏 on 2018/3/6.
 */

public class ShapeView extends View {

    int position=1;
    private Paint mPaint;
    private Path mPath;

    public ShapeView(Context context) {
        this(context,null);
    }

    public ShapeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ShapeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint=new Paint();
        mPaint.setStyle(Paint.Style.FILL);
    }

    public void exchangeShape(){
        position++;
        if (position==4)position=1;
        invalidate();
    }

    public int getPosition(){
        return position;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        switch (position){
            case 1:
                int center=getWidth()/2;
                mPaint.setColor(Color.BLACK);
                canvas.drawCircle(center,center,center,mPaint);
                break;
            case 2:
                mPaint.setColor(Color.YELLOW);
                canvas.drawRect(new RectF(0,0,getWidth(),getHeight()),mPaint);
                break;
            case 3:
                mPaint.setColor(Color.BLUE);
                if (mPath==null){
                    mPath=new Path();
                    mPath.moveTo(getWidth()/2,0);
                    mPath.lineTo(0,(float)((getWidth()/2)*Math.sqrt(3)));
                    mPath.lineTo(getWidth(),(float)((getWidth()/2)*Math.sqrt(3)));
                    mPath.close();
                }
                canvas.drawPath(mPath,mPaint);
                break;
        }
    }
}
