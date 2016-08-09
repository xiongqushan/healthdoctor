package haozuo.com.healthdoctor.ioc;

import dagger.Component;
import haozuo.com.healthdoctor.view.custom.GroupCustomListActivity;
import haozuo.com.healthdoctor.view.group.GroupActivity;

/**
 * Created by xiongwei1 on 2016/8/8.
 */

@ScopeType.ActivityScope
@Component(modules = GroupPresenterModule.class, dependencies = AppComponent.class)
public interface GroupPresenterComponent {
    void inject(GroupActivity activity);
}
