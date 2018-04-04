package mode.com.recyclergallery;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by 姬玉鹏 on 2018/1/2.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{
    private final String TAG = "RecyclerAdapter";
    private List<Integer> mDatas;
    private Context context;

    public MyAdapter(List<Integer> mDatas, Context context) {
        this.mDatas = mDatas;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // 需要增加此代码修改每一页的宽高
        //GalleryAdapterHelper.newInstance().setItemLayoutParams(mParent, holder.itemView, position, getItemCount());
        holder.iv.setImageResource(mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        return mDatas==null?0:mDatas.size();
    }

    /**
     * 获取position位置的resId
     * @param position
     * @return
     */
    public int getResId(int position) {
        return mDatas == null ? 0 : mDatas.get(position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        public final ImageView iv;
        public MyViewHolder(View itemView) {
            super(itemView);
            iv=itemView.findViewById(R.id.iv_photo);
        }
    }

}
