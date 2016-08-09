package haozuo.com.healthdoctor.contract;

import java.util.List;

import haozuo.com.healthdoctor.bean.DoctorGroupBean;

/**
 * Created by xiongwei1 on 2016/7/7.
 */
public interface GroupContract {
    interface IGroupView extends IBaseView<IGroupPresenter> {
        //        void refreshGroupAdapter(List<DoctorGroupBean>doctorGroupBeanList);
        void setGroupInfo(List<DoctorGroupBean>doctorGroupBeanList);
    }

    interface IGroupPresenter extends IBasePresenter {

    }

}
