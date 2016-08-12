package haozuo.com.healthdoctor.view.base;

import android.annotation.TargetApi;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.framework.HZApplication;
import haozuo.com.healthdoctor.ioc.AppComponent;
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

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.to_right_in, R.anim.to_right_out);
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

    protected void setCustomerTitle(String title) {
        TextView textView = (TextView) findViewById(R.id.txt_TitleBar_title);
        textView.setText(title);
        findViewById(R.id.btn_go_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    protected void hideGoBackBtn() {
        findViewById(R.id.btn_go_back).setVisibility(View.GONE);
    }


    protected AppComponent getAppComponent() {
        return ((HZApplication) getApplication()).getAppComponent();
    }


}