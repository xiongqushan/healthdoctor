package haozuo.com.healthdoctor.presenter;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import haozuo.com.healthdoctor.bean.GlobalShell;
import haozuo.com.healthdoctor.bean.GroupCustInfoBean;
import haozuo.com.healthdoctor.bean.PageBean;
import haozuo.com.healthdoctor.contract.IBaseModel;
import haozuo.com.healthdoctor.contract.IBaseView;
import haozuo.com.healthdoctor.contract.GroupCustomListContract;
import haozuo.com.healthdoctor.listener.OnHandlerResultListener;
import haozuo.com.healthdoctor.manager.UserManager;
import haozuo.com.healthdoctor.model.GroupModel;
import haozuo.com.healthdoctor.model.UserModel;
import haozuo.com.healthdoctor.view.threePart.PullToRefresh.PullToRefreshLayout;

/**
 * Created by xiongwei1 on 2016/7/11.
 */
public class GroupCustomListPresenter extends AbstractPresenter implements GroupCustomListContract.IGroupCustomListPresenter {
    private int PAGE_SIZE=20;
    private int mCurrentPageIndex=1;
    private int mLeastPageIndex=1;
    private List<GroupCustInfoBean>mGroupCustInfoBeanList;
    private GroupCustomListContract.IGroupCustomListView mGroupCustomListView;
    private GroupModel mGroupModel;
    private int mGroupId;

    @Inject
    public GroupCustomListPresenter(@NonNull GroupCustomListContract.IGroupCustomListView iGroupCustomListView,@NonNull GroupModel groupModel,@NonNull int groupId){
        mGroupCustInfoBeanList=new ArrayList<>();
        mGroupId=groupId;
        mGroupCustomListView=iGroupCustomListView;
        mGroupModel=groupModel;
        mGroupCustomListView.setPresenter(this);
    }

    @Override
    public IBaseView getBaseView() {
        return mGroupCustomListView;
    }

    @Override
    public IBaseModel getBaseModel() {
        return mGroupModel;
    }

    @Override
    public void start() {
        refreshCustomList();
    }

    @Override
    public void refreshCustomList() {
        mLeastPageIndex=mCurrentPageIndex;
        mCurrentPageIndex=1;
        int doctorId= UserManager.getInstance().getDoctorInfo().Doctor_ID;
        int departId= UserManager.getInstance().getDoctorInfo().Dept;
        mGroupModel.GetGroupCustInfoList(departId, mGroupId, doctorId, mCurrentPageIndex, PAGE_SIZE, new OnHandlerResultListener<GlobalShell<PageBean<GroupCustInfoBean>>>() {
            @Override
            public void handlerResult(GlobalShell<PageBean<GroupCustInfoBean>> resultData) {
                if(resultData.LogicSuccess) {
                    mGroupCustomListView.hideDialog();
                    mGroupCustInfoBeanList.clear();
                    if (resultData.Data.CurrentPageDataList != null){
                        mGroupCustInfoBeanList.addAll(resultData.Data.CurrentPageDataList);
                    }
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
        int doctorId= UserManager.getInstance().getDoctorInfo().Doctor_ID;
        int departId= UserManager.getInstance().getDoctorInfo().Dept;
        mGroupModel.GetGroupCustInfoList(departId, mGroupId, doctorId, mCurrentPageIndex, PAGE_SIZE, new OnHandlerResultListener<GlobalShell<PageBean<GroupCustInfoBean>>>() {
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
