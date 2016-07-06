package haozuo.com.healthdoctor.contract;

import haozuo.com.healthdoctor.bean.GlobalShell;
import haozuo.com.healthdoctor.presenter.*;

/**
 * Created by xiongwei1 on 2016/7/4.
 */
public interface LoginContract {

    interface ILoginView extends BaseView<ILoginPresenter>{
        void setSMSButtonEnableStatus(boolean isEnabled);

        void toHomeActivity();
    }

    interface ILoginPresenter extends BasePresenter{
        void requestLoginSMS(String mobile);

        void requestLoginWithSMSCode(String mobile, int code);
    }
}
