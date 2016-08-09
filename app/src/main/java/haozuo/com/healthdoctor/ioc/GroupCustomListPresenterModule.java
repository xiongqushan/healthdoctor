package haozuo.com.healthdoctor.ioc;

import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;
import haozuo.com.healthdoctor.contract.GroupCustomListContract;

/**
 * Created by xiongwei1 on 2016/8/8.
 */

@Module
public class GroupCustomListPresenterModule {
    GroupCustomListContract.IGroupCustomListView mIGroupCustomListView;
    int mGroupId;

    public GroupCustomListPresenterModule(@NonNull GroupCustomListContract.IGroupCustomListView iGroupCustomListView, @NonNull int groupId){
        mIGroupCustomListView=iGroupCustomListView;
        mGroupId=groupId;
    }

    @ScopeType.ActivityScope
    @Provides
    GroupCustomListContract.IGroupCustomListView provideGroupCustomListView(){
        return mIGroupCustomListView;
    }

    @ScopeType.ActivityScope
    @Provides
    int provideGroupId(){
        return mGroupId;
    }
}
