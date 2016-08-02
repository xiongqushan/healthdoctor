package haozuo.com.healthdoctor.contract;

import java.util.List;

import haozuo.com.healthdoctor.bean.ConsultDetailBean;
import haozuo.com.healthdoctor.bean.DoctorGroupBean;

/**
 * Created by hzguest3 on 2016/8/1.
 */
public interface ConsultContract {

    interface IConsultView extends IBaseView<IConsultPresenter>{
        void RefreshPendingPageList();
    }

    interface IConsultPresenter extends IBasePresenter{
        void getConsultList();
    }
}
