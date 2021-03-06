package haozuo.com.healthdoctor.model;

import android.support.annotation.NonNull;

import com.squareup.okhttp.OkHttpClient;

import java.util.ArrayList;
import java.util.List;

import haozuo.com.healthdoctor.bean.BaseBean;
import haozuo.com.healthdoctor.bean.GlobalShell;
import haozuo.com.healthdoctor.listener.OnHandlerResultListener;
import rx.Subscriber;

/**
 * Created by xiongwei1 on 2016/7/27.
 */
public abstract class AbstractModel implements IBaseModel {
    //    OkHttpClient mOkHttpClient;
    List<Subscriber> subscriberList;


    public AbstractModel(@NonNull OkHttpClient okHttpClient) {
//        mOkHttpClient=okHttpClient;
        subscriberList = new ArrayList<>();
    }
//    public static  <T> Observable.Transformer<T, T> applyAsySchedulers() {
//        return new Observable.Transformer<T, T>() {
//            @Override
//            public Observable<T> call(Observable<T> observable) {
//                return observable.subscribeOn(Schedulers.io())
//                        .unsubscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread());
//            }
//        };
//    }

    protected <T> Subscriber<BaseBean<T>> getSubscriber(@NonNull final OnHandlerResultListener<GlobalShell<T>> callbackListener) {
         Subscriber subscriber =  new Subscriber<BaseBean<T>>() {

           @Override
           public void onStart() {
//               boolean unsubscribed = isUnsubscribed();
//               Log.e("onStart-unsubscribed",""+unsubscribed);
//               if (!ConnectedUtils.isConnected(HZApplication.shareApplication())){
//                   unsubscribe();
//               }
           }

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
        for (Subscriber subscriber : subscriberList) {
            subscriber.unsubscribe();
//            boolean unsubscribed = subscriber.isUnsubscribed();
//            Log.e("isUnsubscribed",""+unsubscribed);
//            if (!unsubscribed) {
                subscriber.unsubscribe();
//                subscriberList.remove(subscriber);
//            }
        }
        subscriberList.clear();
    }
}
