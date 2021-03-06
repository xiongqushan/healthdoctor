
package haozuo.com.healthdoctor.presenter;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import haozuo.com.healthdoctor.bean.CustomDetailBean;
import haozuo.com.healthdoctor.bean.DoctorGroupBean;
import haozuo.com.healthdoctor.bean.GlobalShell;
import haozuo.com.healthdoctor.contract.CustomerInfoContract;
import haozuo.com.healthdoctor.listener.OnHandlerResultListener;
import haozuo.com.healthdoctor.manager.GroupInfoManager;
import haozuo.com.healthdoctor.manager.UserManager;
import haozuo.com.healthdoctor.model.GroupModel;
import haozuo.com.healthdoctor.model.IBaseModel;
import haozuo.com.healthdoctor.model.UserModel;
import haozuo.com.healthdoctor.view.IBaseView;

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
    public CustomerInfoPresenter(@NonNull CustomerInfoContract.ICustomerInfoView iCustomerInfoView, @NonNull UserModel userModel, @NonNull GroupModel groupModel, @NonNull CustomDetailBean customInfo) {
        mCustomInfo = customInfo;
        mICustomerInfoView = iCustomerInfoView;
        mUserModel = userModel;
        mGroupModel = groupModel;
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
        final int DeleteGroupId = groupBean.id;
        mOperateBy = (String) UserManager.getInstance().getDoctorInfo().Name;
        mGroupModel.DeleteCustomerGroup(mCustomInfo.Id, DeleteGroupId, mOperateBy, new OnHandlerResultListener<GlobalShell<Boolean>>() {
            @Override
            public void handlerResult(GlobalShell<Boolean> resultData) {
                if (resultData.LogicSuccess) {
                    mICustomerInfoView.hideDialog();
                    for (int i = 0; i < mCustomInfo.GroupIdList.size(); i++) {
                        if (mCustomInfo.GroupIdList.get(i) == DeleteGroupId) {
                            mCustomInfo.GroupIdList.remove(i);
                        }
                    }
                    mICustomerInfoView.refreshLabelView(mCustomInfo);
                } else {
                    mICustomerInfoView.hideDialog(resultData.Message);
                }

            }
        });
    }

    @Override
    public void InitGroupLabel() {
        //遍历标签名称数组
        List<DoctorGroupBean> GroupInfo = (List<DoctorGroupBean>) GroupInfoManager.getInstance().getGroupInfo();
        mGroups.clear();
        for (int s : mCustomInfo.GroupIdList) {
            for (int i = 0; i < GroupInfo.size(); i++) {
                if (s == GroupInfo.get(i).id) {
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
