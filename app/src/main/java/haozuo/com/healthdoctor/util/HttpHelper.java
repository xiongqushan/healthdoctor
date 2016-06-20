package haozuo.com.healthdoctor.util;

import android.app.DownloadManager;
import android.content.Context;
import android.util.Base64;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import haozuo.com.healthdoctor.framework.SysConfig;
import haozuo.com.healthdoctor.listener.OnAsyncCallbackListener;

import static com.android.volley.Request.*;

/**
 * Created by xiongwei1 on 16/4/20.
 */
public class HttpHelper {
    private static final String BASIC_USER_NAME  = SysConfig.BASE_API[1];
    private static final String SIGN_SECRET  =SysConfig.BASE_API[2];

    static RequestQueue mRequestQueue;

    public static void Init(Context context){
        if(mRequestQueue==null){
            mRequestQueue=Volley.newRequestQueue(context);
        }
    }

    public static void HttpGet(final String url,String tag,final OnAsyncCallbackListener<JSONObject> listener) {
        if(mRequestQueue==null){
            throw new ExceptionInInitializerError("需要初始化Volly");
        }
        try {
            JsonObjectRequestSort request = new JsonObjectRequestSort(Method.GET, url, "", new com.android.volley.Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    listener.onSuccess(jsonObject);
                }
            }, new com.android.volley.Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    int statusCode = -1;
                    if (volleyError.networkResponse != null) {
                        statusCode = volleyError.networkResponse.statusCode;
                    }
                    listener.onError(statusCode, volleyError.getMessage());
                }
            }) {
                @Override
                public Map<String, String> getHeaders() {
                    String sign=url+"|"+SIGN_SECRET;
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


                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Accept", "application/json");
                    headers.put("Content-Type", "application/json; charset=UTF-8");
                    headers.put("Authorization",encoded);
                    return headers;
                }
            };
            request.setRetryPolicy(new DefaultRetryPolicy(30 * 1000,1,1.0f));
            request.setTag(tag);
            mRequestQueue.add(request);
        } catch (Exception e) {
            listener.onError(400, e.getMessage());
        }
    }

    public static void HttpPost(final String url,final Map<String,Object>params,String tag,final OnAsyncCallbackListener<JSONObject> listener) {
        if(mRequestQueue==null){
            throw new ExceptionInInitializerError("需要初始化Volly");
        }
        try {
            GsonBuilder gb =new GsonBuilder();
            gb.disableHtmlEscaping();
            final String paramString=gb.create().toJson(params);
            JsonObjectRequestSort request = new JsonObjectRequestSort(Request.Method.POST, url, paramString, new com.android.volley.Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    listener.onSuccess(jsonObject);
                }
            }, new com.android.volley.Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    int statusCode = -1;
                    if (volleyError.networkResponse != null) {
                        statusCode = volleyError.networkResponse.statusCode;
                    }
                    listener.onError(statusCode, volleyError.getMessage());
                }
            }) {
                @Override
                public Map<String, String> getHeaders() {
                    String sign=url+"|"+paramString+"|"+SIGN_SECRET;
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


                    HashMap<String, String> headers = new HashMap<String,String>();
                    headers.put("Accept", "application/json");
                    //headers.put("Content-Type", "application/json; charset=UTF-8");
                    headers.put("Authorization",encoded);
                    return headers;
                }
            };
            request.setRetryPolicy(new DefaultRetryPolicy(30 * 1000,1,1.0f));
            request.setTag(tag);
            mRequestQueue.add(request);
        } catch (Exception e) {
            listener.onError(400, e.getMessage());
        }
    }

    public static void HttpCancel(String tag) {
        if (mRequestQueue == null) {
            throw new ExceptionInInitializerError("需要初始化Volly");
        }
        mRequestQueue.cancelAll(tag);
    }

}
