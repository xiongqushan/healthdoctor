package haozuo.com.healthdoctor.ioc;

import dagger.Component;
import haozuo.com.healthdoctor.view.custom.CustomerReportActivity;

/**
 * by zy on 2016/8/19.
 */

@ScopeType.ActivityScope
@Component(modules = {CustomerReportModule.class}, dependencies = AppComponent.class)
public interface CustomerReportComponent {
    void inject(CustomerReportActivity activity);
}
