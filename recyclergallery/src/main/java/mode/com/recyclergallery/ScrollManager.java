package mode.com.recyclergallery;

import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

/**
 * Created by 姬玉鹏 on 2018/1/2.
 */

public class ScrollManager {

    private RecyclerView mRecyclerView;
    private LinearSnapHelper mLinearySnapHelper;
    private PagerSnapHelper mPagerSnapHelper;

    private int mPosition = 0;

    // 使偏移量为左边距 + 左边Item的可视部分宽度
    private int mConsumeX = 0;
    private int mConsumeY = 0;
    // 滑动方向
    private int slideDirct = SLIDE_RIGHT;

    private static final int SLIDE_LEFT = 1;    // 左滑
    private static final int SLIDE_RIGHT = 2;   // 右滑
    private static final int SLIDE_TOP = 3;     // 上滑
    private static final int SLIDE_BOTTOM = 4;  // 下滑

    public ScrollManager(RecyclerView mRecyclerView) {
        this.mRecyclerView = mRecyclerView;
    }

    /**
     * 初始化SnapHelper
     *
     * @param helper
     */
    public void initSnapHelper(int helper) {
        switch (helper) {
            case 1:
                mLinearySnapHelper = new LinearSnapHelper();
                mLinearySnapHelper.attachToRecyclerView(mRecyclerView);
                break;
            case 2:
                mPagerSnapHelper = new PagerSnapHelper();
                mPagerSnapHelper.attachToRecyclerView(mRecyclerView);
                break;
        }
    }
    /**
     * 监听RecyclerView的滑动
     */
    public void initScrollListener() {
        CustomRecyclerViewScrollListener mScrollerListener = new CustomRecyclerViewScrollListener();
        mRecyclerView.addOnScrollListener(mScrollerListener);
    }

    public void updateComsume() {
        mConsumeX += OsUtil.dpToPx(MyItemDecoration.mLeftPageVisibleWidth + MyItemDecoration.mPageMargin * 2);
        mConsumeY += OsUtil.dpToPx(MyItemDecoration.mLeftPageVisibleWidth + MyItemDecoration.mPageMargin * 2);

    }
    class CustomRecyclerViewScrollListener extends RecyclerView.OnScrollListener{

        @Override
        public void onScrolled(final RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            mConsumeX+=dx;
            if (dx > 0) {
                // 右滑
                slideDirct = SLIDE_RIGHT;
            } else {
                // 左滑
                slideDirct = SLIDE_LEFT;
            }
            recyclerView.post(new Runnable() {
                @Override
                public void run() {
                    int shouldConsumeX=MyItemDecoration.mItemComusemx;
                    int position=getPosition(mConsumeX,shouldConsumeX);
                    float offset = (float) mConsumeX / (float) shouldConsumeX;     // 位置浮点值（即总消耗距离 / 每一页理论消耗距离 = 一个浮点型的位置值）
                   // Log.d("TAG", "offset=" + offset + "; mConsumeX=" + mConsumeX + "; shouldConsumeX=" + shouldConsumeX);
                    // 避免offset值取整时进一，从而影响了percent值

                    // 获取当前页移动的百分值
                    float percent = offset - ((int) offset);
                    Log.d("TAG","percent="+percent);
                    // 设置动画变化
                    AnimManager.getInstance().setAnimation(recyclerView, position, percent);
                }
            });


        }
    }
    /**
     * 获取位置
     *
     * @param mConsumeX      实际消耗距离
     * @param shouldConsumeX 理论消耗距离
     * @return
     */
    private int getPosition(int mConsumeX, int shouldConsumeX) {
        float offsetX=(float) mConsumeX/(float) shouldConsumeX;
        int position=Math.round(offsetX);
        mPosition=position;
        return  position;
    }

    public int getPosition() {
        return mPosition;
    }
}
