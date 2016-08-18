package haozuo.com.healthdoctor.presenter;

import android.support.annotation.NonNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import haozuo.com.healthdoctor.bean.ConsultItemBean;
import haozuo.com.healthdoctor.bean.ConsultReplyBean;
import haozuo.com.healthdoctor.bean.GlobalShell;
import haozuo.com.healthdoctor.contract.ConsultDetailContract;
import haozuo.com.healthdoctor.contract.IBaseModel;
import haozuo.com.healthdoctor.contract.IBaseView;
import haozuo.com.healthdoctor.listener.OnHandlerResultListener;
import haozuo.com.healthdoctor.model.ConsultModel;
import haozuo.com.healthdoctor.util.DateUtil;
import haozuo.com.healthdoctor.view.threePart.PullToRefresh.PullToRefreshLayout;

/**
 * Created by hzguest3 on 2016/8/3.
 */

public class ConsultDetailPresenter extends AbstractPresenter implements ConsultDetailContract.IConsultDetailPresenter {
    private ConsultDetailContract.IConsultDetailView mIConsultDetailView;
    private ConsultModel mConsultModel;
    private ConsultReplyBean consultReplyBean;
    private List<ConsultReplyBean> mConsultItemBeanList;
    private String mCommitOn;
    private int mCustomerId;

    @Inject
    public ConsultDetailPresenter(@NonNull ConsultDetailContract.IConsultDetailView iConsultDetailView,@NonNull ConsultModel consultModel,@NonNull int customerId){
        mConsultItemBeanList = new ArrayList<ConsultReplyBean>();
        mIConsultDetailView=iConsultDetailView;
        mConsultModel=consultModel;
        mCustomerId=customerId;
        iConsultDetailView.setPresenter(this);
//        DateFormat df = new SimpleDateFormat("yyyyMMddhhmmss");
//        mCommitOn = df.format(new Date()).toString();
        mCommitOn = DateUtil.date2Str(new Date(),"yyyyMMddHHmmss");
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
    public void refreshConsultList() {
        mIConsultDetailView.showDialog();
        mConsultModel.GetConsultReplyList(mCustomerId,mCommitOn, new OnHandlerResultListener<GlobalShell<List<ConsultReplyBean>>>() {
            @Override
            public void handlerResult(GlobalShell<List<ConsultReplyBean>> resultData) {
                if(resultData.LogicSuccess) {
                    mIConsultDetailView.hideDialog();
                    if ((List<ConsultReplyBean>) resultData.Data != null){
                        mConsultItemBeanList.clear();
                        mConsultItemBeanList.addAll(resultData.Data);
                        Collections.sort(mConsultItemBeanList,consultReplyBean);
                        mIConsultDetailView.refreshCustomAdapter(mConsultItemBeanList);
                        mIConsultDetailView.refreshFinish(PullToRefreshLayout.SUCCEED);
                    }
                }
                else{
                    mIConsultDetailView.hideDialog(resultData.Message);
                    mIConsultDetailView.refreshFinish(PullToRefreshLayout.FAIL);
                }
            }
        });
    }

    @Override
    public void loadmoreConsultList() {
        mIConsultDetailView.showDialog();
        mConsultModel.GetConsultReplyList(mCustomerId,mCommitOn, new OnHandlerResultListener<GlobalShell<List<ConsultReplyBean>>>() {
            @Override
            public void handlerResult(GlobalShell<List<ConsultReplyBean>> resultData) {
                if(resultData.LogicSuccess) {
                    mIConsultDetailView.hideDialog();
                    if ((List<ConsultReplyBean>) resultData.Data != null){
                        mConsultItemBeanList.clear();
                        mConsultItemBeanList.addAll(resultData.Data);
                        Collections.sort(mConsultItemBeanList,consultReplyBean);
                        mIConsultDetailView.refreshCustomAdapter(mConsultItemBeanList);
                        mIConsultDetailView.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                    }
                }
                else{
                    mIConsultDetailView.hideDialog(resultData.Message);
                    mIConsultDetailView.loadmoreFinish(PullToRefreshLayout.FAIL);
                }
            }
        });
    }

    @Override
    public void addDoctorReply(int DoctorId,int ReDoctorId,String ReDoctorName,int CustomerId,String ReplyContent,String ReplyTime) {
        mIConsultDetailView.showDialog();
        mConsultModel.addDoctorReply(DoctorId,ReDoctorId,ReDoctorName,CustomerId,ReplyContent,ReplyTime ,new OnHandlerResultListener<GlobalShell<Boolean>>() {
            @Override
            public void handlerResult(GlobalShell<Boolean> resultData) {
                if (resultData.LogicSuccess) {
//                    mIConsultDetailView.hideDialog();
                    mCommitOn = DateUtil.date2Str(new Date(),"yyyyMMddHHmmss");
                    refreshConsultList();
                } else {
                    mIConsultDetailView.hideDialog(resultData.Message);
                }
            }
        });
    }


}

