package haozuo.com.healthdoctor.contract;

import java.util.List;

import haozuo.com.healthdoctor.bean.GroupCustInfoBean;
import haozuo.com.healthdoctor.presenter.IBasePresenter;
import haozuo.com.healthdoctor.view.IBaseView;

/**
 * Created by xiongwei1 on 2016/7/11.
 */
public interface GroupCustomListContract {
    interface IGroupCustomListView extends IBaseView<IGroupCustomListPresenter> {
        void refreshCustomAdapter(List<GroupCustInfoBean> dataList);

        void refreshFinish(int status);

    }

    interface IGroupCustomListPresenter extends IBasePresenter {
        void refreshCustomList(String customNameOrMobile);

        void loadmoreCustomList(String customNameOrMobile);
    }
}
