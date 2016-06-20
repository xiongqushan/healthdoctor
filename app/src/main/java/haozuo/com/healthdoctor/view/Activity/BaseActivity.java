package haozuo.com.healthdoctor.view.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.widget.Toast;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import haozuo.com.healthdoctor.util.CustomDialog;
import haozuo.com.healthdoctor.util.LoadingDialog;
import haozuo.com.healthdoctor.util.StringUtil;

/**
 * Created by xiongwei on 16/5/7.
 */
public abstract class BaseActivity extends FragmentActivity {
    private LoadingDialog loadingDialog;
    private CustomDialog comfirmDialog;
    BaseBroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void showDialog() {
        showDialog(null);
    }

    public void showDialog(String msg) {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(this);
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
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }
    }

    public void showConfirmDialog(String string, CustomDialog.OnDialogListener onConfirmDialogListener) {
        comfirmDialog = new CustomDialog(this, onConfirmDialogListener);
        comfirmDialog.setContentText(string);
        comfirmDialog.show();
    }

    public void showConfirmDialog(String content, String confirmText, String cancelText, CustomDialog.OnDialogListener onConfirmDialogListener) {
        comfirmDialog = new CustomDialog(this, onConfirmDialogListener);
        comfirmDialog.setContentText(content);
        comfirmDialog.setConfirmText(confirmText);
        comfirmDialog.setCancelText(cancelText);
        comfirmDialog.show();
    }

    public void showConfirmDialog(String content, String confirmText, CustomDialog.OnDialogListener onConfirmDialogListener) {
        comfirmDialog = new CustomDialog(this, onConfirmDialogListener);
        comfirmDialog.setContentText(content);
        comfirmDialog.setConfirmText(confirmText);
        comfirmDialog.show();
    }

    public void registerCustomReceiver(String activeName) {
        String[] filterActiveNames = new String[]{activeName};
        registerCustomReceiver(filterActiveNames);
    }

    public void registerCustomReceiver(String[] filterActiveNames) {
        if (receiver != null) {
            unregisterReceiver(receiver);
        }
        receiver = new BaseBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        for (String activeName : filterActiveNames) {
            filter.addAction(activeName);
        }
        registerReceiver(receiver, filter);
    }

    public void sendCustomBroadcast(String activeName) {
        Intent intent = new Intent(activeName);
        sendBroadcast(intent);
    }

    protected abstract void onReceiveBroadcast(String filterAction) ;

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (receiver != null) {
            unregisterReceiver(receiver);
        }
    }

    class BaseBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String actionName = intent.getAction();
            onReceiveBroadcast(actionName);
        }
    }
}
