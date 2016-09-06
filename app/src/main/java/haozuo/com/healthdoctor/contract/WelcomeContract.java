package haozuo.com.healthdoctor.contract;

import haozuo.com.healthdoctor.bean.UpdateInfoBean;

/**
 * Created by zhangzhongyao on 2016/9/5.
 */
public interface WelcomeContract {

    interface IWelcomeView {
        void updateInfo(UpdateInfoBean bean);
    }
}
