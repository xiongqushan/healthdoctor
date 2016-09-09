package haozuo.com.healthdoctor.view.welcome;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Timer;
import java.util.TimerTask;

import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.bean.UpdateInfoBean;
import haozuo.com.healthdoctor.contract.WelcomeContract;
import haozuo.com.healthdoctor.manager.UserManager;
import haozuo.com.healthdoctor.presenter.IBasePresenter;
import haozuo.com.healthdoctor.util.ConnectedUtils;
import haozuo.com.healthdoctor.util.StringUtil;
import haozuo.com.healthdoctor.util.UHealthUtils;
import haozuo.com.healthdoctor.view.base.AbstractView;
import haozuo.com.healthdoctor.view.home.HomeActivity;
import haozuo.com.healthdoctor.view.login.LoginActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class WelcomeFragment extends AbstractView implements WelcomeContract.IWelcomeView {
    private final long turnTimeDelay = 2500;
    private static long lastTime;
    private View rootView;

    private WelcomeContract.IWelcomePresenter mWelcomePresenter;

    public WelcomeFragment() {
        // Required empty public constructor
    }

    @Override
    protected IBasePresenter getPresenter() {
        return null;
    }

    @Override
    protected View getRootView() {
        return rootView;
    }

    public static WelcomeFragment getInstance() {
        WelcomeFragment fragment = new WelcomeFragment();
        return fragment;
    }

    @Override
    public void updateInfo(final UpdateInfoBean bean) {
        if (bean.IsValid) {//TODO 还能用
            if (StringUtil.isTrimEmpty(bean.Notification)) {
                turnNextAty();
            } else {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setTitle("检测到更新")
                        .setCancelable(false)
                        .setMessage(bean.Notification)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                turnNextAty();
                            }
                        }).show();
            }

        } else {//TODO 不能用
            AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
            dialog.setTitle("检测到更新，当前版本不再维护")
                    .setCancelable(false)
                    .setMessage(bean.Notification)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //TODO 跳AppStore，没有就关闭 待修改为跳转到固定的下载界面
                            UHealthUtils.turnStore(getActivity());
                            getActivity().finish();
                        }
                    }).show();
        }
    }

    @Override
    public void turnNextAty() {
        final TimerTask task = new TimerTask() {
            public void run() {
                turnAction();
            }
        };
        Timer timer = new Timer();
        long time = System.currentTimeMillis();
        long requestTime = time - lastTime;
        if (requestTime < turnTimeDelay) {
            timer.schedule(task, turnTimeDelay - requestTime);
        } else {
            turnAction();
        }
    }

    private void turnAction() {
        boolean exist = UserManager.getInstance().exist();
        if (exist) {
            startActivity(new Intent(getActivity(), HomeActivity.class));
        } else {
            startActivity(new Intent(getActivity(), LoginActivity.class));
        }
        getActivity().finish();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_welcome, container, false);
            lastTime = System.currentTimeMillis();
            if (ConnectedUtils.isConnected(getActivity().getApplicationContext())) {
                mWelcomePresenter.start();
            } else {
                turnNextAty();
            }
        }
        return rootView;
    }

    @Override
    public void setPresenter(WelcomeContract.IWelcomePresenter presenter) {
        mWelcomePresenter = presenter;
    }
}
