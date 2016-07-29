package haozuo.com.healthdoctor.contract;

import haozuo.com.healthdoctor.bean.CustomDetailBean;
import haozuo.com.healthdoctor.bean.GroupCustInfoBean;

/**
 * Created by hzguest3 on 2016/7/22.
 */
public interface CustomDetailContract {

    interface ICustomDetailView extends BaseView<ICustomDetailPresenter>{
        void InitView(CustomDetailBean custom);
    }

    interface ICustomDetailPresenter extends IBasePresenter {

    }
}
