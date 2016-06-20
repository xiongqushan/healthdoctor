package haozuo.com.healthdoctor.presenter;

import haozuo.com.healthdoctor.util.StringUtil;

/**
 * Created by Administrator on 2016/6/18.
 */
public interface ILoginPresenter extends IBasePresenter {
    void requestLoginSMS(String mobile);

    void requestLoginWithSMSCode(String mobile, int code);


}
