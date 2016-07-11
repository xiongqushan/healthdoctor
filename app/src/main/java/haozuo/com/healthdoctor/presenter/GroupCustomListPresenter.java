package haozuo.com.healthdoctor.presenter;

import android.support.annotation.NonNull;

import haozuo.com.healthdoctor.contract.AbsPresenter;
import haozuo.com.healthdoctor.contract.BaseModel;
import haozuo.com.healthdoctor.contract.BaseView;
import haozuo.com.healthdoctor.contract.GroupCustomList;
import haozuo.com.healthdoctor.model.UserModel;

/**
 * Created by xiongwei1 on 2016/7/11.
 */
public class GroupCustomListPresenter extends AbsPresenter implements GroupCustomList.IGroupCustomListPresenter {
    private GroupCustomList.IGroupCustomListView mGroupCustomListView;
    private UserModel mUserModel;
    private int mGroupId;
    public GroupCustomListPresenter(int groupId, @NonNull GroupCustomList.IGroupCustomListView iGroupCustomListView){
        mGroupId=groupId;
        mGroupCustomListView=iGroupCustomListView;
        mUserModel=new UserModel();
        mGroupCustomListView.setPresenter(this);
    }

    @Override
    public BaseView getBaseView() {
        return null;
    }

    @Override
    public BaseModel getBaseModel() {
        return null;
    }

    @Override
    public void start() {

    }
}
