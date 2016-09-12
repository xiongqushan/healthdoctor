package haozuo.com.healthdoctor.framework;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.decoder.ProgressiveJpegConfig;
import com.facebook.imagepipeline.image.ImmutableQualityInfo;
import com.facebook.imagepipeline.image.QualityInfo;
import com.iflytek.cloud.Setting;
import com.iflytek.cloud.SpeechUtility;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.umeng.analytics.MobclickAgent;

import cn.jpush.android.api.JPushInterface;
import haozuo.com.healthdoctor.BuildConfig;
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
        mRefWatcher = LeakCanary.install(this);

        SpeechUtility.createUtility(this, "appid=" + getString(R.string.app_id));
        // 以下语句用于设置日志开关（默认开启），设置成false时关闭语音云SDK日志打印
        // Setting.setShowLog(false);
        Setting.setShowLog(true);

        //FRESCO 配置渐进式加载JPEG图片
        ProgressiveJpegConfig pjpegConfig = new ProgressiveJpegConfig() {
            @Override
            public int getNextScanNumberToDecode(int scanNumber) {
                return scanNumber + 2;
            }

            public QualityInfo getQualityInfo(int scanNumber) {
                boolean isGoodEnough = (scanNumber >= 5);
                return ImmutableQualityInfo.of(scanNumber, isGoodEnough, false);
            }
        };
        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(this)
                .setProgressiveJpegConfig(pjpegConfig)
                .setDownsampleEnabled(true) //图片代替resizeoption 向下采样  支持PNG和WebP
                .build();
        Fresco.initialize(this, config);
//        Fresco.initialize(this);
        JPushInterface.setDebugMode(BuildConfig.DEBUG);    // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);
        // JPushInterface.setLatestNotificationNumber(this, 3);//限制保留的通知条数。默认为保留最近 5 条通知。
        PreferenceManager.init(this);
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);

    }

    public RefWatcher getRefWatcher() {
        return mRefWatcher;
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}
