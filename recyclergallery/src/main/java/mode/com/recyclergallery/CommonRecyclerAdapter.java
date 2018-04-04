package mode.com.recyclergallery;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by 姬玉鹏 on 2018/3/23.
 */

public  abstract   class CommonRecyclerAdapter<T> extends RecyclerView.Adapter<ViewHolder>{

    protected  int layoutID;
    protected Context mContext;
    protected List<T> mDatas;
    protected LayoutInflater layoutInflater;

    public CommonRecyclerAdapter(int layoutID, Context mContext, List<T> mDatas) {
        this.layoutID = layoutID;
        this.mContext = mContext;
        layoutInflater=LayoutInflater.from(mContext);
        this.mDatas = mDatas;
    }


    protected abstract void convert(ViewHolder holder, T t);

    @Override
    public int getItemCount() {
        return mDatas.size();
    }


}
