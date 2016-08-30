package haozuo.com.healthdoctor.view.mine;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.OnClick;
import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.manager.GroupInfoManager;
import haozuo.com.healthdoctor.manager.InternalStorageManager;
import haozuo.com.healthdoctor.manager.UserManager;
import haozuo.com.healthdoctor.view.base.BaseFragment;
import haozuo.com.healthdoctor.view.home.HomeActivity;
import haozuo.com.healthdoctor.view.login.LoginActivity;


/**
 * by zy 2016.08.24
 */
public class SettingsFragment extends BaseFragment {
    private Context mContext;

    @OnClick(R.id.layout_aboutus)
    void aboutusClick() {
    }

    @OnClick(R.id.layout_help)
    void helpClick() {
    }

    @OnClick(R.id.layout_feedback)
    void feedbackClick() {
    }

    @OnClick(R.id.layout_disclaimer)
    void disclaimerClick() {
    }

    @OnClick(R.id.layout_clearcache)
    void clearcacheClick() {
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
            rootView = inflater.inflate(R.layout.fragment_settings_v2, container, false);
            ButterKnife.bind(this, rootView);
        }
        return rootView;
    }

    public void ShowSignOutDialog(){
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(mContext);
        builder.setTitle("退出当前账号");
        builder.setMessage("退出当前账号，你可能不能及时回复客户咨询，确认退出？");
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("确定退出", SignOut);
        builder.show();
    }

    DialogInterface.OnClickListener SignOut = new DialogInterface.OnClickListener(){
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
