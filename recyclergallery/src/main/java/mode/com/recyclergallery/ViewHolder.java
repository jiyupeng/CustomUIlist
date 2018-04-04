package mode.com.recyclergallery;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

/**
 * Created by 姬玉鹏 on 2018/3/23.
 */

public class ViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> mViews;

    public ViewHolder(View itemView) {
        super(itemView);
        mViews=new SparseArray<>();
    }

    public ViewHolder setText(int viewID,CharSequence text){
        TextView textView=getView(viewID);
        textView.setText(text);
        return this;
    }

    public <T extends View> T getView(int viewID) {
        View view = mViews.get(viewID);
        if (view == null) {
            view = itemView.findViewById(viewID);
            mViews.put(viewID, view);
        }
        return (T) view;
    }

}
