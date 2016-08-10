package haozuo.com.healthdoctor.ioc;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Base64;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import haozuo.com.healthdoctor.bean.BaseBean;
import haozuo.com.healthdoctor.bean.CustomDetailBean;
import haozuo.com.healthdoctor.bean.DoctorBean;
import haozuo.com.healthdoctor.bean.GroupCustInfoBean;
import haozuo.com.healthdoctor.bean.PageBean;
import haozuo.com.healthdoctor.framework.HZApplication;
import haozuo.com.healthdoctor.framework.SysConfig;
import haozuo.com.healthdoctor.service.IConsultService;
import haozuo.com.healthdoctor.service.IGroupService;
import haozuo.com.healthdoctor.service.IUserService;
import haozuo.com.healthdoctor.util.StringUtil;
import okio.Buffer;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import retrofit.http.Body;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by xiongwei1 on 2016/8/5.
 */
@Module
public class AppModule {
    private static final String CURRENT_VERSION= SysConfig.CURRENT_BASE_VERSION;
    private static final String API_BASE_URL=SysConfig.BASE_API[0];
    private static final String BASIC_USER_NAME  = SysConfig.BASE_API[1];
    private static final String BASIC_SIGN_SECRET  =SysConfig.BASE_API[2];
    private Map<String,OkHttpClient> mClentTagDictionary=new HashMap<>();

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

    @Provides
    @Singleton
    IUserService provideUserService(@NonNull Retrofit retrofit){
        return retrofit.create(IUserService.class);
    }

    @Provides
    @Singleton
    IGroupService provideGroupService(@NonNull Retrofit retrofit){
        return retrofit.create(IGroupService.class);
    }

    @Provides
    @Singleton
    IConsultService provideConsultService(@NonNull Retrofit retrofit){
        return retrofit.create(IConsultService.class);
    }

    @Provides
    @Singleton
    OkHttpClient createHttpClient(){
        final String tag=java.util.UUID.randomUUID().toString();
        OkHttpClient httpClient = new OkHttpClient();
        mClentTagDictionary.put(tag,httpClient);
        httpClient.interceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                String originUrl=request.urlString();
                long timespan=System.currentTimeMillis()/1000L;
                if(originUrl.contains("?")){
                    originUrl+="&timespan="+timespan;
                }
                else{
                    originUrl+="?timespan="+timespan;
                }
                request= request.newBuilder().url(originUrl).tag(tag).build();
                String sign ="";
                if(request.method().toLowerCase().equals("get")) {
                    sign = request.urlString() + "|" + BASIC_SIGN_SECRET;
                }
                else{
                    final Request copy = request.newBuilder().build();
                    final Buffer buffer = new Buffer();
                    copy.body().writeTo(buffer);
                    String postData= buffer.readUtf8();
                    sign=request.urlString()+"|" + postData + "|"+BASIC_SIGN_SECRET;
                }
                sign= StringUtil.encodeByMD5(sign);
                String usernameAndPassword = BASIC_USER_NAME+":"+sign;
                byte[] bytes = new byte[0];
                try {
                    bytes = usernameAndPassword.getBytes("ISO-8859-1");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                String encoded = Base64.encodeToString(bytes, Base64.NO_WRAP);
                encoded = "Basic " + encoded;
                request= request.newBuilder()
                        .addHeader("Content-Type", "application/json; charset=UTF-8")
                        .addHeader("Accept", "application/json")
                        .addHeader("Authorization", encoded)
                        .build();
                return chain.proceed(request);
            }
        });
        return httpClient;
    }

    @Provides
    @Singleton
    Retrofit createRetrofit(@NonNull OkHttpClient httpClient){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit;
    }
}
