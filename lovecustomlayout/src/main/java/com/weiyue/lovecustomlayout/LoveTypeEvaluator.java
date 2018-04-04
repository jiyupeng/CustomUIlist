package com.weiyue.lovecustomlayout;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

/**
 * Created by 姬玉鹏 on 2018/3/31.
 */

public class LoveTypeEvaluator implements TypeEvaluator<PointF> {

    PointF p1;
    PointF p2;

    public LoveTypeEvaluator(PointF p1, PointF p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    @Override
    public PointF evaluate(float t, PointF p0, PointF p3) {
       PointF pointF=new PointF();
       pointF.x=p0.x*(1-t)*(1-t)*(1-t)
               +3*p1.x*t*(1-t)*(1-t)
               +3*p2.x*t*t*(1-t)
               +p3.x*t*t*t;
        pointF.y=p0.y*(1-t)*(1-t)*(1-t)
                +3*p1.y*t*(1-t)*(1-t)
                +3*p2.y*t*t*(1-t)
                +p3.y*t*t*t;
        return pointF;
    }
}
