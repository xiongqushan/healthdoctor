package haozuo.com.healthdoctor.view.group;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import javax.inject.Inject;

import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.contract.GroupContract;
import haozuo.com.healthdoctor.ioc.DaggerGroupComponent;
import haozuo.com.healthdoctor.ioc.GroupModule;
import haozuo.com.healthdoctor.presenter.GroupPresenter;
import haozuo.com.healthdoctor.util.ActivityUtils;
import haozuo.com.healthdoctor.view.base.BaseActivity;

public class GroupActivity extends BaseActivity {
    @Inject
    GroupPresenter mGroupPresenter;
    @Inject
    GroupContract.IGroupView mIGroupView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        setCustomerTitle("客户分组");
        hideGoBackBtn();
        DaggerGroupComponent.builder()
                .appComponent(getAppComponent())
                .groupModule(new GroupModule())
                .build()
                .inject(this);

        FragmentManager fragmentManager = getSupportFragmentManager();
        GroupFragment groupFragment = (GroupFragment) fragmentManager.findFragmentById(R.id.frameContent);
        if (groupFragment == null) {
            groupFragment = (GroupFragment) mIGroupView;
            ActivityUtils.addFragmentToActivity(fragmentManager, groupFragment, R.id.frameContent);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initTabhostMenu();
    }



}
