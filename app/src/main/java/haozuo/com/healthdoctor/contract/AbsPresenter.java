package haozuo.com.healthdoctor.contract;

import java.util.ArrayList;
import java.util.UUID;

import haozuo.com.healthdoctor.model.IBaseModel;
import haozuo.com.healthdoctor.presenter.*;

/**
 * Created by Administrator on 2016/7/4.
 */
public abstract class AbsPresenter implements BasePresenter{
    private ArrayList<String> mTagList;

    protected AbsPresenter(){
        mTagList=new ArrayList<>();
    }

    protected String createRequestTag(){
        String tag= UUID.randomUUID().toString();
        mTagList.add(tag);
        return tag;
    }

    /*public abstract IBaseModel getIBaseModel();

    public void cancelRequest(){
        IBaseModel iBaseModel=getIBaseModel();
        for (String tag:mTagList) {
            iBaseModel.cancel(tag);
        }
    }*/
}
