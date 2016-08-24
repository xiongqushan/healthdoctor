package haozuo.com.healthdoctor.contract;

import java.util.List;

import haozuo.com.healthdoctor.bean.ConsultDoneItemBean;
import haozuo.com.healthdoctor.bean.ConsultItemBean;
import haozuo.com.healthdoctor.bean.FeedbackItemBean;

/**
 * Created by hzguest3 on 2016/8/1.
 */
public interface ConsultContract {


    interface IConsultView extends IBaseView<IConsultPresenter> {
        void refreshPendingPageList(List<ConsultItemBean> consultItemBeanList, int flag);

        void refreshFinish(int status, int flag, boolean isRefresh);

        void refreshConsultDonePageList(List<ConsultDoneItemBean> dataList, int flag);

        void refreshConsultDonePageFinish(int status, int flag, boolean isRefresh);

        void refreshFeedbackPageList(List<FeedbackItemBean> dataList, int flag);

        void refreshFeedbackPageFinish(int status, int flag, boolean isRefresh);

        void updateMsgCounts(int newCount);

    }

    interface IConsultPresenter extends IBasePresenter {

        void refreshCustomList(int flag, boolean initData);

        void loadmoreCustomList(int flag);

        void refreshConsultDoneList(int flag, boolean initData);

        void loadmoreConsultDoneList(int flag);

        void refreshFeedBackList(int flag, boolean initData);

        void loadmoreFeedBackList(int flag);
    }
}
