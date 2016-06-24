package haozuo.com.healthdoctor.presenter;

import haozuo.com.healthdoctor.bean.GlobalShell;
import haozuo.com.healthdoctor.bean.GroupCustInfoBean;
import haozuo.com.healthdoctor.bean.PageBean;
import haozuo.com.healthdoctor.listener.OnHandlerResultListener;
import haozuo.com.healthdoctor.model.IBaseModel;
import haozuo.com.healthdoctor.model.IUserModel;
import haozuo.com.healthdoctor.model.UserModel;
import haozuo.com.healthdoctor.view.Interface.IGroupCustomListActivity;

/**
 * Created by Administrator on 2016/6/24.
 */
public class GroupCustomListPresenterImpl extends BasePresenter implements IGroupCustomListPresenter{
    IGroupCustomListActivity mIGroupCustomListActivity;
    IUserModel mIUserModel;
    public GroupCustomListPresenterImpl(IGroupCustomListActivity iGroupCustomListActivity){
        mIGroupCustomListActivity=iGroupCustomListActivity;
        mIUserModel=new UserModel();
    }

    @Override
    public IBaseModel getIBaseModel() {
        return mIUserModel;
    }

    @Override
    public void RequestGroupCustInfoList(int serviceDeptId, int groupId, String customNameOrId, int pageIndex, int pageSize) {
        String tag=createRequestTag();
        mIUserModel.GetGroupCustInfoList(tag, serviceDeptId, groupId, customNameOrId, pageIndex, pageSize, new OnHandlerResultListener<GlobalShell<PageBean<GroupCustInfoBean>>>() {
            @Override
            public void handlerResult(GlobalShell<PageBean<GroupCustInfoBean>> resultData) {
                mIGroupCustomListActivity.handlerLogin(resultData);
            }
        });

    }
}
