package com.weiyue.lovecustomlayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private LoveLayout mLoveLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLoveLayout=findViewById(R.id.love_layout);
    }

    public void addLove(View v){
            mLoveLayout.addLove();

    }

}
