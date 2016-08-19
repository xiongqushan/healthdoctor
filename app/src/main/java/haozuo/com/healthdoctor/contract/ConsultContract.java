package haozuo.com.healthdoctor.contract;

import java.util.List;

import haozuo.com.healthdoctor.bean.ConsultDoneItemBean;
import haozuo.com.healthdoctor.bean.ConsultItemBean;
import haozuo.com.healthdoctor.bean.FeedbackItemBean;

/**
 * Created by hzguest3 on 2016/8/1.
 */
public interface ConsultContract {

//    interface IConsultPendingView extends IBaseView<IConsultPendingPresenter> {
//        void refreshPendingPageList(List<ConsultItemBean> consultItemBeanList);
//
//        void refreshFinish(int status);
//
//    }
//
//    interface IConsultPendingPresenter extends IBasePresenter {
//        void refreshPendingPageList(int flag);
//
//        void loadmorePendingPageList(int flag);
//
//    }
//
//    interface IConsultDoneView extends IBaseView<IConsultDonePresenter> {
//        void refreshConsultDoneList(List<ConsultDoneItemBean> dataList);
//
//        void refreshFinish(int status);
//
//    }
//
//    interface IConsultDonePresenter extends IBasePresenter {
//        void refreshConsultDoneList(int flag);
//
//        void loadmoreConsultDoneList(int flag);
//
//    }
//
//    interface IConsultFeedbackView extends IBaseView<IConsultFeedbackPresenter> {
//        void refreshFeedbackList(List<FeedbackItemBean> dataList);
//
//        void refreshFinish(int status);
//
//    }
//
//    interface IConsultFeedbackPresenter extends IBasePresenter {
//        void refreshFeedbackList(int flag);
//
//        void loadmoreFeedbackList(int flag);
//
//    }

    interface IConsultView extends IBaseView<IConsultPresenter> {
        void refreshPendingPageList(List<ConsultItemBean> consultItemBeanList, int flag);

        void refreshFinish(int status, int flag);

        void refreshConsultDonePageList(List<ConsultDoneItemBean> dataList,int flag);

        void refreshConsultDonePageFinish(int status,int flag);

        void refreshFeedbackPageList(List<FeedbackItemBean> dataList,int flag);

        void refreshFeedbackPageFinish(int status,int flag);

    }

    interface IConsultPresenter extends IBasePresenter {

        void refreshCustomList(int flag,boolean isPullToRefresh);

        void loadmoreCustomList(int flag);

        void refreshConsultDoneList(int flag);

        void loadmoreConsultDoneList(int flag);

        void refreshFeedBackList(int flag);

        void loadmoreFeedBackList(int flag);
    }
}
