package haozuo.com.healthdoctor.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import haozuo.com.healthdoctor.bean.GlobalShell;
import haozuo.com.healthdoctor.bean.UpdateInfoBean;
import haozuo.com.healthdoctor.contract.WelcomeContract;
import haozuo.com.healthdoctor.listener.OnHandlerResultListener;
import haozuo.com.healthdoctor.model.IBaseModel;
import haozuo.com.healthdoctor.model.SystemModel;
import haozuo.com.healthdoctor.view.IBaseView;

/**
 * Created by zhangzhongyao on 2016/9/5.
 */
public class WelcomePresenter extends AbstractPresenter implements WelcomeContract.IWelcomePresenter {

    private WelcomeContract.IWelcomeView mIWelcomeView;
    private SystemModel mSystemModel;
    private int currVersion;

    @Inject
    public WelcomePresenter(@NonNull WelcomeContract.IWelcomeView iWelcomeView, @NonNull SystemModel systemModel,
                            @NonNull int currVersion) {
        mIWelcomeView = iWelcomeView;
        mSystemModel = systemModel;
        mIWelcomeView.setPresenter(this);
        this.currVersion = currVersion;
    }


    @Override
    public IBaseView getBaseView() {
        return mIWelcomeView;
    }

    @Override
    public IBaseModel[] getBaseModelList() {
        return new IBaseModel[]{mSystemModel};
    }

    @Override
    public void start() {
        mSystemModel.GetVersionStatus(currVersion, new OnHandlerResultListener<GlobalShell<UpdateInfoBean>>() {
            @Override
            public void handlerResult(GlobalShell<UpdateInfoBean> resultData) {

                if (resultData.LogicSuccess) {
                    mIWelcomeView.updateInfo(resultData.Data);
                    // mIWelcomeView = null;
                } else {
                    // start();
                    mIWelcomeView.turnNextAty();
                }
            }
        });
    }
}
