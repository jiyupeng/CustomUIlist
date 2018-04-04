package mode.com.recyclergallery;

import android.content.res.Resources;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by 姬玉鹏 on 2018/1/2.
 */

public class MyItemDecoration extends RecyclerView.ItemDecoration {

    public static int mPageMargin=0; //页面默认的页边距
    public static int mLeftPageVisibleWidth=50;//左右两边可以显示的宽度
    public static int mItemComusemx=0;
    public static int mItemComusemy=0;

    @Override
    public void getItemOffsets(Rect outRect, final View view, final RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        final int position = parent.getChildAdapterPosition(view);
        final int itemCount = parent.getAdapter().getItemCount();
        parent.post(new Runnable() {
            @Override
            public void run() {
                //动态修改view的参数
                int itemNewWidth=parent.getWidth()-dpToPx(4 * mPageMargin + 2 * mLeftPageVisibleWidth);
                //一页的理论消耗距离
                mItemComusemx=itemNewWidth+OsUtil.dpToPx(2*mPageMargin);
                // 第0页和最后一页没有左页面和右页面，让他们保持左边距和右边距和其他项一样
                int leftMargin=position==0?dpToPx(mLeftPageVisibleWidth + 2 * mPageMargin) : dpToPx(mPageMargin);
                int rightMargin = position == itemCount - 1 ? dpToPx(mLeftPageVisibleWidth + 2 * mPageMargin) : dpToPx(mPageMargin);
                RecyclerView.LayoutParams params= (RecyclerView.LayoutParams) view.getLayoutParams();
                params.setMargins(leftMargin,0,rightMargin,0);
                params.width=itemNewWidth;
                params.height=parent.getHeight();
                view.setLayoutParams(params);
            }
        });

    }

    public int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density + 0.5f);
    }

}
