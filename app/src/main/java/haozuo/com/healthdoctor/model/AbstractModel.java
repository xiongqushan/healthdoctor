package haozuo.com.healthdoctor.model;

import android.util.Base64;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import haozuo.com.healthdoctor.contract.IBaseModel;
import haozuo.com.healthdoctor.framework.SysConfig;
import haozuo.com.healthdoctor.util.StringUtil;
import okio.Buffer;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * Created by xiongwei1 on 2016/7/27.
 */
public abstract class AbstractModel implements IBaseModel {
    /*private static final String CURRENT_VERSION= SysConfig.CURRENT_BASE_VERSION;
    private static final String API_BASE_URL=SysConfig.BASE_API[0];
    private static final String BASIC_USER_NAME  = SysConfig.BASE_API[1];
    private static final String BASIC_SIGN_SECRET  =SysConfig.BASE_API[2];

    private Map<String,OkHttpClient>mClentTagDictionary=new HashMap<>();

    private OkHttpClient createHttpClient(){
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
    */

    @Override
    public void cancelRequest() {
        /*
        for(Map.Entry<String,OkHttpClient> entry :mClentTagDictionary.entrySet()){
            entry.getValue().cancel(entry.getKey());
        }
        */
    }
}
