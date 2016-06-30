package haozuo.com.healthdoctor.framework;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;

import haozuo.com.healthdoctor.util.HttpHelper;

/**
 * Created by xiongwei on 16/5/7.
 */
public class HZApplication extends Application {
    private static Context instance;
    public static Context shareContext() {
        return instance;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
        HttpHelper.Init(this);
        Fresco.initialize(this);
    }
}
