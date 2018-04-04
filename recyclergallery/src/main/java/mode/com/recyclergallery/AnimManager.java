package mode.com.recyclergallery;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

/**
 * Created by 姬玉鹏 on 2018/1/2.
 */

public class AnimManager {

    private static AnimManager INSTANCE;

    public static final int ANIM_BOTTOM_TO_TOP = 0;
    public static final int ANIM_TOP_TO_BOTTOM = 1;

    private int mAnimType = ANIM_BOTTOM_TO_TOP; //动画类型
    private float mAnimFactor = 0.2f;   //变化因子

    public static AnimManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AnimManager();
        }
        return INSTANCE;
    }

    public void setAnimation(RecyclerView recyclerView,int position,float percent){
        switch (mAnimType) {
            case ANIM_BOTTOM_TO_TOP:
                setBottomToTopAnim(recyclerView, position, percent);
                break;
            case ANIM_TOP_TO_BOTTOM:
                setTopToBottomAnim(recyclerView, position, percent);
                break;
            default:
                setBottomToTopAnim(recyclerView, position, percent);
                break;
        }

    }

    private void setTopToBottomAnim(RecyclerView recyclerView, int position, float percent) {

    }
    /**
     * 从下到上的动画效果
     *
     * @param recyclerView
     * @param position
     * @param percent
     */
    private void setBottomToTopAnim(RecyclerView recyclerView, int position, float percent) {
        View mCurItem=recyclerView.getLayoutManager().findViewByPosition(position);
        View mLeftItem=recyclerView.getLayoutManager().findViewByPosition(position-1);
        View mRightItem=recyclerView.getLayoutManager().findViewByPosition(position+1);
        if(percent<=0.5){
          if(mLeftItem!=null){
              Log.d("TAG", "mLeftView=" + ((1 - mAnimFactor) + percent * mAnimFactor) + "; position=" + position);
              mLeftItem.setScaleX((1 - mAnimFactor) + percent * mAnimFactor);
              mLeftItem.setScaleY((1 - mAnimFactor) + percent * mAnimFactor);
          }
            if(mCurItem!=null){
               // Log.d("TAG", "mLeftView=" + ((1 - mAnimFactor) + percent * mAnimFactor) + "; position=" + position);
                mCurItem.setScaleX(1 - percent * mAnimFactor);
                mCurItem.setScaleY(1 - percent * mAnimFactor);
            }
            if (mRightItem != null) {
             //  Log.d("TAG", "mRightView=" + ((1 - mAnimFactor) + percent * mAnimFactor) + "; position=" + position);
                mRightItem.setScaleX((1 - mAnimFactor) + percent * mAnimFactor);
                mRightItem.setScaleY((1 - mAnimFactor) + percent * mAnimFactor);
            }
        }else {
            if (mLeftItem != null) {
                Log.d("TAG", "mLeftView=" + (1 - percent * mAnimFactor) + "; position=" + position);
                mLeftItem.setScaleX(1 - percent * mAnimFactor);
                mLeftItem.setScaleY(1 - percent * mAnimFactor);
            }
            if (mCurItem != null) {
              //  Log.d("TAG", "mCurView=" + ((1 - mAnimFactor) + percent * mAnimFactor) + "; position=" + position);
                mCurItem.setScaleX((1 - mAnimFactor) + percent * mAnimFactor);
                mCurItem.setScaleY((1 - mAnimFactor) + percent * mAnimFactor);
            }
            if (mRightItem != null) {
             //   Log.d("TAG", "mRightView=" + (1 - percent * mAnimFactor) + "; position=" + position);
                mRightItem.setScaleX(1 - percent * mAnimFactor);
                mRightItem.setScaleY(1 - percent * mAnimFactor);
            }
        }
    }

    public void setmAnimFactor(float mAnimFactor) {
        this.mAnimFactor = mAnimFactor;
    }

    public void setmAnimType(int mAnimType) {
        this.mAnimType = mAnimType;
    }

}
