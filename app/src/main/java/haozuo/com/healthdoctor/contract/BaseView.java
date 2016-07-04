package haozuo.com.healthdoctor.contract;

import haozuo.com.healthdoctor.presenter.*;

/**
 * Created by xiongwei1 on 2016/7/4.
 */
public interface BaseView<T extends BasePresenter> {
    void setPresenter(T presenter);
}
