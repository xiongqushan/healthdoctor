package haozuo.com.healthdoctor.model;

import java.util.List;

import javax.inject.Inject;

import haozuo.com.healthdoctor.bean.BaseBean;
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
 * Created by xiongwei1 on 2016/7/27.
 */
public class GroupModel extends AbstractModel {

    @Inject
    public GroupModel(){
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
