package haozuo.com.healthdoctor.ioc;

import dagger.Module;
import dagger.Provides;
import haozuo.com.healthdoctor.contract.LoginContract;
import haozuo.com.healthdoctor.view.login.LoginFragment;

/**
 * Created by xiongwei1 on 2016/8/5.
 */
@Module
public class LoginModule {
    /*LoginContract.ILoginView mILoginView;
    public LoginModule(@NonNull LoginContract.ILoginView fragmentView){
        mILoginView=fragmentView;
    }*/


    public LoginModule(){
    }

    @Provides
    @ScopeType.ActivityScope
    LoginContract.ILoginView provideLoginView(){
        return LoginFragment.newInstance() ;
    }

}
