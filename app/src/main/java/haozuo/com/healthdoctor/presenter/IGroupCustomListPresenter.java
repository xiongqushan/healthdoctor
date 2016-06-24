package haozuo.com.healthdoctor.presenter;

/**
 * Created by Administrator on 2016/6/24.
 */
public interface IGroupCustomListPresenter {
    void RequestGroupCustInfoList(int serviceDeptId,int groupId,String customNameOrId,int pageIndex,int pageSize);
}
