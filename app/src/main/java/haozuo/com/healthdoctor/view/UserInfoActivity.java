package haozuo.com.healthdoctor.view;


import android.os.Bundle;
import android.os.Message;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.view.Activity.BaseActivity;
import haozuo.com.healthdoctor.view.adapter.GroupCustInfoAdapter;
import haozuo.com.healthdoctor.dispatcher.BaseDispatcher;
import haozuo.com.healthdoctor.bean.GroupCustInfoBean;
import haozuo.com.healthdoctor.bean.PageBean;
import haozuo.com.healthdoctor.manager.UserManager;
import haozuo.com.healthdoctor.view.custom.PullToRefresh.PullToRefreshLayout;

/**
 * Created by hzguest3 on 2016/6/14.
 */
public class UserInfoActivity extends BaseActivity {
    @Bind(R.id.list_group_custinfo)ListView list_group_custinfo;
    @Bind(R.id.pull_to_refresh_layout)PullToRefreshLayout pull_to_refresh_layout;

    List<GroupCustInfoBean> dataList;
    List<GroupCustInfoBean> custInfoList ;
    GroupCustInfoAdapter groupCustInfoAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_custinfo_list);
        ButterKnife.bind(this);

        pull_to_refresh_layout.setOnRefreshListener(new PullListener());

        Bundle bundle = getIntent().getExtras();
        int groupId= bundle.getInt("GroupId");

        custInfoList=new ArrayList<>();
        groupCustInfoAdapter=new GroupCustInfoAdapter(UserInfoActivity.this,custInfoList);
        list_group_custinfo.setAdapter(groupCustInfoAdapter);

        Map<String, Object> params = new HashMap<>();
        int serviceDeptId = UserManager.getInstance().getDoctorInfo().ServiceDeptId;
        int pageIndex = 1;
        int pageSize = 20;
        params.put("serviceDeptId", serviceDeptId);
        params.put("groupId", groupId);
        params.put("pageIndex", pageIndex);
        params.put("pageSize", pageSize);
        //shareControllerInstance(UserDispatcher.class).sendMessage(BaseDispatcher.WHAT_USER_GETGROUPCUSTINFOLIST,params);
    }

    @Override
    protected void onReceiveBroadcast(String filterAction) {

    }

    private void handlerGetGroupCustInfoList(Message msg){
        /*BaseDispatcher.RequestResult requestResult = (BaseDispatcher.RequestResult) msg.obj;
        List<GroupCustInfoBean> dataList = ((PageBean<GroupCustInfoBean>) requestResult.ResultData).Data;
        if (requestResult.LogicSuccess && dataList != null){
            custInfoList.clear();
            custInfoList.addAll(dataList);
            groupCustInfoAdapter.notifyDataSetChanged();
        }*/
    }

    class PullListener implements PullToRefreshLayout.OnRefreshListener {

        @Override
        public void onRefresh() {
            // 下拉刷新操作
        }

        @Override
        public void onLoadMore() {
            //上拉分页
        }

    }
}
