package haozuo.com.healthdoctor.model;

import android.support.annotation.NonNull;

import com.squareup.okhttp.OkHttpClient;

import java.util.List;

import javax.inject.Inject;

import haozuo.com.healthdoctor.bean.BaseBean;
import haozuo.com.healthdoctor.bean.CustomDetailBean;
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
    IGroupService mIGroupService;
    IUserService mIUserService;

    @Inject
    public GroupModel(@NonNull OkHttpClient okHttpClient, @NonNull IGroupService iGroupService, @NonNull IUserService userService) {
        super(okHttpClient);
        mIGroupService = iGroupService;
        mIUserService = userService;
    }

    public void GetGroup(int doctorId, final OnHandlerResultListener<GlobalShell<List<DoctorGroupBean>>> callbackListener) {
        mIGroupService.getGroup(requestTag(), doctorId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseBean<List<DoctorGroupBean>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        GlobalShell<List<DoctorGroupBean>> entity = new GlobalShell<List<DoctorGroupBean>>(e.getMessage());
                        callbackListener.handlerResult(entity);
                    }

                    @Override
                    public void onNext(BaseBean<List<DoctorGroupBean>> resultBean) {
                        GlobalShell<List<DoctorGroupBean>> entity = null;
                        if (resultBean.state > 0) {
                            List<DoctorGroupBean> result = resultBean.Data;
                            entity = new GlobalShell<List<DoctorGroupBean>>(result);
                        } else {
                            entity = new GlobalShell<List<DoctorGroupBean>>(resultBean.message);
                        }
                        callbackListener.handlerResult(entity);
                    }
                });

    }

    public void GetGroupCustInfoList(int serviceDeptId, int groupId, int doctorId, String customNameOrMobile, int pageIndex, int pageSize, final OnHandlerResultListener<GlobalShell<PageBean<GroupCustInfoBean>>> callbackListener) {
        mIUserService.getGroupCustInfoList(requestTag(), serviceDeptId, doctorId, groupId, customNameOrMobile, pageIndex, pageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseBean<PageBean<GroupCustInfoBean>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        GlobalShell<PageBean<GroupCustInfoBean>> entity = new GlobalShell<PageBean<GroupCustInfoBean>>(e.getMessage());
                        callbackListener.handlerResult(entity);
                    }

                    @Override
                    public void onNext(BaseBean<PageBean<GroupCustInfoBean>> resultBean) {
                        GlobalShell<PageBean<GroupCustInfoBean>> entity = null;
                        if (resultBean.state > 0) {
                            PageBean<GroupCustInfoBean> result = resultBean.Data;
                            entity = new GlobalShell<PageBean<GroupCustInfoBean>>(result);
                        } else {
                            entity = new GlobalShell<PageBean<GroupCustInfoBean>>(resultBean.message);
                        }
                        callbackListener.handlerResult(entity);
                    }
                });
    }

    public void DeleteCustomerGroup(int customerId, int groupId, String operateBy, final OnHandlerResultListener<GlobalShell<Boolean>> callbackListener) {
        mIGroupService.DeleteGroup(requestTag(), customerId, groupId, operateBy)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseBean<Boolean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        GlobalShell<Boolean> entity = new GlobalShell<Boolean>(e.getMessage());
                        callbackListener.handlerResult(entity);
                    }

                    @Override
                    public void onNext(BaseBean<Boolean> resultBean) {
                        GlobalShell<Boolean> entity = null;
                        if (resultBean.state > 0) {
                            boolean result = resultBean.Data;
                            entity = new GlobalShell<Boolean>(result);
                        } else {
                            entity = new GlobalShell<Boolean>(resultBean.message);
                        }
                        callbackListener.handlerResult(entity);
                    }
                });
    }

    public void GetUserDetail(int customerId, final OnHandlerResultListener<GlobalShell<CustomDetailBean>> callbackListener) {
        mIUserService.GetCusInfo(requestTag(), customerId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseBean<CustomDetailBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        GlobalShell<CustomDetailBean> entity = new GlobalShell<CustomDetailBean>(e.getMessage());
                        callbackListener.handlerResult(entity);
                    }

                    @Override
                    public void onNext(BaseBean<CustomDetailBean> resultBean) {
                        GlobalShell<CustomDetailBean> entity = null;
                        if (resultBean.state > 0) {
                            CustomDetailBean result = resultBean.Data;
                            entity = new GlobalShell<CustomDetailBean>(result);
                        } else {
                            entity = new GlobalShell<CustomDetailBean>(resultBean.message);
                        }
                        callbackListener.handlerResult(entity);
                    }
                });
    }


}
