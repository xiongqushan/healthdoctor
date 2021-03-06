
package haozuo.com.healthdoctor.presenter;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import haozuo.com.healthdoctor.bean.ConsultReplyBean;
import haozuo.com.healthdoctor.bean.CustomDetailBean;
import haozuo.com.healthdoctor.bean.GlobalShell;
import haozuo.com.healthdoctor.bean.UsefulExpressionBean;
import haozuo.com.healthdoctor.contract.UsefulMessageContract;
import haozuo.com.healthdoctor.listener.OnHandlerResultListener;
import haozuo.com.healthdoctor.manager.UsefulMessageManager;
import haozuo.com.healthdoctor.model.ConsultModel;
import haozuo.com.healthdoctor.model.IBaseModel;
import haozuo.com.healthdoctor.view.IBaseView;

/**
 * Created by hzguest3 on 2016/8/9.
 */
public class UsefulMessagePresenter extends AbstractPresenter implements UsefulMessageContract.IUsefulMessagePresenter{
    UsefulMessageContract.IUsefulMessageView mIUsefulMessageView;
    Context mContext;
    ConsultModel mConsultModel;
    CustomDetailBean mCustomDetailBean;

    @Inject
    public UsefulMessagePresenter(@NonNull UsefulMessageContract.IUsefulMessageView iUsefulMessageView, @NonNull Context context, @NonNull ConsultModel consultModel){
        mIUsefulMessageView = iUsefulMessageView;
        mConsultModel = consultModel;
        mContext = context;
        mIUsefulMessageView.setPresenter(this);
    }

    @Override
    public IBaseView getBaseView() {
        return mIUsefulMessageView;
    }

    @Override
    public IBaseModel[] getBaseModelList() {
        return new IBaseModel[]{mConsultModel};
    }

    @Override
    public void start() {
        getDefaultUsefulExpression();
    }

    public void getDefaultUsefulExpression() {
        if (UsefulMessageManager.getInstance().getDefaultExpression() != null){
            mIUsefulMessageView.refreshUsefulMessageAdapter(UsefulMessageManager.getInstance().getDefaultExpression());
        }
        else {
            mIUsefulMessageView.showDialog();
            mConsultModel.getUsefulExpression(new OnHandlerResultListener<GlobalShell<List<UsefulExpressionBean>>>() {
                @Override
                public void handlerResult(GlobalShell<List<UsefulExpressionBean>> resultData) {
                    if (resultData.LogicSuccess){
                        UsefulMessageManager.getInstance().setDefaultExpression(resultData.Data);
                        mIUsefulMessageView.hideDialog();
                        mIUsefulMessageView.refreshUsefulMessageAdapter(resultData.Data);
                    }else {
                        mIUsefulMessageView.hideDialog();
                    }
                }
            });
        }
    }

    @Override
    public void searchUsefulExpression(String keyword) {
        mIUsefulMessageView.showDialog();
        mConsultModel.searchUsefulExpression(keyword,new OnHandlerResultListener<GlobalShell<List<UsefulExpressionBean>>>() {
            @Override
            public void handlerResult(GlobalShell<List<UsefulExpressionBean>> resultData) {
                if (resultData.LogicSuccess){
                    mIUsefulMessageView.hideDialog();
                    mIUsefulMessageView.refreshUsefulMessageAdapter(resultData.Data);
                }else {
                    mIUsefulMessageView.hideDialog();
                }
            }
        });
    }


    @Override
    public void addDoctorReply(int DoctorId, final int ReDoctorId, String ReDoctorName, int CustomerId, final String ReplyContent, final String ReplyTime) {
        mIUsefulMessageView.showDialog();
        mConsultModel.addDoctorReply(DoctorId,ReDoctorId,ReDoctorName,CustomerId,ReplyContent,ReplyTime ,new OnHandlerResultListener<GlobalShell<Boolean>>() {
            @Override
            public void handlerResult(GlobalShell<Boolean> resultData) {
                if (resultData.LogicSuccess) {
                    mIUsefulMessageView.hideDialog();
                    mIUsefulMessageView.refreshConsultList(getAddConsultReply(ReDoctorId,ReplyContent,ReplyTime));
                } else {
                    mIUsefulMessageView.hideDialog(resultData.Message);
                }
            }
        });
    }

    public ConsultReplyBean getAddConsultReply(int ReDoctorId, String ReplyContent, String ReplyTime){
        ConsultReplyBean consultReplyBean = new ConsultReplyBean();
        consultReplyBean.ReDoctorId =  ReDoctorId;
        consultReplyBean.Content =  ReplyContent;
        consultReplyBean.IsDoctorReply = 1;
        consultReplyBean.CommitOn =  ReplyTime;
        return consultReplyBean;
    }
}
