package haozuo.com.healthdoctor.view.Activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Parcel;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import haozuo.com.healthdoctor.R;

public class CustomDetailActivity extends BaseActivity {

    @Bind(R.id.customerName)TextView customerName;
    @Bind(R.id.customerImg) ImageView customerImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_detail);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        int customerId = bundle.getInt("CustomerId");
        String Cname = bundle.getString("CustomerName");
        String Nickname = bundle.getString("CustomerNickname");
        //String Cphoto = bundle.getString("CustomerPhoto");
        String Cphoto = "http://pic002.cnblogs.com/images/2011/103608/2011062022023456.jpg";
        Uri uri = Uri.parse(Cphoto);
        customerImg.setImageURI(uri);

//        Bitmap bitmap = getHttpBitmap(Cphoto);
//        customerImg.setImageBitmap(bitmap);
        customerName.setText(Cname+"("+Nickname+")");
    }




    @Override
    protected void onReceiveBroadcast(String filterAction) {

    }



    public static Bitmap getHttpBitmap(String url){
        URL myFileURL;
        Bitmap bitmap=null;
        try{
            myFileURL = new URL(url);
            //获得连接
            HttpURLConnection conn=(HttpURLConnection)myFileURL.openConnection();
            //设置超时时间为6000毫秒，conn.setConnectionTiem(0);表示没有时间限制
            conn.setConnectTimeout(6000);
            //连接设置获得数据流
            conn.setDoInput(true);
            //不使用缓存
            conn.setUseCaches(false);
            //这句可有可无，没有影响
            conn.connect();
            //得到数据流
            InputStream is = conn.getInputStream();
            //解析得到图片
            bitmap = BitmapFactory.decodeStream(is);
            //关闭数据流
            is.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return bitmap;
    }



}


