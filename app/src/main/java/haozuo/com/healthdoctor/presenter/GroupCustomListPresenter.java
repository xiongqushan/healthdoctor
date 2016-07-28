package haozuo.com.healthdoctor.presenter;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

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
import haozuo.com.healthdoctor.view.threePart.PullToRefresh.PullToRefreshLayout;

/**
 * Created by xiongwei1 on 2016/7/11.
 */
public class GroupCustomListPresenter extends AbsPresenter implements GroupCustomListContract.IGroupCustomListPresenter {
    private int PAGE_SIZE=20;
    private int mCurrentPageIndex=1;
    private int mLeastPageIndex=1;
    private List<GroupCustInfoBean>mGroupCustInfoBeanList;
    private GroupCustomListContract.IGroupCustomListView mGroupCustomListView;
    private UserModel mUserModel;
    private int mGroupId;
    public GroupCustomListPresenter(int groupId, @NonNull GroupCustomListContract.IGroupCustomListView iGroupCustomListView){
        mGroupCustInfoBeanList=new ArrayList<>();
        mGroupId=groupId;
        mGroupCustomListView=iGroupCustomListView;
        mUserModel=UserModel.createInstance();
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
        refreshCustomList();
    }

    @Override
    public void refreshCustomList() {
        mLeastPageIndex=mCurrentPageIndex;
        mCurrentPageIndex=1;
        int doctorId= UserManager.getInstance().getDoctorInfo().Id;
        int departId= UserManager.getInstance().getDoctorInfo().ServiceDeptId;
        mUserModel.GetGroupCustInfoList(departId, mGroupId, doctorId, mCurrentPageIndex, PAGE_SIZE, new OnHandlerResultListener<GlobalShell<PageBean<GroupCustInfoBean>>>() {
            @Override
            public void handlerResult(GlobalShell<PageBean<GroupCustInfoBean>> resultData) {
                if(resultData.LogicSuccess) {
                    mGroupCustomListView.hideDialog();
                    mGroupCustInfoBeanList.clear();
                    mGroupCustInfoBeanList.addAll(resultData.Data.CurrentPageDataList);
                    mGroupCustomListView.refreshCustomAdapter(mGroupCustInfoBeanList);
                    mGroupCustomListView.refreshFinish(PullToRefreshLayout.SUCCEED);
                }
                else{
                    mGroupCustomListView.hideDialog(resultData.Message);
                    mGroupCustomListView.refreshFinish(PullToRefreshLayout.FAIL);
                    mCurrentPageIndex=mLeastPageIndex;
                }
            }
        });
    }

    @Override
    public void loadmoreCustomList() {
        mLeastPageIndex=mCurrentPageIndex;
        mCurrentPageIndex++;
        int doctorId= UserManager.getInstance().getDoctorInfo().Id;
        int departId= UserManager.getInstance().getDoctorInfo().ServiceDeptId;
        mUserModel.GetGroupCustInfoList(departId, mGroupId, doctorId, mCurrentPageIndex, PAGE_SIZE, new OnHandlerResultListener<GlobalShell<PageBean<GroupCustInfoBean>>>() {
            @Override
            public void handlerResult(GlobalShell<PageBean<GroupCustInfoBean>> resultData) {
                if(resultData.LogicSuccess) {
                    mGroupCustomListView.hideDialog();
                    mGroupCustInfoBeanList.addAll(resultData.Data.CurrentPageDataList);
                    mGroupCustomListView.refreshCustomAdapter(mGroupCustInfoBeanList);
                    mGroupCustomListView.refreshFinish(PullToRefreshLayout.SUCCEED);
                }
                else{
                    mGroupCustomListView.hideDialog(resultData.Message);
                    mGroupCustomListView.refreshFinish(PullToRefreshLayout.FAIL);
                    mCurrentPageIndex=mLeastPageIndex;
                }
            }
        });
    }
}
