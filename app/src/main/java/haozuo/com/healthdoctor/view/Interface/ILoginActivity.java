package haozuo.com.healthdoctor.view.Interface;

import haozuo.com.healthdoctor.bean.DoctorBean;
import haozuo.com.healthdoctor.bean.GlobalShell;

/**
 * Created by Administrator on 2016/6/18.
 */
public interface ILoginActivity extends IBaseActivity{
    void handlerGetSMSCode(GlobalShell<Boolean> result);

    void handlerLogin(GlobalShell<DoctorBean> result);
}
