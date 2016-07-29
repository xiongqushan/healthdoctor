package haozuo.com.healthdoctor.contract;

import java.util.List;

import haozuo.com.healthdoctor.bean.CustomDetailBean;
import haozuo.com.healthdoctor.bean.DoctorGroupBean;

/**
 * Created by xiongwei1 on 2016/7/25.
 */
public interface CustomerInfoContract {
    interface ICustomerInfoView extends BaseView<ICustomerInfoPresenter>{
        void InitView(CustomDetailBean custom);
        void addLabelView(DoctorGroupBean groupBean);
        void refreshLabelView(List<DoctorGroupBean> mGroups);
    }

    interface ICustomerInfoPresenter extends BasePresenter{
        void DeleteCustomerGroup(DoctorGroupBean groupBean);
        void InitGroupLabel();
    }
}
