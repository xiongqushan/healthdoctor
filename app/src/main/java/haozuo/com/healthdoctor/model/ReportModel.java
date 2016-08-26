package haozuo.com.healthdoctor.model;

import android.support.annotation.NonNull;

import com.squareup.okhttp.OkHttpClient;

import java.util.List;

import javax.inject.Inject;

import haozuo.com.healthdoctor.bean.GlobalShell;
import haozuo.com.healthdoctor.bean.ReportDetailBean;
import haozuo.com.healthdoctor.bean.ReportParamsBean;
import haozuo.com.healthdoctor.bean.RequestPhotoReportListBean;
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
        Subscriber subscriber = getSubscriber(callbackListener);
        mIReportService.getReportParams(customerId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void GetReportDetailInfo(int customerId, String checkCode, String workNo, final OnHandlerResultListener<GlobalShell<ReportDetailBean>> callbackListener) {
        Subscriber subscriber = getSubscriber(callbackListener);
        mIReportService.GetHealthReport(customerId, checkCode, workNo).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void requestPhotoReportList(String accountID, final OnHandlerResultListener<GlobalShell<List<RequestPhotoReportListBean>>> callbackListener) {
        Subscriber subscriber = getSubscriber(callbackListener);
        mIReportService.requestPhotoReportList(accountID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

    }


}
