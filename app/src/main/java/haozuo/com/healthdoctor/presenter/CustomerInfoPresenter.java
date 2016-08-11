package haozuo.com.healthdoctor.presenter;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import haozuo.com.healthdoctor.bean.CustomDetailBean;
import haozuo.com.healthdoctor.bean.DoctorGroupBean;
import haozuo.com.healthdoctor.bean.GlobalShell;
import haozuo.com.healthdoctor.bean.GroupCustInfoBean;
import haozuo.com.healthdoctor.contract.IBaseModel;
import haozuo.com.healthdoctor.contract.IBaseView;
import haozuo.com.healthdoctor.contract.CustomerInfoContract;
import haozuo.com.healthdoctor.listener.OnHandlerResultListener;
import haozuo.com.healthdoctor.manager.UserManager;
import haozuo.com.healthdoctor.model.GroupModel;
import haozuo.com.healthdoctor.model.UserModel;

/**
 * Created by xiongwei1 on 2016/7/25.
 */
public class CustomerInfoPresenter extends AbstractPresenter implements CustomerInfoContract.ICustomerInfoPresenter {
    private CustomDetailBean mCustomInfo;

    private CustomerInfoContract.ICustomerInfoView mICustomerInfoView;
    private UserModel mUserModel;
    private GroupModel mGroupModel;
    private String mOperateBy;
    private List<DoctorGroupBean> mGroups = new ArrayList<DoctorGroupBean>();

    @Inject
    public CustomerInfoPresenter(@NonNull CustomerInfoContract.ICustomerInfoView iCustomerInfoView,@NonNull UserModel userModel,@NonNull CustomDetailBean customInfo){
        mCustomInfo=customInfo;
        mICustomerInfoView=iCustomerInfoView;
        mUserModel=userModel;
        mICustomerInfoView.setPresenter(this);
    }

    @Override
    public IBaseView getBaseView() {
        return mICustomerInfoView;
    }

    @Override
    public IBaseModel[] getBaseModelList() {
        return new IBaseModel[]{mUserModel};
    }

    @Override
    public void start() {
        mICustomerInfoView.InitView(mCustomInfo);
    }

    @Override
    public void DeleteCustomerGroup(final DoctorGroupBean groupBean) {
        mICustomerInfoView.showDialog();
        int DeleteGroupId = groupBean.id;
        mOperateBy = (String) UserManager.getInstance().getDoctorInfo().Name;
        mGroupModel.DeleteCustomerGroup(mCustomInfo.Id, DeleteGroupId, mOperateBy, new OnHandlerResultListener<GlobalShell<Boolean>>() {
            @Override
            public void handlerResult(GlobalShell<Boolean> resultData) {
                if(resultData.LogicSuccess) {
                    mICustomerInfoView.hideDialog();
                    mICustomerInfoView.refreshLabelView(mGroups);
                }
                else{
                    mICustomerInfoView.hideDialog(resultData.Message);
                }

            }
        });
    }

    public void InitGroupLabel() {
        //遍历标签名称数组
        List<DoctorGroupBean> GroupInfo =(List<DoctorGroupBean>) UserManager.getInstance().getGroupInfo();
        mGroups.clear();
        for (int s: mCustomInfo.GroupIdList){
            for (int i=0; i<GroupInfo.size();i++){
                if (s == GroupInfo.get(i).id){
                    mGroups.add(GroupInfo.get(i));
                }
            }
        }

        for (DoctorGroupBean entry : mGroups) {
            String groupName = entry.name;
            int groupId = Integer.parseInt(String.valueOf(entry.id));
            mICustomerInfoView.addLabelView(entry);
        }


    }



}
