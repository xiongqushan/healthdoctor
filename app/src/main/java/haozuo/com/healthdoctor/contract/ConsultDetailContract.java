package haozuo.com.healthdoctor.contract;

import java.util.List;

import haozuo.com.healthdoctor.bean.ConsultReplyBean;

/**
 * Created by hzguest3 on 2016/8/3.
 */
public interface ConsultDetailContract {

    interface IConsultDetailView extends IBaseView<IConsultDetailPresenter> {

        void setPresenter(ConsultDetailContract.IConsultDetailPresenter presenter);

        void refreshCustomAdapter(List<ConsultReplyBean> dataList);

        void refreshFinish(int status);

    }

    interface IConsultDetailPresenter extends IBasePresenter {

        void refreshConsultList();

        void loadmoreConsultList();
    }

}

