package haozuo.com.healthdoctor.presenter;

import java.util.List;

import haozuo.com.healthdoctor.bean.DoctorGroupBean;
import haozuo.com.healthdoctor.bean.GlobalShell;
import haozuo.com.healthdoctor.listener.OnAsyncCallbackListener;
import haozuo.com.healthdoctor.model.IBaseModel;
import haozuo.com.healthdoctor.model.IUserModel;
import haozuo.com.healthdoctor.model.UserModel;
import haozuo.com.healthdoctor.view.Interface.IGroupFragment;

/**
 * Created by xiongwei1 on 2016/6/20.
 */
public class GroupPresenterImpl extends BasePresenter implements IGroupPresenter{
    IGroupFragment mIGroupFragment;
    IUserModel mIUserModel;
    public GroupPresenterImpl(IGroupFragment iGroupFragment){
        super();
        mIGroupFragment=iGroupFragment;
        mIUserModel=new UserModel();
    }

    @Override
    public void requestGroupList(int doctorId) {
        String tag=createRequestTag();
        mIUserModel.GetGroup(tag, doctorId, new OnAsyncCallbackListener<GlobalShell<List<DoctorGroupBean>>>() {
            @Override
            public void onSuccess(GlobalShell<List<DoctorGroupBean>> resultData) {
                mIGroupFragment.handlerGetGroupList(resultData);
            }

            @Override
            public void onError(int code, String msg) {

            }
        });
    }

    @Override
    public IBaseModel getIBaseModel() {
        return mIUserModel;
    }
}
