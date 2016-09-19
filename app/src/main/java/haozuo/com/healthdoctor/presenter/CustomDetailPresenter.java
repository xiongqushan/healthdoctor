package haozuo.com.healthdoctor.presenter;

import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import haozuo.com.healthdoctor.bean.CustomDetailBean;
import haozuo.com.healthdoctor.bean.GlobalShell;
import haozuo.com.healthdoctor.bean.ReportParamsBean;
import haozuo.com.healthdoctor.bean.RequestPhotoReportListBean;
import haozuo.com.healthdoctor.contract.CustomDetailContract;
import haozuo.com.healthdoctor.listener.OnHandlerResultListener;
import haozuo.com.healthdoctor.model.IBaseModel;
import haozuo.com.healthdoctor.model.ReportModel;
import haozuo.com.healthdoctor.model.UserModel;
import haozuo.com.healthdoctor.view.IBaseView;

/**
 * Created by hzguest3 on 2016/7/22.
 */
public class CustomDetailPresenter extends AbstractPresenter implements CustomDetailContract.ICustomDetailPresenter {
    private CustomDetailContract.ICustomDetailView mICustomDetailView;
    private UserModel mUserModel;
    private ReportModel mReportModel;
    private int mCustomerId;
    private String mAccountID;
    private CustomDetailBean mCustomBean;
    List<ReportParamsBean> mReportParamList;
    List<RequestPhotoReportListBean> mRequestPhotoReportList;

    @Inject
    public CustomDetailPresenter(@NonNull CustomDetailContract.ICustomDetailView iView,@NonNull UserModel userModel, @NonNull ReportModel reportModel,int customerId,@NonNull String accountID){
        mICustomDetailView=iView;
        mUserModel=userModel;
        mReportModel = reportModel;
        mICustomDetailView.setPresenter(this);
        mCustomerId = customerId;
        mAccountID = accountID;
    }

    @Override
    public void start() {
        if (mCustomBean==null)GetUserDetail();
        if (mReportParamList==null)GetReportParams();
        if (mReportParamList==null)RequestPhotoReportList();
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
                    mCustomBean = resultData.Data;
//                    customBean.Sex = CustomDetailBean.GenderConvert(customBean.Gender);
                    mICustomDetailView.InitView(mCustomBean);
                    if (mReportParamList!=null&&mReportParamList!=null){
                        mICustomDetailView.changeRetryLayer(true);
                    }
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
                    if (resultData.Data != null){
                        mReportParamList = resultData.Data;
                        mICustomDetailView.RefreshReportParams(mReportParamList);
                    }
                    if (mCustomBean!=null&&mReportParamList!=null){
                        mICustomDetailView.changeRetryLayer(true);
                    }
                }
                else{
                    mICustomDetailView.hideDialog(resultData.Message);
                    mICustomDetailView.changeRetryLayer(false);
                }
            }
        });
    }

    public void RequestPhotoReportList(){
        mICustomDetailView.showDialog();
        mReportModel.requestPhotoReportList(mAccountID,new OnHandlerResultListener<GlobalShell<List<RequestPhotoReportListBean>>>() {
            @Override
            public void handlerResult(GlobalShell<List<RequestPhotoReportListBean>> resultData) {
                if(resultData.LogicSuccess) {
                    mICustomDetailView.hideDialog();
                    if (resultData.Data != null) {
                        mRequestPhotoReportList = resultData.Data;
                        mICustomDetailView.RefreshPhotoReport(mRequestPhotoReportList);
                    }
                    if (mCustomBean != null&& mReportParamList!=null){
                        mICustomDetailView.changeRetryLayer(true);
                    }
                }
                else{
                    mICustomDetailView.hideDialog(resultData.Message);
                    mICustomDetailView.changeRetryLayer(false);
                }
            }
        });
    }



}
