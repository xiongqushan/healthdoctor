package haozuo.com.healthdoctor.contract;

import java.util.List;

import haozuo.com.healthdoctor.bean.GroupCustInfoBean;

/**
 * Created by xiongwei1 on 2016/7/11.
 */
public interface GroupCustomListContract {
    interface IGroupCustomListView extends BaseView<IGroupCustomListPresenter>{
        void refreshCustomAdapter(List<GroupCustInfoBean> dataList);
    }

    interface IGroupCustomListPresenter extends BasePresenter{
        void requestCustomList(int pageIndex);
    }
}
