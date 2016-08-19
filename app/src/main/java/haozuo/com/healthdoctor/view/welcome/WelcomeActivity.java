package haozuo.com.healthdoctor.view.welcome;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.WindowManager;

import java.util.Timer;
import java.util.TimerTask;

import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.view.base.BaseActivity;
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
                startActivity(new Intent(getBaseContext(), LoginActivity.class));
                finish();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 2000);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
        }
        return super.onKeyDown(keyCode, event);
    }
}
