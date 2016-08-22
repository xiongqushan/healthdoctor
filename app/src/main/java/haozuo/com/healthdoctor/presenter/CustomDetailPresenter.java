package haozuo.com.healthdoctor.presenter;

import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import haozuo.com.healthdoctor.bean.CustomDetailBean;
import haozuo.com.healthdoctor.bean.GlobalShell;
import haozuo.com.healthdoctor.bean.GroupCustInfoBean;
import haozuo.com.healthdoctor.bean.ReportParamsBean;
import haozuo.com.healthdoctor.contract.IBaseModel;
import haozuo.com.healthdoctor.contract.IBaseView;
import haozuo.com.healthdoctor.contract.CustomDetailContract;
import haozuo.com.healthdoctor.listener.OnHandlerResultListener;

import haozuo.com.healthdoctor.model.ReportModel;
import haozuo.com.healthdoctor.model.UserModel;

/**
 * Created by hzguest3 on 2016/7/22.
 */
public class CustomDetailPresenter extends AbstractPresenter implements CustomDetailContract.ICustomDetailPresenter {
    private CustomDetailContract.ICustomDetailView mICustomDetailView;
    private UserModel mUserModel;
    private ReportModel mReportModel;
    private int mCustomerId;

    @Inject
    public CustomDetailPresenter(@NonNull CustomDetailContract.ICustomDetailView iView,@NonNull UserModel userModel, @NonNull ReportModel reportModel,@NonNull int customerId){
        mICustomDetailView=iView;
        mUserModel=userModel;
        mReportModel = reportModel;
        mICustomDetailView.setPresenter(this);
        mCustomerId = customerId;
    }

    @Override
    public void start() {
        GetUserDetail();
        GetReportParams();
    }

    @Override
    public IBaseView getBaseView() {
        return mICustomDetailView;
    }

    @Override
    public IBaseModel[] getBaseModelList() {
        return new IBaseModel[]{mUserModel};
    }

    public void GetUserDetail(){
        mICustomDetailView.showDialog();
        mUserModel.GetUserDetail(mCustomerId, new OnHandlerResultListener<GlobalShell<CustomDetailBean>>() {
            @Override
            public void handlerResult(GlobalShell<CustomDetailBean> resultData) {
                if(resultData.LogicSuccess) {
                    mICustomDetailView.hideDialog();
                    CustomDetailBean customBean = resultData.Data;
                    customBean.Sex = CustomDetailBean.GenderConvert(customBean.Gender);
                    mICustomDetailView.InitView(customBean);
                    mICustomDetailView.changeRetryLayer(true);
                }
                else{
                    mICustomDetailView.hideDialog(resultData.Message);
                    mICustomDetailView.changeRetryLayer(false);
                }
            }
        });
    }

    public void GetReportParams(){
        mICustomDetailView.showDialog();
        mReportModel.getReportParams(mCustomerId,new OnHandlerResultListener<GlobalShell<List<ReportParamsBean>>>() {
            @Override
            public void handlerResult(GlobalShell<List<ReportParamsBean>> resultData) {
                if(resultData.LogicSuccess) {
                    mICustomDetailView.hideDialog();
                    List<ReportParamsBean> ReportParamList = resultData.Data;
                    mICustomDetailView.changeRetryLayer(true);
                }
                else{
                    mICustomDetailView.hideDialog(resultData.Message);
                    mICustomDetailView.changeRetryLayer(false);
                }
            }
        });
    }



}
