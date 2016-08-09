package haozuo.com.healthdoctor.ioc;

import dagger.Component;
import haozuo.com.healthdoctor.view.consult.ConsultActivity;
import haozuo.com.healthdoctor.view.consult.ConsultDetailActivity;

/**
 * Created by xiongwei1 on 2016/8/9.
 */

@ScopeType.ActivityScope
@Component(modules = ConsultDetailPresenterModule.class,dependencies = AppComponent.class)
public interface ConsultDetailPresenterComponent {
    void inject(ConsultDetailActivity activity);
}
