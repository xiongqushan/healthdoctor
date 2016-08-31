package haozuo.com.healthdoctor.model;

import android.support.annotation.NonNull;

import com.squareup.okhttp.OkHttpClient;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import haozuo.com.healthdoctor.bean.CustomDetailBean;
import haozuo.com.healthdoctor.bean.DoctorBean;
import haozuo.com.healthdoctor.bean.GlobalShell;
import haozuo.com.healthdoctor.listener.OnHandlerResultListener;
import haozuo.com.healthdoctor.service.IUserService;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xiongwei on 16/5/19.
 */
public class UserModel extends AbstractModel {
    IUserService mIUserService;

    @Inject
    public UserModel(@NonNull OkHttpClient okHttpClient, IUserService iUserService){
        super(okHttpClient);
        mIUserService=iUserService;
    }

    public void GetSMSCode(String mobile, final OnHandlerResultListener<GlobalShell<Boolean>> callbackListener){
        Subscriber subscriber=getSubscriber(callbackListener);
        mIUserService.getSMSCode(mobile)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void Login(String mobile,int smsCode, final OnHandlerResultListener<GlobalShell<DoctorBean>> callbackListener) {
        Map<String, Object> params = new HashMap<>();
        params.put("Mobile", mobile);
        params.put("SmsCode", smsCode);
        mIUserService.login(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getSubscriber(callbackListener));
    }

    public void LoginValidate(String account,String password, final OnHandlerResultListener<GlobalShell<DoctorBean>> callbackListener) {
        Map<String, Object> params = new HashMap<>();
        params.put("Account", account);
        params.put("Password", password);
        Subscriber subscriber=getSubscriber(callbackListener);
        mIUserService.LoginValidate(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void GetUserDetail(int customerId, final OnHandlerResultListener<GlobalShell<CustomDetailBean>> callbackListener) {
        mIUserService.GetCusInfo(customerId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getSubscriber(callbackListener));
    }

}
