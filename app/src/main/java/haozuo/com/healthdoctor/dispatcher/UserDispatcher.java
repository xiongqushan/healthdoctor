package haozuo.com.healthdoctor.dispatcher;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.util.List;

import haozuo.com.healthdoctor.bean.DoctorBean;
import haozuo.com.healthdoctor.bean.DoctorGroupBean;
import haozuo.com.healthdoctor.bean.GlobalShell;
import haozuo.com.healthdoctor.bean.GroupCustInfoBean;
import haozuo.com.healthdoctor.bean.PageBean;
import haozuo.com.healthdoctor.model.UserModel;

/**
 * Created by xiongwei on 16/5/15.
 */
public class UserDispatcher extends BaseDispatcher {
    public UserDispatcher(Looper requestLooper, Handler responseHandler) {
        super(requestLooper, responseHandler);
    }

    @Override
    protected void dispatchRequest(Message msg) {
        RequestMessage requestMessage = (RequestMessage) msg.obj;
        switch (msg.what) {
            case WHAT_USER_GetSMSCode:
                getSMSCode(requestMessage);
                break;
            case WHAT_USER_LOGIN:
                login(requestMessage);
                break;
            case WHAT_USER_LOGINSCAN:
                scanLogin(requestMessage);
                break;
            case WHAT_USER_GETGROUP:
                getGroup(requestMessage);
                break;
            case WHAT_USER_GETGROUPCUSTINFOLIST:
                GetGroupCustInfoList(requestMessage);
                break;
        }
    }

    //获取验证码
    void getSMSCode(final RequestMessage requestMessage) {
        String mobile = (String) requestMessage.Params.get("mobile");
       /* UserModel.createInstance().GetSMSCode(requestMessage.What, mobile, new OnAsyncCallbackListener<GlobalShell<Boolean>>() {
            @Override
            public void onSuccess(GlobalShell<Boolean> resultData) {
                int statusCode = resultData.Code;
                boolean isLogicSuccess = resultData.Data;
                String message = resultData.OriginMessage;
                RequestResult requestResult = new RequestResult(requestMessage);
                requestResult.Attach(statusCode, true, isLogicSuccess, null, message);
                handleResponse(requestResult);
            }

            @Override
            public void onError(int code, String msg) {
                RequestResult requestResult = new RequestResult(requestMessage);
                requestResult.Attach(code, msg);
                handleResponse(requestResult);
            }
        });*/
    }

    //登陆
    void login(final RequestMessage requestMessage) {
        /*String mobile = (String) requestMessage.Params.get("Mobile");
        int smsCode = Integer.parseInt((String) requestMessage.Params.get("SmsCode"));
        UserModel.createInstance().Login(requestMessage.What, mobile, smsCode, new OnAsyncCallbackListener<GlobalShell<DoctorBean>>() {
            @Override
            public void onSuccess(GlobalShell<DoctorBean> resultData) {
                int statusCode = resultData.Code;
                String message = resultData.OriginMessage;
                DoctorBean doctorEntity = resultData.Data;
                RequestResult requestResult = new RequestResult(requestMessage);
                requestResult.Attach(statusCode, true, resultData.Code > 0, doctorEntity, message);
                handleResponse(requestResult);
            }

            @Override
            public void onError(int code, String msg) {
                RequestResult requestResult = new RequestResult(requestMessage);
                requestResult.Attach(code, msg);
                handleResponse(requestResult);
            }
        });*/
    }

    void scanLogin(final RequestMessage requestMessage) {
       /* String identity = (String) requestMessage.Params.get("identity");
        int userId = Integer.parseInt((String) requestMessage.Params.get("userId"));
        UserModel.createInstance().ScanLogin(requestMessage.What, userId, identity, new OnAsyncCallbackListener<GlobalShell<Boolean>>() {
            @Override
            public void onSuccess(GlobalShell<Boolean> resultData) {
                int statusCode = resultData.Code;
                String message = resultData.OriginMessage;
                RequestResult requestResult = new RequestResult(requestMessage);
                requestResult.Attach(statusCode, true, statusCode > 0, resultData.Data, message);
                handleResponse(requestResult);
            }

            @Override
            public void onError(int code, String msg) {
                RequestResult requestResult = new RequestResult(requestMessage);
                requestResult.Attach(code, msg);
                handleResponse(requestResult);
            }
        });*/
    }

    void getGroup(final RequestMessage requestMessage) {
        /*int doctorId = (int) requestMessage.Params.get("doctorId");
        UserModel.createInstance().GetGroup(requestMessage.What, doctorId, new OnAsyncCallbackListener<GlobalShell<List<DoctorGroupBean>>>() {
            @Override
            public void onSuccess(GlobalShell<List<DoctorGroupBean>> resultData) {
                int statusCode = resultData.Code;
                String message = resultData.OriginMessage;
                RequestResult requestResult = new RequestResult(requestMessage);
                requestResult.Attach(statusCode, true, statusCode > 0, resultData.Data, message);
                handleResponse(requestResult);
            }

            @Override
            public void onError(int code, String msg) {
                RequestResult requestResult = new RequestResult(requestMessage);
                requestResult.Attach(code, msg);
                handleResponse(requestResult);
            }
        });*/
    }

    void GetGroupCustInfoList(final RequestMessage requestMessage) {
        /*int serviceDeptId = (int) requestMessage.Params.get("serviceDeptId");
        int groupId = (int) requestMessage.Params.get("groupId");
        //String customNameOrId = (String) requestMessage.Params.get("customNameOrId");
        String customNameOrId ="";
        int pageIndex = (int) requestMessage.Params.get("pageIndex");
        int pageSize = (int) requestMessage.Params.get("pageSize");
        UserModel.createInstance().GetGroupCustInfoList(requestMessage.What, serviceDeptId,groupId,customNameOrId,pageIndex,pageSize, new OnAsyncCallbackListener<GlobalShell<PageBean<GroupCustInfoBean>>>() {
            @Override
            public void onSuccess(GlobalShell<PageBean<GroupCustInfoBean>> resultData) {
                int statusCode = resultData.Code;
                String message = resultData.OriginMessage;
                RequestResult requestResult = new RequestResult(requestMessage);
                requestResult.Attach(statusCode, true, statusCode > 0, resultData.Data, message);
                handleResponse(requestResult);
            }

            @Override
            public void onError(int code, String msg) {
                RequestResult requestResult = new RequestResult(requestMessage);
                requestResult.Attach(code, msg);
                handleResponse(requestResult);
            }
        });*/
    }
}
