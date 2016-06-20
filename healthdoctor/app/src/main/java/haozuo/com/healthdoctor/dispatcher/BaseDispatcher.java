package haozuo.com.healthdoctor.dispatcher;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import haozuo.com.healthdoctor.util.HttpHelper;

/**
 * Created by xiongwei on 16/5/7.
 */
public abstract class BaseDispatcher {
    public static final int WHAT_USER_GetSMSCode = 1000;
    public static final int WHAT_USER_LOGIN = 1001;
    public static final int WHAT_USER_LOGINSCAN = 1002;
    public static final int WHAT_USER_GETGROUP = 1003;
    public static final int WHAT_USER_GETGROUPCUSTINFOLIST = 1004;

    Handler mRequestHandler;
    Handler mResponseHandler;
    public BaseDispatcher(Looper requestLooper, Handler responseHandler){
        mResponseHandler=responseHandler;
        mRequestHandler=new Handler(requestLooper){
            @Override
            public void handleMessage(Message msg) {
                BaseDispatcher.this.dispatchRequest(msg);
            }
        };

    }

    public String sendMessage(int what) {
        return sendMessage(what, null);
    }

    public String sendMessage(int what, Map<String, Object> params) {
        return sendMessage(what, params, null);
    }

    public String sendMessage(int what, Map<String, Object> params, Object rebackObj) {
        RequestMessage messageData = new RequestMessage(what, params,rebackObj);
        Message message = mRequestHandler.obtainMessage(what, messageData);
        message.sendToTarget();
        return messageData.Identity;
    }

    protected void handleResponse(RequestResult requestResult) {
        Message message = new Message();
        message.what = requestResult.What;
        message.obj=requestResult;
        mResponseHandler.sendMessage(message);
    }

    public void cancelRequest(int[] tags){
        if(tags!=null) {
            for (int tag:tags) {
                cancelRequest(tag);
            }
        }
    }

    public void cancelRequest(int tag){
        HttpHelper.HttpCancel(tag);
    }

    protected abstract void dispatchRequest(Message msg);

    public static class RequestMessage {
        public String Identity;

        public int What;

        public Object RebackObject;

        public Map<String, Object> Params;

        public RequestMessage(int what) {
            What=what;
            Identity = UUID.randomUUID().toString();
            Params = new HashMap<>();
        }

        public RequestMessage(int what,Map<String, Object> params,Object rebackObject){
            this(what);
            Params=params;
            RebackObject=rebackObject;
        }
    }

    public static class RequestResult {
        public String Identity;

        public int What;

        public Object RebackObject;

        public int StatusCode;

        public boolean RequestSuccess;

        public boolean LogicSuccess;

        public Object ResultData;

        public String Description;

        public RequestResult(RequestMessage requestMessage) {
            Identity=requestMessage.Identity;
            What=requestMessage.What;
            RebackObject=requestMessage.RebackObject;
        }

        public void Attach(int statusCode,boolean requestSuccess,boolean logicSuccess,Object resultData,String description) {
            StatusCode = statusCode;
            RequestSuccess=requestSuccess;
            LogicSuccess=logicSuccess;
            ResultData=resultData;
            Description=description;
            prepareDescription(statusCode);
        }

        public void Attach(int statusCode,String description) {
            Attach(statusCode,false,false,null,description);
        }

        void prepareDescription(int statuscode) {
            String message;
            if (statuscode == 403) {
                message = "没有访问权限";
            } else if (statuscode == 404) {
                message = "请求资源不存在";
            } else if (statuscode >= 500) {
                message = "服务异常，请稍后重试"+statuscode;
            } else {
                message = "未知异常"+statuscode;
            }
            Description = message;
        }

    }
}
