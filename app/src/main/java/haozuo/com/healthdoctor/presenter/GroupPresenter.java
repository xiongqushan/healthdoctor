package haozuo.com.healthdoctor.presenter;
import android.content.ContentValues;
import android.support.annotation.NonNull;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import haozuo.com.healthdoctor.bean.DoctorBean;
import haozuo.com.healthdoctor.bean.DoctorGroupBean;
import haozuo.com.healthdoctor.bean.GlobalShell;
import haozuo.com.healthdoctor.contract.AbsPresenter;
import haozuo.com.healthdoctor.contract.BaseModel;
import haozuo.com.healthdoctor.contract.BaseView;
import haozuo.com.healthdoctor.contract.GroupContract;
import haozuo.com.healthdoctor.contract.GroupContract.IGroupPresenter;
import haozuo.com.healthdoctor.listener.OnHandlerResultListener;
import haozuo.com.healthdoctor.manager.UserManager;
import haozuo.com.healthdoctor.model.UserModel;

/**
 * Created by xiongwei1 on 2016/7/7.
 */
public class GroupPresenter extends AbsPresenter implements IGroupPresenter{
    private GroupContract.IGroupView mIGroupView;
    private UserModel mUserModel;
    public GroupPresenter(@NonNull GroupContract.IGroupView iGroupView){
        mIGroupView=iGroupView;
        mUserModel=new UserModel();
        iGroupView.setPresenter(this);
    }

    @Override
    public BaseView getBaseView() {
        return mIGroupView;
    }

    @Override
    public BaseModel getBaseModel() {
        return mUserModel;
    }

    @Override
    public void start() {
        int doctorId= UserManager.getInstance().getDoctorInfo().Id;
        mIGroupView.showDialog();
        mUserModel.GetGroup(createRequestTag(), doctorId, new OnHandlerResultListener<GlobalShell<List<DoctorGroupBean>>>() {
            @Override
            public void handlerResult(GlobalShell<List<DoctorGroupBean>> resultData) {
                if(resultData.LogicSuccess) {
                    mIGroupView.hideDialog();
                    DoctorGroupBean doctorGroupBean = new DoctorGroupBean();
                    Collections.sort(resultData.Data,doctorGroupBean);
                    mIGroupView.setGroupInfo(resultData.Data);
//                    mIGroupView.refreshGroupAdapter(resultData.Data);
                }
                else{
                    mIGroupView.hideDialog(resultData.Message);
                }
            }
        });
    }


}
