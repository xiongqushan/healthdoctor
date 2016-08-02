package haozuo.com.healthdoctor.presenter;

import android.support.annotation.NonNull;


import haozuo.com.healthdoctor.contract.ConsultContract;
import haozuo.com.healthdoctor.contract.GroupContract;
import haozuo.com.healthdoctor.contract.IBaseModel;
import haozuo.com.healthdoctor.contract.IBaseView;
import haozuo.com.healthdoctor.listener.OnHandlerResultListener;
import haozuo.com.healthdoctor.manager.UserManager;
import haozuo.com.healthdoctor.model.UserModel;
import haozuo.com.healthdoctor.view.custom.CustomDetailFragment;

/**
 * Created by hzguest3 on 2016/8/1.
 */

public class ConsultPresenter extends AbstractPresenter implements ConsultContract.IConsultPresenter {
    private ConsultContract.IConsultView mIConsultView;
    private UserModel mUserModel;
    public ConsultPresenter(@NonNull  ConsultContract.IConsultView iConsultView){
        mIConsultView=iConsultView;
        mUserModel=UserModel.createInstance();
        iConsultView.setPresenter(this);
    }

    @Override
    public IBaseView getBaseView() {
        return mIConsultView;
    }

    @Override
    public IBaseModel getBaseModel() {
        return mUserModel;
    }

    @Override
    public void start() {

    }

    @Override
    public void getConsultList(){
        //http request here;
    }
}
