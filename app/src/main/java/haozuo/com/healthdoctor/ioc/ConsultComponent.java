package haozuo.com.healthdoctor.ioc;

import dagger.Component;
import haozuo.com.healthdoctor.view.consult.ConsultActivity;

/**
 * Created by xiongwei1 on 2016/8/9.
 */

@ScopeType.ActivityScope
@Component(modules = ConsultModule.class,dependencies = AppComponent.class)
public interface ConsultComponent {
    void inject(ConsultActivity activity);
}
