package haozuo.com.healthdoctor.view.welcome;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.WindowManager;

import java.util.Timer;
import java.util.TimerTask;

import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.manager.UserManager;
import haozuo.com.healthdoctor.util.StringUtil;
import haozuo.com.healthdoctor.util.UHealthUtils;
import haozuo.com.healthdoctor.view.base.BaseActivity;
import haozuo.com.healthdoctor.view.home.HomeActivity;
import haozuo.com.healthdoctor.view.login.LoginActivity;

public class WelcomeActivity extends BaseActivity {
    private final long turnTimeDelay = 2000;
    private static long lastTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setTranslucentStatus(0);
        setContentView(R.layout.activity_welcome);
        TimerTask task = new TimerTask() {
            public void run() {
                boolean exist = UserManager.getInstance().exist();
                if (exist) {
                    startActivity(new Intent(getBaseContext(), HomeActivity.class));
                } else {
                    startActivity(new Intent(getBaseContext(), LoginActivity.class));
                }

                finish();
            }
        };
        lastTime = System.currentTimeMillis();
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent intent = new Intent();
//                intent.setAction("android.intent.action.VIEW");
//                Uri url = Uri.parse("http://sj.qq.com/myapp/detail.htm?apkName=com.todayonhistory.toh");
//                intent.setData(url);
//                startActivity(intent);
//            }
//        }, 2500);
        //getUpdateTime(task);
        new Timer().schedule(task, turnTimeDelay);
    }


    private void getUpdateTime(final TimerTask task) {
        new Handler().postDelayed(new Runnable() {
            private String notifycation = "asdf";
            private boolean isValid = true;
            private int lastVersion = 11;

            @Override
            public void run() {
                int currVersion = UHealthUtils
                        .getCurrVersion(WelcomeActivity.this);
                final Timer timer = new Timer();
                if (currVersion >= lastVersion) {//TODO 不需要更新
                    long time = System.currentTimeMillis();
                    long requestTime = time - lastTime;
                    if (requestTime < turnTimeDelay) {
                        timer.schedule(task, turnTimeDelay - requestTime);
                    } else {
                        timer.schedule(task, 0);
                    }
                } else {//TODO 需要更新
                    if (isValid) {//TODO 还能用
                        if (StringUtil.isTrimEmpty(notifycation)) {
                            long time = System.currentTimeMillis();
                            long requestTime = time - lastTime;
                            if (requestTime < turnTimeDelay) {
                                timer.schedule(task, turnTimeDelay - requestTime);
                            } else {
                                timer.schedule(task, 0);
                            }
                        } else {
                            AlertDialog.Builder dialog = new AlertDialog.Builder(WelcomeActivity.this);
                            dialog.setTitle("检测到更新")
                                    .setCancelable(false)
                                    .setMessage(notifycation)
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            timer.schedule(task, 0);
                                        }
                                    }).show();
                        }

                    } else {//TODO 不能用
                        AlertDialog.Builder dialog = new AlertDialog.Builder(WelcomeActivity.this);
                        dialog.setTitle("检测到更新，当前版本不再维护")
                                .setCancelable(false)
                                .setMessage(notifycation)
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //TODO 跳AppStore，没有就关闭
                                        if (!UHealthUtils.turnStore(WelcomeActivity.this)) {
                                            WelcomeActivity.this.finish();
                                        }
                                    }
                                }).show();
                    }
                }
            }
        }, 5000);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }


}
