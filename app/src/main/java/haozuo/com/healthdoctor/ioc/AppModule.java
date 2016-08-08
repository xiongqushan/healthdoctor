package haozuo.com.healthdoctor.ioc;

import android.content.Context;
import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import haozuo.com.healthdoctor.framework.HZApplication;

/**
 * Created by xiongwei1 on 2016/8/5.
 */
@Module
public class AppModule {
    private HZApplication mHZApplication;

    public AppModule(@NonNull HZApplication application) {
        mHZApplication = application;
    }

    @Provides
    @Singleton
    HZApplication provideApplication(){
        return mHZApplication;
    }

    @Provides
    @Singleton
    Context provideContext(){
        return mHZApplication.getApplicationContext();
    }
}
