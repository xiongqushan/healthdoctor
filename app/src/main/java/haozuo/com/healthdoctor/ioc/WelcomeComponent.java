package haozuo.com.healthdoctor.ioc;

import dagger.Component;
import haozuo.com.healthdoctor.view.welcome.WelcomeActivity;

/**
 * Created by zhangzhongyao on 2016/9/5.
 */
@ScopeType.ActivityScope
@Component(modules = WelcomeModule.class, dependencies = AppComponent.class)
public interface WelcomeComponent {
    void inject(WelcomeActivity activity);
}
