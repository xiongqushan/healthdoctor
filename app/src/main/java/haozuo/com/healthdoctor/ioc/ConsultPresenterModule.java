package haozuo.com.healthdoctor.ioc;

import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;
import haozuo.com.healthdoctor.contract.ConsultContract;

/**
 * Created by xiongwei1 on 2016/8/9.
 */

@Module
public class ConsultPresenterModule {
    ConsultContract.IConsultView mIConsultView;
    public ConsultPresenterModule(@NonNull ConsultContract.IConsultView iConsultView){
        mIConsultView=iConsultView;
    }

    @ScopeType.ActivityScope
    @Provides
    ConsultContract.IConsultView provideConsultView(){
        return  mIConsultView;
    }
}
