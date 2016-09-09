package haozuo.com.healthdoctor.contract;

import java.util.List;

import haozuo.com.healthdoctor.bean.ConsultReplyBean;
import haozuo.com.healthdoctor.bean.CustomDetailBean;
import haozuo.com.healthdoctor.presenter.IBasePresenter;
import haozuo.com.healthdoctor.view.IBaseView;

/**
 * Created by hzguest3 on 2016/8/3.
 */
public interface ConsultDetailContract {

    interface IConsultDetailView extends IBaseView<IConsultDetailPresenter> {

        void setPresenter(ConsultDetailContract.IConsultDetailPresenter presenter);

        void refreshCustomAdapter(List<ConsultReplyBean> dataList);

        void setListViewPosition(int position, String SELECT_POSITION_TYPE);

        void refreshFinish(int status);

        void setCustmoerInfo(CustomDetailBean customDetailItem);

        void RefreshConsultPage(List<ConsultReplyBean> mConsultReplyBeanList);
    }

    interface IConsultDetailPresenter extends IBasePresenter {

        void loadmoreConsultList();

        void addDoctorReply(int DoctorId,int ReDoctorId,String ReDoctorName,int CustomerId,String ReplyContent,String ReplyTime);

        void getUserDetail(int CustomID);
    }

}

