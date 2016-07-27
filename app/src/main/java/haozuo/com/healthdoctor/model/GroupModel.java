package haozuo.com.healthdoctor.model;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import haozuo.com.healthdoctor.bean.GlobalShell;
import haozuo.com.healthdoctor.bean.RequestErrorEnum;
import haozuo.com.healthdoctor.contract.AbsBaseModel;
import haozuo.com.healthdoctor.listener.OnHandlerResultListener;
import haozuo.com.healthdoctor.listener.OnHttpCallbackListener;

/**
 * Created by xiongwei1 on 2016/7/27.
 */
public class GroupModel extends AbsBaseModel {
    public static UserModel createInstance(){
        return new UserModel();
    }

    @Override
    protected String getModule() {
        return "User";
    }

    public void DeleteCustomerGroup(String tag,int customerId,int groupId,String operateBy, final OnHandlerResultListener<GlobalShell<Boolean>> callbackListener){
        Map<String, Object> params=new HashMap<>();
        params.put("customerId", customerId);
        params.put("curGroupId", groupId);
        params.put("operateBy", operateBy);
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
        get(tag,"DeleteCustomerGroup",params,onAsyncCallbackListener);
    }
}
