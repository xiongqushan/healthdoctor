package haozuo.com.healthdoctor.framework;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by xiongwei on 16/5/7.
 */
public class HZApplication extends Application {
    private static HZApplication applictaion;
    public static HZApplication shareApplication() {
        return applictaion;
    }

    private RefWatcher mRefWatcher;
    @Override
    public void onCreate() {
        super.onCreate();
        applictaion = this;
        //HttpHelper.Init(this);
        Fresco.initialize(this);
        mRefWatcher = LeakCanary.install(this);
    }

    public RefWatcher getRefWatcher(){
        return mRefWatcher;
    }
}
