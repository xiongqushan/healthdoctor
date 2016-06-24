package haozuo.com.healthdoctor.view.Activity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.bean.GlobalShell;
import haozuo.com.healthdoctor.bean.GroupCustInfoBean;
import haozuo.com.healthdoctor.bean.PageBean;
import haozuo.com.healthdoctor.manager.UserManager;
import haozuo.com.healthdoctor.presenter.GroupCustomListPresenterImpl;
import haozuo.com.healthdoctor.presenter.IGroupCustomListPresenter;
import haozuo.com.healthdoctor.view.Interface.IGroupCustomListActivity;
import haozuo.com.healthdoctor.view.adapter.GroupCustInfoAdapter;
import haozuo.com.healthdoctor.view.custom.PullToRefresh.PullToRefreshLayout;

public class GroupCustomListActivity extends BaseActivity implements IGroupCustomListActivity {
    @Bind(R.id.list_group_customlist)ListView list_group_customlist;
    //@Bind(R.id.pull_to_refresh_layout)PullToRefreshLayout pull_to_refresh_layout;

    IGroupCustomListPresenter mIGroupCustomListPresenter;
    List<GroupCustInfoBean> dataList;
    List<GroupCustInfoBean> custInfoList ;
    GroupCustInfoAdapter groupCustInfoAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_custom_list);
        ButterKnife.bind(this);

        setCustomerTitle("分组用户列表1");
        mIGroupCustomListPresenter=new GroupCustomListPresenterImpl(this);
        custInfoList=new ArrayList<>();
        groupCustInfoAdapter=new GroupCustInfoAdapter(GroupCustomListActivity.this,custInfoList);
        list_group_customlist.setAdapter(groupCustInfoAdapter);

        //pull_to_refresh_layout.setOnRefreshListener(new PullListener());

        requestData();
    }

    void requestData(){
        Bundle bundle = getIntent().getExtras();
        int groupId= bundle.getInt("GroupId");
        int serviceDeptId = UserManager.getInstance().getDoctorInfo().ServiceDeptId;
        int pageIndex = 1;
        int pageSize = 20;
        showDialog();
        mIGroupCustomListPresenter.RequestGroupCustInfoList(serviceDeptId,groupId,"",pageIndex,pageSize);
    }

    @Override
    protected void onReceiveBroadcast(String filterAction) {

    }

    @Override
    public void handlerLogin(GlobalShell<PageBean<GroupCustInfoBean>> result) {
        if(result.LogicSuccess) {
            hideDialog();
            custInfoList.clear();
            custInfoList.addAll(result.Data.CurrentPageDataList);
            groupCustInfoAdapter.notifyDataSetChanged();
        }
        else{
            hideDialog(result.Message);
        }
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
