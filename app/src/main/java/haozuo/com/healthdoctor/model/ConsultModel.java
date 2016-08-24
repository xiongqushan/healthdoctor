package haozuo.com.healthdoctor.model;

import android.support.annotation.NonNull;

import com.squareup.okhttp.OkHttpClient;


import javax.inject.Inject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import haozuo.com.healthdoctor.bean.BaseBean;
import haozuo.com.healthdoctor.bean.ConsultDoneItemBean;
import haozuo.com.healthdoctor.bean.ConsultItemBean;
import haozuo.com.healthdoctor.bean.ConsultReplyBean;
import haozuo.com.healthdoctor.bean.FeedbackItemBean;
import haozuo.com.healthdoctor.bean.GlobalShell;
import haozuo.com.healthdoctor.bean.PageBean;
import haozuo.com.healthdoctor.bean.UsefulExpressionBean;
import haozuo.com.healthdoctor.listener.OnHandlerResultListener;
import haozuo.com.healthdoctor.service.IConsultService;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xiongwei1 on 2016/8/2.
 */
public class ConsultModel extends AbstractModel {
    IConsultService mIConsultService;

    @Inject
    public ConsultModel(@NonNull OkHttpClient okHttpClient, @NonNull IConsultService iConsultService) {
        super(okHttpClient);
        mIConsultService = iConsultService;
    }

    public void GetGroupCustInfoList(int doctorId, int pageIndex, int pageSize, int flag, final OnHandlerResultListener<GlobalShell<PageBean<ConsultItemBean>>> callbackListener) {
        Subscriber subscriber=getSubscriber(callbackListener);
        mIConsultService.getPendingAskData(doctorId, pageIndex, pageSize, flag)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void GetConsultDoneInfoList(int doctorId, String beginCommitOn, String endCommitOn, int pageIndex, int pageSize, final OnHandlerResultListener<GlobalShell<PageBean<ConsultDoneItemBean>>> callbackListener) {
        Subscriber subscriber=getSubscriber(callbackListener);
        mIConsultService.GetProcessedAskData(doctorId, beginCommitOn, endCommitOn, pageIndex, pageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void GetFeedbackInfoList(int flag, int doctorId, int pageIndex, int pageSize, final OnHandlerResultListener<GlobalShell<PageBean<FeedbackItemBean>>> callbackListener) {
        Subscriber subscriber=getSubscriber(callbackListener);
        mIConsultService.GetFeedbackAskData(flag, doctorId, pageIndex, pageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void GetConsultReplyList(int customerId, String commitOn, final OnHandlerResultListener<GlobalShell<List<ConsultReplyBean>>> callbackListener) {
        Subscriber subscriber=getSubscriber(callbackListener);
        mIConsultService.getConsultReplyData(customerId, commitOn)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void getUsefulExpression(final OnHandlerResultListener<GlobalShell<List<UsefulExpressionBean>>> callbackListener) {
        Subscriber subscriber=getSubscriber(callbackListener);
        mIConsultService.getUsefulExpression()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void searchUsefulExpression(String keyWord, final OnHandlerResultListener<GlobalShell<List<UsefulExpressionBean>>> callbackListener) {
        Subscriber subscriber=getSubscriber(callbackListener);
        mIConsultService.searchUsefulExpression(keyWord)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void addDoctorReply(int DoctorId,int ReDoctorId,String ReDoctorName,int CustomerId,String ReplyContent,String ReplyTime, final OnHandlerResultListener<GlobalShell<Boolean>> callbackListener) {
        Subscriber subscriber=getSubscriber(callbackListener);
        Map<String, Object> params = new HashMap<>();
        params.put("DoctorId", DoctorId);
        params.put("ReDoctorId", ReDoctorId);
        params.put("ReDoctorName", ReDoctorName);
        params.put("CustomerId", CustomerId);
        params.put("ReplyContent", ReplyContent);
        params.put("ReplyTime", ReplyTime);
        mIConsultService.addDoctorReply(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
