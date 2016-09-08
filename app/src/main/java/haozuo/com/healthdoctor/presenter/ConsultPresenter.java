package haozuo.com.healthdoctor.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import haozuo.com.healthdoctor.BuildConfig;
import haozuo.com.healthdoctor.bean.ConsultDoneItemBean;
import haozuo.com.healthdoctor.bean.ConsultItemBean;
import haozuo.com.healthdoctor.bean.FeedbackItemBean;
import haozuo.com.healthdoctor.bean.GlobalShell;
import haozuo.com.healthdoctor.bean.PageBean;
import haozuo.com.healthdoctor.contract.ConsultContract;
import haozuo.com.healthdoctor.listener.OnHandlerResultListener;
import haozuo.com.healthdoctor.manager.UserManager;
import haozuo.com.healthdoctor.model.ConsultModel;
import haozuo.com.healthdoctor.model.IBaseModel;
import haozuo.com.healthdoctor.view.IBaseView;
import haozuo.com.healthdoctor.view.threePart.PullToRefresh.PullToRefreshLayout;

/**
 * Created by hzguest3 on 2016/8/1.
 */

public class ConsultPresenter extends AbstractPresenter implements ConsultContract.IConsultPresenter {

    private int PAGE_SIZE = 20;
    private ConsultContract.IConsultView mIConsultView;
    private ConsultModel mConsultModel;

    @Inject
    public ConsultPresenter(@NonNull ConsultContract.IConsultView iConsultView, @NonNull ConsultModel consultModel) {
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

    public void refreshCustomList(final int flag, final boolean initData) {
        if (flag == 3 && initData) mIConsultView.showDialog();
        int doctorId = UserManager.getInstance().getDoctorInfo().Doctor_ID;
        mConsultModel.GetGroupCustInfoList(doctorId, 1, PAGE_SIZE, flag, new OnHandlerResultListener<GlobalShell<PageBean<ConsultItemBean>>>() {
            @Override
            public void handlerResult(GlobalShell<PageBean<ConsultItemBean>> resultData) {
                if (resultData.LogicSuccess) {
//                    mConsultItemBeanList.clear();
//                    if (resultData.Data.CurrentPageDataList != null) {
//                        mConsultItemBeanList.addAll(resultData.Data.CurrentPageDataList);
//                    }
                    mIConsultView.refreshPendingPageList(resultData.Data.CurrentPageDataList, flag, true);
                    if (!initData) {
                        mIConsultView.refreshFinish(PullToRefreshLayout.SUCCEED, flag, true);
                    }
                    // TODO 添加改变 资讯页面 消息数量
                    if (flag == 3) {
                        mIConsultView.updateMsgCounts(resultData.Data.Count);
                    }
//                    if (!(flag == 2 && initData)) {
//                        mIConsultView.hideDialog();
//                    }
                    if (flag == 3 && initData) mIConsultView.hideDialog();

                } else {
//                    mIConsultView.hideDialog(resultData.Message);
                    if (!initData) {
                        mIConsultView.refreshFinish(PullToRefreshLayout.FAIL, flag, true);
                    }
                }
            }
        });
    }

    @Override
    public void loadmoreCustomList(final int flag, int pageIndex) {
        //http request here;
        // mIConsultView.showDialog();
        int doctorId = UserManager.getInstance().getDoctorInfo().Doctor_ID;
        mConsultModel.GetGroupCustInfoList(doctorId, pageIndex, PAGE_SIZE, flag, new OnHandlerResultListener<GlobalShell<PageBean<ConsultItemBean>>>() {
            @Override
            public void handlerResult(GlobalShell<PageBean<ConsultItemBean>> resultData) {
                if (resultData.LogicSuccess) {
                    // mIConsultView.hideDialog();
                    //mConsultItemBeanList.addAll(resultData.Data.CurrentPageDataList);
                    mIConsultView.refreshPendingPageList(resultData.Data.CurrentPageDataList, flag, false);
                    mIConsultView.refreshFinish(PullToRefreshLayout.SUCCEED, flag, false);
                } else {
                    //mIConsultView.hideDialog(resultData.Message);
                    mIConsultView.refreshFinish(PullToRefreshLayout.FAIL, flag, false);
                }
            }
        });
    }

    private String getEndDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
        return format.format(new Date());
    }

    private String getBeginDate(int type) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        if (type == 2) {
            Calendar c = Calendar.getInstance();
            c.add(Calendar.DATE, -7);
            Date monday = c.getTime();
            String preMonday = format.format(monday);
            return preMonday;
        }
        if (type == 3) {
            SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-01");
            return mFormat.format(new Date());
        }
        return format.format(new Date());
    }

    @Override
    public void refreshConsultDoneList(final int flag, final boolean initData) {
        String beginCommitOn = getBeginDate(flag);
        String endCommitOn = getEndDate();
        if (BuildConfig.DEBUG) {
            Log.e("beginCommitOn", beginCommitOn);
            Log.e("endCommitOn", endCommitOn);
        }
        if (flag == 1 && initData) mIConsultView.showDialog();
        int doctorId = UserManager.getInstance().getDoctorInfo().Doctor_ID;
        mConsultModel.GetConsultDoneInfoList(doctorId, beginCommitOn, endCommitOn, 1, PAGE_SIZE, new OnHandlerResultListener<GlobalShell<PageBean<ConsultDoneItemBean>>>() {
            @Override
            public void handlerResult(GlobalShell<PageBean<ConsultDoneItemBean>> resultData) {
                if (resultData.LogicSuccess) {
//                    mConsultDoneBeanList.clear();
//                    if (resultData.Data.CurrentPageDataList != null) {
//                        mConsultDoneBeanList.addAll(resultData.Data.CurrentPageDataList);
//                    }
                    mIConsultView.refreshConsultDonePageList(resultData.Data.CurrentPageDataList, flag, true);
                    if (!initData) {
                        mIConsultView.refreshConsultDonePageFinish(PullToRefreshLayout.SUCCEED, flag, true);
                    }
//                    if (!(flag == 2 && initData)) {
                    if (flag == 1 && initData) mIConsultView.hideDialog();
//                    }
                } else {
                    if (!initData) {
                        mIConsultView.refreshConsultDonePageFinish(PullToRefreshLayout.FAIL, flag, true);
                    }
                    mIConsultView.hideDialog(resultData.Message);
                }
            }
        });
    }

    @Override
    public void loadmoreConsultDoneList(final int flag, int pageIndex) {
        String beginCommitOn = getBeginDate(flag);
        String endCommitOn = getEndDate();
//        mIConsultView.showDialog();
        int doctorId = UserManager.getInstance().getDoctorInfo().Doctor_ID;
        mConsultModel.GetConsultDoneInfoList(doctorId, beginCommitOn, endCommitOn, pageIndex, PAGE_SIZE, new OnHandlerResultListener<GlobalShell<PageBean<ConsultDoneItemBean>>>() {
            @Override
            public void handlerResult(GlobalShell<PageBean<ConsultDoneItemBean>> resultData) {
                if (resultData.LogicSuccess) {
//                    mIConsultView.hideDialog();
//                    if (resultData.Data.CurrentPageDataList != null) {
//                        mConsultDoneBeanList.addAll(resultData.Data.CurrentPageDataList);
//                    }
                    mIConsultView.refreshConsultDonePageList(resultData.Data.CurrentPageDataList, flag, false);
                    mIConsultView.refreshConsultDonePageFinish(PullToRefreshLayout.SUCCEED, flag, false);
                } else {
//                    mIConsultView.hideDialog(resultData.Message);
                    mIConsultView.refreshConsultDonePageFinish(PullToRefreshLayout.FAIL, flag, false);
                }
            }
        });
    }

    @Override
    public void refreshFeedBackList(final int flag, final boolean initData) {
        if (flag == 3 && initData) mIConsultView.showDialog();
        int doctorId = UserManager.getInstance().getDoctorInfo().Doctor_ID;
        mConsultModel.GetFeedbackInfoList(flag, doctorId, 1, PAGE_SIZE, new OnHandlerResultListener<GlobalShell<PageBean<FeedbackItemBean>>>() {
            @Override
            public void handlerResult(GlobalShell<PageBean<FeedbackItemBean>> resultData) {
                if (resultData.LogicSuccess) {
//                    mFeedbackBeanList.clear();
//                    if (resultData.Data.CurrentPageDataList != null) {
//                        mFeedbackBeanList.addAll(resultData.Data.CurrentPageDataList);
//                    }
                    if (!initData) {
                        mIConsultView.refreshFeedbackPageFinish(PullToRefreshLayout.SUCCEED, flag, true);
                    }
//                    if (!(flag == 2 && initData)) {
                    if (flag == 3 && initData) mIConsultView.hideDialog();
                    // }
                    mIConsultView.refreshFeedbackPageList(resultData.Data.CurrentPageDataList, flag, true);
                } else {
                    mIConsultView.hideDialog(resultData.Message);
                    if (!initData) {
                        mIConsultView.refreshFeedbackPageFinish(PullToRefreshLayout.FAIL, flag, true);
                    }
                }
            }
        });
    }

    @Override
    public void loadmoreFeedBackList(final int flag, int pageIndex) {
//        mIConsultView.showDialog();
        int doctorId = UserManager.getInstance().getDoctorInfo().Doctor_ID;
        mConsultModel.GetFeedbackInfoList(flag, doctorId, pageIndex, PAGE_SIZE, new OnHandlerResultListener<GlobalShell<PageBean<FeedbackItemBean>>>() {
            @Override
            public void handlerResult(GlobalShell<PageBean<FeedbackItemBean>> resultData) {
                if (resultData.LogicSuccess) {
                    // mIConsultView.hideDialog();
//                    if (resultData.Data.CurrentPageDataList != null) {
//                        mFeedbackBeanList.addAll(resultData.Data.CurrentPageDataList);
//                    }
                    mIConsultView.refreshFeedbackPageList(resultData.Data.CurrentPageDataList, flag, false);
                    mIConsultView.refreshFeedbackPageFinish(PullToRefreshLayout.SUCCEED, flag, false);
                } else {
                    // mIConsultView.hideDialog(resultData.Message);
                    mIConsultView.refreshFeedbackPageFinish(PullToRefreshLayout.FAIL, flag, false);
                }
            }
        });
    }


}
