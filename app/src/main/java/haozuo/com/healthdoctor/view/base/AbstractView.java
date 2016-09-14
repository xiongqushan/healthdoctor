package haozuo.com.healthdoctor.view.base;

import android.media.AudioManager;
import android.media.SoundPool;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.presenter.IBasePresenter;
import haozuo.com.healthdoctor.util.CustomDialog;
import haozuo.com.healthdoctor.util.LoadingDialog;
import haozuo.com.healthdoctor.util.StringUtil;
import haozuo.com.healthdoctor.util.UHealthUtils;

/**
 * Created by Administrator on 2016/7/5.
 */
@SuppressWarnings("ResourceType")
public abstract class AbstractView extends BaseFragment {
    private LoadingDialog loadingDialog;
    private CustomDialog comfirmDialog;
    private static SoundPool soundPool;
    private static int soundId;


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

//    public void showRetryLayer(int frameLayoutContainerId) {
//        showRetryLayer(frameLayoutContainerId,true,getString(R.string.connect_fail));
////        showRetryLayer(frameLayoutContainerId);
//    }

    public void showRetryLayer(int frameLayoutContainerId, String reloadTips) {
        FrameLayout rLayout = (FrameLayout) getRootView().findViewById(frameLayoutContainerId);
        View btnReload = getRootView().findViewById(ID_BTNRELOAD);
        if (btnReload == null) {
            btnReload = LayoutInflater.from(getActivity()).inflate(R.layout.retrylayer_layout, null);
            btnReload.setId(ID_BTNRELOAD);
            TextView tv_reloadTips = (TextView) btnReload.findViewById(R.id.tv_reloadTips);
            tv_reloadTips.setText(reloadTips);
            btnReload.setBackground(null);
            rLayout.addView(btnReload);
        }
    }

    public void showRetryLayer(int frameLayoutContainerId) {
        FrameLayout rLayout = (FrameLayout) getRootView().findViewById(frameLayoutContainerId);
        View btnReload = getRootView().findViewById(ID_BTNRELOAD);
        if (btnReload == null) {
            btnReload = LayoutInflater.from(getActivity()).inflate(R.layout.retrylayer_layout, null);
            btnReload.setId(ID_BTNRELOAD);
            btnReload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (UHealthUtils.isFastDoubleClick()) {
                        return;
                    }
                    IBasePresenter presenter = getPresenter();
                    presenter.start();
                }
            });
            rLayout.addView(btnReload);
        }
    }

    public void hideRetryLayer(int frameLayoutContainerId) {
        final FrameLayout rLayout = (FrameLayout) getRootView().findViewById(frameLayoutContainerId);
        View btnReload = getRootView().findViewById(ID_BTNRELOAD);
        if (btnReload != null) {
            rLayout.removeView(btnReload);
        }
    }


    //    pulltorefresh成功播放提示音
    public void playSuccessSound() {
        if (soundPool == null) {
            soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 5);
            soundId = soundPool.load(getContext(), R.raw.loadmore_success, 0);
        }
        soundPool.play(soundId, 1.0f, 1.0f, 0, 0, 1.0f);
//        play(int soundID, float leftVolume, float rightVolume, int priority //优先级, int loop//0 循环 -1不循环, float rate)
    }
}
