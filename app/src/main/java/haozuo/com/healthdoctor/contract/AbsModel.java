package haozuo.com.healthdoctor.contract;

import android.util.Base64;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

import haozuo.com.healthdoctor.framework.SysConfig;
import haozuo.com.healthdoctor.util.StringUtil;
import okio.Buffer;
import okio.BufferedSink;
import okio.ByteString;
import okio.Source;
import okio.Timeout;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * Created by xiongwei1 on 2016/7/27.
 */
public abstract class AbsModel implements BaseModel{
    private static final String CURRENT_VERSION= SysConfig.CURRENT_BASE_VERSION;
    private static final String API_BASE_URL=SysConfig.BASE_API[0];
    private static final String BASIC_USER_NAME  = SysConfig.BASE_API[1];
    private static final String BASIC_SIGN_SECRET  =SysConfig.BASE_API[2];

    private OkHttpClient createHttpClient(){
        OkHttpClient httpClient = new OkHttpClient();
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
                request= request.newBuilder().url(originUrl).build();
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

    private Retrofit createRetrofit(){
        OkHttpClient httpClient=createHttpClient();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit;
    }

    protected  <T> T createService(final Class<T> service) {
        Retrofit retrofit=createRetrofit();
        return retrofit.create(service);
    }

}
