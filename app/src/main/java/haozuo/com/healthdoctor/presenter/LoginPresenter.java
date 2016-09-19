package haozuo.com.healthdoctor.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.bean.DoctorBean;
import haozuo.com.healthdoctor.bean.DoctorGroupBean;
import haozuo.com.healthdoctor.bean.GlobalShell;
import haozuo.com.healthdoctor.contract.LoginContract;
import haozuo.com.healthdoctor.listener.OnHandlerResultListener;
import haozuo.com.healthdoctor.manager.GroupInfoManager;
import haozuo.com.healthdoctor.manager.UserManager;
import haozuo.com.healthdoctor.model.GroupModel;
import haozuo.com.healthdoctor.model.IBaseModel;
import haozuo.com.healthdoctor.model.UserModel;
import haozuo.com.healthdoctor.util.StringUtil;
import haozuo.com.healthdoctor.view.IBaseView;


/**
 * Created by xiongwei1 on 2016/7/4.
 */
public class LoginPresenter extends AbstractPresenter implements LoginContract.ILoginPresenter {
    LoginContract.ILoginView mILoginView;
    UserModel mUserModel;
    GroupModel mGroupModel;
    Context mContext;

    @Inject
    public LoginPresenter(@NonNull LoginContract.ILoginView iLoginView, @NonNull Context context, @NonNull UserModel userModel, @NonNull GroupModel groupModel) {
        mILoginView = iLoginView;
        mUserModel = userModel;
        mGroupModel = groupModel;
        mILoginView.setPresenter(this);
        mContext = context;
    }

    @Override
    public void start() {

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
                    try {
                        UserManager.getInstance().setDoctorInfo(resultData.Data);
                    } catch (CloneNotSupportedException e) {
                        e.printStackTrace();
                    }
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

        //TODO rxjava
//        mGroupModel.LoginRequestGroup(mobile, password, new OnHandlerResultListener<GlobalShell<List<DoctorGroupBean>>>() {
//            @Override
//            public void handlerResult(GlobalShell<List<DoctorGroupBean>> resultData) {
//                if (resultData.LogicSuccess) {
//                    DoctorGroupBean doctorGroupBean = new DoctorGroupBean();
//                    Collections.sort(resultData.Data, doctorGroupBean);
//                    if ((List<DoctorGroupBean>) resultData.Data != null) {
//                        GroupInfoManager.getInstance().setGroupInfo((List<DoctorGroupBean>) resultData.Data);
//                    }
//                    mILoginView.hideDialog();
//                    mILoginView.toHomeActivity();
//                } else {
//                    mILoginView.hideDialog(resultData.Message);
//                }
//            }
//        });
        mUserModel.LoginValidate(mobile, password, new OnHandlerResultListener<GlobalShell<DoctorBean>>() {
            @Override
            public void handlerResult(GlobalShell<DoctorBean> resultData) {
                if (resultData.LogicSuccess) {
                    if (resultData.Data.PhotoUrl == null) {
                        resultData.Data.PhotoUrl = "res://haozuo.com.healthdoctor.view.custom/" + R.drawable.doctor_default_photourl;
                    }
                    try {
                        UserManager.getInstance().setDoctorInfo(resultData.Data);
                    } catch (CloneNotSupportedException e) {
                        e.printStackTrace();
                    }
                    getGroupInfo(resultData.Data.Doctor_ID);
                } else {
                    mILoginView.hideDialog(resultData.Message);
                }
            }
        });
    }

    @Override
    public IBaseView getBaseView() {
        return mILoginView;
    }

    @Override
    public IBaseModel[] getBaseModelList() {
        return new IBaseModel[]{mUserModel};
    }

    public void getGroupInfo(int doctorID) {
        mGroupModel.GetGroup(doctorID, new OnHandlerResultListener<GlobalShell<List<DoctorGroupBean>>>() {
            @Override
            public void handlerResult(GlobalShell<List<DoctorGroupBean>> resultData) {
                if (resultData.LogicSuccess) {
                    DoctorGroupBean doctorGroupBean = new DoctorGroupBean();
                    Collections.sort(resultData.Data, doctorGroupBean);
                    if ((List<DoctorGroupBean>) resultData.Data != null) {
                        GroupInfoManager.getInstance().setGroupInfo((List<DoctorGroupBean>) resultData.Data);
                    }
                    mILoginView.hideDialog();
                    mILoginView.toHomeActivity();
                } else {
                    mILoginView.hideDialog(resultData.Message);
                }
            }
        });
    }

}

