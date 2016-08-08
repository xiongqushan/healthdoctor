package haozuo.com.healthdoctor.presenter;

import android.support.annotation.NonNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import haozuo.com.healthdoctor.bean.ConsultItemBean;
import haozuo.com.healthdoctor.bean.ConsultReplyBean;
import haozuo.com.healthdoctor.bean.GlobalShell;
import haozuo.com.healthdoctor.contract.ConsultDetailContract;
import haozuo.com.healthdoctor.contract.IBaseModel;
import haozuo.com.healthdoctor.contract.IBaseView;
import haozuo.com.healthdoctor.listener.OnHandlerResultListener;
import haozuo.com.healthdoctor.model.ConsultModel;

/**
 * Created by hzguest3 on 2016/8/3.
 */

public class ConsultDetailPresenter extends AbstractPresenter implements ConsultDetailContract.IConsultDetailPresenter {
    private ConsultDetailContract.IConsultDetailView mIConsultDetailView;
    private ConsultModel mConsultModel;
    private List<ConsultItemBean> mConsultItemBeanList;
    private String mCommitOn;
    public ConsultDetailPresenter(@NonNull ConsultDetailContract.IConsultDetailView iConsultDetailView){
        mConsultItemBeanList = new ArrayList<ConsultItemBean>();
        mIConsultDetailView=iConsultDetailView;
        mConsultModel=ConsultModel.createInstance();
        iConsultDetailView.setPresenter(this);
        DateFormat df = new SimpleDateFormat("yyyyMMddhhmmss");
        mCommitOn = df.format(new Date()).toString();
    }

    @Override
    public IBaseView getBaseView() {
        return mIConsultDetailView;
    }

    @Override
    public IBaseModel getBaseModel() {
        return mConsultModel;
    }


    @Override
    public void start() {}

    @Override
    public void GetConsultReply(int customerId) {
        mIConsultDetailView.showDialog();
        mConsultModel.GetConsultReplyList(customerId,mCommitOn, new OnHandlerResultListener<GlobalShell<List<ConsultReplyBean>>>() {
            @Override
            public void handlerResult(GlobalShell<List<ConsultReplyBean>> resultData) {
                if(resultData.LogicSuccess) {
                    mIConsultDetailView.hideDialog();
                    ConsultReplyBean consultReplyBean = new ConsultReplyBean();
                    Collections.sort(resultData.Data,consultReplyBean);
                    if ((List<ConsultReplyBean>) resultData.Data != null){
                        mIConsultDetailView.refreshCustomAdapter(resultData.Data);
                    }
                }
                else{
                    mIConsultDetailView.hideDialog(resultData.Message);
                }
            }
        });
    }


}

