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
    String mAccountId;
    public ConsultDetailModule(int customerId, String accountId){
        mCustomerId=customerId;
        mAccountId=accountId;
    }

    @ScopeType.ActivityScope
    @Provides
    ConsultDetailContract.IConsultDetailView provideConsultDetailView(){
        return ConsultDetailFragment.newInstance(mCustomerId,mAccountId) ;
    }

    @ScopeType.ActivityScope
    @Provides
    int provideCustomerId(){
        return mCustomerId;
    }
}
