package haozuo.com.healthdoctor.contract;

import java.util.List;

import haozuo.com.healthdoctor.bean.DoctorGroupBean;

/**
 * Created by hzguest3 on 2016/8/3.
 */
public interface ConsultDetailContract {

    interface IConsultDetailView extends IBaseView<IConsultDetailPresenter> {
        void setPresenter(ConsultDetailContract.IConsultDetailPresenter presenter);
    }

    interface IConsultDetailPresenter extends IBasePresenter {

    }

}

