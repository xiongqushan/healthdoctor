package haozuo.com.healthdoctor.presenter;

import java.util.ArrayList;
import java.util.UUID;

import haozuo.com.healthdoctor.model.IBaseModel;

/**
 * Created by Administrator on 2016/6/19.
 */
public abstract class BasePresenter {
    private ArrayList<String> tagList;

    protected BasePresenter(){
        tagList=new ArrayList<>();
    }

    protected String createRequestTag(){
        String tag= UUID.randomUUID().toString();
        tagList.add(tag);
        return tag;
    }

    public abstract IBaseModel getIBaseModel();

    public void cancelRequest(){
        IBaseModel iBaseModel=getIBaseModel();
        for (String tag:tagList) {
            iBaseModel.cancel(tag);
        }
    }
}
