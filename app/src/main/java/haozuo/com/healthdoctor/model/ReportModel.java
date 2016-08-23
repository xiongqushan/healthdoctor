package haozuo.com.healthdoctor.model;

import android.support.annotation.NonNull;

import com.squareup.okhttp.OkHttpClient;

import java.util.List;

import javax.inject.Inject;

import haozuo.com.healthdoctor.bean.BaseBean;
import haozuo.com.healthdoctor.bean.ReportParamsBean;
import haozuo.com.healthdoctor.bean.GlobalShell;
import haozuo.com.healthdoctor.bean.RequestPhotoReportListBean;
import haozuo.com.healthdoctor.listener.OnHandlerResultListener;
import haozuo.com.healthdoctor.service.IReportService;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by hzguest3 on 2016/8/22.
 */
public class ReportModel extends AbstractModel{
    IReportService mIReportService;

    @Inject
    public ReportModel(@NonNull OkHttpClient okHttpClient, @NonNull IReportService iReportService) {
        super(okHttpClient);
        mIReportService = iReportService;
    }

    public void getReportParams(int customerId, final OnHandlerResultListener<GlobalShell<List<ReportParamsBean>>> callbackListener) {
        mIReportService.getReportParams(requestTag(), customerId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseBean<List<ReportParamsBean>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        GlobalShell<List<ReportParamsBean>> entity = new GlobalShell<List<ReportParamsBean>>(e.getMessage());
                        callbackListener.handlerResult(entity);
                    }

                    @Override
                    public void onNext(BaseBean<List<ReportParamsBean>> resultBean) {
                        GlobalShell<List<ReportParamsBean>> entity = null;
                        if (resultBean.state > 0) {
                            List<ReportParamsBean> result = resultBean.Data;
                            entity = new GlobalShell<List<ReportParamsBean>>(result);
                        } else {
                            entity = new GlobalShell<List<ReportParamsBean>>(resultBean.message);
                        }
                        callbackListener.handlerResult(entity);
                    }
                });

    }

    public void requestPhotoReportList(String accountID, final OnHandlerResultListener<GlobalShell<List<RequestPhotoReportListBean>>> callbackListener) {
        mIReportService.requestPhotoReportList(requestTag(), accountID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseBean<List<RequestPhotoReportListBean>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        GlobalShell<List<RequestPhotoReportListBean>> entity = new GlobalShell<List<RequestPhotoReportListBean>>(e.getMessage());
                        callbackListener.handlerResult(entity);
                    }

                    @Override
                    public void onNext(BaseBean<List<RequestPhotoReportListBean>> resultBean) {
                        GlobalShell<List<RequestPhotoReportListBean>> entity = null;
                        if (resultBean.state > 0) {
                            List<RequestPhotoReportListBean> result = resultBean.Data;
                            entity = new GlobalShell<List<RequestPhotoReportListBean>>(result);
                        } else {
                            entity = new GlobalShell<List<RequestPhotoReportListBean>>(resultBean.message);
                        }
                        callbackListener.handlerResult(entity);
                    }
                });

    }



}
