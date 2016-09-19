package haozuo.com.healthdoctor.ioc;

import dagger.Module;
import dagger.Provides;
import haozuo.com.healthdoctor.contract.ConsultContract;
import haozuo.com.healthdoctor.view.consult.ConsultFragment;

/**
 * Created by xiongwei1 on 2016/8/9.
 */

@Module
public class ConsultModule {
    public ConsultModule(){
    }

    @ScopeType.ActivityScope
    @Provides
    ConsultContract.IConsultView provideConsultView(){
        return  ConsultFragment.newInstance();
    }
}
