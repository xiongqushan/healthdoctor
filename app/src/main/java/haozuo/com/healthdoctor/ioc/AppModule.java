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
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import haozuo.com.healthdoctor.framework.HZApplication;
import haozuo.com.healthdoctor.framework.SysConfig;
import haozuo.com.healthdoctor.model.ConsultModel;
import haozuo.com.healthdoctor.model.GroupModel;
import haozuo.com.healthdoctor.model.ReportModel;
import haozuo.com.healthdoctor.model.SystemModel;
import haozuo.com.healthdoctor.model.UserModel;
import haozuo.com.healthdoctor.service.IConsultService;
import haozuo.com.healthdoctor.service.IGroupService;
import haozuo.com.healthdoctor.service.IReportService;
import haozuo.com.healthdoctor.service.ISystemService;
import haozuo.com.healthdoctor.service.IUserService;
import haozuo.com.healthdoctor.util.StringUtil;
import okio.Buffer;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * Created by xiongwei1 on 2016/8/5.
 */
@Module
public class AppModule {
    private static final String CURRENT_VERSION = SysConfig.CURRENT_BASE_VERSION;
    private static final String API_BASE_URL = SysConfig.BASE_API[0];
    private static final String BASIC_USER_NAME = SysConfig.BASE_API[1];
    private static final String BASIC_SIGN_SECRET = SysConfig.BASE_API[2];

    private HZApplication mHZApplication;

    public AppModule(@NonNull HZApplication application) {
        mHZApplication = application;
    }

    @Provides
    @Singleton
    HZApplication provideApplication() {
        return mHZApplication;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return mHZApplication.getApplicationContext();
    }

    @Provides
//    @Singleton
    IUserService provideUserService(@NonNull Retrofit retrofit) {
        return retrofit.create(IUserService.class);
    }

    @Provides
    @Singleton
    IGroupService provideGroupService(@NonNull Retrofit retrofit) {
        return retrofit.create(IGroupService.class);
    }

    @Provides
    @Singleton
    IConsultService provideConsultService(@NonNull Retrofit retrofit) {
        return retrofit.create(IConsultService.class);
    }

    @Provides
    @Singleton
    IReportService provideReportService(@NonNull Retrofit retrofit) {
        return retrofit.create(IReportService.class);
    }

    @Provides
    @Singleton
    ISystemService provideSystemService(@NonNull Retrofit retrofit) {
        return retrofit.create(ISystemService.class);
    }

    @Provides
    @Singleton
    ConsultModel provideConsultModel(@NonNull OkHttpClient okHttpClient, @NonNull IConsultService iConsultService){
        return new ConsultModel(okHttpClient,iConsultService);
    }

    @Provides
    @Singleton
    GroupModel provideGroupModel(@NonNull OkHttpClient okHttpClient, @NonNull IGroupService iGroupService, @NonNull IUserService iUserService){
        return new GroupModel(okHttpClient,iGroupService,iUserService);
    }
    @Provides
    @Singleton
    ReportModel provideReportModel(@NonNull OkHttpClient okHttpClient, @NonNull IReportService iReportService){
        return new ReportModel(okHttpClient,iReportService);
    }
    @Provides
    @Singleton
    UserModel provideUserModel(@NonNull OkHttpClient okHttpClient, IUserService iUserService){
        return new UserModel(okHttpClient,iUserService);
    }

    @Provides
    @Singleton
    SystemModel provideSystemModel(@NonNull OkHttpClient okHttpClient, ISystemService mISystemService){
        return new SystemModel(okHttpClient,mISystemService);
    }

    @Provides
    @Singleton
    OkHttpClient createHttpClient() {
        OkHttpClient httpClient = new OkHttpClient();
        httpClient.setConnectTimeout(SysConfig.CONNECT_TIMEOUT, TimeUnit.SECONDS);
        httpClient.setWriteTimeout(SysConfig.WRITE_TIMEOUT, TimeUnit.SECONDS);
        httpClient.setReadTimeout(SysConfig.READ_TIMEOUT, TimeUnit.SECONDS);
        httpClient.interceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                String originUrl = request.urlString();
                long timespan = System.currentTimeMillis() / 1000L;
                if (originUrl.contains("?")) {
                    originUrl += "&timespan=" + timespan;
                } else {
                    originUrl += "?timespan=" + timespan;
                }
                request = request.newBuilder().url(originUrl).build();
                String sign = "";
                if (request.method().toLowerCase().equals("get")) {
                    sign = request.urlString() + "|" + BASIC_SIGN_SECRET;
                } else {
                    final Request copy = request.newBuilder().build();
                    final Buffer buffer = new Buffer();
                    copy.body().writeTo(buffer);
                    String postData = buffer.readUtf8();
                    sign = request.urlString() + "|" + postData + "|" + BASIC_SIGN_SECRET;
                }
                sign = StringUtil.encodeByMD5(sign);
                String usernameAndPassword = BASIC_USER_NAME + ":" + sign;
                byte[] bytes = new byte[0];
                try {
                    bytes = usernameAndPassword.getBytes("ISO-8859-1");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                String encoded = Base64.encodeToString(bytes, Base64.NO_WRAP);
                encoded = "Basic " + encoded;
                request = request.newBuilder()
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
    Retrofit createRetrofit(@NonNull OkHttpClient httpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit;
    }
}
