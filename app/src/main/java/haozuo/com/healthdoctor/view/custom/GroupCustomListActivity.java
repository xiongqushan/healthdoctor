package haozuo.com.healthdoctor.view.custom;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.contract.BaseActivity;
import haozuo.com.healthdoctor.presenter.GroupCustomListPresenter;
import haozuo.com.healthdoctor.util.ActivityUtils;

public class GroupCustomListActivity extends BaseActivity {
    public static String EXTRA_GROUP_ID="GROUP_ID";
    GroupCustomListPresenter mGroupCustomListPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_custom_list);
        setCustomerTitle("分组用户列表");
        int groupId=getIntent().getIntExtra(EXTRA_GROUP_ID,-1);
        FragmentManager fragmentManager=getSupportFragmentManager();
        GroupCustomListFragment groupCustomListFragment=(GroupCustomListFragment)fragmentManager.findFragmentById(R.id.frameContent);
        if(groupCustomListFragment==null){
            groupCustomListFragment=GroupCustomListFragment.newInstance();
            ActivityUtils.addFragmentToActivity(fragmentManager,groupCustomListFragment,R.id.frameContent);
        }
        mGroupCustomListPresenter=new GroupCustomListPresenter(groupId, groupCustomListFragment);
    }
}