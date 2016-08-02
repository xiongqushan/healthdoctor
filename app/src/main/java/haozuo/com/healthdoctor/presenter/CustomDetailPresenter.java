package haozuo.com.healthdoctor.presenter;

import android.support.annotation.NonNull;

import haozuo.com.healthdoctor.bean.CustomDetailBean;
import haozuo.com.healthdoctor.bean.GlobalShell;
import haozuo.com.healthdoctor.bean.GroupCustInfoBean;
import haozuo.com.healthdoctor.contract.IBaseModel;
import haozuo.com.healthdoctor.contract.IBaseView;
import haozuo.com.healthdoctor.contract.CustomDetailContract;
import haozuo.com.healthdoctor.listener.OnHandlerResultListener;

import haozuo.com.healthdoctor.model.UserModel;

/**
 * Created by hzguest3 on 2016/7/22.
 */
public class CustomDetailPresenter extends AbstractPresenter implements CustomDetailContract.ICustomDetailPresenter {
    private CustomDetailContract.ICustomDetailView mICustomDetailView;
    private UserModel mUserModel;
    private int mCustomerId;
    public CustomDetailPresenter(@NonNull CustomDetailContract.ICustomDetailView iView, int customerId){
        mICustomDetailView=iView;
        mUserModel=UserModel.createInstance();
        mICustomDetailView.setPresenter(this);
        mCustomerId = customerId;
    }

    @Override
    public void start() {
        mICustomDetailView.showDialog();
        mUserModel.GetUserDetail(mCustomerId, new OnHandlerResultListener<GlobalShell<CustomDetailBean>>() {
            @Override
            public void handlerResult(GlobalShell<CustomDetailBean> resultData) {
            if(resultData.LogicSuccess) {
                mICustomDetailView.hideDialog();
                CustomDetailBean customBean = resultData.Data;
                mICustomDetailView.InitView(customBean);
            }
            else{
                mICustomDetailView.hideDialog(resultData.Message);
            }
            }
        });
    }

    @Override
    public IBaseView getBaseView() {
        return mICustomDetailView;
    }

    @Override
    public IBaseModel getBaseModel() {
        return mUserModel;
    }
}
