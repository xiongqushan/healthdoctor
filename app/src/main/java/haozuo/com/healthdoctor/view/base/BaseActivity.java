package haozuo.com.healthdoctor.view.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
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
import haozuo.com.healthdoctor.framework.HZApplication;
import haozuo.com.healthdoctor.ioc.AppComponent;
import haozuo.com.healthdoctor.util.SystemBarTintUtil;
import haozuo.com.healthdoctor.view.consult.ConsultActivity;
import haozuo.com.healthdoctor.view.group.GroupActivity;

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

    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.to_right_in, R.anim.to_right_out);
    }

//    public void finishThis() {
//        this.finish();
//        overridePendingTransition(R.anim.to_right_in, R.anim.to_right_out);
//    }

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
        switch(getLocalClassName()){
            case "view.group.GroupActivity":
                RadioButton btnRbChat = (RadioButton) findViewById(R.id.rbChat);
                btnRbChat.setChecked(true);
                break;
            case "view.consult.ConsultActivity":
                RadioButton btnRbAddress = (RadioButton) findViewById(R.id.rbAddress);
                btnRbAddress.setChecked(true);
                break;
            case "":
                Toast.makeText(getApplicationContext(),"333",Toast.LENGTH_SHORT).show();
                break;
        }
        tab_menu.setOnCheckedChangeListener(new OnNavChangeListener());
    }

    protected AppComponent getAppComponent(){
        return ((HZApplication)getApplication()).getAppComponent();
    }

    private class OnNavChangeListener implements RadioGroup.OnCheckedChangeListener{

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId){
                case R.id.rbChat:
                    startActivity(new Intent(getBaseContext(),GroupActivity.class));
                    finish();
                    break;
                case R.id.rbAddress:
                    startActivity(new Intent(getBaseContext(),ConsultActivity.class));
                    finish();
                    break;
                case R.id.rbFind:
                    Toast.makeText(getBaseContext(),"333",Toast.LENGTH_SHORT).show();
                    //do sth
                    break;
            }
        }
    }

}