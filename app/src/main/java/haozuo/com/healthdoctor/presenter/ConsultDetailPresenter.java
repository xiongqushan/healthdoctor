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
    private CustomDetailBean mCustomDetail;
    private ConsultReplyBean consultReplyBean;
    private List<ConsultReplyBean> mConsultReplyList;
    private String mCommitOn;
    private int mCustomerId;
    private boolean isInit;
    private boolean loadmoreSuccess;


    @Inject
    public ConsultDetailPresenter(@NonNull ConsultDetailContract.IConsultDetailView iConsultDetailView, @NonNull ConsultModel consultModel,@NonNull GroupModel groupModel,@NonNull UserModel userModel, @NonNull int customerId){
        mConsultReplyList = new ArrayList<ConsultReplyBean>();
        mIConsultDetailView=iConsultDetailView;
        mConsultModel=consultModel;
        mUserModel = userModel;
        mCustomerId=customerId;
        isInit = true;
        loadmoreSuccess = false;
        mCommitOn = DateUtil.date2Str(new Date(),"yyyyMMddHHmmss");
        mIConsultDetailView.setPresenter(this);
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
    public void start() {
        if (mCustomDetail==null)getUserDetail();
        loadmoreConsultList();
    }

    public void getUserDetail(){
        mIConsultDetailView.showDialog();
        mUserModel.GetUserDetail(mCustomerId, new OnHandlerResultListener<GlobalShell<CustomDetailBean>>() {
            @Override
            public void handlerResult(GlobalShell<CustomDetailBean> resultData) {
                if (resultData.LogicSuccess) {
                    if(loadmoreSuccess){
                        mIConsultDetailView.hideDialog();
                        mIConsultDetailView.changeRetryLayer(false);
                    }
                    if (resultData.Data != null){
                        mCustomDetail = resultData.Data;
                        mIConsultDetailView.setCustmoerInfo(mCustomDetail);
                    }
                } else {
                    mIConsultDetailView.hideDialog(resultData.Message);
                    mIConsultDetailView.changeRetryLayer(true);
                }
            }
        });
    }

    @Override
    public void loadmoreConsultList() {
        if (isInit){ //是否通过pull刷新页面
            mIConsultDetailView.showDialog();
        }
        mConsultModel.GetConsultReplyList(mCustomerId,mCommitOn, new OnHandlerResultListener<GlobalShell<List<ConsultReplyBean>>>() {
            @Override
            public void handlerResult(GlobalShell<List<ConsultReplyBean>> resultData) {
                if(resultData.LogicSuccess) {
                    if (isInit){
                        mIConsultDetailView.hideDialog();
                    }else{
                        mIConsultDetailView.refreshFinish(PullToRefreshLayout.SUCCEED);
                    }
                    loadmoreSuccess = true;
                    isInit = false;
                    if(mCustomDetail != null){ //若存在客户信息、则展示页面
                        mIConsultDetailView.changeRetryLayer(false);
                    }
                    if ((List<ConsultReplyBean>) resultData.Data != null){
                        List<ConsultReplyBean> loadmoreConsultResults = new ArrayList<ConsultReplyBean>();
                        loadmoreConsultResults.addAll(resultData.Data);
                        for (int i=0;i<resultData.Data.size();i++){
                            for (int j=0;j<mConsultReplyList.size();j++){
                                if (resultData.Data.get(i).Id == mConsultReplyList.get(j).Id){
                                    loadmoreConsultResults.remove(resultData.Data.get(i));
                                }
                            }
                        }
                        if(loadmoreConsultResults.size() == 0) {
                            return;
                        }
                        Collections.sort(loadmoreConsultResults,consultReplyBean);
                        mConsultReplyList.addAll(0,loadmoreConsultResults);
                        mIConsultDetailView.refreshCustomAdapter(mConsultReplyList);
                        mCommitOn =mConsultReplyList.get(0).CommitOn.replaceAll("(?:T|:|-)","");
                        mIConsultDetailView.setListViewPosition(loadmoreConsultResults.size(), ConsultDetailFragment.SELECT_POSITION_DIRECT);
//                        mIConsultDetailView.setListViewPosition(loadmoreConsultResults.size()-1, ConsultDetailFragment.SELECT_POSITION_SMOOTH);
                    }
                }
                else{
                    isInit = false;
                    loadmoreSuccess = false;
                    if (isInit){
                        mIConsultDetailView.hideDialog(resultData.Message);
                    }else {
                        mIConsultDetailView.refreshFinish(PullToRefreshLayout.FAIL);
                    }
                    mIConsultDetailView.changeRetryLayer(true);
                }
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
                    mIConsultDetailView.setListViewPosition(mConsultReplyList.size(),ConsultDetailFragment.SELECT_POSITION_SMOOTH);
                    mCommitOn = DateUtil.date2Str(new Date(),"yyyyMMddHHmmss");
                } else {
                    mIConsultDetailView.hideDialog(resultData.Message);
                    mIConsultDetailView.hideDialog();
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
        mConsultReplyList.add(consultReplyBean);
        return mConsultReplyList;
    }
}