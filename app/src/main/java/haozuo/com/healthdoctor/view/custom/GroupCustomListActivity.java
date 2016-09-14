package haozuo.com.healthdoctor.view.custom;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import java.io.Serializable;

import javax.inject.Inject;

import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.bean.DoctorGroupBean;
import haozuo.com.healthdoctor.contract.GroupCustomListContract;
import haozuo.com.healthdoctor.ioc.DaggerGroupCustomListComponent;
import haozuo.com.healthdoctor.ioc.GroupCustomListModule;
import haozuo.com.healthdoctor.presenter.GroupCustomListPresenter;
import haozuo.com.healthdoctor.util.ActivityUtils;
import haozuo.com.healthdoctor.view.base.BaseActivity;

public class GroupCustomListActivity extends BaseActivity {
    @Inject
    GroupCustomListPresenter mGroupCustomListPresenter;
    @Inject
    GroupCustomListContract.IGroupCustomListView mIGroupCustomListView;

    public static String EXTRA_GROUP_ID="GROUP_ID";
    int groupId;
    String groupName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTranslucentStatus(R.color.main_color_blue);
        setContentView(R.layout.activity_group_custom_list);

        Serializable obj = getIntent().getSerializableExtra(EXTRA_GROUP_ID);
        if (obj != null) {
            DoctorGroupBean doctorGroupBean = (DoctorGroupBean) obj;
            groupName = doctorGroupBean.name;
            groupId = doctorGroupBean.id;
        }
        setTitleWithSearch(groupName);

        DaggerGroupCustomListComponent.builder()
                .appComponent(getAppComponent())
                .groupCustomListModule(new GroupCustomListModule(groupId))
                .build()
                .inject(this);

        FragmentManager fragmentManager = getSupportFragmentManager();
        GroupCustomListFragment groupCustomListFragment = (GroupCustomListFragment) fragmentManager.findFragmentById(R.id.frameContent);
        if (groupCustomListFragment == null) {
            groupCustomListFragment = (GroupCustomListFragment) mIGroupCustomListView;
            ActivityUtils.addFragmentToActivity(fragmentManager, groupCustomListFragment, R.id.frameContent);
        }
    }

}