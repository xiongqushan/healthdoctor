package haozuo.com.healthdoctor.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public static UserModel createInstance(){
        return new UserModel();
    }

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

    public void LoginValidate(String account,int password, final OnHandlerResultListener<GlobalShell<DoctorBean>> callbackListener) {
        Map<String, Object> params = new HashMap<>();
        params.put("Account", account);
        params.put("Password", password);
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

    public void GetGroup(int doctorId, final OnHandlerResultListener<GlobalShell<List<DoctorGroupBean>>> callbackListener){
        IGroupService groupService=createService(IGroupService.class);
        groupService.getGroup(doctorId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseBean<List<DoctorGroupBean>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        GlobalShell<List<DoctorGroupBean>> entity=new GlobalShell<List<DoctorGroupBean>>(e.getMessage());
                        callbackListener.handlerResult(entity);
                    }

                    @Override
                    public void onNext(BaseBean<List<DoctorGroupBean>> resultBean) {
                        GlobalShell<List<DoctorGroupBean>> entity=null;
                        if(resultBean.state>0) {
                            List<DoctorGroupBean> result = resultBean.Data;
                            entity=new GlobalShell<List<DoctorGroupBean>>(result);
                        }
                        else{
                            entity=new GlobalShell<List<DoctorGroupBean>>(resultBean.message);
                        }
                        callbackListener.handlerResult(entity);
                    }
                });

    }

    public void GetGroupCustInfoList(int serviceDeptId,int groupId,int doctorId,int pageIndex,int pageSize, final OnHandlerResultListener<GlobalShell<PageBean<GroupCustInfoBean>>> callbackListener){
        IUserService userService=createService(IUserService.class);
        userService.getGroupCustInfoList(serviceDeptId,doctorId,groupId,"",pageIndex,pageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseBean<PageBean<GroupCustInfoBean>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        GlobalShell<PageBean<GroupCustInfoBean>> entity=new GlobalShell<PageBean<GroupCustInfoBean>>(e.getMessage());
                        callbackListener.handlerResult(entity);
                    }

                    @Override
                    public void onNext(BaseBean<PageBean<GroupCustInfoBean>> resultBean) {
                        GlobalShell<PageBean<GroupCustInfoBean>> entity=null;
                        if(resultBean.state>0) {
                            PageBean<GroupCustInfoBean> result = resultBean.Data;
                            entity=new GlobalShell<PageBean<GroupCustInfoBean>>(result);
                        }
                        else{
                            entity=new GlobalShell<PageBean<GroupCustInfoBean>>(resultBean.message);
                        }
                        callbackListener.handlerResult(entity);
                    }
                });
    }

    public void GetUserDetail(int customerId, final OnHandlerResultListener<GlobalShell<GroupCustInfoBean>> callbackListener){
        IUserService userService=createService(IUserService.class);
        userService.GetCusInfo(customerId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseBean<GroupCustInfoBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        GlobalShell<GroupCustInfoBean> entity=new GlobalShell<GroupCustInfoBean>(e.getMessage());
                        callbackListener.handlerResult(entity);
                    }

                    @Override
                    public void onNext(BaseBean<GroupCustInfoBean> resultBean) {
                        GlobalShell<GroupCustInfoBean> entity=null;
                        if(resultBean.state>0) {
                            GroupCustInfoBean result = resultBean.Data;
                            entity=new GlobalShell<GroupCustInfoBean>(result);
                        }
                        else{
                            entity=new GlobalShell<GroupCustInfoBean>(resultBean.message);
                        }
                        callbackListener.handlerResult(entity);
                    }
                });
    }

}
