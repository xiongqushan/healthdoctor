package haozuo.com.healthdoctor.ioc;

import dagger.Component;
import haozuo.com.healthdoctor.view.custom.CustomerInfoActivity;

/**
 * Created by xiongwei1 on 2016/8/8.
 */
@ScopeType.ActivityScope
@Component(modules = CustomerInfoModule.class,dependencies = AppComponent.class)
public interface CustomerInfoComponent {
    void inject(CustomerInfoActivity activity);
}
