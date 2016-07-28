package haozuo.com.healthdoctor.contract;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Administrator on 2016/7/4.
 */
public abstract class AbsPresenter {

    public abstract BaseView getBaseView();

    public abstract BaseModel getBaseModel();

    public void cancelRequest(){
        BaseModel iBaseModel= getBaseModel();
        iBaseModel.cancelRequest();

        BaseView baseView=getBaseView();
        baseView.hideDialog();
    }
}
