package haozuo.com.healthdoctor.contract;

import java.util.List;

import haozuo.com.healthdoctor.bean.ConsultReplyBean;
import haozuo.com.healthdoctor.bean.CustomDetailBean;

/**
 * Created by hzguest3 on 2016/8/3.
 */
public interface ConsultDetailContract {

    interface IConsultDetailView extends IBaseView<IConsultDetailPresenter> {

        void setPresenter(ConsultDetailContract.IConsultDetailPresenter presenter);

        void refreshCustomAdapter(List<ConsultReplyBean> dataList);

        void setListViewPosition(int position);

        void refreshFinish(int status);

        void loadmoreFinish(int status);

        void setCustmoerInfo(CustomDetailBean customDetailItem);

    }

    interface IConsultDetailPresenter extends IBasePresenter {

        void refreshConsultList();

        void loadmoreConsultList();

        void addDoctorReply(int DoctorId,int ReDoctorId,String ReDoctorName,int CustomerId,String ReplyContent,String ReplyTime);

        void getUserDetail(int CustomID);
    }

}

