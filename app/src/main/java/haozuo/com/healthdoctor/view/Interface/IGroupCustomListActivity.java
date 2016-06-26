package haozuo.com.healthdoctor.view.Interface;

import haozuo.com.healthdoctor.bean.GlobalShell;
import haozuo.com.healthdoctor.bean.GroupCustInfoBean;
import haozuo.com.healthdoctor.bean.PageBean;

/**
 * Created by Administrator on 2016/6/24.
 */
public interface IGroupCustomListActivity {
    void handleGroupCustInfoList(GlobalShell<PageBean<GroupCustInfoBean>> result);
}
