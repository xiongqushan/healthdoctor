package haozuo.com.healthdoctor.presenter;
import android.support.annotation.NonNull;

import haozuo.com.healthdoctor.contract.AbsPresenter;
import haozuo.com.healthdoctor.contract.BaseModel;
import haozuo.com.healthdoctor.contract.BasePresenter;
import haozuo.com.healthdoctor.contract.BaseView;
import haozuo.com.healthdoctor.contract.GroupContract;
import haozuo.com.healthdoctor.contract.GroupContract.IGroupPresenter;
import haozuo.com.healthdoctor.model.UserModel;

/**
 * Created by xiongwei1 on 2016/7/7.
 */
public class GroupPresenter extends AbsPresenter implements IGroupPresenter {
    private GroupContract.IGroupView mIGroupView;
    private UserModel mUserModel;
    public GroupPresenter(@NonNull GroupContract.IGroupView iGroupView){
        mIGroupView=iGroupView;
        mUserModel=new UserModel();
    }

    @Override
    public BaseView getBaseView() {
        return mIGroupView;
    }

    @Override
    public BaseModel getBaseModel() {
        return mUserModel;
    }

    @Override
    public void start() {

    }

}
