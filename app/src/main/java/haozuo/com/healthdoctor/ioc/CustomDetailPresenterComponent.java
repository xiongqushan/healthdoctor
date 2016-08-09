package haozuo.com.healthdoctor.ioc;

import dagger.Component;
import haozuo.com.healthdoctor.view.custom.CustomDetailActivity;

/**
 * Created by xiongwei1 on 2016/8/8.
 */
@ScopeType.ActivityScope
@Component( modules = CustomDetailPresenterModule.class, dependencies = AppComponent.class)
public interface CustomDetailPresenterComponent {
    void inject(CustomDetailActivity activity);
}
