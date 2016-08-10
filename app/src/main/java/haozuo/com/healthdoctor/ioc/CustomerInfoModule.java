package haozuo.com.healthdoctor.ioc;

import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import haozuo.com.healthdoctor.bean.CustomDetailBean;
import haozuo.com.healthdoctor.contract.CustomerInfoContract;
import haozuo.com.healthdoctor.view.custom.CustomerInfoFragment;

/**
 * Created by xiongwei1 on 2016/8/8.
 */
@Module
public class CustomerInfoModule {
    CustomDetailBean mCustomDetailBean;
    public CustomerInfoModule(@NonNull CustomDetailBean customInfo){
        mCustomDetailBean=customInfo;
    }

    @ScopeType.ActivityScope
    @Provides
    CustomerInfoContract.ICustomerInfoView provideCustomerInfoView(){
        return CustomerInfoFragment.newInstance();
    }

    @ScopeType.ActivityScope
    @Provides
    CustomDetailBean provideCustomDetailBean(){
        return mCustomDetailBean;
    }
}
