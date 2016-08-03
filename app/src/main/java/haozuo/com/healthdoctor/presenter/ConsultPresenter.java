package haozuo.com.healthdoctor.presenter;

import android.support.annotation.NonNull;


import java.util.ArrayList;
import java.util.List;

import haozuo.com.healthdoctor.bean.ConsultItemBean;
import haozuo.com.healthdoctor.bean.GlobalShell;
import haozuo.com.healthdoctor.bean.GroupCustInfoBean;
import haozuo.com.healthdoctor.bean.PageBean;
import haozuo.com.healthdoctor.contract.ConsultContract;
import haozuo.com.healthdoctor.contract.GroupContract;
import haozuo.com.healthdoctor.contract.IBaseModel;
import haozuo.com.healthdoctor.contract.IBaseView;
import haozuo.com.healthdoctor.listener.OnHandlerResultListener;
import haozuo.com.healthdoctor.manager.UserManager;
import haozuo.com.healthdoctor.model.ConsultModel;
import haozuo.com.healthdoctor.model.UserModel;
import haozuo.com.healthdoctor.view.custom.CustomDetailFragment;
import haozuo.com.healthdoctor.view.threePart.PullToRefresh.PullToRefreshLayout;

/**
 * Created by hzguest3 on 2016/8/1.
 */

public class ConsultPresenter extends AbstractPresenter implements ConsultContract.IConsultPresenter {
    private int PAGE_SIZE=20;
    private int mCurrentPageIndex=1;
    private int mLeastPageIndex=1;
    private ConsultContract.IConsultView mIConsultView;
    private ConsultModel mUserModel;
    private List<ConsultItemBean> mConsultItemBeanList;
    public ConsultPresenter(@NonNull  ConsultContract.IConsultView iConsultView){
        mConsultItemBeanList = new ArrayList<ConsultItemBean>();
        mIConsultView=iConsultView;
        mUserModel=ConsultModel.createInstance();
        iConsultView.setPresenter(this);
    }

    @Override
    public IBaseView getBaseView() {
        return mIConsultView;
    }

    @Override
    public IBaseModel getBaseModel() {
        return mUserModel;
    }

    @Override
    public void start() {

    }

    @Override
    public void refreshCustomList(int flag) {
        //http request here;
        mIConsultView.showDialog();
        int doctorId= UserManager.getInstance().getDoctorInfo().Doctor_ID;
        mUserModel.GetGroupCustInfoList(doctorId,mCurrentPageIndex,PAGE_SIZE,flag, new OnHandlerResultListener<GlobalShell<PageBean<ConsultItemBean>>>() {
            @Override
            public void handlerResult(GlobalShell<PageBean<ConsultItemBean>> resultData) {
                if(resultData.LogicSuccess) {
                    mIConsultView.hideDialog();
                    mConsultItemBeanList.clear();
                    if(resultData.Data.CurrentPageDataList != null){
                        mConsultItemBeanList.addAll(resultData.Data.CurrentPageDataList);
                    }
                    mIConsultView.RefreshPendingPageList(mConsultItemBeanList);
                    mIConsultView.refreshFinish(PullToRefreshLayout.SUCCEED);
                }
                else{
                    mIConsultView.hideDialog(resultData.Message);
                    mIConsultView.refreshFinish(PullToRefreshLayout.FAIL);
                    mCurrentPageIndex=mLeastPageIndex;
                }
            }
        });
    }

    @Override
    public void loadmoreCustomList(int flag) {
        //http request here;
        mLeastPageIndex=mCurrentPageIndex;
        mCurrentPageIndex++;
        mIConsultView.showDialog();
        int doctorId= UserManager.getInstance().getDoctorInfo().Doctor_ID;
        mUserModel.GetGroupCustInfoList(doctorId,mCurrentPageIndex,PAGE_SIZE,flag, new OnHandlerResultListener<GlobalShell<PageBean<ConsultItemBean>>>() {
            @Override
            public void handlerResult(GlobalShell<PageBean<ConsultItemBean>> resultData) {
                if(resultData.LogicSuccess) {
                    mIConsultView.hideDialog();
                    mConsultItemBeanList.addAll(resultData.Data.CurrentPageDataList);
                    mIConsultView.RefreshPendingPageList(mConsultItemBeanList);
                    mIConsultView.refreshFinish(PullToRefreshLayout.SUCCEED);
                }
                else{
                    mIConsultView.hideDialog(resultData.Message);
                    mIConsultView.refreshFinish(PullToRefreshLayout.FAIL);
                    mCurrentPageIndex=mLeastPageIndex;
                }
            }
        });
    }
}
