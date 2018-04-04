package mode.com.myapplication;

import android.app.ActionBar;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String url="http://10.254.245.100:8050/AAA/aaa?ptype=8&plocation=7000&puser=00e400104697&ptoken=64eff9664082581f3cd06b63&pversion=&pserialNumber=20510217280004909&hmac=&timestamp=&nonce=&authType=0&secondAuthid=&t=64eff9664082581f3cd06b63&u=00e400104697&p=8&cid=&l=7000&d=10075398&v=2&proid=100000&errorcode=0&sid=hrYNreuzZYyL88kjjqWekg%3D%3D&reqtime=20171211155836&expiredtime=20171216155836&nonce=vXYuatScyyKH&acl=0&errorReason=0&previewduration=60&n=";

    private LoadingView loadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadingView=findViewById(R.id.load);
        loadingView.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadingView.stopAnim();
            }
        },5000);
       /* String[] str1=url.split("&");
        String str2=str1[20];
        String str3=str2.substring(4);
        Log.d("str2",""+str2);
        Log.d("str2","str3="+str3);*/

        //Edited by mythou
//http://www.cnblogs.com/mythou/
//要转换的地址或字符串,可以是中文
       // Bitmap bitmap=RQcode.getRQcode("其实我爱你");
      //  iv.setImageBitmap(bitmap);
       // tostart(10);
        //createQRImage("weixin://wxpay/bizpayurl?pr=WcW9v3j");
    }

    /**
     * 利用反射获取状态栏高度
     * @return
     */
    public int getStatusBarHeight() {
        int result = 0;
        //获取状态栏高度的资源id
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


    private void tostart(int i) {
        List<String> items=new ArrayList<>();
        int start=1, end=12,count=i;
        while (count-12>0){
            items.add("第"+start+"-"+end+"集");
            start+=12;
            end+=12;
            count-=12;
        }
        items.add("第"+start+"-"+i+"集");
        for (String item : items) {
            Log.d("tag",item);
        }

    }

    private int QR_WIDTH = 200, QR_HEIGHT = 200;
    public void createQRImage(String url) {
        try {
            //判断URL合法性
            if (url == null || "".equals(url) || url.length() < 1) {
                return;
            }
            Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            //图像数据转换，使用了矩阵转换
            BitMatrix bitMatrix = new QRCodeWriter().encode(url, BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT, hints);
            int[] pixels = new int[QR_WIDTH * QR_HEIGHT];
            //下面这里按照二维码的算法，逐个生成二维码的图片，
            //两个for循环是图片横列扫描的结果
            for (int y = 0; y < QR_HEIGHT; y++) {
                for (int x = 0; x < QR_WIDTH; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * QR_WIDTH + x] = 0xff000000;
                    }
                    else {
                        pixels[y * QR_WIDTH + x] = 0xffffffff;
                    }
                }
            }
            //生成二维码图片的格式，使用ARGB_8888
            Bitmap bitmap = Bitmap.createBitmap(QR_WIDTH, QR_HEIGHT, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, QR_WIDTH, 0, 0, QR_WIDTH, QR_HEIGHT);
            //显示到一个ImageView上面
        }
        catch (WriterException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        Toast.makeText(this,"event="+event.getKeyCode(),Toast.LENGTH_SHORT).show();
        return false;
    }
}
