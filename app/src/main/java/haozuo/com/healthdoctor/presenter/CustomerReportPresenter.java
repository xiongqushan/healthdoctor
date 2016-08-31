package haozuo.com.healthdoctor.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import haozuo.com.healthdoctor.bean.GlobalShell;
import haozuo.com.healthdoctor.bean.ReportDetailBean;
import haozuo.com.healthdoctor.bean.ReportParamsBean;
import haozuo.com.healthdoctor.contract.CustomerReportContract;
import haozuo.com.healthdoctor.model.IBaseModel;
import haozuo.com.healthdoctor.view.IBaseView;
import haozuo.com.healthdoctor.listener.OnHandlerResultListener;
import haozuo.com.healthdoctor.model.ReportModel;

/**
 * by zy 2016.08.23.
 */
public class CustomerReportPresenter extends AbstractPresenter implements CustomerReportContract.ICustomerReportPresenter {
    private CustomerReportContract.ICustomerReportView mICustomerReportView;
    private ReportModel mReportModel;
    private ReportParamsBean mReportParamsBean;

    @Inject
    public CustomerReportPresenter(@NonNull CustomerReportContract.ICustomerReportView mICustomerReportView, @NonNull ReportModel reportModel, @NonNull ReportParamsBean bean) {
        this.mICustomerReportView = mICustomerReportView;
        mReportModel = reportModel;
        mICustomerReportView.setPresenter(this);
        mReportParamsBean = bean;
    }


    @Override
    public void start() {
        mICustomerReportView.showDialog();
//        if (logicSuccess) {
//            mICustomerReportView.changeRetryLayer(false);
//        } else {
//            logicSuccess = true;
//            mICustomerReportView.changeRetryLayer(true);
//        }
//        mReportModel.GetReportDetailInfo(138, "bjbr002", "0000000081", new OnHandlerResultListener<GlobalShell<ReportDetailBean>>() {
        mReportModel.GetReportDetailInfo(mReportParamsBean.customerId, mReportParamsBean.CheckUnitCode, mReportParamsBean.WorkNo, new OnHandlerResultListener<GlobalShell<ReportDetailBean>>() {
            @Override
            public void handlerResult(GlobalShell<ReportDetailBean> resultData) {
                if (resultData.LogicSuccess) {
                    mICustomerReportView.hideDialog();
                    ReportDetailBean bean = resultData.Data;
                    mICustomerReportView.updateChildUI(bean);
                    mICustomerReportView.changeRetryLayer(false);

                } else {
                    mICustomerReportView.hideDialog(resultData.Message);
                    mICustomerReportView.changeRetryLayer(true);
                }
            }
        });


    }

    @Override
    public IBaseView getBaseView() {
        return mICustomerReportView;
    }

    @Override
    public IBaseModel[] getBaseModelList() {
        return new IBaseModel[]{mReportModel};
    }


}
