package haozuo.com.healthdoctor.ioc;

import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import haozuo.com.healthdoctor.bean.CustomDetailBean;
import haozuo.com.healthdoctor.contract.CustomerInfoContract;

/**
 * Created by xiongwei1 on 2016/8/8.
 */
@Module
public class CustomerInfoPresenterModule {
    CustomerInfoContract.ICustomerInfoView mICustomerInfoView;
    CustomDetailBean mCustomDetailBean;
    public CustomerInfoPresenterModule(@NonNull CustomerInfoContract.ICustomerInfoView fragmentView, @NonNull CustomDetailBean customInfo){
        mICustomerInfoView=fragmentView;
        mCustomDetailBean=customInfo;
    }

    @ScopeType.ActivityScope
    @Provides
    CustomerInfoContract.ICustomerInfoView provideCustomerInfoView(){
        return mICustomerInfoView;
    }

    @ScopeType.ActivityScope
    @Provides
    CustomDetailBean provideCustomDetailBean(){
        return mCustomDetailBean;
    }
}
