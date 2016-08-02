package haozuo.com.healthdoctor.presenter;

import android.support.annotation.NonNull;

import java.util.Collections;
import java.util.List;

import haozuo.com.healthdoctor.bean.DoctorGroupBean;
import haozuo.com.healthdoctor.bean.GlobalShell;
import haozuo.com.healthdoctor.contract.AbsPresenter;
import haozuo.com.healthdoctor.contract.BaseModel;
import haozuo.com.healthdoctor.contract.BaseView;
import haozuo.com.healthdoctor.contract.ConsultContract;
import haozuo.com.healthdoctor.contract.GroupContract;
import haozuo.com.healthdoctor.listener.OnHandlerResultListener;
import haozuo.com.healthdoctor.manager.UserManager;
import haozuo.com.healthdoctor.model.UserModel;
import haozuo.com.healthdoctor.view.custom.CustomDetailFragment;

/**
 * Created by hzguest3 on 2016/8/1.
 */

public class ConsultPresenter extends AbsPresenter implements ConsultContract.IConsultPresenter {
    private ConsultContract.IConsultView mIConsultView;
    private UserModel mUserModel;
    public ConsultPresenter(@NonNull  ConsultContract.IConsultView iConsultView){
        mIConsultView=iConsultView;
        mUserModel=UserModel.createInstance();
        iConsultView.setPresenter(this);
    }

    @Override
    public BaseView getBaseView() {
        return mIConsultView;
    }

    @Override
    public BaseModel getBaseModel() {
        return mUserModel;
    }

    @Override
    public void start() {
        mIConsultView.InitView();
    }

}
