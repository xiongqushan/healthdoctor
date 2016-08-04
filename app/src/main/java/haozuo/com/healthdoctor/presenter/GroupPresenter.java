package haozuo.com.healthdoctor.presenter;
import android.support.annotation.NonNull;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import haozuo.com.healthdoctor.bean.DoctorGroupBean;
import haozuo.com.healthdoctor.bean.GlobalShell;
import haozuo.com.healthdoctor.contract.IBaseModel;
import haozuo.com.healthdoctor.contract.IBaseView;
import haozuo.com.healthdoctor.contract.GroupContract;
import haozuo.com.healthdoctor.contract.GroupContract.IGroupPresenter;
import haozuo.com.healthdoctor.listener.OnHandlerResultListener;
import haozuo.com.healthdoctor.manager.UserManager;
import haozuo.com.healthdoctor.model.UserModel;

/**
 * Created by xiongwei1 on 2016/7/7.
 */
public class GroupPresenter extends AbstractPresenter implements IGroupPresenter{
    private GroupContract.IGroupView mIGroupView;
    private UserModel mUserModel;
    public GroupPresenter(@NonNull GroupContract.IGroupView iGroupView){
        mIGroupView=iGroupView;
        mUserModel=UserModel.createInstance();
        iGroupView.setPresenter(this);
    }

    @Override
    public IBaseView getBaseView() {
        return mIGroupView;
    }

    @Override
    public IBaseModel getBaseModel() {
        return mUserModel;
    }

    @Override
    public void start() {
        int doctorId= UserManager.getInstance().getDoctorInfo().Doctor_ID;
        mIGroupView.showDialog();
        mUserModel.GetGroup(doctorId, new OnHandlerResultListener<GlobalShell<List<DoctorGroupBean>>>() {
            @Override
            public void handlerResult(GlobalShell<List<DoctorGroupBean>> resultData) {
            if(resultData.LogicSuccess) {
                mIGroupView.hideDialog();
                DoctorGroupBean doctorGroupBean = new DoctorGroupBean();
                Collections.sort(resultData.Data,doctorGroupBean);
                if ((List<DoctorGroupBean>) resultData.Data != null){
                    UserManager.getInstance().setGroupInfo((List<DoctorGroupBean>) resultData.Data);
                    mIGroupView.setGroupInfo(resultData.Data);
                }
            }
            else{
                mIGroupView.hideDialog(resultData.Message);
            }
            }
        });
    }

}
