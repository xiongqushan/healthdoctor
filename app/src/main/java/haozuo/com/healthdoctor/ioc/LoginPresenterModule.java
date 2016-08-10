package haozuo.com.healthdoctor.ioc;

import android.app.Application;
import android.support.annotation.NonNull;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;
import haozuo.com.healthdoctor.contract.LoginContract;
import haozuo.com.healthdoctor.presenter.LoginPresenter;
import haozuo.com.healthdoctor.service.IUserService;
import haozuo.com.healthdoctor.view.login.LoginFragment;
import retrofit.Retrofit;

/**
 * Created by xiongwei1 on 2016/8/5.
 */
@Module
public class LoginPresenterModule {
    /*LoginContract.ILoginView mILoginView;
    public LoginPresenterModule(@NonNull LoginContract.ILoginView fragmentView){
        mILoginView=fragmentView;
    }*/


    public LoginPresenterModule(){
    }

    @Provides
    @ScopeType.ActivityScope
    LoginContract.ILoginView provideLoginView(){
        return LoginFragment.newInstance() ;
    }

}
