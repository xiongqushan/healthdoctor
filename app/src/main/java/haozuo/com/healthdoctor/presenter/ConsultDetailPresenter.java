package haozuo.com.healthdoctor.presenter;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import haozuo.com.healthdoctor.bean.ConsultReplyBean;
import haozuo.com.healthdoctor.bean.CustomDetailBean;
import haozuo.com.healthdoctor.bean.GlobalShell;
import haozuo.com.healthdoctor.contract.ConsultDetailContract;
import haozuo.com.healthdoctor.model.IBaseModel;
import haozuo.com.healthdoctor.view.IBaseView;
import haozuo.com.healthdoctor.listener.OnHandlerResultListener;
import haozuo.com.healthdoctor.model.ConsultModel;
import haozuo.com.healthdoctor.model.GroupModel;
import haozuo.com.healthdoctor.model.UserModel;
import haozuo.com.healthdoctor.util.DateUtil;
import haozuo.com.healthdoctor.view.consult.ConsultDetailFragment;
import haozuo.com.healthdoctor.view.threePart.PullToRefresh.PullToRefreshLayout;

/**
 * Created by hzguest3 on 2016/8/3.
 */

public class ConsultDetailPresenter extends AbstractPresenter implements ConsultDetailContract.IConsultDetailPresenter {
    private ConsultDetailContract.IConsultDetailView mIConsultDetailView;
    private ConsultModel mConsultModel;
    private UserModel mUserModel;
    private ConsultReplyBean consultReplyBean;
    private List<ConsultReplyBean> mConsultItemBeanList;
    private String mCommitOn;
    private int mCustomerId;
    private boolean isInit;

    @Inject
    public ConsultDetailPresenter(@NonNull ConsultDetailContract.IConsultDetailView iConsultDetailView, @NonNull ConsultModel consultModel,@NonNull GroupModel groupModel,@NonNull UserModel userModel, @NonNull int customerId){
        mConsultItemBeanList = new ArrayList<ConsultReplyBean>();
        mIConsultDetailView=iConsultDetailView;
        mConsultModel=consultModel;
        mUserModel = userModel;
        mCustomerId=customerId;
        isInit = true;
        mCommitOn = DateUtil.date2Str(new Date(),"yyyyMMddHHmmss");
        iConsultDetailView.setPresenter(this);
        consultReplyBean = new ConsultReplyBean();
    }

    @Override
    public IBaseView getBaseView() {
        return mIConsultDetailView;
    }

    @Override
    public IBaseModel[] getBaseModelList() {
        return new IBaseModel[]{mConsultModel};
    }

    @Override
    public void start() {}

    @Override
    public void loadmoreConsultList() {
        if (isInit){
            mIConsultDetailView.showDialog();
        }
        mConsultModel.GetConsultReplyList(mCustomerId,mCommitOn, new OnHandlerResultListener<GlobalShell<List<ConsultReplyBean>>>() {
            @Override
            public void handlerResult(GlobalShell<List<ConsultReplyBean>> resultData) {
                if(resultData.LogicSuccess) {
                    mIConsultDetailView.hideDialog();
                    if ((List<ConsultReplyBean>) resultData.Data != null){
                        List<ConsultReplyBean> loadmoreConsultResults = new ArrayList<ConsultReplyBean>();
                        loadmoreConsultResults.addAll(resultData.Data);
                        for (int i=0;i<resultData.Data.size();i++){
                            for (int j=0;j<mConsultItemBeanList.size();j++){
                                if (resultData.Data.get(i).Id == mConsultItemBeanList.get(j).Id){
                                    loadmoreConsultResults.remove(resultData.Data.get(i));
                                }
                            }
                        }
                        if (!isInit){
                            mIConsultDetailView.refreshFinish(PullToRefreshLayout.SUCCEED);
                        }


                        if(loadmoreConsultResults.size() == 0) {
                            return;
                        }
                        Collections.sort(loadmoreConsultResults,consultReplyBean);
                        mConsultItemBeanList.addAll(0,loadmoreConsultResults);
                        mIConsultDetailView.refreshCustomAdapter(mConsultItemBeanList);
                        mCommitOn =mConsultItemBeanList.get(0).CommitOn.replaceAll("(?:T|:|-)","");
                        mIConsultDetailView.setListViewPosition(loadmoreConsultResults.size(), ConsultDetailFragment.SELECT_POSITION_DIRECT);
//                        mIConsultDetailView.setListViewPosition(loadmoreConsultResults.size()-1, ConsultDetailFragment.SELECT_POSITION_SMOOTH);
                    }
                }
                else{
//                    mIConsultDetailView.hideDialog(resultData.Message);
                    if (!isInit){
                        mIConsultDetailView.refreshFinish(PullToRefreshLayout.FAIL);
                    }

                }
                isInit = false;
            }
        });
    }

    @Override
    public void addDoctorReply(int DoctorId, final int ReDoctorId, String ReDoctorName, int CustomerId, final String ReplyContent, final String ReplyTime) {
        mIConsultDetailView.showDialog();
        mConsultModel.addDoctorReply(DoctorId,ReDoctorId,ReDoctorName,CustomerId,ReplyContent,ReplyTime ,new OnHandlerResultListener<GlobalShell<Boolean>>() {
            @Override
            public void handlerResult(GlobalShell<Boolean> resultData) {
                if (resultData.LogicSuccess) {
                    mIConsultDetailView.hideDialog();
                    mIConsultDetailView.RefreshConsultPage(getAddConsultReply(ReDoctorId, ReplyContent, ReplyTime));
                    mIConsultDetailView.setListViewPosition(mConsultItemBeanList.size(),ConsultDetailFragment.SELECT_POSITION_SMOOTH);
                    mCommitOn = DateUtil.date2Str(new Date(),"yyyyMMddHHmmss");
//                    loadmoreConsultList();
                } else {
                    mIConsultDetailView.hideDialog(resultData.Message);
                    mIConsultDetailView.hideDialog();
                }
            }
        });
    }

    @Override
    public void getUserDetail(int customerId){
        mIConsultDetailView.showDialog();
        mUserModel.GetUserDetail(customerId, new OnHandlerResultListener<GlobalShell<CustomDetailBean>>() {
            @Override
            public void handlerResult(GlobalShell<CustomDetailBean> resultData) {
                if (resultData.LogicSuccess) {
                    mIConsultDetailView.hideDialog();
                    if (resultData.Data != null){
                        mIConsultDetailView.setCustmoerInfo(resultData.Data);
                    }
//                     refreshConsultList();
                } else {
                    mIConsultDetailView.hideDialog(resultData.Message);
                }
            }
        });

    }

    public List<ConsultReplyBean> getAddConsultReply(int ReDoctorId, String ReplyContent, String ReplyTime){
        ConsultReplyBean consultReplyBean = new ConsultReplyBean();
        consultReplyBean.ReDoctorId =  ReDoctorId;
        consultReplyBean.Content =  ReplyContent;
        consultReplyBean.IsDoctorReply = 1;
        consultReplyBean.CommitOn =  ReplyTime;
        mConsultItemBeanList.add(consultReplyBean);
        return mConsultItemBeanList;
    }

}

