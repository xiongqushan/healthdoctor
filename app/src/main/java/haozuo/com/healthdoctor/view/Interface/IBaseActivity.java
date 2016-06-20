package haozuo.com.healthdoctor.view.Interface;

import haozuo.com.healthdoctor.util.CustomDialog;

/**
 * Created by Administrator on 2016/6/18.
 */
public interface IBaseActivity {
    public void showDialog() ;

    public void showDialog(String msg) ;

    public void hideDialog();

    public void hideDialog(String msg) ;

    public void showConfirmDialog(String string, CustomDialog.OnDialogListener onConfirmDialogListener) ;

    public void showConfirmDialog(String content, String confirmText, String cancelText, CustomDialog.OnDialogListener onConfirmDialogListener) ;

    public void showConfirmDialog(String content, String confirmText, CustomDialog.OnDialogListener onConfirmDialogListener);
}
