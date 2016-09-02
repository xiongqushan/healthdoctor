
package haozuo.com.healthdoctor.view.mine;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipeline;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.manager.GroupInfoManager;
import haozuo.com.healthdoctor.manager.UserManager;
import haozuo.com.healthdoctor.util.UHealthUtils;
import haozuo.com.healthdoctor.view.base.BaseFragment;
import haozuo.com.healthdoctor.view.home.HomeActivity;
import haozuo.com.healthdoctor.view.login.LoginActivity;
import haozuo.com.healthdoctor.view.threePart.switchbutton.SwitchButton;


/**
 * by zy 2016.08.24
 */
public class SettingsFragment extends BaseFragment {
    private Context mContext;

    @Bind(R.id.btn_push)
    SwitchButton btnPush;

    @OnClick(R.id.layout_aboutus)
    void aboutusClick() {
        getActivity().startActivity(new Intent(getActivity(), AboutUsActivity.class));
    }

    @OnClick(R.id.layout_help)
    void helpClick() {
        getActivity().startActivity(new Intent(getActivity(), HelpActivity.class));
    }

    @OnClick(R.id.layout_feedback)
    void feedbackClick() {
        getActivity().startActivity(new Intent(getActivity(), AdviceActivity.class));
    }

    @OnClick(R.id.layout_disclaimer)
    void disclaimerClick() {
        getActivity().startActivity(new Intent(getActivity(), StatementActivity.class));
    }

    @OnClick(R.id.layout_clearcache)
    void clearcacheClick() {
        //清理图片缓存
        Fresco.getImagePipeline().clearCaches();
        GroupInfoManager.getInstance().clear();
        UserManager.getInstance().clear();
    }

    @OnClick(R.id.layout_push)
    void changePushState() {
        if (UHealthUtils.isFastDoubleClick()) return;
        btnPush.setChecked(!btnPush.isChecked());
    }


    @OnClick(R.id.btnSignOut)
    void btnSignOut() {
        ShowSignOutDialog();
    }

    private View rootView;

    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = getContext();
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_settings, container, false);
            ButterKnife.bind(this, rootView);
            btnPush.setChecked(!JPushInterface.isPushStopped(getActivity()));
            btnPush.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        JPushInterface.resumePush(getActivity());
                    } else {
                        JPushInterface.stopPush(getActivity());
                    }
                    // PreferenceManager.getInstance().writeJpush(isChecked);
                     Toast.makeText(getContext(), "消息推送:" + (isChecked ? "on" : "off"), Toast.LENGTH_SHORT).show();
                }
            });
        }
        return rootView;
    }

    public void ShowSignOutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("退出当前账号")
                .setMessage("退出当前账号，你可能不能及时回复客户咨询，确认退出？")
                .setNegativeButton("取消", null)
                .setPositiveButton("确定退出", SignOut)
                .show();
    }

    DialogInterface.OnClickListener SignOut = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            UserManager.getInstance().clear();
            GroupInfoManager.getInstance().clear();
            startActivity(new Intent(mContext, LoginActivity.class));
            sendCustomBroadcast(HomeActivity.FINISHACTIVITY);
            getActivity().finish();
        }
    };


}
