package haozuo.com.healthdoctor.ioc;

import dagger.Component;
import haozuo.com.healthdoctor.view.mine.DoctorInfoActivity;

/**
 * Created by zhangzhongyao on 2016/9/8.
 */
@ScopeType.ActivityScope
@Component(modules = {DoctorInfoModule.class}, dependencies = AppComponent.class)
public interface DoctorInfoComponent {
    void inject(DoctorInfoActivity activity);
}
