package haozuo.com.healthdoctor.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import haozuo.com.healthdoctor.bean.GlobalShell;
import haozuo.com.healthdoctor.bean.ReportDetailBean;
import haozuo.com.healthdoctor.contract.CustomerReportContract;
import haozuo.com.healthdoctor.contract.IBaseModel;
import haozuo.com.healthdoctor.contract.IBaseView;
import haozuo.com.healthdoctor.listener.OnHandlerResultListener;
import haozuo.com.healthdoctor.model.ReportModel;

/**
 * by zy 2016.08.23.
 */
public class CustomerReportPresenter extends AbstractPresenter implements CustomerReportContract.ICustomerReportPresenter {
    private CustomerReportContract.ICustomerReportView mICustomerReportView;
    private ReportModel mReportModel;
//    private int mCustomerId;

    @Inject
    public CustomerReportPresenter(@NonNull CustomerReportContract.ICustomerReportView mICustomerReportView, @NonNull ReportModel reportModel) {
        this.mICustomerReportView = mICustomerReportView;
        mReportModel = reportModel;
        mICustomerReportView.setPresenter(this);
//        mCustomerId = customerId;
    }

    private boolean logicSuccess;

    @Override
    public void start() {
        mICustomerReportView.showDialog();
//        if (logicSuccess) {
//            mICustomerReportView.changeRetryLayer(false);
//        } else {
//            logicSuccess = true;
//            mICustomerReportView.changeRetryLayer(true);
//        }
        mReportModel.GetReportDetailInfo(138, "bjbr002", "0000000081", new OnHandlerResultListener<GlobalShell<ReportDetailBean>>() {
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
