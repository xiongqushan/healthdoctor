package haozuo.com.healthdoctor.contract;

import haozuo.com.healthdoctor.bean.ReportDetailBean;
import haozuo.com.healthdoctor.presenter.IBasePresenter;
import haozuo.com.healthdoctor.view.IBaseView;

/**
 * by zy on 2016.08.19.
 */
public interface CustomerReportContract {
    interface ICustomerReportView extends IBaseView<ICustomerReportPresenter> {
        void changeRetryLayer(boolean isShow);

        void updateChildUI(ReportDetailBean bean);

    }

    interface ICustomerReportPresenter extends IBasePresenter {

    }
}
