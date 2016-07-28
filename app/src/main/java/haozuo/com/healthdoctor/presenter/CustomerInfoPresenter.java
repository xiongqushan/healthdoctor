package haozuo.com.healthdoctor.presenter;

import android.support.annotation.NonNull;

import java.util.Collections;
import java.util.List;

import haozuo.com.healthdoctor.bean.CustomDetailBean;
import haozuo.com.healthdoctor.bean.DoctorGroupBean;
import haozuo.com.healthdoctor.bean.GlobalShell;
import haozuo.com.healthdoctor.bean.GroupCustInfoBean;
import haozuo.com.healthdoctor.contract.AbsPresenter;
import haozuo.com.healthdoctor.contract.BaseModel;
import haozuo.com.healthdoctor.contract.BaseView;
import haozuo.com.healthdoctor.contract.CustomerInfoContract;
import haozuo.com.healthdoctor.listener.OnHandlerResultListener;
import haozuo.com.healthdoctor.manager.UserManager;
import haozuo.com.healthdoctor.model.GroupModel;
import haozuo.com.healthdoctor.model.UserModel;

/**
 * Created by xiongwei1 on 2016/7/25.
 */
public class CustomerInfoPresenter extends AbsPresenter implements CustomerInfoContract.ICustomerInfoPresenter {
    private CustomDetailBean mCustomInfo;
    private CustomerInfoContract.ICustomerInfoView mICustomerInfoView;
    private UserModel mUserModel;
    private GroupModel mGroupModel;
    private int mCustomerId;
    private String mOperateBy;
    public CustomerInfoPresenter(@NonNull CustomDetailBean customInfo, @NonNull CustomerInfoContract.ICustomerInfoView iCustomerInfoView, int customerId){
//    public CustomerInfoPresenter(@NonNull GroupCustInfoBean customInfo, @NonNull CustomerInfoContract.ICustomerInfoView iCustomerInfoView){
        mCustomInfo=customInfo;
        mICustomerInfoView=iCustomerInfoView;
        mUserModel=new UserModel();
        mGroupModel = new GroupModel();
        mICustomerInfoView.setPresenter(this);
        mCustomerId = customerId;
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
        mICustomerInfoView.InitView(mCustomInfo);
    }

    @Override
    public void DeleteCustomerGroup(int DeleteGroupId) {
        mICustomerInfoView.showDialog();
        mOperateBy = (String) UserManager.getInstance().getDoctorInfo().Username;
        mGroupModel.DeleteCustomerGroup(createRequestTag(), mCustomerId, DeleteGroupId, mOperateBy, new OnHandlerResultListener<GlobalShell<Boolean>>() {
            @Override
            public void handlerResult(GlobalShell<Boolean> resultData) {
                if(resultData.LogicSuccess) {
                    mICustomerInfoView.hideDialog();
                }
                else{
                    mICustomerInfoView.hideDialog(resultData.Message);
                }

            }
        });
    }



}
