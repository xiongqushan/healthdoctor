package haozuo.com.healthdoctor.contract;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Administrator on 2016/7/4.
 */
public abstract class AbsPresenter {
    public AbsPresenter(){
        mTagList=new ArrayList<>();
    }

    private ArrayList<String> mTagList;

    protected String createRequestTag(){
        String tag= UUID.randomUUID().toString();
        mTagList.add(tag);
        return tag;
    }

    public abstract BaseView getBaseView();

    public abstract BaseModel getBaseModel();

    public void cancelRequest(){
        BaseModel iBaseModel= getBaseModel();
        for (String tag:mTagList) {
            iBaseModel.cancel(tag);
        }
        BaseView baseView=getBaseView();
        baseView.hideDialog();
    }
}
