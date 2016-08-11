package haozuo.com.healthdoctor.framework;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import haozuo.com.healthdoctor.ioc.AppComponent;
import haozuo.com.healthdoctor.ioc.AppModule;
import haozuo.com.healthdoctor.ioc.DaggerAppComponent;


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
    }

    public RefWatcher getRefWatcher() {
        return mRefWatcher;
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}
