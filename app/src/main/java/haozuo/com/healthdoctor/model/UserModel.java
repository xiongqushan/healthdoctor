package haozuo.com.healthdoctor.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import haozuo.com.healthdoctor.bean.CustomDetailBean;
import haozuo.com.healthdoctor.bean.BaseBean;
import haozuo.com.healthdoctor.bean.DoctorBean;
import haozuo.com.healthdoctor.bean.DoctorGroupBean;
import haozuo.com.healthdoctor.bean.GlobalShell;
import haozuo.com.healthdoctor.bean.GroupCustInfoBean;
import haozuo.com.healthdoctor.bean.PageBean;
import haozuo.com.healthdoctor.listener.OnHandlerResultListener;
import haozuo.com.healthdoctor.service.IGroupService;
import haozuo.com.healthdoctor.service.IUserService;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xiongwei on 16/5/19.
 */
public class UserModel extends AbstractModel {

    @Inject
    public UserModel(){

    }

    /*public static UserModel createInstance(){
        return new UserModel();
    }*/


    public void GetSMSCode(String mobile, final OnHandlerResultListener<GlobalShell<Boolean>> callbackListener){
        IUserService userService= createService(IUserService.class);
        userService.getSMSCode(mobile)
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

    public void Login(String mobile,int smsCode, final OnHandlerResultListener<GlobalShell<DoctorBean>> callbackListener) {
        Map<String, Object> params = new HashMap<>();
        params.put("Mobile", mobile);
        params.put("SmsCode", smsCode);
        IUserService userService= createService(IUserService.class);
        userService.login(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseBean<DoctorBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        GlobalShell<DoctorBean> entity=new GlobalShell<DoctorBean>(e.getMessage());
                        callbackListener.handlerResult(entity);
                    }

                    @Override
                    public void onNext(BaseBean<DoctorBean> resultBean) {
                        GlobalShell<DoctorBean> entity=null;
                        if(resultBean.state>0) {
                            DoctorBean result = resultBean.Data;
                            entity=new GlobalShell<DoctorBean>(result);
                        }
                        else{
                            entity=new GlobalShell<DoctorBean>(resultBean.message);
                        }
                        callbackListener.handlerResult(entity);
                    }
                });
    }

    public void LoginValidate(String account,String password, final OnHandlerResultListener<GlobalShell<DoctorBean>> callbackListener) {
        Map<String, Object> params = new HashMap<>();
        params.put("Account", account);
        params.put("Password", password);
        IUserService userService= createService(IUserService.class);
        userService.LoginValidate(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseBean<DoctorBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        GlobalShell<DoctorBean> entity=new GlobalShell<DoctorBean>(e.getMessage());
                        callbackListener.handlerResult(entity);
                    }

                    @Override
                    public void onNext(BaseBean<DoctorBean> resultBean) {
                        GlobalShell<DoctorBean> entity=null;
                        if(resultBean.state>0) {
                            DoctorBean result = resultBean.Data;
                            entity=new GlobalShell<DoctorBean>(result);
                        }
                        else{
                            entity=new GlobalShell<DoctorBean>(resultBean.message);
                        }
                        callbackListener.handlerResult(entity);
                    }
                });
    }

    public void GetUserDetail(int customerId, final OnHandlerResultListener<GlobalShell<CustomDetailBean>> callbackListener){
        IUserService userService=createService(IUserService.class);
        userService.GetCusInfo(customerId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseBean<CustomDetailBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        GlobalShell<CustomDetailBean> entity=new GlobalShell<CustomDetailBean>(e.getMessage());
                        callbackListener.handlerResult(entity);
                    }

                    @Override
                    public void onNext(BaseBean<CustomDetailBean> resultBean) {
                        GlobalShell<CustomDetailBean> entity=null;
                        if(resultBean.state>0) {
                            CustomDetailBean result = resultBean.Data;
                            entity=new GlobalShell<CustomDetailBean>(result);
                        }
                        else{
                            entity=new GlobalShell<CustomDetailBean>(resultBean.message);
                        }
                        callbackListener.handlerResult(entity);
                    }
                });

    }

}
