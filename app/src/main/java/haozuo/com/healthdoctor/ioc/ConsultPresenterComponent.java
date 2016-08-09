package haozuo.com.healthdoctor.ioc;

import dagger.Component;
import haozuo.com.healthdoctor.view.consult.ConsultActivity;

/**
 * Created by xiongwei1 on 2016/8/9.
 */

@ScopeType.ActivityScope
@Component(modules = ConsultPresenterModule.class,dependencies = AppComponent.class)
public interface ConsultPresenterComponent {
    void inject(ConsultActivity activity);
}
