package haozuo.com.healthdoctor.contract;

import java.util.List;

import haozuo.com.healthdoctor.bean.BasConstBean;
import haozuo.com.healthdoctor.bean.ServiceDeptBean;
import haozuo.com.healthdoctor.presenter.IBasePresenter;
import haozuo.com.healthdoctor.view.IBaseView;

/**
 * Created by zhangzhongyao on 2016/9/7.
 */
public interface DoctorInfoContract {

    interface IDoctorInfoView extends IBaseView<IDoctorInfoPresenter> {

        void updateUI(List<BasConstBean> listJob, List<ServiceDeptBean> listDept);

        //void setServiceDept(List<ServiceDeptBean> list);

        void changeRetryLayer(boolean isShow);
    }

    interface IDoctorInfoPresenter extends IBasePresenter {
        void GetBasConstList();

        void GetServiceDeptList();
    }
}
