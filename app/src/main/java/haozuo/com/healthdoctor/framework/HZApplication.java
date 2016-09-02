package haozuo.com.healthdoctor.framework;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.iflytek.cloud.SpeechUtility;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import cn.jpush.android.api.JPushInterface;
import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.ioc.AppComponent;
import haozuo.com.healthdoctor.ioc.AppModule;
import haozuo.com.healthdoctor.ioc.DaggerAppComponent;
import haozuo.com.healthdoctor.manager.PreferenceManager;


/**
 * Created by xiongwei on 16/5/7.
 */
public class HZApplication extends Application {
    private AppComponent mAppComponent;
    private static HZApplication applictaion;

    public static HZApplication shareApplication() {
        return applictaion;
    }

    private RefWatcher mRefWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();

        applictaion = this;
        Fresco.initialize(this);
        mRefWatcher = LeakCanary.install(this);

        SpeechUtility.createUtility(this, "appid=" + getString(R.string.app_id));
        // 以下语句用于设置日志开关（默认开启），设置成false时关闭语音云SDK日志打印
        // Setting.setShowLog(false);

        JPushInterface.setDebugMode(SysConfig.DebugMode);    // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);
        PreferenceManager.init(this);

    }

    public RefWatcher getRefWatcher() {
        return mRefWatcher;
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}
