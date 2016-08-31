package haozuo.com.healthdoctor.contract;

import haozuo.com.healthdoctor.presenter.IBasePresenter;
import haozuo.com.healthdoctor.view.IBaseView;

/**
 * Created by xiongwei1 on 2016/7/4.
 */
public interface LoginContract {

    interface ILoginView extends IBaseView<ILoginPresenter> {
        void setSMSButtonEnableStatus(boolean isEnabled);

        void toHomeActivity();
    }

    interface ILoginPresenter extends IBasePresenter {
        void requestLoginSMS(String mobile);

        void requestLoginWithSMSCode(String mobile, int code);

        void requestLoginWithPassWord(String mobile,String password);
    }
}
