package haozuo.com.healthdoctor.contract;

import java.util.List;

import haozuo.com.healthdoctor.bean.DoctorGroupBean;
import haozuo.com.healthdoctor.presenter.IBasePresenter;
import haozuo.com.healthdoctor.view.IBaseView;

/**
 * Created by xiongwei1 on 2016/7/7.
 */
public interface GroupContract {
    interface IGroupView extends IBaseView<IGroupPresenter> {
        //        void refreshGroupAdapter(List<DoctorGroupBean>doctorGroupBeanList);

        void setGroupInfo(List<DoctorGroupBean> doctorGroupBeanList);

        void changeRetryLayer(boolean isShow);
    }

    interface IGroupPresenter extends IBasePresenter {

    }

}
