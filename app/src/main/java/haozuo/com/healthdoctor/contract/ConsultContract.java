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
        void refreshPendingPageList(List<ConsultItemBean> consultItemBeanList);

        void refreshFinish(int status);

        void refreshConsultDonePageList(List<ConsultDoneItemBean> dataList);

        void refreshConsultDonePageFinish(int status);

        void refreshFeedbackPageList(List<FeedbackItemBean> dataList);

        void refreshFeedbackPageFinish(int status);

    }

    interface IConsultPresenter extends IBasePresenter {

        void refreshCustomList(int flag);

        void loadmoreCustomList(int flag);

        void refreshConsultDoneList(int flag);

        void loadmoreConsultDoneList(int flag);

        void refreshFeedBackList(int flag);

        void loadmoreFeedBackList(int flag);
    }
}
