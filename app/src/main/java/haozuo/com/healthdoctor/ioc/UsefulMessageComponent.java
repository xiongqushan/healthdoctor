package haozuo.com.healthdoctor.ioc;

import dagger.Component;
import haozuo.com.healthdoctor.view.consult.UsefulMesasgeActivity;

/**
 * Created by hzguest3 on 2016/8/9.
 */

@ScopeType.ActivityScope
@Component(modules = UsefulMessageModule.class, dependencies = AppComponent.class)
public interface UsefulMessageComponent {
    void inject(UsefulMesasgeActivity activity);

}