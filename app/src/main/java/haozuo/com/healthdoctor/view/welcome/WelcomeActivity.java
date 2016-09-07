package haozuo.com.healthdoctor.view.welcome;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;

import com.umeng.analytics.MobclickAgent;

import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.bean.UpdateInfoBean;
import haozuo.com.healthdoctor.contract.WelcomeContract;
import haozuo.com.healthdoctor.ioc.DaggerWelcomeComponent;
import haozuo.com.healthdoctor.ioc.WelcomeModule;
import haozuo.com.healthdoctor.manager.UserManager;
import haozuo.com.healthdoctor.presenter.WelcomePresenter;
import haozuo.com.healthdoctor.util.StringUtil;
import haozuo.com.healthdoctor.util.UHealthUtils;
import haozuo.com.healthdoctor.view.base.BaseActivity;
import haozuo.com.healthdoctor.view.home.HomeActivity;
import haozuo.com.healthdoctor.view.login.LoginActivity;

public class WelcomeActivity extends BaseActivity implements WelcomeContract.IWelcomeView {
    private final long turnTimeDelay = 2000;
    private static long lastTime;
    @Inject
    WelcomePresenter mWelcomePresenter;

    @Override
    public void updateInfo(final UpdateInfoBean bean) {
        Log.e("WelcomeActivity", bean.toString());
        final TimerTask task = new TimerTask() {
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

        final Timer timer = new Timer();
        if (bean.IsValid) {//TODO 还能用
            if (StringUtil.isTrimEmpty(bean.Notification)) {
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
                        .setMessage(bean.Notification)
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
                    .setMessage(bean.Notification)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //TODO 跳AppStore，没有就关闭
                            UHealthUtils.turnStore(WelcomeActivity.this);
                            WelcomeActivity.this.finish();
                        }
                    }).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setTranslucentStatus(0);
        setContentView(R.layout.activity_welcome);
        DaggerWelcomeComponent.builder()
                .welcomeModule(new WelcomeModule(this))
                .appComponent(getAppComponent())
                .build()
                .inject(this);

        lastTime = System.currentTimeMillis();
        // mWelcomePresenter.start(UHealthUtils.getCurrVersion(getApplicationContext()));

        initUmeng();
        final TimerTask task = new TimerTask() {
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
        new Timer().schedule(task, turnTimeDelay);
    }

    private void initUmeng() {
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }


}
