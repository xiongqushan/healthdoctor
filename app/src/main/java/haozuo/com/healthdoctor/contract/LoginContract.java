package haozuo.com.healthdoctor.contract;

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
