package haozuo.com.healthdoctor.contract;

import haozuo.com.healthdoctor.bean.CustomBean;
import haozuo.com.healthdoctor.bean.GroupCustInfoBean;

/**
 * Created by xiongwei1 on 2016/7/25.
 */
public interface CustomerInfoContract {
    interface ICustomerInfoView extends BaseView<ICustomerInfoPresenter>{
        void InitView(GroupCustInfoBean custom);
    }

    interface ICustomerInfoPresenter extends BasePresenter{

    }
}
