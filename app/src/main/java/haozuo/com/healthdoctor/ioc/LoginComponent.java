package haozuo.com.healthdoctor.ioc;

import dagger.Component;
import haozuo.com.healthdoctor.view.login.LoginActivity;

/**
 * Created by xiongwei1 on 2016/8/5.
 */
@ScopeType.ActivityScope
@Component(modules = LoginModule.class, dependencies = AppComponent.class)
public interface LoginComponent {
    void inject(LoginActivity activity);

}
