package haozuo.com.healthdoctor.model;

import android.support.annotation.NonNull;

import com.squareup.okhttp.OkHttpClient;

import javax.inject.Inject;

import haozuo.com.healthdoctor.bean.GlobalShell;
import haozuo.com.healthdoctor.bean.UpdateInfoBean;
import haozuo.com.healthdoctor.listener.OnHandlerResultListener;
import haozuo.com.healthdoctor.service.ISystemService;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zhangzhongyao on 2016/9/5.
 */
public class SystemModel extends AbstractModel {
    ISystemService mISystemService;

//    @Inject
    public SystemModel(@NonNull OkHttpClient okHttpClient, ISystemService mISystemService) {
        super(okHttpClient);
        this.mISystemService = mISystemService;
    }

    public void GetVersionStatus(int currVersion, final OnHandlerResultListener<GlobalShell<UpdateInfoBean>> callbackListener) {
        Subscriber subscriber = getSubscriber(callbackListener);
        mISystemService.GetVersionStatus(currVersion)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
