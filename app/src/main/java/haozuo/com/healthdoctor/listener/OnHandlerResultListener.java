package haozuo.com.healthdoctor.listener;

import haozuo.com.healthdoctor.bean.RequestErrorEnum;

/**
 * Created by xiongwei1 on 16/4/20.
 */
public interface OnHandlerResultListener<T> {
    void handlerResult(T resultData);
}
