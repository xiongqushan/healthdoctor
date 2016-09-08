package haozuo.com.healthdoctor.view.home;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Set;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.contract.ConsultContract;
import haozuo.com.healthdoctor.contract.GroupContract;
import haozuo.com.healthdoctor.ioc.DaggerHomeComponent;
import haozuo.com.healthdoctor.ioc.HomeModule;
import haozuo.com.healthdoctor.manager.UserManager;
import haozuo.com.healthdoctor.presenter.ConsultPresenter;
import haozuo.com.healthdoctor.presenter.GroupPresenter;
import haozuo.com.healthdoctor.util.ActivityUtils;
import haozuo.com.healthdoctor.util.ConnectedUtils;
import haozuo.com.healthdoctor.view.base.BaseActivity;
import haozuo.com.healthdoctor.view.consult.ConsultFragment;
import haozuo.com.healthdoctor.view.group.GroupFragment;
import haozuo.com.healthdoctor.view.mine.MineFragment;

public class HomeActivity extends BaseActivity {
    public static final String FINISHACTIVITY = "FINISHACTIVITY";
    @Bind(R.id.tab_menu)
    RadioGroup rgTabhost;
    //    @Bind(R.id.rbGroup)
//    RadioButton rbGroup;
//    @Bind(R.id.rbConsult)
//    RadioButton rbConsult;
//    @Bind(R.id.rbMine)
//    RadioButton rbMine;
    @Bind(R.id.content_group)
    FrameLayout layoutGroup;
    @Bind(R.id.content_consult)
    FrameLayout layoutConsult;
    @Bind(R.id.content_mine)
    FrameLayout layoutMine;


    @Inject
    GroupPresenter mGroupPresenter;
    @Inject
    GroupContract.IGroupView mIGroupView;
    @Inject
    ConsultPresenter mConsultPresenter;
    @Inject
    ConsultContract.IConsultView mIConsultView;

    private FragmentManager fragmentManager;


    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs;
            switch (code) {
                case 0:
                    logs = "Set tag and alias success";
                    Log.e("mAliasCallback", logs);
                    break;

                case 6002:
                    if (ConnectedUtils.isConnected(getApplicationContext())) {
                        mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                    }
                    break;
                default:
            }

        }

    };

    private static final int MSG_SET_ALIAS = 1001;
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SET_ALIAS:
                    JPushInterface.setAliasAndTags(getApplicationContext(), (String) msg.obj, null, mAliasCallback);
                    break;
                default:
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTranslucentStatus(R.color.main_color_blue);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        DaggerHomeComponent.builder()
                .appComponent(getAppComponent())
                .homeModule(new HomeModule())
                .build()
                .inject(this);

        initView();
        //调用JPush API设置Alias
        String alias = UserManager.getInstance().getDoctorInfo().Account;
        int doctor_IDid = UserManager.getInstance().getDoctorInfo().Doctor_ID;
        Log.e("doctor_Account", alias);
        Log.e("doctor_IDid", doctor_IDid + "");
        mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, "2055"));
    }


    private void initView() {
        fragmentManager = getSupportFragmentManager();
        ConsultFragment consultFragment = (ConsultFragment) fragmentManager.findFragmentById(R.id.content_consult);
        if (consultFragment == null) {
            consultFragment = (ConsultFragment) mIConsultView;
            ActivityUtils.addFragmentToActivity(fragmentManager, consultFragment, R.id.content_consult);
        }

        rgTabhost.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                layoutGroup.setVisibility(View.GONE);
                layoutConsult.setVisibility(View.GONE);
                layoutMine.setVisibility(View.GONE);
                if (id == R.id.rbGroup) {
                    layoutGroup.setVisibility(View.VISIBLE);
                    GroupFragment groupFragment = (GroupFragment) fragmentManager.findFragmentById(R.id.content_group);
                    if (groupFragment == null) {
                        groupFragment = (GroupFragment) mIGroupView;
                        ActivityUtils.addFragmentToActivity(fragmentManager, groupFragment, R.id.content_group);
                    }
//                    rbGroup.setChecked(true);
                }
                if (id == R.id.rbConsult) {
                    layoutConsult.setVisibility(View.VISIBLE);
//                    rbConsult.setChecked(true);
                }
                if (id == R.id.rbMine) {
                    MineFragment groupFragment = (MineFragment) fragmentManager.findFragmentById(R.id.content_mine);
                    if (groupFragment == null) {
                        groupFragment = new MineFragment();
                        ActivityUtils.addFragmentToActivity(fragmentManager, groupFragment, R.id.content_mine);
                    }
                    layoutMine.setVisibility(View.VISIBLE);
//                    rbMine.setChecked(true);
                }
            }
        });
    }


    // TODO 0811 添加双击退出  by zy
    private boolean isExit;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isExit) {
                System.exit(0);
                return true;
            } else {
                isExit = true;
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isExit = false;
                    }
                }, 2000);
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

}
