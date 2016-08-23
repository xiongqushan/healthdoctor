package haozuo.com.healthdoctor.contract;

import java.util.List;

import haozuo.com.healthdoctor.bean.CustomDetailBean;
import haozuo.com.healthdoctor.bean.ReportParamsBean;
import haozuo.com.healthdoctor.bean.RequestPhotoReportListBean;

/**
 * Created by hzguest3 on 2016/7/22.
 */
public interface CustomDetailContract {

    interface ICustomDetailView extends IBaseView<ICustomDetailPresenter>{
        void InitView(CustomDetailBean custom);

        void changeRetryLayer(boolean isShow);

        void RefreshReportParams(List<ReportParamsBean> ReportParamList);

        void RefreshPhotoReport(List<RequestPhotoReportListBean> RequestPhotoReportList);

    }

    interface ICustomDetailPresenter extends IBasePresenter {

    }
}
