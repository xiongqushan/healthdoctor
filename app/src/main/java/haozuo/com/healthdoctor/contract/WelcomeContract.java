package haozuo.com.healthdoctor.contract;

import haozuo.com.healthdoctor.bean.UpdateInfoBean;
import haozuo.com.healthdoctor.presenter.IBasePresenter;
import haozuo.com.healthdoctor.view.IBaseView;

/**
 * Created by zhangzhongyao on 2016/9/5.
 */
public interface WelcomeContract {

    interface IWelcomeView extends IBaseView<IWelcomePresenter> {
        void updateInfo(UpdateInfoBean bean);

        void turnNextAty();
    }

    interface IWelcomePresenter extends IBasePresenter {
    }
}
