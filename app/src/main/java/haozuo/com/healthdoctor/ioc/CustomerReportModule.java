package haozuo.com.healthdoctor.ioc;

import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;
import haozuo.com.healthdoctor.bean.ReportParamsBean;
import haozuo.com.healthdoctor.contract.CustomerReportContract;
import haozuo.com.healthdoctor.view.custom.CustomerReportFragment;

/**
 * by zy 2016.08.19.
 */

@Module
public class CustomerReportModule {
    ReportParamsBean mReportParamsBean;

    public CustomerReportModule(@NonNull ReportParamsBean bean) {
        mReportParamsBean = bean;
    }


    @ScopeType.ActivityScope
    @Provides
    CustomerReportContract.ICustomerReportView provideCustomerReportView() {
        return CustomerReportFragment.newInstance();
    }

    @ScopeType.ActivityScope
    @Provides
    ReportParamsBean provideReportParamsBean() {
        return mReportParamsBean;
    }

}
