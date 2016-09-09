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
    int mCustomId;
    String mAccountId;

    public CustomDetailModule(int customId, @NonNull String accountId){
        mCustomId=customId;
        mAccountId = accountId;
    }

    @ScopeType.ActivityScope
    @Provides
    CustomDetailContract.ICustomDetailView provideCustomDetailView() {
        return CustomDetailFragment.newInstance();
    }

    @ScopeType.ActivityScope
    @Provides
    int provideCustomId() {
        return mCustomId;
    }

    @ScopeType.ActivityScope
    @Provides
    String provideAccountId(){
        return mAccountId;
    }
}
