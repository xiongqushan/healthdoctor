package haozuo.com.healthdoctor.contract;

import haozuo.com.healthdoctor.presenter.*;

/**
 * Created by xiongwei1 on 2016/7/4.
 */
public interface LoginContract {

    interface ILoginView extends BaseView<ILoginPresenter>{
        void loginSuccess();

        void loginError(String msg);
    }

    interface ILoginPresenter extends BasePresenter{
        void requestLoginSMS(String mobile);

        void requestLoginWithSMSCode(String mobile, int code);
    }
}
