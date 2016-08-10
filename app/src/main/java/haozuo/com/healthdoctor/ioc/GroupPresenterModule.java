package haozuo.com.healthdoctor.ioc;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;
import haozuo.com.healthdoctor.contract.GroupContract;

/**
 * Created by xiongwei1 on 2016/8/8.
 */

@Module
public class GroupPresenterModule {
    GroupContract.IGroupView mIGroupView;

    public GroupPresenterModule(@NonNull GroupContract.IGroupView iGroupView){
        mIGroupView=iGroupView;
    }

    @ScopeType.ActivityScope
    @Provides
    GroupContract.IGroupView provideGroupView(){
        return mIGroupView;
    }
}
