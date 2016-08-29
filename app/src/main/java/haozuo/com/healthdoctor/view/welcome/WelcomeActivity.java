package haozuo.com.healthdoctor.view.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;

import com.umeng.analytics.MobclickAgent;

import java.util.Timer;
import java.util.TimerTask;

import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.framework.SysConfig;
import haozuo.com.healthdoctor.manager.GroupInfoManager;
import haozuo.com.healthdoctor.manager.UserManager;
import haozuo.com.healthdoctor.view.base.BaseActivity;
import haozuo.com.healthdoctor.view.custom.GroupCustomListActivity;
import haozuo.com.healthdoctor.view.home.HomeActivity;
import haozuo.com.healthdoctor.view.login.LoginActivity;

public class WelcomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setTranslucentStatus(0);
        setContentView(R.layout.activity_welcome);
        TimerTask task = new TimerTask() {
            public void run() {

                if (UserManager.getInstance().exist()){
                    startActivity(new Intent(getBaseContext(), HomeActivity.class));
                    finish();
                }else {
                    startActivity(new Intent(getBaseContext(), LoginActivity.class));
                    finish();
                }


            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 2000);
        initUmeng();
    }

    private void initUmeng() {
        MobclickAgent.setDebugMode(SysConfig.DebugMode);
        // SDK在统计Fragment时，需要关闭Activity自带的页面统计，
        // 然后在每个页面中重新集成页面统计的代码(包括调用了 onResume 和 onPause 的Activity)。
//        MobclickAgent.openActivityDurationTrack(false);
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);

//        Android统计SDK从V4.6版本开始内建错误统计，不需要开发者再手动集成。
//        SDK通过Thread.UncaughtExceptionHandler  捕获程序崩溃日志，并在程序下次启动时发送到服务器。 如不需要错误统计功能，可通过此方法关闭
//        MobclickAgent.setCatchUncaughtExceptions(false);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
        }
        return super.onKeyDown(keyCode, event);
    }
}
