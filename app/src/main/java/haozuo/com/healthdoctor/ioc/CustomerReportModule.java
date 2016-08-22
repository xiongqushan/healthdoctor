package haozuo.com.healthdoctor.ioc;

import dagger.Module;
import dagger.Provides;
import haozuo.com.healthdoctor.contract.CustomerReportContract;
import haozuo.com.healthdoctor.view.custom.CustomerReportFragment;

/**
 * by zy 2016.08.19.
 */

@Module
public class CustomerReportModule {
    public CustomerReportModule() {

    }

    @ScopeType.ActivityScope
    @Provides
    CustomerReportContract.ICustomerReportView provideCustomerReportView() {
        return CustomerReportFragment.newInstance();
    }

}
