package haozuo.com.healthdoctor.presenter;

import android.support.annotation.NonNull;

import haozuo.com.healthdoctor.bean.DoctorBean;
import haozuo.com.healthdoctor.bean.GlobalShell;
import haozuo.com.healthdoctor.contract.IBaseModel;
import haozuo.com.healthdoctor.contract.IBaseView;
import haozuo.com.healthdoctor.contract.LoginContract;
import haozuo.com.healthdoctor.listener.OnHandlerResultListener;
import haozuo.com.healthdoctor.manager.UserManager;
import haozuo.com.healthdoctor.model.UserModel;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by xiongwei1 on 2016/7/4.
 */
public class LoginPresenter extends AbstractPresenter implements LoginContract.ILoginPresenter {
    LoginContract.ILoginView mILoginView;
    UserModel mUserModel;
    public LoginPresenter(@NonNull LoginContract.ILoginView iLoginView){
        mILoginView=iLoginView;
        mUserModel=UserModel.createInstance();
        mILoginView.setPresenter(this);
    }

    @Override
    public void requestLoginSMS(String mobile) {
        mILoginView.setSMSButtonEnableStatus(false);
        mILoginView.showDialog();
        mUserModel.GetSMSCode(mobile, new OnHandlerResultListener<GlobalShell<Boolean>>() {
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
        mILoginView.showDialog();
        mUserModel.Login(mobile, code, new OnHandlerResultListener<GlobalShell<DoctorBean>>() {
            @Override
            public void handlerResult(GlobalShell<DoctorBean> resultData) {
                if(resultData.LogicSuccess) {
                    UserManager.getInstance().setDoctorInfo(resultData.Data);
                    mILoginView.hideDialog();
                    mILoginView.toHomeActivity();
                }
                else{
                    mILoginView.hideDialog(resultData.Message);
                }
            }
        });
    }

    @Override
    public void start() {

    }

    @Override
    public IBaseView getBaseView() {
        return mILoginView;
    }

    @Override
    public IBaseModel getBaseModel() {
        return mUserModel;
    }
}
