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

import haozuo.com.healthdoctor.contract.IBaseModel;
import haozuo.com.healthdoctor.framework.SysConfig;
import haozuo.com.healthdoctor.listener.OnSimpleRequestListener;
import haozuo.com.healthdoctor.util.StringUtil;
import okio.Buffer;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * Created by xiongwei1 on 2016/7/27.
 */
public abstract class AbstractModel implements IBaseModel {
    List<String>mTagList;
    OkHttpClient mOkHttpClient;
    public AbstractModel(@NonNull OkHttpClient okHttpClient){
        mOkHttpClient=okHttpClient;
        mTagList=new ArrayList<>();
    }

    protected String requestTag(){
        String tag=java.util.UUID.randomUUID().toString();
        mTagList.add(tag);
        return tag;
    }

    @Override
    public void cancelRequest() {
        for(String tag : mTagList){
            mOkHttpClient.cancel(tag);
        }
    }
}
