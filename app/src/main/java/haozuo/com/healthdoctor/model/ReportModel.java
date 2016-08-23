package haozuo.com.healthdoctor.model;

import android.support.annotation.NonNull;

import com.squareup.okhttp.OkHttpClient;

import java.util.List;

import javax.inject.Inject;

import haozuo.com.healthdoctor.bean.BaseBean;
import haozuo.com.healthdoctor.bean.GlobalShell;
import haozuo.com.healthdoctor.bean.ReportDetailBean;
import haozuo.com.healthdoctor.bean.ReportParamsBean;
import haozuo.com.healthdoctor.listener.OnHandlerResultListener;
import haozuo.com.healthdoctor.service.IReportService;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by hzguest3 on 2016/8/22.
 */
public class ReportModel extends AbstractModel {
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

    public void GetReportDetailInfo(int customerId, String checkCode, String workNo, final OnHandlerResultListener<GlobalShell<ReportDetailBean>> callbackListener) {
        mIReportService.GetHealthReport(requestTag(), customerId, checkCode, workNo).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseBean<ReportDetailBean>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        GlobalShell<ReportDetailBean> entity = new GlobalShell<ReportDetailBean>(e.getMessage());
                        callbackListener.handlerResult(entity);
                    }

                    @Override
                    public void onNext(BaseBean<ReportDetailBean> resultBean) {
                        GlobalShell<ReportDetailBean> entity = null;
                        if (resultBean.state > 0) {
                            ReportDetailBean result = resultBean.Data;
                            entity = new GlobalShell<ReportDetailBean>(result);
                        } else {
                            entity = new GlobalShell<ReportDetailBean>(resultBean.message);
                        }
                        callbackListener.handlerResult(entity);
                    }
                });
    }

}
