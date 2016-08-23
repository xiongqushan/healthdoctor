package haozuo.com.healthdoctor.contract;

import haozuo.com.healthdoctor.bean.CustomDetailBean;

/**
 * Created by hzguest3 on 2016/7/22.
 */
public interface CustomDetailContract {

    interface ICustomDetailView extends IBaseView<ICustomDetailPresenter>{
        void InitView(CustomDetailBean custom);

        void changeRetryLayer(boolean isShow);

    }

    interface ICustomDetailPresenter extends IBasePresenter {

    }
}
