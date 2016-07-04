package haozuo.com.healthdoctor.presenter;

import haozuo.com.healthdoctor.bean.GlobalShell;
import haozuo.com.healthdoctor.contract.LoginContract;
import haozuo.com.healthdoctor.listener.OnHandlerResultListener;
import haozuo.com.healthdoctor.model.UserModel;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by xiongwei1 on 2016/7/4.
 */
public class LoginPresenter implements LoginContract.ILoginPresenter {
    LoginContract.ILoginView mILoginView;
    UserModel mUserModel;
    public LoginPresenter(LoginContract.ILoginView iLoginView){
        checkNotNull(iLoginView,"iLoginView can't be null");
        mILoginView=iLoginView;
        mUserModel=new UserModel();
        mILoginView.setPresenter(this);
    }

    @Override
    public void requestLoginSMS(String mobile) {
        mILoginView.setSMSButtonEnableStatus(false);
        mILoginView.showDialog();
        mUserModel.GetSMSCode("1", mobile, new OnHandlerResultListener<GlobalShell<Boolean>>() {
            @Override
            public void handlerResult(GlobalShell<Boolean> resultData) {
                String msg="获取验证码成功！";
                if(!resultData.LogicSuccess){
                    msg=resultData.Message;
                }
                mILoginView.hideDialog(msg);
                mILoginView.setSMSButtonEnableStatus(true);
            }
        });
    }

    @Override
    public void requestLoginWithSMSCode(String mobile, int code) {

    }

    @Override
    public void start() {

    }
}
