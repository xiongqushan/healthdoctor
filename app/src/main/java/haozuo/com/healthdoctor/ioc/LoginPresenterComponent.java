package haozuo.com.healthdoctor.ioc;

import android.app.Activity;

import dagger.Component;

/**
 * Created by xiongwei1 on 2016/8/5.
 */
@ScopeType.ActivityScope
@Component(dependencies = AppComponent.class,modules = LoginPresenterModule.class)
public interface LoginPresenterComponent {
    void inject(Activity activity);
}
