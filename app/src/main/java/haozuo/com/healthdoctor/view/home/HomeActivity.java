package haozuo.com.healthdoctor.view.home;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.contract.ConsultContract;
import haozuo.com.healthdoctor.contract.GroupContract;
import haozuo.com.healthdoctor.ioc.DaggerHomeComponent;
import haozuo.com.healthdoctor.ioc.HomeModule;
import haozuo.com.healthdoctor.presenter.ConsultPresenter;
import haozuo.com.healthdoctor.presenter.GroupPresenter;
import haozuo.com.healthdoctor.util.ActivityUtils;
import haozuo.com.healthdoctor.view.base.BaseActivity;
import haozuo.com.healthdoctor.view.consult.ConsultFragment;
import haozuo.com.healthdoctor.view.group.GroupFragment;

public class HomeActivity extends BaseActivity {
    @Bind(R.id.tab_menu)
    RadioGroup rgTabhost;
    @Bind(R.id.rbGroup)
    RadioButton rbGroup;
    @Bind(R.id.rbConsult)
    RadioButton rbConsult;
    @Bind(R.id.rbMine)
    RadioButton rbMine;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        DaggerHomeComponent.builder()
                .appComponent(getAppComponent())
                .homeModule(new HomeModule())
                .build()
                .inject(this);


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
                    rbGroup.setChecked(true);
                }
                if (id == R.id.rbConsult) {
                    layoutConsult.setVisibility(View.VISIBLE);
                    rbConsult.setChecked(true);
                }
                if (id == R.id.rbMine) {
//                    GroupFragment groupFragment = (GroupFragment) fragmentManager.findFragmentById(R.id.content_mine);
//                    if (groupFragment == null) {
//                        groupFragment = new GroupFragment();
//                        ActivityUtils.addFragmentToActivity(fragmentManager, groupFragment, R.id.content_mine);
//                    }
                    layoutMine.setVisibility(View.VISIBLE);
                    rbMine.setChecked(true);
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
