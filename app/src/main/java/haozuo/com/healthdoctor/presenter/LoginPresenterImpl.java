package haozuo.com.healthdoctor.presenter;

import haozuo.com.healthdoctor.bean.DoctorBean;
import haozuo.com.healthdoctor.bean.GlobalShell;
import haozuo.com.healthdoctor.listener.OnHandlerResultListener;
import haozuo.com.healthdoctor.contract.BaseModel;
import haozuo.com.healthdoctor.model.IUserModel;
import haozuo.com.healthdoctor.model.UserModel;
import haozuo.com.healthdoctor.view.Interface.ILoginActivity;

/**
 * Created by Administrator on 2016/6/18.
 */
public class LoginPresenterImpl extends BasePresenter implements ILoginPresenter{
    ILoginActivity mILoginActivity;
    IUserModel mIUserModel;
    public LoginPresenterImpl(ILoginActivity iLoginActivity){
        super();
        mIUserModel=new UserModel();
        mILoginActivity=iLoginActivity;
    }

    @Override
    public void requestLoginSMS(String mobile) {
        String tag=createRequestTag();
        mIUserModel.GetSMSCode(tag,mobile, new OnHandlerResultListener<GlobalShell<Boolean>>() {

            @Override
            public void handlerResult(GlobalShell<Boolean> resultData) {
                mILoginActivity.handlerGetSMSCode(resultData);
            }
        });
    }

    @Override
    public void requestLoginWithSMSCode(String mobile, int code) {
        String tag=createRequestTag();
        mIUserModel.Login(tag, mobile, code, new OnHandlerResultListener<GlobalShell<DoctorBean>>() {
            @Override
            public void handlerResult(GlobalShell<DoctorBean> resultData) {
                mILoginActivity.handlerLogin(resultData);
            }
        });
    }

    @Override
    public BaseModel getIBaseModel() {
        return mIUserModel;
    }

}
