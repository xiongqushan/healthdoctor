package haozuo.com.healthdoctor.view.group;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;

import javax.inject.Inject;

import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.ioc.DaggerGroupPresenterComponent;
import haozuo.com.healthdoctor.ioc.GroupPresenterModule;
import haozuo.com.healthdoctor.view.base.BaseActivity;
import haozuo.com.healthdoctor.manager.UserManager;
import haozuo.com.healthdoctor.presenter.GroupPresenter;
import haozuo.com.healthdoctor.util.ActivityUtils;
import haozuo.com.healthdoctor.view.login.LoginActivity;

public class GroupActivity extends BaseActivity {
    @Inject
    GroupPresenter mGroupPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        setCustomerTitle("客户分组");
        hideGoBackBtn();

        FragmentManager fragmentManager=getSupportFragmentManager();
        GroupFragment groupFragment=(GroupFragment)fragmentManager.findFragmentById(R.id.frameContent);
        if(groupFragment==null){
            groupFragment=GroupFragment.newInstance();
            ActivityUtils.addFragmentToActivity(fragmentManager,groupFragment,R.id.frameContent);
        }

        DaggerGroupPresenterComponent.builder()
                .appComponent(getAppComponent())
                .groupPresenterModule(new GroupPresenterModule(groupFragment))
                .build()
                .inject(this);
    }

    @Override
    public void onResume(){
        super.onResume();
        initTabhostMenu();
    }

}
