package haozuo.com.healthdoctor.contract;

import java.util.List;

import haozuo.com.healthdoctor.bean.GroupCustInfoBean;
import haozuo.com.healthdoctor.view.threePart.PullToRefresh.PullToRefreshLayout;

/**
 * Created by xiongwei1 on 2016/7/11.
 */
public interface GroupCustomListContract {
    interface IGroupCustomListView extends BaseView<IGroupCustomListPresenter>{
        void refreshCustomAdapter(List<GroupCustInfoBean> dataList);

        void refreshFinish(int status);
    }

    interface IGroupCustomListPresenter extends BasePresenter{
        void refreshCustomList();

        void loadmoreCustomList();
    }
}
