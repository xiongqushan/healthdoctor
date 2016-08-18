package haozuo.com.healthdoctor.ioc;

import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;
import haozuo.com.healthdoctor.contract.ConsultContract;
import haozuo.com.healthdoctor.contract.ConsultDetailContract;
import haozuo.com.healthdoctor.view.consult.ConsultDetailFragment;

/**
 * Created by xiongwei1 on 2016/8/9.
 */

@Module
public class ConsultDetailModule {
    int mCustomerId;
    public ConsultDetailModule(int customerId ){
        mCustomerId=customerId;
    }

    @ScopeType.ActivityScope
    @Provides
    ConsultDetailContract.IConsultDetailView provideConsultDetailView(){
        return ConsultDetailFragment.newInstance(mCustomerId) ;
    }

    @ScopeType.ActivityScope
    @Provides
    int provideCustomerId(){
        return mCustomerId;
    }
}
