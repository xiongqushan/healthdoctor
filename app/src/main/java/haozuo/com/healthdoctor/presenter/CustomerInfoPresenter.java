package haozuo.com.healthdoctor.presenter;

import android.support.annotation.NonNull;

import haozuo.com.healthdoctor.bean.GlobalShell;
import haozuo.com.healthdoctor.bean.GroupCustInfoBean;
import haozuo.com.healthdoctor.contract.AbsPresenter;
import haozuo.com.healthdoctor.contract.BaseModel;
import haozuo.com.healthdoctor.contract.BaseView;
import haozuo.com.healthdoctor.contract.CustomerInfoContract;
import haozuo.com.healthdoctor.listener.OnHandlerResultListener;
import haozuo.com.healthdoctor.model.UserModel;

/**
 * Created by xiongwei1 on 2016/7/25.
 */
public class CustomerInfoPresenter extends AbsPresenter implements CustomerInfoContract.ICustomerInfoPresenter {
    private int mCustomerId;
    private CustomerInfoContract.ICustomerInfoView mICustomerInfoView;
    private UserModel mUserModel;
    public CustomerInfoPresenter(@NonNull int customerId, @NonNull CustomerInfoContract.ICustomerInfoView iCustomerInfoView){
        mCustomerId=customerId;
        mICustomerInfoView=iCustomerInfoView;
        mUserModel=new UserModel();
        mICustomerInfoView.setPresenter(this);
    }

    @Override
    public BaseView getBaseView() {
        return mICustomerInfoView;
    }

    @Override
    public BaseModel getBaseModel() {
        return mUserModel;
    }

    @Override
    public void start() {
        mICustomerInfoView.showDialog();
        mUserModel.GetUserDetail(createRequestTag(),mCustomerId, new OnHandlerResultListener<GlobalShell<GroupCustInfoBean>>() {
            @Override
            public void handlerResult(GlobalShell<GroupCustInfoBean> resultData) {

                if(resultData.LogicSuccess) {
                    mICustomerInfoView.hideDialog();
                    mICustomerInfoView.InitView(resultData.Data);
                }
                else{
                    mICustomerInfoView.hideDialog(resultData.Message);
                }
            }
        });
    }
}
