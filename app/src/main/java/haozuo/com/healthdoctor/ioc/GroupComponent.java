package haozuo.com.healthdoctor.ioc;

import dagger.Component;
import haozuo.com.healthdoctor.view.group.GroupActivity;

/**
 * Created by xiongwei1 on 2016/8/8.
 */

@ScopeType.ActivityScope
@Component(modules = GroupModule.class, dependencies = AppComponent.class)
public interface GroupComponent {
    void inject(GroupActivity activity);
}
