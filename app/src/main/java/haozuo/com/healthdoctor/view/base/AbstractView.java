package haozuo.com.healthdoctor.view.base;

import android.widget.Toast;

import haozuo.com.healthdoctor.contract.IBasePresenter;
import haozuo.com.healthdoctor.util.CustomDialog;
import haozuo.com.healthdoctor.util.LoadingDialog;
import haozuo.com.healthdoctor.util.StringUtil;

/**
 * Created by Administrator on 2016/7/5.
 */
public abstract class AbstractView extends BaseFragment{
    private LoadingDialog loadingDialog;
    private CustomDialog comfirmDialog;
    protected AbstractView(){
    }

    protected abstract IBasePresenter getPresenter();

    public void showDialog() {
        showDialog(null);
    }


    public void showDialog(String msg) {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(getContext());
        }
        if (msg == null || msg.length() == 0) {
            msg = "数据加载中…";
        }
        loadingDialog.setMessage(msg);
        loadingDialog.show();
    }


    public void hideDialog() {
        hideDialog(null);
    }


    public void hideDialog(String msg) {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
        if (StringUtil.isNotTrimEmpty(msg)) {
            Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
        }
    }


    public void showConfirmDialog(String string, CustomDialog.OnDialogListener onConfirmDialogListener) {
        comfirmDialog = new CustomDialog(getContext(), onConfirmDialogListener);
        comfirmDialog.setContentText(string);
        comfirmDialog.show();
    }


    public void showConfirmDialog(String content, String confirmText, String cancelText, CustomDialog.OnDialogListener onConfirmDialogListener) {
        comfirmDialog = new CustomDialog(getContext(), onConfirmDialogListener);
        comfirmDialog.setContentText(content);
        comfirmDialog.setConfirmText(confirmText);
        comfirmDialog.setCancelText(cancelText);
        comfirmDialog.show();
    }


    public void showConfirmDialog(String content, String confirmText, CustomDialog.OnDialogListener onConfirmDialogListener) {
        comfirmDialog = new CustomDialog(getContext(), onConfirmDialogListener);
        comfirmDialog.setContentText(content);
        comfirmDialog.setConfirmText(confirmText);
        comfirmDialog.show();
    }

    public void showRetryLayer(){
        IBasePresenter presenter=getPresenter();
        presenter.start();
    }
}
