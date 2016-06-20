package haozuo.com.healthdoctor.listener;

/**
 * Created by xiongwei1 on 16/4/20.
 */
public interface OnAsyncCallbackListener<T> {
    void onSuccess(T resultData);
    void onError(int code, String msg);
}
