package haozuo.com.healthdoctor.presenter;

import android.support.annotation.NonNull;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import haozuo.com.healthdoctor.bean.DoctorGroupBean;
import haozuo.com.healthdoctor.bean.GlobalShell;
import haozuo.com.healthdoctor.contract.GroupContract;
import haozuo.com.healthdoctor.contract.GroupContract.IGroupPresenter;
import haozuo.com.healthdoctor.listener.OnHandlerResultListener;
import haozuo.com.healthdoctor.manager.GroupInfoManager;
import haozuo.com.healthdoctor.manager.UserManager;
import haozuo.com.healthdoctor.model.GroupModel;
import haozuo.com.healthdoctor.model.IBaseModel;
import haozuo.com.healthdoctor.view.IBaseView;

/**
 * Created by xiongwei1 on 2016/7/7.
 */
public class GroupPresenter extends AbstractPresenter implements IGroupPresenter {
    private GroupContract.IGroupView mIGroupView;
    private GroupModel mGroupModel;

    @Inject
    public GroupPresenter(@NonNull GroupContract.IGroupView iGroupView, @NonNull GroupModel groupModel) {

        mIGroupView = iGroupView;
        mGroupModel = groupModel;
        iGroupView.setPresenter(this);
    }

    @Override
    public IBaseView getBaseView() {
        return mIGroupView;
    }

    @Override
    public IBaseModel[] getBaseModelList() {
        return new IBaseModel[]{mGroupModel};
    }

    @Override
    public void start() {
        getGroupInfo();
    }

    public void getGroupInfo(){
        int doctorId = UserManager.getInstance().getDoctorInfo().Doctor_ID;
        mIGroupView.showDialog();
        mGroupModel.GetGroup(doctorId, new OnHandlerResultListener<GlobalShell<List<DoctorGroupBean>>>() {
            @Override
            public void handlerResult(GlobalShell<List<DoctorGroupBean>> resultData) {
                if (resultData.LogicSuccess) {
                    DoctorGroupBean doctorGroupBean = new DoctorGroupBean();
                    Collections.sort(resultData.Data, doctorGroupBean);
                    if ((List<DoctorGroupBean>) resultData.Data != null) {
                        GroupInfoManager.getInstance().setGroupInfo((List<DoctorGroupBean>) resultData.Data);
                        mIGroupView.setGroupInfo(resultData.Data);
                    }
                    mIGroupView.changeRetryLayer(false);
                    mIGroupView.hideDialog();
                } else {
                    mIGroupView.hideDialog(resultData.Message);
                    mIGroupView.changeRetryLayer(true);
                }
            }
        });
    }

}
