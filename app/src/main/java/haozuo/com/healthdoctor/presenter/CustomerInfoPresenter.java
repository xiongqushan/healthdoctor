package haozuo.com.healthdoctor.presenter;

import android.support.annotation.NonNull;

import haozuo.com.healthdoctor.bean.GroupCustInfoBean;
import haozuo.com.healthdoctor.contract.IBaseModel;
import haozuo.com.healthdoctor.contract.IBaseView;
import haozuo.com.healthdoctor.contract.CustomerInfoContract;
import haozuo.com.healthdoctor.model.UserModel;

/**
 * Created by xiongwei1 on 2016/7/25.
 */
public class CustomerInfoPresenter extends AbstractPresenter implements CustomerInfoContract.ICustomerInfoPresenter {
    private GroupCustInfoBean mCustomInfo;
    private CustomerInfoContract.ICustomerInfoView mICustomerInfoView;
    private UserModel mUserModel;
    public CustomerInfoPresenter(@NonNull GroupCustInfoBean customInfo, @NonNull CustomerInfoContract.ICustomerInfoView iCustomerInfoView){
        mCustomInfo=customInfo;
        mICustomerInfoView=iCustomerInfoView;
        mUserModel=UserModel.createInstance();
        mICustomerInfoView.setPresenter(this);
    }

    @Override
    public IBaseView getBaseView() {
        return mICustomerInfoView;
    }

    @Override
    public IBaseModel getBaseModel() {
        return mUserModel;
    }

    @Override
    public void start() {
        mICustomerInfoView.InitView(mCustomInfo);
    }
}
