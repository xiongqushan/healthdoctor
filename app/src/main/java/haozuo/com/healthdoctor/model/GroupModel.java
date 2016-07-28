package haozuo.com.healthdoctor.model;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import haozuo.com.healthdoctor.bean.BaseBean;
import haozuo.com.healthdoctor.bean.GlobalShell;
import haozuo.com.healthdoctor.bean.RequestErrorEnum;
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

    public void DeleteCustomerGroup(int customerId,int groupId,String operateBy, final OnHandlerResultListener<GlobalShell<Boolean>> callbackListener){
        IGroupService groupService=createService(IGroupService.class);
        groupService.DeleteGroup(customerId,groupId,operateBy)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseBean<Boolean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        GlobalShell<Boolean> entity=new GlobalShell<Boolean>(e.getMessage());
                        callbackListener.handlerResult(entity);
                    }

                    @Override
                    public void onNext(BaseBean<Boolean> resultBean) {
                        GlobalShell<Boolean> entity=null;
                        if(resultBean.state>0) {
                            boolean result = resultBean.Data;
                            entity=new GlobalShell<Boolean>(result);
                        }
                        else{
                            entity=new GlobalShell<Boolean>(resultBean.message);
                        }
                        callbackListener.handlerResult(entity);
                    }
                });
    }

}
