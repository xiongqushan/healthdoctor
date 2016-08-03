package haozuo.com.healthdoctor.contract;

import java.util.List;

import haozuo.com.healthdoctor.bean.ConsultItemBean;
import haozuo.com.healthdoctor.bean.GroupCustInfoBean;

/**
 * Created by hzguest3 on 2016/8/1.
 */
public interface ConsultContract {

    interface IConsultView extends IBaseView<IConsultPresenter>{
        void RefreshPendingPageList(List<ConsultItemBean> consultItemBeanList);


        void refreshFinish(int status);
    }

    interface IConsultPresenter extends IBasePresenter{
//        void getConsultList(int flag);

        void refreshCustomList(int flag);

        void loadmoreCustomList(int flag);
    }
}
