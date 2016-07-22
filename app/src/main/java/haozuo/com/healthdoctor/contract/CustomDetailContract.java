package haozuo.com.healthdoctor.contract;

import haozuo.com.healthdoctor.bean.CustomBean;

/**
 * Created by hzguest3 on 2016/7/22.
 */
public interface CustomDetailContract {
    interface ICustomDetailView extends BaseView<ICustomDetailPresenter>{
        void InitView(CustomBean custom);
    }

    interface ICustomDetailPresenter extends BasePresenter{

    }
}
