package haozuo.com.healthdoctor.contract;

import haozuo.com.healthdoctor.bean.CustomDetailBean;
import haozuo.com.healthdoctor.bean.GroupCustInfoBean;

/**
 * Created by xiongwei1 on 2016/7/25.
 */
public interface CustomerInfoContract {
    interface ICustomerInfoView extends BaseView<ICustomerInfoPresenter>{
        void InitView(CustomDetailBean custom);
    }

    interface ICustomerInfoPresenter extends BasePresenter{
        void DeleteCustomerGroup(int DeleteGroupId);
    }
}
