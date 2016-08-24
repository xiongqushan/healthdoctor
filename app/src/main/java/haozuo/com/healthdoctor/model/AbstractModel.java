package haozuo.com.healthdoctor.model;

import android.support.annotation.NonNull;
import android.util.Base64;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import haozuo.com.healthdoctor.bean.BaseBean;
import haozuo.com.healthdoctor.bean.DoctorBean;
import haozuo.com.healthdoctor.bean.GlobalShell;
import haozuo.com.healthdoctor.contract.IBaseModel;
import haozuo.com.healthdoctor.framework.SysConfig;
import haozuo.com.healthdoctor.listener.OnHandlerResultListener;
import haozuo.com.healthdoctor.listener.OnSimpleRequestListener;
import haozuo.com.healthdoctor.util.StringUtil;
import okio.Buffer;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import rx.Subscriber;

/**
 * Created by xiongwei1 on 2016/7/27.
 */
public abstract class AbstractModel implements IBaseModel {
    OkHttpClient mOkHttpClient;
    List<Subscriber> subscriberList;
    public AbstractModel(@NonNull OkHttpClient okHttpClient){
        mOkHttpClient=okHttpClient;
        subscriberList=new ArrayList<>();
    }

    protected <T> Subscriber<BaseBean<T>> getSubscriber(@NonNull final OnHandlerResultListener<GlobalShell<T>> callbackListener){
        Subscriber subscriber= new Subscriber<BaseBean<T>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                GlobalShell<T> entity = new GlobalShell<T>(e.getMessage());
                callbackListener.handlerResult(entity);
            }

            @Override
            public void onNext(BaseBean<T> resultBean) {
                GlobalShell<T> entity = null;
                if (resultBean.state > 0) {
                    T result = resultBean.Data;
                    entity = new GlobalShell<T>(result);
                } else {
                    entity = new GlobalShell<T>(resultBean.message);
                }
                callbackListener.handlerResult(entity);
            }
        };
        subscriberList.add(subscriber);
        return subscriber;
    }

    @Override
    public void cancelRequest() {
        for (Subscriber subscriber:subscriberList){
            subscriber.unsubscribe();
        }
    }
}
