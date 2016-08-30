package haozuo.com.healthdoctor.view.base;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.presenter.IBasePresenter;
import haozuo.com.healthdoctor.util.CustomDialog;
import haozuo.com.healthdoctor.util.LoadingDialog;
import haozuo.com.healthdoctor.util.StringUtil;

/**
 * Created by Administrator on 2016/7/5.
 */
public abstract class AbstractView extends BaseFragment {
    private LoadingDialog loadingDialog;
    private CustomDialog comfirmDialog;

    protected AbstractView() {
    }

    protected abstract IBasePresenter getPresenter();

    protected abstract View getRootView();

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

    private static final int ID_BTNRELOAD = 1357902468;

    public void showRetryLayer(int frameLayoutContainerId) {
        FrameLayout rLayout = (FrameLayout) getRootView().findViewById(frameLayoutContainerId);
        View btnReload = getRootView().findViewById(ID_BTNRELOAD);
//        ImageView btnReload = (ImageView) getRootView().findViewById(ID_BTNRELOAD);
        Log.e("show==btnReload", btnReload + "");
        if (btnReload == null) {
//            btnReload = new ImageView(getActivity());
            btnReload = LayoutInflater.from(getActivity()).inflate(R.layout.retrylayer_layout, null);
            btnReload.setId(ID_BTNRELOAD);
//            btnReload.setImageResource(R.drawable.ic_launcher);
//            btnReload.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
            btnReload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    IBasePresenter presenter = getPresenter();
                    presenter.start();
                    //
                }
            });
            rLayout.addView(btnReload);
        }

    }

    public void hideRetryLayer(int frameLayoutContainerId) {
        final FrameLayout rLayout = (FrameLayout) getRootView().findViewById(frameLayoutContainerId);
//        ImageView btnReload = (ImageView) getRootView().findViewById(ID_BTNRELOAD);
        View btnReload = getRootView().findViewById(ID_BTNRELOAD);
        Log.e("hide==btnReload", btnReload + "");
        if (btnReload != null) {
            rLayout.removeView(btnReload);
        }
    }
}
