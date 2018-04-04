package mode.com.myapplication;

import android.content.res.Resources;
import android.graphics.Paint;
import android.support.v4.media.RatingCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TwoActivity extends AppCompatActivity {

    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);
        tv=findViewById(R.id.tv);
        WeekDays weekDays=WeekDays.Mon;
        weekDays.next();
        for (WeekDays a:WeekDays.values()){
            Log.d("tag","weekday="+a);
        }
    }

    public void onClick(View view){
        tv.setText("20");
    }

}
