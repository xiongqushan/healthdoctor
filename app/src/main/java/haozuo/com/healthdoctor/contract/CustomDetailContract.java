package haozuo.com.healthdoctor.contract;

import haozuo.com.healthdoctor.bean.GroupCustInfoBean;

/**
 * Created by hzguest3 on 2016/7/22.
 */
public interface CustomDetailContract {
    interface ICustomDetailView extends BaseView<ICustomDetailPresenter>{
        void InitView(GroupCustInfoBean custom);
    }

    interface ICustomDetailPresenter extends BasePresenter{

    }
}
