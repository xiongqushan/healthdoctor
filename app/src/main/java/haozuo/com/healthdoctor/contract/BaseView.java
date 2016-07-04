package haozuo.com.healthdoctor.contract;

import haozuo.com.healthdoctor.presenter.*;
import haozuo.com.healthdoctor.util.CustomDialog;

/**
 * Created by xiongwei1 on 2016/7/4.
 */
public interface BaseView<T extends BasePresenter> {
    public void showDialog();

    public void showDialog(String msg) ;

    public void hideDialog();

    public void hideDialog(String msg);

    public void showConfirmDialog(String string, CustomDialog.OnDialogListener onConfirmDialogListener);

    public void showConfirmDialog(String content, String confirmText, String cancelText, CustomDialog.OnDialogListener onConfirmDialogListener) ;

    public void showConfirmDialog(String content, String confirmText, CustomDialog.OnDialogListener onConfirmDialogListener);

    void setPresenter(T presenter);
}
