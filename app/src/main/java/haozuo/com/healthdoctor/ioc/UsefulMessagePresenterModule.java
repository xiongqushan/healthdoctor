package haozuo.com.healthdoctor.ioc;

import android.support.annotation.NonNull;

import javax.inject.Scope;

import dagger.Module;
import dagger.Provides;
import haozuo.com.healthdoctor.contract.LoginContract;
import haozuo.com.healthdoctor.contract.UsefulMessageContract;

/**
 * Created by hzguest3 on 2016/8/9.
 */

@Module
public class UsefulMessagePresenterModule {
    UsefulMessageContract.IUsefulMessageView mIUsefulMessageView;
    public UsefulMessagePresenterModule(@NonNull UsefulMessageContract.IUsefulMessageView iUsefulMessageView){
        mIUsefulMessageView=iUsefulMessageView;
    }

    @Provides
    @ScopeType.ActivityScope
    UsefulMessageContract.IUsefulMessageView provideUsefulMessageView(){
        return mIUsefulMessageView;
    }
}
