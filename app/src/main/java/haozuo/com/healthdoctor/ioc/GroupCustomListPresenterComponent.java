package haozuo.com.healthdoctor.ioc;

import dagger.Component;
import haozuo.com.healthdoctor.view.custom.GroupCustomListActivity;

/**
 * Created by xiongwei1 on 2016/8/8.
 */

@ScopeType.ActivityScope
@Component(modules = GroupCustomListPresenterModule.class, dependencies = AppComponent.class)
public interface GroupCustomListPresenterComponent {
    void inject(GroupCustomListActivity activity);
}
