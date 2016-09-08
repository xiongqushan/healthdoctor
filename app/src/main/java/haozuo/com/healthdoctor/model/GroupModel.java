package haozuo.com.healthdoctor.model;

import android.support.annotation.NonNull;

import com.squareup.okhttp.OkHttpClient;

import java.util.List;

import javax.inject.Inject;

import haozuo.com.healthdoctor.bean.DoctorGroupBean;
import haozuo.com.healthdoctor.bean.GlobalShell;
import haozuo.com.healthdoctor.bean.GroupCustInfoBean;
import haozuo.com.healthdoctor.bean.PageBean;
import haozuo.com.healthdoctor.listener.OnHandlerResultListener;
import haozuo.com.healthdoctor.service.IGroupService;
import haozuo.com.healthdoctor.service.IReportService;
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
    IReportService mIReportService;

    @Inject
    public GroupModel(@NonNull OkHttpClient okHttpClient, @NonNull IGroupService iGroupService, @NonNull IUserService userService) {
        super(okHttpClient);
        mIGroupService = iGroupService;
        mIUserService = userService;
    }

    public void GetGroup(int doctorId, final OnHandlerResultListener<GlobalShell<List<DoctorGroupBean>>> callbackListener) {
        Subscriber subscriber = getSubscriber(callbackListener);
        mIGroupService.getGroup(doctorId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

    }

    public void GetGroupCustInfoList(int serviceDeptId, int groupId, int doctorId, String customNameOrMobile, int pageIndex, int pageSize, final OnHandlerResultListener<GlobalShell<PageBean<GroupCustInfoBean>>> callbackListener) {
        Subscriber subscriber = getSubscriber(callbackListener);
        mIUserService.getGroupCustInfoList(serviceDeptId, doctorId, groupId, customNameOrMobile, pageIndex, pageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void DeleteCustomerGroup(int customerId, int groupId, String operateBy, final OnHandlerResultListener<GlobalShell<Boolean>> callbackListener) {
        Subscriber subscriber = getSubscriber(callbackListener);
        mIGroupService.DeleteGroup(customerId, groupId, operateBy)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

}
