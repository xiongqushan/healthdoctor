package haozuo.com.healthdoctor.ioc;

import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;
import haozuo.com.healthdoctor.contract.ConsultContract;
import haozuo.com.healthdoctor.contract.ConsultDetailContract;

/**
 * Created by xiongwei1 on 2016/8/9.
 */

@Module
public class ConsultDetailPresenterModule {
    ConsultDetailContract.IConsultDetailView mIConsultDetailView;
    public ConsultDetailPresenterModule(@NonNull ConsultDetailContract.IConsultDetailView iConsultDetailView){
        mIConsultDetailView=iConsultDetailView;
    }

    @ScopeType.ActivityScope
    @Provides
    ConsultDetailContract.IConsultDetailView provideConsultDetailView(){
        return  mIConsultDetailView;
    }
}
