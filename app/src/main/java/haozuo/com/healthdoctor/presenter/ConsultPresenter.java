package haozuo.com.healthdoctor.presenter;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import haozuo.com.healthdoctor.bean.ConsultDoneItemBean;
import haozuo.com.healthdoctor.bean.ConsultItemBean;
import haozuo.com.healthdoctor.bean.FeedbackItemBean;
import haozuo.com.healthdoctor.bean.GlobalShell;
import haozuo.com.healthdoctor.bean.PageBean;
import haozuo.com.healthdoctor.contract.ConsultContract;
import haozuo.com.healthdoctor.contract.IBaseModel;
import haozuo.com.healthdoctor.contract.IBaseView;
import haozuo.com.healthdoctor.listener.OnHandlerResultListener;
import haozuo.com.healthdoctor.manager.UserManager;
import haozuo.com.healthdoctor.model.ConsultModel;
import haozuo.com.healthdoctor.view.threePart.PullToRefresh.PullToRefreshLayout;

/**
 * Created by hzguest3 on 2016/8/1.
 */

public class ConsultPresenter extends AbstractPresenter implements ConsultContract.IConsultPresenter {
    private int PAGE_SIZE = 20;
    private int mCurrentPageIndex = 1;
    private int mConssultDonePageIndex = 1;
    private int mFeedbackPageIndex = 1;
    private int mLeastPageIndex = 1;
    private ConsultContract.IConsultView mIConsultView;
    private ConsultModel mConsultModel;
    private List<ConsultItemBean> mConsultItemBeanList;
    private List<ConsultDoneItemBean> mConsultDoneBeanList;
    private List<FeedbackItemBean> mFeedbackBeanList;

    @Inject
    public ConsultPresenter(@NonNull ConsultContract.IConsultView iConsultView, @NonNull ConsultModel consultModel) {
        mConsultItemBeanList = new ArrayList<ConsultItemBean>();
        mConsultDoneBeanList = new ArrayList<ConsultDoneItemBean>();
        mFeedbackBeanList = new ArrayList<FeedbackItemBean>();
        mIConsultView = iConsultView;
        mConsultModel = consultModel;
        iConsultView.setPresenter(this);
    }

    @Override
    public IBaseView getBaseView() {
        return mIConsultView;
    }

    @Override
    public IBaseModel[] getBaseModelList() {
        return new IBaseModel[]{mConsultModel};
    }

    @Override
    public void start() {

    }

    @Override
    public void refreshCustomList(int flag) {
        mCurrentPageIndex = 1;
        mIConsultView.showDialog();
        int doctorId = UserManager.getInstance().getDoctorInfo().Doctor_ID;
        mConsultModel.GetGroupCustInfoList(doctorId, mCurrentPageIndex, PAGE_SIZE, flag, new OnHandlerResultListener<GlobalShell<PageBean<ConsultItemBean>>>() {
            @Override
            public void handlerResult(GlobalShell<PageBean<ConsultItemBean>> resultData) {
                if (resultData.LogicSuccess) {
                    mIConsultView.hideDialog();
                    mConsultItemBeanList.clear();
                    if (resultData.Data.CurrentPageDataList != null) {
                        mConsultItemBeanList.addAll(resultData.Data.CurrentPageDataList);
                    }
                    mIConsultView.refreshPendingPageList(mConsultItemBeanList);
                    mIConsultView.refreshFinish(PullToRefreshLayout.SUCCEED);
                } else {
                    mIConsultView.hideDialog(resultData.Message);
                    mIConsultView.refreshFinish(PullToRefreshLayout.FAIL);
                    mCurrentPageIndex = mLeastPageIndex;
                }
            }
        });
    }

    @Override
    public void loadmoreCustomList(int flag) {
        //http request here;
        mLeastPageIndex = mCurrentPageIndex;
        mCurrentPageIndex++;
        mIConsultView.showDialog();
        int doctorId = UserManager.getInstance().getDoctorInfo().Doctor_ID;
        mConsultModel.GetGroupCustInfoList(doctorId, mCurrentPageIndex, PAGE_SIZE, flag, new OnHandlerResultListener<GlobalShell<PageBean<ConsultItemBean>>>() {
            @Override
            public void handlerResult(GlobalShell<PageBean<ConsultItemBean>> resultData) {
                if (resultData.LogicSuccess) {
                    mIConsultView.hideDialog();
                    mConsultItemBeanList.addAll(resultData.Data.CurrentPageDataList);
                    mIConsultView.refreshPendingPageList(mConsultItemBeanList);
                    mIConsultView.refreshFinish(PullToRefreshLayout.SUCCEED);
                } else {
                    mIConsultView.hideDialog(resultData.Message);
                    mIConsultView.refreshFinish(PullToRefreshLayout.FAIL);
                    mCurrentPageIndex = mLeastPageIndex;
                }
            }
        });
    }

    @Override
    public void refreshConsultDoneList(int flag) {
        String beginCommitOn = "2016-08-01";
        String endCommitOn = "2016-08-16";
        mConssultDonePageIndex = 1;
        mIConsultView.showDialog();
        int doctorId = UserManager.getInstance().getDoctorInfo().Doctor_ID;
        mConsultModel.GetConsultDoneInfoList(doctorId, beginCommitOn, endCommitOn, mConssultDonePageIndex, PAGE_SIZE, new OnHandlerResultListener<GlobalShell<PageBean<ConsultDoneItemBean>>>() {
            @Override
            public void handlerResult(GlobalShell<PageBean<ConsultDoneItemBean>> resultData) {
                if (resultData.LogicSuccess) {
                    mIConsultView.hideDialog();
                    mConsultDoneBeanList.clear();
                    if (resultData.Data.CurrentPageDataList != null) {
                        mConsultDoneBeanList.addAll(resultData.Data.CurrentPageDataList);
                    }
                    mIConsultView.refreshConsultDonePageList(mConsultDoneBeanList);
                    mIConsultView.refreshConsultDonePageFinish(PullToRefreshLayout.SUCCEED);
                    mConssultDonePageIndex++;
                } else {
                    mIConsultView.hideDialog(resultData.Message);
                    mIConsultView.refreshConsultDonePageFinish(PullToRefreshLayout.FAIL);
                }
            }
        });
    }

    @Override
    public void loadmoreConsultDoneList(int flag) {
        String beginCommitOn = "2016-08-01";
        String endCommitOn = "2016-08-16";
        mIConsultView.showDialog();
        int doctorId = UserManager.getInstance().getDoctorInfo().Doctor_ID;
        mConsultModel.GetConsultDoneInfoList(doctorId, beginCommitOn, endCommitOn, mConssultDonePageIndex, PAGE_SIZE, new OnHandlerResultListener<GlobalShell<PageBean<ConsultDoneItemBean>>>() {
            @Override
            public void handlerResult(GlobalShell<PageBean<ConsultDoneItemBean>> resultData) {
                if (resultData.LogicSuccess) {
                    mIConsultView.hideDialog();
                    if (resultData.Data.CurrentPageDataList != null) {
                        mConsultDoneBeanList.addAll(resultData.Data.CurrentPageDataList);
                    }
                    mIConsultView.refreshConsultDonePageList(mConsultDoneBeanList);
                    mIConsultView.refreshConsultDonePageFinish(PullToRefreshLayout.SUCCEED);
                    mConssultDonePageIndex++;
                } else {
                    mIConsultView.hideDialog(resultData.Message);
                    mIConsultView.refreshConsultDonePageFinish(PullToRefreshLayout.FAIL);
                }
            }
        });
    }

    @Override
    public void refreshFeedBackList(int flag) {
        mFeedbackPageIndex = 1;
        mIConsultView.showDialog();
        int doctorId = UserManager.getInstance().getDoctorInfo().Doctor_ID;
        mConsultModel.GetFeedbackInfoList(flag, doctorId, mFeedbackPageIndex, PAGE_SIZE, new OnHandlerResultListener<GlobalShell<PageBean<FeedbackItemBean>>>() {
            @Override
            public void handlerResult(GlobalShell<PageBean<FeedbackItemBean>> resultData) {
                if (resultData.LogicSuccess) {
                    mIConsultView.hideDialog();
                    mFeedbackBeanList.clear();
                    if (resultData.Data.CurrentPageDataList != null) {
                        mFeedbackBeanList.addAll(resultData.Data.CurrentPageDataList);
                    }
                    mIConsultView.refreshFeedbackPageList(mFeedbackBeanList);
                    mIConsultView.refreshFeedbackPageFinish(PullToRefreshLayout.SUCCEED);
                    mFeedbackPageIndex++;
                } else {
                    mIConsultView.hideDialog(resultData.Message);
                    mIConsultView.refreshFeedbackPageFinish(PullToRefreshLayout.FAIL);
                }
            }
        });
    }

    @Override
    public void loadmoreFeedBackList(int flag) {
        mIConsultView.showDialog();
        int doctorId = UserManager.getInstance().getDoctorInfo().Doctor_ID;
        mConsultModel.GetFeedbackInfoList(flag, doctorId, mFeedbackPageIndex, PAGE_SIZE, new OnHandlerResultListener<GlobalShell<PageBean<FeedbackItemBean>>>() {
            @Override
            public void handlerResult(GlobalShell<PageBean<FeedbackItemBean>> resultData) {
                if (resultData.LogicSuccess) {
                    mIConsultView.hideDialog();
                    if (resultData.Data.CurrentPageDataList != null) {
                        mFeedbackBeanList.addAll(resultData.Data.CurrentPageDataList);
                    }
                    mIConsultView.refreshFeedbackPageList(mFeedbackBeanList);
                    mIConsultView.refreshFeedbackPageFinish(PullToRefreshLayout.SUCCEED);
                    mConssultDonePageIndex++;
                } else {
                    mIConsultView.hideDialog(resultData.Message);
                    mIConsultView.refreshFeedbackPageFinish(PullToRefreshLayout.FAIL);
                }
            }
        });
    }
}
