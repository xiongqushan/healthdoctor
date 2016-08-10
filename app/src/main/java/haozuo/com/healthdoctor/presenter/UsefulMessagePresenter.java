package haozuo.com.healthdoctor.presenter;

import android.content.Context;
import android.support.annotation.NonNull;

import javax.inject.Inject;

import haozuo.com.healthdoctor.contract.IBaseModel;
import haozuo.com.healthdoctor.contract.IBaseView;
import haozuo.com.healthdoctor.contract.UsefulMessageContract;
import haozuo.com.healthdoctor.model.ConsultModel;

/**
 * Created by hzguest3 on 2016/8/9.
 */
public class UsefulMessagePresenter extends AbstractPresenter implements UsefulMessageContract.IUsefulMessagePresenter{
    UsefulMessageContract.IUsefulMessageView mIUsefulMessageView;
    Context mContext;
    ConsultModel mConsultModel;

    @Inject
    public UsefulMessagePresenter(@NonNull UsefulMessageContract.IUsefulMessageView iUsefulMessageView,@NonNull Context context, @NonNull ConsultModel consultModel){
        mIUsefulMessageView = iUsefulMessageView;
        mConsultModel = consultModel;
        mContext = context;
        mIUsefulMessageView.setPresenter(this);
    }

    @Override
    public IBaseView getBaseView() {
        return mIUsefulMessageView;
    }

    @Override
    public IBaseModel getBaseModel() {
        return mConsultModel;
    }

    @Override
    public void start() {}
}
