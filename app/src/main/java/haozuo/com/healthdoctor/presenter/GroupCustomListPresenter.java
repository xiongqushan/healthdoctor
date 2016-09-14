package haozuo.com.healthdoctor.presenter;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import haozuo.com.healthdoctor.bean.GlobalShell;
import haozuo.com.healthdoctor.bean.GroupCustInfoBean;
import haozuo.com.healthdoctor.bean.PageBean;
import haozuo.com.healthdoctor.contract.GroupCustomListContract;
import haozuo.com.healthdoctor.model.IBaseModel;
import haozuo.com.healthdoctor.view.IBaseView;
import haozuo.com.healthdoctor.listener.OnHandlerResultListener;
import haozuo.com.healthdoctor.manager.UserManager;
import haozuo.com.healthdoctor.model.GroupModel;
import haozuo.com.healthdoctor.view.threePart.PullToRefresh.PullToRefreshLayout;

/**
 * Created by xiongwei1 on 2016/7/11.
 */
public class GroupCustomListPresenter extends AbstractPresenter implements GroupCustomListContract.IGroupCustomListPresenter {
    private int PAGE_SIZE = 20;
    private int mCurrentPageIndex = 1;
    private int mLeastPageIndex = 1;
    private int mGroupId;
    //    private String mCustomNameOrMobile;
    private List<GroupCustInfoBean> mGroupCustInfoBeanList;
    private GroupCustomListContract.IGroupCustomListView mGroupCustomListView;
    private GroupModel mGroupModel;
//    private boolean isInit;


    @Inject
    public GroupCustomListPresenter(@NonNull GroupCustomListContract.IGroupCustomListView iGroupCustomListView, @NonNull GroupModel groupModel, @NonNull int groupId) {
        mGroupCustInfoBeanList = new ArrayList<>();
        mGroupId = groupId;
        mGroupCustomListView = iGroupCustomListView;
        mGroupModel = groupModel;
        mGroupCustomListView.setPresenter(this);
//        isInit = true;
    }

    @Override
    public IBaseView getBaseView() {
        return mGroupCustomListView;
    }

    @Override
    public IBaseModel[] getBaseModelList() {
        return new IBaseModel[]{mGroupModel};
    }

    @Override
    public void start() {
        refreshCustomList("", true);
    }

    @Override
    public void refreshCustomList(String customNameOrMobile, final Boolean isInit) {
        if (isInit) {
            mGroupCustomListView.showDialog();
        }
        mLeastPageIndex = mCurrentPageIndex;
        mCurrentPageIndex = 1;
        int doctorId = UserManager.getInstance().getDoctorInfo().Doctor_ID;
        int departId = UserManager.getInstance().getDoctorInfo().Dept;
        mGroupModel.GetGroupCustInfoList(departId, mGroupId, doctorId, customNameOrMobile, mCurrentPageIndex, PAGE_SIZE, new OnHandlerResultListener<GlobalShell<PageBean<GroupCustInfoBean>>>() {
            @Override
            public void handlerResult(GlobalShell<PageBean<GroupCustInfoBean>> resultData) {
                if (resultData.LogicSuccess) {
                    mGroupCustInfoBeanList.clear();
                    if (resultData.Data.CurrentPageDataList != null) {
                        mGroupCustInfoBeanList.addAll(resultData.Data.CurrentPageDataList);
                    }
                    mGroupCustomListView.refreshCustomAdapter(mGroupCustInfoBeanList);
                    if (!isInit) {
                        mGroupCustomListView.refreshFinish(PullToRefreshLayout.SUCCEED, true);

                    }
                    if (isInit) {
                        mGroupCustomListView.hideDialog();
                    }
                } else {
                    if (!isInit) {
                        mGroupCustomListView.refreshFinish(PullToRefreshLayout.FAIL, true);
                    }
                    mCurrentPageIndex = mLeastPageIndex;
                    if (isInit) {
                        mGroupCustomListView.hideDialog(resultData.Message);
                    }
                }
//                isInit = false;
            }
        });
    }

    @Override
    public void loadmoreCustomList(String customNameOrMobile) {
        mLeastPageIndex = mCurrentPageIndex;
        mCurrentPageIndex++;
        int doctorId = UserManager.getInstance().getDoctorInfo().Doctor_ID;
        int departId = UserManager.getInstance().getDoctorInfo().Dept;
//        mGroupCustomListView.showDialog();
        mGroupModel.GetGroupCustInfoList(departId, mGroupId, doctorId, customNameOrMobile, mCurrentPageIndex, PAGE_SIZE, new OnHandlerResultListener<GlobalShell<PageBean<GroupCustInfoBean>>>() {
            @Override
            public void handlerResult(GlobalShell<PageBean<GroupCustInfoBean>> resultData) {
                if (resultData.LogicSuccess) {
                    mGroupCustInfoBeanList.addAll(resultData.Data.CurrentPageDataList);
                    mGroupCustomListView.refreshCustomAdapter(mGroupCustInfoBeanList);
                    mGroupCustomListView.refreshFinish(PullToRefreshLayout.SUCCEED, false);
//                    mGroupCustomListView.hideDialog();
                } else {
                    mGroupCustomListView.refreshFinish(PullToRefreshLayout.FAIL, false);
                    mCurrentPageIndex = mLeastPageIndex;
//                    mGroupCustomListView.hideDialog(resultData.Message);
                }
            }
        });
    }
}
