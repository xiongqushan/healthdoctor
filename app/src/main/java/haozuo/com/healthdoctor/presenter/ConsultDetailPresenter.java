package haozuo.com.healthdoctor.presenter;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import haozuo.com.healthdoctor.bean.ConsultItemBean;
import haozuo.com.healthdoctor.contract.ConsultDetailContract;
import haozuo.com.healthdoctor.contract.IBaseModel;
import haozuo.com.healthdoctor.contract.IBaseView;
import haozuo.com.healthdoctor.model.ConsultModel;

/**
 * Created by hzguest3 on 2016/8/3.
 */

public class ConsultDetailPresenter extends AbstractPresenter implements ConsultDetailContract.IConsultDetailPresenter {
    private ConsultDetailContract.IConsultDetailView mIConsultDetailView;
    private ConsultModel mConsultModel;
    private List<ConsultItemBean> mConsultItemBeanList;
    public ConsultDetailPresenter(@NonNull ConsultDetailContract.IConsultDetailView iConsultDetailView){
        mConsultItemBeanList = new ArrayList<ConsultItemBean>();
        mIConsultDetailView=iConsultDetailView;
        mConsultModel=ConsultModel.createInstance();
        iConsultDetailView.setPresenter(this);
    }

    @Override
    public IBaseView getBaseView() {
        return mIConsultDetailView;
    }

    @Override
    public IBaseModel getBaseModel() {
        return mConsultModel;
    }

    @Override
    public void start() {

    }


}

