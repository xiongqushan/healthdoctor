package haozuo.com.healthdoctor.contract;

import java.util.List;

import haozuo.com.healthdoctor.bean.CustomDetailBean;
import haozuo.com.healthdoctor.bean.DoctorGroupBean;
import haozuo.com.healthdoctor.presenter.IBasePresenter;
import haozuo.com.healthdoctor.view.IBaseView;

/**
 * Created by xiongwei1 on 2016/7/25.
 */
public interface CustomerInfoContract {
    interface ICustomerInfoView extends IBaseView<ICustomerInfoPresenter> {

        void InitView(CustomDetailBean custom);

        void addLabelView(DoctorGroupBean groupBean);

        void refreshLabelView(CustomDetailBean customInfo);
    }

    interface ICustomerInfoPresenter extends IBasePresenter {

        void DeleteCustomerGroup(DoctorGroupBean groupBean);

        void InitGroupLabel();
    }
}
