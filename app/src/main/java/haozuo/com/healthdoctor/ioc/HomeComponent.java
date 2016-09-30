package haozuo.com.healthdoctor.ioc;

import dagger.Component;
import haozuo.com.healthdoctor.view.home.HomeActivity;

/**
 * Created by xiongwei1 on 2016/8/9.
 */

@ScopeType.ActivityScope
@Component(modules = {ConsultModule.class, GroupModule.class}, dependencies = AppComponent.class)
public interface HomeComponent {
    void inject(HomeActivity activity);
}
