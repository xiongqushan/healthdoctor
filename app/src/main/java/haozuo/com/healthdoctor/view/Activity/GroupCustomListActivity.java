package haozuo.com.healthdoctor.view.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
import haozuo.com.healthdoctor.view.threePart.PullToRefresh.PullToRefreshLayout;

public class GroupCustomListActivity extends BaseActivity implements IGroupCustomListActivity {
    int PAGE_SIZE = 20;
    int currentPageIndex = 1;
    boolean isRefreshing=false;
    boolean isLoadMore=false;

    @Bind(R.id.list_group_customlist)ListView list_group_customlist;
    @Bind(R.id.pull_to_refresh_layout)PullToRefreshLayout pull_to_refresh_layout;

    IGroupCustomListPresenter mIGroupCustomListPresenter;
    List<GroupCustInfoBean> dataList;
    List<GroupCustInfoBean> custInfoList ;
    GroupCustInfoAdapter groupCustInfoAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_custom_list_2);
        ButterKnife.bind(this);

        setCustomerTitle("分组用户列表");
        mIGroupCustomListPresenter=new GroupCustomListPresenterImpl(this);
        custInfoList=new ArrayList<>();

        View.OnClickListener onCustomerItemClickListener =new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Object[]tag=(Object[])view.getTag();
                int customerId = ((GroupCustInfoBean) tag[0]).CustId;
                String Cname = ((GroupCustInfoBean) tag[0]).Cname;
                String NickName = ((GroupCustInfoBean) tag[0]).NickName;
                //String Cphoto = ((GroupCustInfoBean) tag[0]).CustomerPhoto;

                Intent intent=new Intent(GroupCustomListActivity.this,CustomDetailActivity.class);
                intent.putExtra("CustomerId", customerId);
                intent.putExtra("CustomerName", Cname);
                intent.putExtra("CustomerNickname", NickName);
                //intent.putExtra("CustomerPhoto", Cphoto);
                startActivity(intent);
            }
        };

        groupCustInfoAdapter=new GroupCustInfoAdapter(GroupCustomListActivity.this,custInfoList,onCustomerItemClickListener);
        list_group_customlist.setAdapter(groupCustInfoAdapter);

        pull_to_refresh_layout.setOnRefreshListener(new PullListener());

        requestData(false);
    }

    void requestData(boolean isLoadmore){
        isLoadMore=isLoadmore;
        Bundle bundle = getIntent().getExtras();
        int groupId= bundle.getInt("GroupId");
        int serviceDeptId = UserManager.getInstance().getDoctorInfo().ServiceDeptId;

        showDialog();
        mIGroupCustomListPresenter.RequestGroupCustInfoList(serviceDeptId,groupId,"", currentPageIndex, PAGE_SIZE);
    }

    @Override
    protected void onReceiveBroadcast(String filterAction) {

    }

    @Override
    public void handleGroupCustInfoList(GlobalShell<PageBean<GroupCustInfoBean>> result) {
        if(result.LogicSuccess) {
            hideDialog();
            if(!isLoadMore) {
                custInfoList.clear();
            }
            custInfoList.addAll(result.Data.CurrentPageDataList);
            groupCustInfoAdapter.notifyDataSetChanged();
            if(isRefreshing){
                pull_to_refresh_layout.refreshFinish(PullToRefreshLayout.SUCCEED);
            }
        }
        else{
            hideDialog(result.Message);
            if(isRefreshing){
                pull_to_refresh_layout.refreshFinish(PullToRefreshLayout.FAIL);
            }
        }
    }

    class PullListener implements PullToRefreshLayout.OnRefreshListener {

        @Override
        public void onRefresh() {
            isRefreshing=true;
            requestData(false);
        }

        @Override
        public void onLoadMore() {
            currentPageIndex++;
            requestData(true);
        }

    }
}
