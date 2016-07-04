package haozuo.com.healthdoctor.contract;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import haozuo.com.healthdoctor.util.CustomDialog;
import haozuo.com.healthdoctor.util.LoadingDialog;
import haozuo.com.healthdoctor.util.StringUtil;

/**
 * Created by Administrator on 2016/7/5.
 */
public abstract class AbsFragmentView extends Fragment  implements BaseView{
    private LoadingDialog loadingDialog;
    private CustomDialog comfirmDialog;
    protected AbsFragmentView(){
    }

    @Override
    public void showDialog() {
        showDialog(null);
    }

    @Override
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

    @Override
    public void hideDialog() {
        hideDialog(null);
    }

    @Override
    public void hideDialog(String msg) {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
        if (StringUtil.isNotTrimEmpty(msg)) {
            Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showConfirmDialog(String string, CustomDialog.OnDialogListener onConfirmDialogListener) {
        comfirmDialog = new CustomDialog(getContext(), onConfirmDialogListener);
        comfirmDialog.setContentText(string);
        comfirmDialog.show();
    }

    @Override
    public void showConfirmDialog(String content, String confirmText, String cancelText, CustomDialog.OnDialogListener onConfirmDialogListener) {
        comfirmDialog = new CustomDialog(getContext(), onConfirmDialogListener);
        comfirmDialog.setContentText(content);
        comfirmDialog.setConfirmText(confirmText);
        comfirmDialog.setCancelText(cancelText);
        comfirmDialog.show();
    }

    @Override
    public void showConfirmDialog(String content, String confirmText, CustomDialog.OnDialogListener onConfirmDialogListener) {
        comfirmDialog = new CustomDialog(getContext(), onConfirmDialogListener);
        comfirmDialog.setContentText(content);
        comfirmDialog.setConfirmText(confirmText);
        comfirmDialog.show();
    }

}
