package haozuo.com.healthdoctor.view.custom;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import java.io.Serializable;

import javax.inject.Inject;

import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.bean.DoctorGroupBean;
import haozuo.com.healthdoctor.ioc.DaggerGroupCustomListPresenterComponent;
import haozuo.com.healthdoctor.ioc.GroupCustomListPresenterModule;
import haozuo.com.healthdoctor.view.base.BaseActivity;
import haozuo.com.healthdoctor.presenter.GroupCustomListPresenter;
import haozuo.com.healthdoctor.util.ActivityUtils;

public class GroupCustomListActivity extends BaseActivity {
    @Inject
    GroupCustomListPresenter mGroupCustomListPresenter;

    public static String EXTRA_GROUP_ID="GROUP_ID";
    int groupId;
    String groupName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_custom_list);

//        int groupId=getIntent().getIntExtra(EXTRA_GROUP_ID,-1);
        Serializable obj= getIntent().getSerializableExtra(EXTRA_GROUP_ID);
        if(obj!=null){
            DoctorGroupBean doctorGroupBean = (DoctorGroupBean) obj;
            groupName = doctorGroupBean.name;
            groupId = doctorGroupBean.id;
        }
        setCustomerTitle(groupName);
        FragmentManager fragmentManager=getSupportFragmentManager();
        GroupCustomListFragment groupCustomListFragment=(GroupCustomListFragment)fragmentManager.findFragmentById(R.id.frameContent);
        if(groupCustomListFragment==null){
            groupCustomListFragment=GroupCustomListFragment.newInstance();
            ActivityUtils.addFragmentToActivity(fragmentManager,groupCustomListFragment,R.id.frameContent);
        }
        //mGroupCustomListPresenter=new GroupCustomListPresenter(groupId, groupCustomListFragment);
        DaggerGroupCustomListPresenterComponent.builder()
                .appComponent(getAppComponent())
                .groupCustomListPresenterModule(new GroupCustomListPresenterModule(groupCustomListFragment,groupId));
    }
}