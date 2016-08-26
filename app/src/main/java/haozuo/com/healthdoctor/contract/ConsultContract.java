package haozuo.com.healthdoctor.contract;

import java.util.List;

import haozuo.com.healthdoctor.bean.ConsultDoneItemBean;
import haozuo.com.healthdoctor.bean.ConsultItemBean;
import haozuo.com.healthdoctor.bean.FeedbackItemBean;
import haozuo.com.healthdoctor.presenter.IBasePresenter;
import haozuo.com.healthdoctor.view.IBaseView;

/**
 * Created by hzguest3 on 2016/8/1.
 */
public interface ConsultContract {


    interface IConsultView extends IBaseView<IConsultPresenter> {
        void refreshPendingPageList(List<ConsultItemBean> consultItemBeanList, int flag, boolean isRefresh);

        void refreshFinish(int status, int flag, boolean isRefresh);

        void refreshConsultDonePageList(List<ConsultDoneItemBean> dataList, int flag, boolean isRefresh);

        void refreshConsultDonePageFinish(int status, int flag, boolean isRefresh);

        void refreshFeedbackPageList(List<FeedbackItemBean> dataList, int flag, boolean isRefresh);

        void refreshFeedbackPageFinish(int status, int flag, boolean isRefresh);

        void updateMsgCounts(int newCount);

    }

    interface IConsultPresenter extends IBasePresenter {

        void refreshCustomList(int flag, boolean initData);

        void loadmoreCustomList(int flag, int pageIndex);

        void refreshConsultDoneList(int flag, boolean initData);

        void loadmoreConsultDoneList(int flag, int pageIndex);

        void refreshFeedBackList(int flag, boolean initData);

        void loadmoreFeedBackList(int flag, int pageIndex);
    }
}
