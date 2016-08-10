package haozuo.com.healthdoctor.ioc;

import android.app.Activity;

import dagger.Component;
import haozuo.com.healthdoctor.view.login.LoginActivity;

/**
 * Created by xiongwei1 on 2016/8/5.
 */
@ScopeType.ActivityScope
@Component(modules = LoginPresenterModule.class, dependencies = AppComponent.class)
public interface LoginPresenterComponent {
    void inject(LoginActivity activity);

}
