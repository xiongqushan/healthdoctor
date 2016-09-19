package haozuo.com.healthdoctor.contract;

import java.util.List;

import haozuo.com.healthdoctor.bean.ConsultReplyBean;
import haozuo.com.healthdoctor.bean.UsefulExpressionBean;
import haozuo.com.healthdoctor.presenter.IBasePresenter;
import haozuo.com.healthdoctor.view.IBaseView;

/**
 * Created by hzguest3 on 2016/8/9.
 */
public interface UsefulMessageContract {

    interface IUsefulMessageView extends IBaseView<IUsefulMessagePresenter> {

        void refreshUsefulMessageAdapter(List<UsefulExpressionBean> dataList);

        void refreshConsultList(ConsultReplyBean addConsultReplyBean);

    }

    interface IUsefulMessagePresenter extends IBasePresenter {

//        void getDefaultUsefulExpression();

        void addDoctorReply(int DoctorId, final int ReDoctorId, String ReDoctorName, int CustomerId, final String ReplyContent, final String ReplyTime);

        void searchUsefulExpression(String keyword);

    }
}
