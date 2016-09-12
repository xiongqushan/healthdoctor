package haozuo.com.healthdoctor.presenter;

import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import haozuo.com.healthdoctor.bean.BasConstBean;
import haozuo.com.healthdoctor.bean.GlobalShell;
import haozuo.com.healthdoctor.bean.ServiceDeptBean;
import haozuo.com.healthdoctor.contract.DoctorInfoContract;
import haozuo.com.healthdoctor.listener.OnHandlerResultListener;
import haozuo.com.healthdoctor.model.IBaseModel;
import haozuo.com.healthdoctor.model.UserModel;
import haozuo.com.healthdoctor.view.IBaseView;


/**
 * Created by zy on 2016/9/8.
 */
public class DoctorInfoPresenter extends AbstractPresenter implements DoctorInfoContract.IDoctorInfoPresenter {
    DoctorInfoContract.IDoctorInfoView mIDoctorInfoView;
    UserModel mUserModel;
    private List<BasConstBean> jobList;
    private List<ServiceDeptBean> deptList;

    @Inject
    public DoctorInfoPresenter(@NonNull DoctorInfoContract.IDoctorInfoView iLoginView, @NonNull UserModel userModel) {
        mIDoctorInfoView = iLoginView;
        mUserModel = userModel;
        mIDoctorInfoView.setPresenter(this);
    }


    @Override
    public void start() {
        mIDoctorInfoView.showDialog();
        if (jobList == null) {
            GetBasConstList();
        }
        if (deptList == null) {
            GetServiceDeptList();
        }
    }

    @Override
    public IBaseView getBaseView() {
        return mIDoctorInfoView;
    }

    @Override
    public IBaseModel[] getBaseModelList() {
        return new IBaseModel[]{mUserModel};
    }

    @Override
    public void GetBasConstList() {
        mUserModel.GetBasConstList(new OnHandlerResultListener<GlobalShell<List<BasConstBean>>>() {
            @Override
            public void handlerResult(GlobalShell<List<BasConstBean>> resultData) {
                if (resultData.LogicSuccess) {
                    jobList = resultData.Data;
                    if (deptList != null) {
                        mIDoctorInfoView.updateUI(jobList, deptList);
                        mIDoctorInfoView.changeRetryLayer(false);
                        mIDoctorInfoView.hideDialog();
                    }
                } else {
                    mIDoctorInfoView.changeRetryLayer(true);
                    mIDoctorInfoView.hideDialog(resultData.Message);
                }
            }
        });
    }

    @Override
    public void GetServiceDeptList() {
        mUserModel.GetServiceDeptList(new OnHandlerResultListener<GlobalShell<List<ServiceDeptBean>>>() {
            @Override
            public void handlerResult(GlobalShell<List<ServiceDeptBean>> resultData) {
                if (resultData.LogicSuccess) {
                    deptList = resultData.Data;
                    if (jobList != null) {
                        mIDoctorInfoView.updateUI(jobList, deptList);
                        mIDoctorInfoView.changeRetryLayer(false);
                        mIDoctorInfoView.hideDialog();
                    }
                } else {
                    mIDoctorInfoView.changeRetryLayer(true);
                    mIDoctorInfoView.hideDialog(resultData.Message);
                }
            }
        });
    }
}

