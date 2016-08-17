package haozuo.com.healthdoctor.model;

import android.support.annotation.NonNull;

import com.squareup.okhttp.OkHttpClient;

import java.util.List;

import javax.inject.Inject;

import haozuo.com.healthdoctor.bean.BaseBean;
import haozuo.com.healthdoctor.bean.ConsultDoneItemBean;
import haozuo.com.healthdoctor.bean.ConsultItemBean;
import haozuo.com.healthdoctor.bean.ConsultReplyBean;
import haozuo.com.healthdoctor.bean.FeedbackItemBean;
import haozuo.com.healthdoctor.bean.GlobalShell;
import haozuo.com.healthdoctor.bean.PageBean;
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
        mIConsultService.getPendingAskData(requestTag(), doctorId, pageIndex, pageSize, flag)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseBean<PageBean<ConsultItemBean>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        GlobalShell<PageBean<ConsultItemBean>> entity = new GlobalShell<PageBean<ConsultItemBean>>(e.getMessage());
                        callbackListener.handlerResult(entity);
                    }

                    @Override
                    public void onNext(BaseBean<PageBean<ConsultItemBean>> resultBean) {
                        GlobalShell<PageBean<ConsultItemBean>> entity = null;
                        if (resultBean.state > 0) {
                            PageBean<ConsultItemBean> result = resultBean.Data;
                            entity = new GlobalShell<PageBean<ConsultItemBean>>(result);
                        } else {
                            entity = new GlobalShell<PageBean<ConsultItemBean>>(resultBean.message);
                        }
                        callbackListener.handlerResult(entity);
                    }
                });
    }

    public void GetConsultDoneInfoList(int doctorId, String beginCommitOn, String endCommitOn, int pageIndex, int pageSize, final OnHandlerResultListener<GlobalShell<PageBean<ConsultDoneItemBean>>> callbackListener) {
        mIConsultService.GetProcessedAskData(requestTag(), doctorId, beginCommitOn, endCommitOn, pageIndex, pageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseBean<PageBean<ConsultDoneItemBean>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        GlobalShell<PageBean<ConsultDoneItemBean>> entity = new GlobalShell<PageBean<ConsultDoneItemBean>>(e.getMessage());
                        callbackListener.handlerResult(entity);
                    }

                    @Override
                    public void onNext(BaseBean<PageBean<ConsultDoneItemBean>> resultBean) {
                        GlobalShell<PageBean<ConsultDoneItemBean>> entity = null;
                        if (resultBean.state > 0) {
                            PageBean<ConsultDoneItemBean> result = resultBean.Data;
                            entity = new GlobalShell<PageBean<ConsultDoneItemBean>>(result);
                        } else {
                            entity = new GlobalShell<PageBean<ConsultDoneItemBean>>(resultBean.message);
                        }
                        callbackListener.handlerResult(entity);
                    }
                });
    }

    public void GetFeedbackInfoList(int flag, int doctorId, int pageIndex, int pageSize, final OnHandlerResultListener<GlobalShell<PageBean<FeedbackItemBean>>> callbackListener) {
        mIConsultService.GetFeedbackAskData(requestTag(), flag, doctorId, pageIndex, pageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseBean<PageBean<FeedbackItemBean>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        GlobalShell<PageBean<FeedbackItemBean>> entity = new GlobalShell<PageBean<FeedbackItemBean>>(e.getMessage());
                        callbackListener.handlerResult(entity);
                    }

                    @Override
                    public void onNext(BaseBean<PageBean<FeedbackItemBean>> resultBean) {
                        GlobalShell<PageBean<FeedbackItemBean>> entity = null;
                        if (resultBean.state > 0) {
                            PageBean<FeedbackItemBean> result = resultBean.Data;
                            entity = new GlobalShell<PageBean<FeedbackItemBean>>(result);
                        } else {
                            entity = new GlobalShell<PageBean<FeedbackItemBean>>(resultBean.message);
                        }
                        callbackListener.handlerResult(entity);
                    }
                });
    }

    public void GetConsultReplyList(int customerId, String commitOn, final OnHandlerResultListener<GlobalShell<List<ConsultReplyBean>>> callbackListener) {
        mIConsultService.getConsultReplyData(requestTag(), customerId, commitOn)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseBean<List<ConsultReplyBean>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        GlobalShell<List<ConsultReplyBean>> entity = new GlobalShell<List<ConsultReplyBean>>(e.getMessage());
                        callbackListener.handlerResult(entity);
                    }

                    @Override
                    public void onNext(BaseBean<List<ConsultReplyBean>> resultBean) {
                        GlobalShell<List<ConsultReplyBean>> entity = null;
                        if (resultBean.state > 0) {
                            List<ConsultReplyBean> result = resultBean.Data;
                            entity = new GlobalShell<List<ConsultReplyBean>>(result);
                        } else {
                            entity = new GlobalShell<List<ConsultReplyBean>>(resultBean.message);
                        }
                        callbackListener.handlerResult(entity);
                    }
                });
    }
}
