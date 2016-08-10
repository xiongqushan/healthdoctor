package haozuo.com.healthdoctor.ioc;
import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;
import haozuo.com.healthdoctor.contract.CustomDetailContract;
import haozuo.com.healthdoctor.view.custom.CustomDetailFragment;

/**
 * Created by xiongwei1 on 2016/8/8.
 */
@Module
public class CustomDetailModule {
    CustomDetailContract.ICustomDetailView mICustomDetailView;
    int mCustomId;
    public CustomDetailModule(@NonNull int customId){
        mCustomId=customId;
    }

    @ScopeType.ActivityScope
    @Provides
    CustomDetailContract.ICustomDetailView provideCustomDetailView(){
        return CustomDetailFragment.newInstance();
    }

    @ScopeType.ActivityScope
    @Provides
    int provideCustomId(){
        return mCustomId;
    }
}
