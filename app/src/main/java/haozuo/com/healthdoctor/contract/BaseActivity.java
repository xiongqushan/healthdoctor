package haozuo.com.healthdoctor.contract;

import android.annotation.TargetApi;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import butterknife.Bind;
import butterknife.OnCheckedChanged;
import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.util.SystemBarTintUtil;

/**
 * Created by xiongwei1 on 2016/7/8.
 */
public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTranslucentStatus();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @TargetApi(19)
    private void setTranslucentStatus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window win = getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            winParams.flags |= bits;
            win.setAttributes(winParams);
        }
        SystemBarTintUtil tintManager = new SystemBarTintUtil(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.color.main_color_blue);//状态栏无背景
    }

    protected void setCustomerTitle(String title){
        TextView textView=(TextView)findViewById(R.id.txt_TitleBar_title);
        textView.setText(title);
    }

    protected void initTabhostMenu(){
        RadioGroup tab_menu = (RadioGroup) findViewById(R.id.tab_menu);
        tab_menu.setOnCheckedChangeListener(new OnNavChangeListener());
    }

    private class OnNavChangeListener implements RadioGroup.OnCheckedChangeListener{

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId){
                case R.id.rbChat:
                    Toast.makeText(getApplicationContext(),"111",Toast.LENGTH_SHORT).show();
                    //do sth
                    break;
                case R.id.rbAddress:
                    Toast.makeText(getApplicationContext(),"222",Toast.LENGTH_SHORT).show();
                    //do sth
                    break;
                case R.id.rbFind:
                    Toast.makeText(getApplicationContext(),"333",Toast.LENGTH_SHORT).show();
                    //do sth
                    break;
            }
        }
    }

}