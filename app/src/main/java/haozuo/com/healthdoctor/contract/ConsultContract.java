package haozuo.com.healthdoctor.contract;

import java.util.List;

import haozuo.com.healthdoctor.bean.DoctorGroupBean;

/**
 * Created by hzguest3 on 2016/8/1.
 */
public interface ConsultContract {

    interface IConsultView extends BaseView<IConsultPresenter>{
        void InitView();
    }

    interface IConsultPresenter extends BasePresenter{

    }
}
