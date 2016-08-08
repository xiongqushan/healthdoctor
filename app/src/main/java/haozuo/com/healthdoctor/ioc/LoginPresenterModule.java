package haozuo.com.healthdoctor.ioc;

import android.app.Application;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;
import haozuo.com.healthdoctor.contract.LoginContract;
import haozuo.com.healthdoctor.presenter.LoginPresenter;

/**
 * Created by xiongwei1 on 2016/8/5.
 */
@Module
public class LoginPresenterModule {
    LoginContract.ILoginView mILoginView;
    public LoginPresenterModule(LoginContract.ILoginView fragmentView){
        mILoginView=fragmentView;
    }


    @Provides
    @ScopeType.ActivityScope
    public LoginContract.ILoginView provideLoginView(){
        return mILoginView;
    }

}
