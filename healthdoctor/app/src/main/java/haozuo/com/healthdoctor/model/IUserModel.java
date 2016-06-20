package haozuo.com.healthdoctor.model;

import haozuo.com.healthdoctor.bean.DoctorBean;
import haozuo.com.healthdoctor.bean.GlobalShell;
import haozuo.com.healthdoctor.listener.OnAsyncCallbackListener;

/**
 * Created by Administrator on 2016/6/18.
 */
public interface IUserModel extends IBaseModel{

    public void GetSMSCode(String tag, String mobile, final OnAsyncCallbackListener<GlobalShell<Boolean>> callbackListener);

    public void Login(String tag,String mobile,int smsCode, final OnAsyncCallbackListener<GlobalShell<DoctorBean>> callbackListener);
}
