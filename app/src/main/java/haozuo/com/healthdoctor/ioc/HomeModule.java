package haozuo.com.healthdoctor.ioc;

import dagger.Module;
import dagger.Provides;
import haozuo.com.healthdoctor.contract.ConsultContract;
import haozuo.com.healthdoctor.contract.GroupContract;
import haozuo.com.healthdoctor.view.consult.ConsultFragment;
import haozuo.com.healthdoctor.view.group.GroupFragment;

/**
 * Created by xiongwei1 on 2016/8/8.
 */

@Module
public class HomeModule {
    public HomeModule() {

    }

    @ScopeType.ActivityScope
    @Provides
    GroupContract.IGroupView provideGroupView() {
        return GroupFragment.newInstance();
    }

    @ScopeType.ActivityScope
    @Provides
    ConsultContract.IConsultView provideConsultView() {
        return ConsultFragment.newInstance();
    }
}
