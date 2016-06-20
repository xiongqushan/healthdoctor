package haozuo.com.healthdoctor.util;

import haozuo.com.healthdoctor.dispatcher.BaseDispatcher;

/**
 * Created by xiongwei on 16/5/16.
 */
public interface OnBatchRequestListener {
    void beforeRequest();

    void endRequest();

    void receiveNotify(BaseDispatcher.RequestResult requestResult);
}
