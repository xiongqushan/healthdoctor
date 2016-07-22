package haozuo.com.healthdoctor.presenter;

import android.support.annotation.NonNull;

import haozuo.com.healthdoctor.bean.CustomBean;
import haozuo.com.healthdoctor.contract.AbsPresenter;
import haozuo.com.healthdoctor.contract.BaseModel;
import haozuo.com.healthdoctor.contract.BaseView;
import haozuo.com.healthdoctor.contract.CustomDetailContract;
import haozuo.com.healthdoctor.model.UserModel;

/**
 * Created by hzguest3 on 2016/7/22.
 */
public class CustomDetailPresenter extends AbsPresenter implements CustomDetailContract.ICustomDetailPresenter {
    private CustomDetailContract.ICustomDetailView mICustomDetailView;
    private UserModel mUserModel;
    public CustomDetailPresenter(@NonNull CustomDetailContract.ICustomDetailView iView){
        mICustomDetailView=iView;
        mUserModel=new UserModel();
        mICustomDetailView.setPresenter(this);
    }

    @Override
    public void start() {
        //request data
        CustomBean customBean=null;

        mICustomDetailView.InitView(customBean);
    }

    @Override
    public BaseView getBaseView() {
        return mICustomDetailView;
    }

    @Override
    public BaseModel getBaseModel() {
        return mUserModel;
    }
}
