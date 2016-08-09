package haozuo.com.healthdoctor.ioc;
import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;
import haozuo.com.healthdoctor.contract.CustomDetailContract;

/**
 * Created by xiongwei1 on 2016/8/8.
 */
@Module
public class CustomDetailPresenterModule {
    CustomDetailContract.ICustomDetailView mICustomDetailView;
    int mCustomId;
    public CustomDetailPresenterModule(@NonNull CustomDetailContract.ICustomDetailView fragmentView, int customId){
        mICustomDetailView=fragmentView;
        mCustomId=customId;
    }

    @ScopeType.ActivityScope
    @Provides
    CustomDetailContract.ICustomDetailView provideCustomDetailView(){
        return mICustomDetailView;
    }

    @ScopeType.ActivityScope
    @Provides
    int provideCustomId(){
        return mCustomId;
    }
}
