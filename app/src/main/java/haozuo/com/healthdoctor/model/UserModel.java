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
import haozuo.com.healthdoctor.listener.OnAsyncCallbackListener;

/**
 * Created by xiongwei on 16/5/19.
 */
public class UserModel extends BaseModel implements IUserModel {

    public static UserModel createInstance(){
        return new UserModel();
    }

    public void GetSMSCode(String tag,String mobile, final OnAsyncCallbackListener<GlobalShell<Boolean>> callbackListener){
        Map<String, Object> params=new HashMap<>();
        params.put("mobile", mobile);
        OnAsyncCallbackListener onAsyncCallbackListener=new OnAsyncCallbackListener<JSONObject>(){
            @Override
            public void onSuccess(JSONObject resultData) {
                int code = -1;
                String msg = "";
                boolean result=false;
                try {
                    code = resultData.getInt("state");
                    msg = resultData.getString("message");
                    if(code>0) {
                        result = resultData.getBoolean("Data");
                    }
                } catch (Exception ex) {
                }
                GlobalShell<Boolean> entity=new GlobalShell<Boolean>(code,msg,result);
                callbackListener.onSuccess(entity);
            }

            @Override
            public void onError(int code, String msg) {
                callbackListener.onError(code, msg);
            }
        };
        get(tag,"LoginSMSCode",params,onAsyncCallbackListener);
    }

    public void Login(String tag,String mobile,int smsCode, final OnAsyncCallbackListener<GlobalShell<DoctorBean>> callbackListener){
        Map<String, Object> params=new HashMap<>();
        params.put("Mobile", mobile);
        params.put("SmsCode", smsCode);
        OnAsyncCallbackListener onAsyncCallbackListener=new OnAsyncCallbackListener<JSONObject>(){
            @Override
            public void onSuccess(JSONObject resultData) {
                int code = -1;
                String msg = "";
                String result = "";
                DoctorBean doctorEntity =null;
                try {
                    code = resultData.getInt("state");
                    msg = resultData.getString("message");
                    result=resultData.getString("Data");
                    if(code>0){
                        doctorEntity = new Gson().fromJson(result, DoctorBean.class);
                    }
                } catch (Exception ex) {
                }
                GlobalShell<DoctorBean> entity=new GlobalShell<DoctorBean>(code,msg,doctorEntity);
                callbackListener.onSuccess(entity);
            }

            @Override
            public void onError(int code, String msg) {
                callbackListener.onError(code, msg);
            }
        };
        post(tag, "Login",params,onAsyncCallbackListener);
    }

    public void GetGroup(String tag,int doctorId, final OnAsyncCallbackListener<GlobalShell<List<DoctorGroupBean>>> callbackListener){
        Map<String, Object> params=new HashMap<>();
        params.put("doctorId", doctorId);
        OnAsyncCallbackListener onAsyncCallbackListener=new OnAsyncCallbackListener<JSONObject>(){
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
                }
                GlobalShell<List<DoctorGroupBean>> entity=new GlobalShell<List<DoctorGroupBean>>(code,msg,result);
                callbackListener.onSuccess(entity);
            }

            @Override
            public void onError(int code, String msg) {
                callbackListener.onError(code, msg);
            }
        };
        get(tag, "GetCusGroupByDoctorId",params,onAsyncCallbackListener);
    }

    public void GetGroupCustInfoList(String tag,int serviceDeptId,int groupId,String customNameOrId,int pageIndex,int pageSize, final OnAsyncCallbackListener<GlobalShell<PageBean<GroupCustInfoBean>>> callbackListener){
        Map<String, Object> params=new HashMap<>();
        params.put("serviceDeptId", serviceDeptId);
        params.put("groupId", groupId);
        params.put("customNameOrId", customNameOrId);
        params.put("pageIndex", pageIndex);
        params.put("pageSize", pageSize);
        OnAsyncCallbackListener onAsyncCallbackListener=new OnAsyncCallbackListener<JSONObject>(){
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
                }
                GlobalShell<PageBean<GroupCustInfoBean>> entity=new GlobalShell<PageBean<GroupCustInfoBean>>(code,msg,result);
                callbackListener.onSuccess(entity);
            }

            @Override
            public void onError(int code, String msg) {
                callbackListener.onError(code, msg);
            }
        };
        get(tag,"GetGroupCustInfoList",params,onAsyncCallbackListener);
    }

    @Override
    protected String getModule() {
        return "User";
    }
}
