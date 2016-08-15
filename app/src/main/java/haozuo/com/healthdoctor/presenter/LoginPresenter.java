package haozuo.com.healthdoctor.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import javax.inject.Inject;

import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.bean.DoctorBean;
import haozuo.com.healthdoctor.bean.GlobalShell;
import haozuo.com.healthdoctor.contract.IBaseModel;
import haozuo.com.healthdoctor.contract.IBaseView;
import haozuo.com.healthdoctor.contract.LoginContract;
import haozuo.com.healthdoctor.listener.OnHandlerResultListener;
import haozuo.com.healthdoctor.manager.UserManager;
import haozuo.com.healthdoctor.model.UserModel;
import haozuo.com.healthdoctor.util.StringUtil;

/**
 * Created by xiongwei1 on 2016/7/4.
 */
public class LoginPresenter extends AbstractPresenter implements LoginContract.ILoginPresenter {
    LoginContract.ILoginView mILoginView;
    UserModel mUserModel;
    Context mContext;

    @Inject
    public LoginPresenter(@NonNull LoginContract.ILoginView iLoginView, @NonNull Context context, @NonNull UserModel userModel) {
        mILoginView = iLoginView;
        mUserModel = userModel;
        mILoginView.setPresenter(this);
        mContext = context;
    }

    @Override
    public void requestLoginSMS(String mobile) {
        mILoginView.setSMSButtonEnableStatus(false);
        mILoginView.showDialog();
        mUserModel.GetSMSCode(mobile, new OnHandlerResultListener<GlobalShell<Boolean>>() {
            @Override
            public void handlerResult(GlobalShell<Boolean> resultData) {
                String msg = "获取验证码成功！";
                if (!resultData.LogicSuccess) {
                    msg = resultData.Message;
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
                if (resultData.LogicSuccess) {
                    UserManager.getInstance().setDoctorInfo(resultData.Data);
                    mILoginView.hideDialog();
                    mILoginView.toHomeActivity();
                } else {
                    mILoginView.hideDialog(resultData.Message);
                }
            }
        });
    }

    @Override
    public void requestLoginWithPassWord(String mobile, String password) {
        if (password.length() == 0 || mobile.length() == 0) {
            Toast.makeText(mContext, "用户名和密码不能为空!",
                    Toast.LENGTH_SHORT).show();
            return;
        } else if (!StringUtil.isMobile(mobile)) {
            Toast.makeText(mContext, "请输入正确的手机号!",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        password = StringUtil.encodeByMD5(password);
        mILoginView.showDialog();
        mUserModel.LoginValidate(mobile, password, new OnHandlerResultListener<GlobalShell<DoctorBean>>() {
            @Override
            public void handlerResult(GlobalShell<DoctorBean> resultData) {
                if (resultData.LogicSuccess) {
                    if (resultData.Data.PhotoUrl == null) {
                        resultData.Data.PhotoUrl = "res://haozuo.com.healthdoctor.view.custom/" + R.drawable.doctor_default_photourl;
                    }
                    UserManager.getInstance().setDoctorInfo(resultData.Data);
                    mILoginView.hideDialog();
                    mILoginView.toHomeActivity();
                } else {
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
    public IBaseModel[] getBaseModelList() {
        return new IBaseModel[]{mUserModel};
    }
}
