package haozuo.com.healthdoctor.ioc;

import dagger.Module;
import dagger.Provides;
import haozuo.com.healthdoctor.contract.UsefulMessageContract;
import haozuo.com.healthdoctor.view.consult.UsefulMessageFragment;

/**
 * Created by hzguest3 on 2016/8/9.
 */

@Module
public class UsefulMessageModule {
    public UsefulMessageModule(){
    }

    @Provides
    @ScopeType.ActivityScope
    UsefulMessageContract.IUsefulMessageView provideUsefulMessageView(){
        return UsefulMessageFragment.newInstance();
    }
}
