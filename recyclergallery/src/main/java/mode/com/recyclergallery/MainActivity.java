package mode.com.recyclergallery;

import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    MyAdapter myAdapter;
    ScrollManager scrollManager;
    private static final String KEY_PRE_DRAW = "key_pre_draw";
    private Map<String, Drawable> mTSDraCacheMap = new HashMap<>();
    private ConstraintLayout contain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.recyclerView);
        contain=findViewById(R.id.contain);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        myAdapter=new MyAdapter(getData(),getApplicationContext());
        recyclerView.addItemDecoration(new MyItemDecoration());
        recyclerView.setAdapter(myAdapter);
        scrollManager=new ScrollManager(recyclerView);
        scrollManager.initSnapHelper(1);
        scrollManager.initScrollListener();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    setBlurImage();
                }
            }
        });
        setBlurImage();
    }

    private void setBlurImage() {
        final MyAdapter myAdapter= (MyAdapter) recyclerView.getAdapter();
        if(myAdapter==null)return;
        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                int resID=myAdapter.getResId(scrollManager.getPosition());
                Bitmap resBitmap= BitmapFactory.decodeResource(getResources(),resID);
                Bitmap endBitmap=BlurBitmapUtil.blurBitmap(resBitmap,15f,MainActivity.this);
                Drawable  resDrawable=new BitmapDrawable(endBitmap);
                // 获取前一页的Drawable
                Drawable preBlurDrawable = mTSDraCacheMap.get(KEY_PRE_DRAW) == null ? resDrawable : mTSDraCacheMap.get(KEY_PRE_DRAW);

                /* 以下为淡入淡出效果 */
                Drawable[] drawableArr = {preBlurDrawable, resDrawable};
                TransitionDrawable transitionDrawable = new TransitionDrawable(drawableArr);
                contain.setBackgroundDrawable(transitionDrawable);
                transitionDrawable.startTransition(500);
                // 存入到cache中
                mTSDraCacheMap.put(KEY_PRE_DRAW, resDrawable);
            }
        });
    }

    public List<Integer> getData(){
        TypedArray typedArray=getResources().obtainTypedArray(R.array.image_array);
        final int[] resIds=new int[typedArray.length()];
        for (int i = 0; i < typedArray.length(); i++) {
            resIds[i] = typedArray.getResourceId(i, 0);
        }
        typedArray.recycle();
        List<Integer> tDatas = new ArrayList<>();
        for (int resId : resIds) {
            tDatas.add(resId);
        }
        return tDatas;

    }


}
