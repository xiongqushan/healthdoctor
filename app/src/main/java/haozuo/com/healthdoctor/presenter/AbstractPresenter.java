package haozuo.com.healthdoctor.presenter;

import haozuo.com.healthdoctor.contract.IBaseModel;
import haozuo.com.healthdoctor.contract.IBaseView;

/**
 * Created by Administrator on 2016/7/4.
 */
public abstract class AbstractPresenter {

    public abstract IBaseView getBaseView();

    public abstract IBaseModel getBaseModel();

    public void cancelRequest(){
        IBaseModel iBaseModel= getBaseModel();
        iBaseModel.cancelRequest();

        IBaseView baseView=getBaseView();
        baseView.hideDialog();
    }
}
