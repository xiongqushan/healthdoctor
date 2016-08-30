package haozuo.com.healthdoctor.view.welcome;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;

import com.umeng.analytics.MobclickAgent;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.framework.SysConfig;
import haozuo.com.healthdoctor.manager.UserManager;
import haozuo.com.healthdoctor.view.base.BaseActivity;
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
                boolean exist = UserManager.getInstance().exist();
                Log.e("WelcomeActivity", exist + "");
                if (exist) {
                    startActivity(new Intent(getBaseContext(), HomeActivity.class));
                } else {
                    startActivity(new Intent(getBaseContext(), LoginActivity.class));
                }

                finish();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 2000);
        initUmeng();
        if (SysConfig.DebugMode) {
            String deviceInfo = getDeviceInfo(this);
            Log.e("DeviceInfo", deviceInfo);
        }
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
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    public static String getDeviceInfo(Context context) {
        try {
            org.json.JSONObject json = new org.json.JSONObject();
            android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            String device_id = null;
            device_id = tm.getDeviceId();
            String mac = null;
            FileReader fstream = null;
            try {
                fstream = new FileReader("/sys/class/net/wlan0/address");
            } catch (FileNotFoundException e) {
                fstream = new FileReader("/sys/class/net/eth0/address");
            }
            BufferedReader in = null;
            if (fstream != null) {
                try {
                    in = new BufferedReader(fstream, 1024);
                    mac = in.readLine();
                } catch (IOException e) {
                } finally {
                    if (fstream != null) {
                        try {
                            fstream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (in != null) {
                        try {
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            json.put("mac", mac);
            if (TextUtils.isEmpty(device_id)) {
                device_id = mac;
            }
            if (TextUtils.isEmpty(device_id)) {
                device_id = android.provider.Settings.Secure.getString(context.getContentResolver(),
                        android.provider.Settings.Secure.ANDROID_ID);
            }
            json.put("device_id", device_id);
            return json.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
