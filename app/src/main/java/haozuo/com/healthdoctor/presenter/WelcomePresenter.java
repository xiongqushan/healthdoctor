package haozuo.com.healthdoctor.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import haozuo.com.healthdoctor.bean.GlobalShell;
import haozuo.com.healthdoctor.bean.UpdateInfoBean;
import haozuo.com.healthdoctor.contract.WelcomeContract;
import haozuo.com.healthdoctor.listener.OnHandlerResultListener;
import haozuo.com.healthdoctor.model.SystemModel;

/**
 * Created by zhangzhongyao on 2016/9/5.
 */
public class WelcomePresenter {

    private WelcomeContract.IWelcomeView mIWelcomeView;
    private SystemModel mSystemModel;

    @Inject
    public WelcomePresenter(@NonNull WelcomeContract.IWelcomeView iWelcomeView, @NonNull SystemModel systemModel) {
        mIWelcomeView = iWelcomeView;
        mSystemModel = systemModel;
    }

    public void start(final int currVersion) {
        mSystemModel.GetVersionStatus(currVersion, new OnHandlerResultListener<GlobalShell<UpdateInfoBean>>() {
            @Override
            public void handlerResult(GlobalShell<UpdateInfoBean> resultData) {
                if (resultData.LogicSuccess) {
                    mIWelcomeView.updateInfo(resultData.Data);
//                    mIWelcomeView = null;
                } else {
                    start(currVersion);
                }
            }
        });

    }
}
