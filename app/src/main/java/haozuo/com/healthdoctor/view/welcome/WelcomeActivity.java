package haozuo.com.healthdoctor.view.welcome;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import java.util.Timer;
import java.util.TimerTask;

import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.manager.UserManager;
import haozuo.com.healthdoctor.view.base.BaseActivity;
import haozuo.com.healthdoctor.view.group.GroupActivity;
import haozuo.com.healthdoctor.view.login.LoginActivity;

public class WelcomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome);
        boolean isLogin=checkLogin();
        if(!isLogin){
            return;
        }

        TimerTask task = new TimerTask()
        {
            public void run()
            {
                startActivity(new Intent(getBaseContext(),GroupActivity.class));
                finish();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task,2000);
    }

    boolean checkLogin() {
        if (!UserManager.getInstance().exist()) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
            return false;
        }
        return true;
    }
}
