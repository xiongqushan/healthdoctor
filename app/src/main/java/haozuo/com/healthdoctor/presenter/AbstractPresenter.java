package haozuo.com.healthdoctor.presenter;

import java.util.List;

import haozuo.com.healthdoctor.contract.IBaseModel;
import haozuo.com.healthdoctor.contract.IBaseView;

/**
 * Created by Administrator on 2016/7/4.
 */
public abstract class AbstractPresenter {

    public abstract IBaseView getBaseView();

    public abstract IBaseModel[] getBaseModelList();

    public void cancelRequest() {
        IBaseModel[] iBaseModelList = getBaseModelList();
        if (iBaseModelList == null) {
            return;
        }
        for (IBaseModel ibaseModel : iBaseModelList) {
            ibaseModel.cancelRequest();
        }
        IBaseView baseView = getBaseView();
        baseView.hideDialog();
    }
}
