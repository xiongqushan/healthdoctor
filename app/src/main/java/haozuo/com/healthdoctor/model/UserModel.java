package haozuo.com.healthdoctor.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import haozuo.com.healthdoctor.bean.DoctorBean;
import haozuo.com.healthdoctor.bean.DoctorGroupBean;
import haozuo.com.healthdoctor.bean.GlobalShell;
import haozuo.com.healthdoctor.bean.GroupCustInfoBean;
import haozuo.com.healthdoctor.bean.PageBean;
import haozuo.com.healthdoctor.bean.RequestErrorEnum;
import haozuo.com.healthdoctor.listener.OnHandlerResultListener;
import haozuo.com.healthdoctor.listener.OnHttpCallbackListener;

/**
 * Created by xiongwei on 16/5/19.
 */
public class UserModel extends BaseModel implements IUserModel {

    public static UserModel createInstance(){
        return new UserModel();
    }

    public void GetSMSCode(String tag,String mobile, final OnHandlerResultListener<GlobalShell<Boolean>> callbackListener){
        Map<String, Object> params=new HashMap<>();
        params.put("mobile", mobile);
        OnHttpCallbackListener onAsyncCallbackListener=new OnHttpCallbackListener<JSONObject>(){
            @Override
            public void onSuccess(JSONObject resultData) {
                GlobalShell<Boolean> entity=null;
                try {
                    int code = resultData.getInt("state");
                    String msg = resultData.getString("message");
                    if(code>0) {
                        boolean result = resultData.getBoolean("Data");
                        entity=new GlobalShell<Boolean>(result);
                    }
                    else{
                        entity=new GlobalShell<Boolean>(msg);
                    }
                } catch (Exception ex) {
                    entity=new GlobalShell<Boolean>(RequestErrorEnum.LogicException,ex.getMessage());
                }
                callbackListener.handlerResult(entity);
            }

            @Override
            public void onError(RequestErrorEnum errorType, String msg) {
                GlobalShell<Boolean> entity=new GlobalShell<Boolean>(errorType,msg);
                callbackListener.handlerResult(entity);
            }
        };
        get(tag,"LoginSMSCode",params,onAsyncCallbackListener);
    }

    public void Login(String tag,String mobile,int smsCode, final OnHandlerResultListener<GlobalShell<DoctorBean>> callbackListener){
        Map<String, Object> params=new HashMap<>();
        params.put("Mobile", mobile);
        params.put("SmsCode", smsCode);
        OnHttpCallbackListener onAsyncCallbackListener=new OnHttpCallbackListener<JSONObject>(){
            @Override
            public void onSuccess(JSONObject resultData) {
                GlobalShell<DoctorBean> entity=null;
                try {
                    int code = resultData.getInt("state");
                    String msg = resultData.getString("message");
                    if(code>0){
                        String result=resultData.getString("Data");
                        entity = new Gson().fromJson(result, DoctorBean.class);
                    }
                } catch (Exception ex) {
                    entity=new GlobalShell<DoctorBean>(RequestErrorEnum.LogicException,ex.getMessage());
                }
                callbackListener.onSuccess(entity);
            }

            @Override
            public void onError(RequestErrorEnum errorType, String msg) {
                GlobalShell<Boolean> entity=new GlobalShell<Boolean>(errorType,msg);
                callbackListener.handlerResult(entity);
            }
        };
        post(tag, "Login",params,onAsyncCallbackListener);
    }

    public void GetGroup(String tag,int doctorId, final OnHandlerResultListener<GlobalShell<List<DoctorGroupBean>>> callbackListener){
        Map<String, Object> params=new HashMap<>();
        params.put("doctorId", doctorId);
        OnHttpCallbackListener onAsyncCallbackListener=new OnHttpCallbackListener<JSONObject>(){
            @Override
            public void onSuccess(JSONObject resultData) {
                int code = -1;
                String msg = "";
                List<DoctorGroupBean> result=null;
                try {
                    code = resultData.getInt("state");
                    msg = resultData.getString("message");
                    if(code>0) {
                        String dataString = resultData.getString("Data");
                        Type listType = new TypeToken<List<DoctorGroupBean>>() {
                        }.getType();
                        result = new Gson().fromJson(dataString, listType);
                    }
                } catch (Exception ex) {
                    code=-200;
                    msg=ex.getMessage();
                }
                GlobalShell<List<DoctorGroupBean>> entity=new GlobalShell<List<DoctorGroupBean>>(code,msg,result);
                callbackListener.onSuccess(entity);
            }

            @Override
            public void onError(RequestErrorEnum errorType, String msg) {
                GlobalShell<Boolean> entity=new GlobalShell<Boolean>(errorType,msg);
                callbackListener.handlerResult(entity);
            }
        };
        get(tag, "GetCusGroupByDoctorId",params,onAsyncCallbackListener);
    }

    public void GetGroupCustInfoList(String tag,int serviceDeptId,int groupId,String customNameOrId,int pageIndex,int pageSize, final OnHandlerResultListener<GlobalShell<PageBean<GroupCustInfoBean>>> callbackListener){
        Map<String, Object> params=new HashMap<>();
        params.put("serviceDeptId", serviceDeptId);
        params.put("groupId", groupId);
        params.put("customNameOrId", customNameOrId);
        params.put("pageIndex", pageIndex);
        params.put("pageSize", pageSize);
        OnHttpCallbackListener onAsyncCallbackListener=new OnHttpCallbackListener<JSONObject>(){
            @Override
            public void onSuccess(JSONObject resultData) {
                int code = -1;
                String msg = "";
                PageBean<GroupCustInfoBean> result=null;
                try {
                    code = resultData.getInt("state");
                    msg = resultData.getString("message");
                    if(code>0) {
                        String dataString = resultData.getString("Data");
                        Type listType = new TypeToken<PageBean<GroupCustInfoBean>>() {
                        }.getType();
                        result = new Gson().fromJson(dataString, listType);
                    }
                } catch (Exception ex) {
                    code=-200;
                    msg=ex.getMessage();
                }
                GlobalShell<PageBean<GroupCustInfoBean>> entity=new GlobalShell<PageBean<GroupCustInfoBean>>(code,msg,result);
                callbackListener.onSuccess(entity);
            }

            @Override
            public void onError(RequestErrorEnum errorType, String msg) {
                GlobalShell<Boolean> entity=new GlobalShell<Boolean>(errorType,msg);
                callbackListener.handlerResult(entity);
            }
        };
        get(tag,"GetGroupCustInfoList",params,onAsyncCallbackListener);
    }

    @Override
    protected String getModule() {
        return "User";
    }
}
