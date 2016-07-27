package haozuo.com.healthdoctor.model;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import haozuo.com.healthdoctor.bean.BaseBean;
import haozuo.com.healthdoctor.bean.GlobalShell;
import haozuo.com.healthdoctor.bean.RequestErrorEnum;
import haozuo.com.healthdoctor.bean.TestGroupBean;
import haozuo.com.healthdoctor.contract.AbsBaseModel;
import haozuo.com.healthdoctor.contract.AbsModel;
import haozuo.com.healthdoctor.listener.OnHandlerResultListener;
import haozuo.com.healthdoctor.listener.OnHttpCallbackListener;
import haozuo.com.healthdoctor.service.IGroupService;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xiongwei1 on 2016/7/27.
 */
public class GroupModel extends AbsModel {

    public static GroupModel createInstance(){
        return new GroupModel();
    }

    /*public void DeleteCustomerGroup(String tag,int customerId,int groupId,String operateBy, final OnHandlerResultListener<GlobalShell<Boolean>> callbackListener){
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
    }*/

    public void GetGroup(int doctorId){
        IGroupService testService=createService(IGroupService.class);
        testService.getGroup(doctorId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseBean<List<TestGroupBean>>>() {
                    @Override
                    public void onCompleted() {
                        String a="";
                    }

                    @Override
                    public void onError(Throwable e) {
                        String a="";
                    }

                    @Override
                    public void onNext(BaseBean<List<TestGroupBean>> testBean) {
                        String a= testBean.toString();
                    }
                });
    }

    @Override
    public void cancel(String tag) {

    }
}
