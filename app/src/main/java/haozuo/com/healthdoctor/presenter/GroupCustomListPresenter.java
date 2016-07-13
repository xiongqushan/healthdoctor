package haozuo.com.healthdoctor.presenter;

import android.support.annotation.NonNull;

import haozuo.com.healthdoctor.bean.GlobalShell;
import haozuo.com.healthdoctor.bean.GroupCustInfoBean;
import haozuo.com.healthdoctor.bean.PageBean;
import haozuo.com.healthdoctor.contract.AbsPresenter;
import haozuo.com.healthdoctor.contract.BaseModel;
import haozuo.com.healthdoctor.contract.BaseView;
import haozuo.com.healthdoctor.contract.GroupCustomListContract;
import haozuo.com.healthdoctor.listener.OnHandlerResultListener;
import haozuo.com.healthdoctor.manager.UserManager;
import haozuo.com.healthdoctor.model.UserModel;

/**
 * Created by xiongwei1 on 2016/7/11.
 */
public class GroupCustomListPresenter extends AbsPresenter implements GroupCustomListContract.IGroupCustomListPresenter {
    private int PAGE_SIZE=20;
    private GroupCustomListContract.IGroupCustomListView mGroupCustomListView;
    private UserModel mUserModel;
    private int mGroupId;
    public GroupCustomListPresenter(int groupId, @NonNull GroupCustomListContract.IGroupCustomListView iGroupCustomListView){
        mGroupId=groupId;
        mGroupCustomListView=iGroupCustomListView;
        mUserModel=new UserModel();
        mGroupCustomListView.setPresenter(this);
    }

    @Override
    public BaseView getBaseView() {
        return mGroupCustomListView;
    }

    @Override
    public BaseModel getBaseModel() {
        return mUserModel;
    }

    @Override
    public void start() {
        requestCustomList(1);
    }

    @Override
    public void requestCustomList(int pageIndex) {
        int departId= UserManager.getInstance().getDoctorInfo().ServiceDeptId;
        mUserModel.GetGroupCustInfoList(createRequestTag(), departId, mGroupId, "", pageIndex, PAGE_SIZE, new OnHandlerResultListener<GlobalShell<PageBean<GroupCustInfoBean>>>() {
            @Override
            public void handlerResult(GlobalShell<PageBean<GroupCustInfoBean>> resultData) {
                if(resultData.LogicSuccess) {
                    mGroupCustomListView.hideDialog();
                    mGroupCustomListView.refreshCustomAdapter(resultData.Data.CurrentPageDataList);
                }
                else{
                    mGroupCustomListView.hideDialog(resultData.Message);
                }
            }
        });
    }
}
