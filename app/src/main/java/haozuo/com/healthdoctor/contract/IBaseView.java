package haozuo.com.healthdoctor.contract;

import haozuo.com.healthdoctor.util.CustomDialog;

/**
 * Created by xiongwei1 on 2016/7/4.
 */
public interface IBaseView<T extends IBasePresenter> {
    void showDialog();

    void showDialog(String msg) ;

    void hideDialog();

    void hideDialog(String msg);

    void showConfirmDialog(String string, CustomDialog.OnDialogListener onConfirmDialogListener);

    void showConfirmDialog(String content, String confirmText, String cancelText, CustomDialog.OnDialogListener onConfirmDialogListener) ;

    void showConfirmDialog(String content, String confirmText, CustomDialog.OnDialogListener onConfirmDialogListener);

    void showRetryLayer();

    void setPresenter(T presenter);
}
