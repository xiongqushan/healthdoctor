package haozuo.com.healthdoctor.ioc;

import dagger.Component;
import haozuo.com.healthdoctor.view.custom.CustomDetailActivity;

/**
 * Created by xiongwei1 on 2016/8/8.
 */
@ScopeType.ActivityScope
@Component( modules = CustomDetailModule.class, dependencies = AppComponent.class)
public interface CustomDetailComponent {
    void inject(CustomDetailActivity activity);
}
