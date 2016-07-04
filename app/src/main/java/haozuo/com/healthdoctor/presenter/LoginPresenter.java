package haozuo.com.healthdoctor.presenter;

import haozuo.com.healthdoctor.contract.LoginContract;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by xiongwei1 on 2016/7/4.
 */
public class LoginPresenter implements LoginContract.ILoginPresenter {
    LoginContract.ILoginView mILoginView;
    public LoginPresenter(LoginContract.ILoginView iLoginView){
        checkNotNull(iLoginView,"iLoginView can't be null");
        mILoginView=iLoginView;
        mILoginView.setPresenter(this);
    }

    @Override
    public void requestLoginSMS(String mobile) {

    }

    @Override
    public void requestLoginWithSMSCode(String mobile, int code) {

    }

    @Override
    public void start() {

    }
}
