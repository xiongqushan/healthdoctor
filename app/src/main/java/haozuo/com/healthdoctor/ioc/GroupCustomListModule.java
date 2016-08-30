package haozuo.com.healthdoctor.ioc;

import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;
import haozuo.com.healthdoctor.contract.GroupCustomListContract;
import haozuo.com.healthdoctor.view.custom.GroupCustomListFragment;

/**
 * Created by xiongwei1 on 2016/8/8.
 */

@Module
public class GroupCustomListModule {
    int mGroupId;

    public GroupCustomListModule(@NonNull int groupId){
        mGroupId=groupId;
    }

    @ScopeType.ActivityScope
    @Provides
    GroupCustomListContract.IGroupCustomListView provideGroupCustomListView(){
        return GroupCustomListFragment.newInstance();
    }

    @ScopeType.ActivityScope
    @Provides
    int provideGroupId(){
        return mGroupId;
    }
}
