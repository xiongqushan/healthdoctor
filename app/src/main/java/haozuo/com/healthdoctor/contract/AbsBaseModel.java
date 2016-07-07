package haozuo.com.healthdoctor.contract;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Map;

import haozuo.com.healthdoctor.framework.SysConfig;
import haozuo.com.healthdoctor.listener.OnHttpCallbackListener;
import haozuo.com.healthdoctor.util.HttpHelper;

/**
 * Created by xiongwei on 16/5/19.
 */
public abstract class AbsBaseModel {
    protected String CURRENT_VERSION= SysConfig.CURRENT_BASE_VERSION;
    protected String API_BASE_URL=SysConfig.BASE_API[0];
    public AbsBaseModel(){

    }

    protected abstract String getModule();

    String getApiUrl(String actionName,Map<String,Object> paramMap) {
        String paramString = "";
        if (paramMap != null) {
            for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
                if (paramString != "") {
                    paramString += "&";
                }
                paramString += entry.getKey() + "=" + entry.getValue().toString();
            }
        }
        paramString="?"+paramString;
        String url = getApiUrl(actionName,paramString);
        return url;
    }

    String getApiUrl(String actionName,ArrayList<Object> paramArray){
        String paramString="";
        for (Object param:paramArray) {
            paramString+="/"+param.toString();
        }
        String url=getApiUrl(actionName,paramString);
        return url;
    }

    String getApiUrl(String actionName,String paramString){
        String url= String.format("%s/%s_%s/%s/%s",API_BASE_URL,CURRENT_VERSION,getModule(),actionName, paramString);
        long timespan=System.currentTimeMillis()/1000L;
        if(url.contains("?")){
            url+="&timespan="+timespan;
        }
        else{
            url+="?timespan="+timespan;
        }
        return url;
    }

    final protected void get(String tag,String actionName,Map<String,Object> paramMap,final OnHttpCallbackListener<JSONObject> listener){
        String url=getApiUrl(actionName, paramMap);
        HttpHelper.HttpGet(url,tag, listener);
    }

    final protected void get(String tag,String actionName,ArrayList<Object> paramArray,final OnHttpCallbackListener<JSONObject> listener){
        String url=getApiUrl(actionName,paramArray);
        HttpHelper.HttpGet(url,tag,listener);
    }

    final protected void get(String tag,String actionName,String urlParam,final OnHttpCallbackListener<JSONObject> listener){
        String url=getApiUrl(actionName,urlParam);
        HttpHelper.HttpGet(url,tag, listener);
    }

    final protected void post(String tag,String actionName,Map<String,Object>postData,final OnHttpCallbackListener<JSONObject> listener){
        String url=getApiUrl(actionName,"");
        HttpHelper.HttpPost(url, postData,tag, listener);
    }

    public void cancel(String  tag){
        HttpHelper.HttpCancel(tag);
    }
}
