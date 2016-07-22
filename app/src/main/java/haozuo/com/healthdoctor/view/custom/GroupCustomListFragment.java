package haozuo.com.healthdoctor.view.custom;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.bean.GroupCustInfoBean;
import haozuo.com.healthdoctor.contract.AbsView;
import haozuo.com.healthdoctor.contract.GroupCustomListContract;
import haozuo.com.healthdoctor.view.threePart.PullToRefresh.PullToRefreshLayout;
import haozuo.com.healthdoctor.view.threePart.PullToRefresh.PullableListView;

/**
 * A simple {@link Fragment} subclass.
 */
public class GroupCustomListFragment extends AbsView implements GroupCustomListContract.IGroupCustomListView {
    Context mContext;
    View rootView;
    GroupCustomListContract.IGroupCustomListPresenter mGroupCustomListPresenter;
    GroupCustInfoAdapter mGroupCustInfoAdapter;
    @Bind(R.id.list_group_customlist)PullableListView list_group_customlist;
    @Bind(R.id.pull_to_refresh_layout)PullToRefreshLayout pull_to_refresh_layout;

    public GroupCustomListFragment(){

    }

    public static GroupCustomListFragment newInstance() {
        GroupCustomListFragment fragment = new GroupCustomListFragment();
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        mGroupCustomListPresenter.start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext=getContext();
        if(rootView==null){
            rootView= inflater.inflate(R.layout.fragment_group_custom_list, container, false);
            ButterKnife.bind(this,rootView);
        }

        mGroupCustInfoAdapter=new GroupCustInfoAdapter(mContext,new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Object[]tag=(Object[])v.getTag();
                int customerId = ((GroupCustInfoBean) tag[0]).CustId;
                Intent intent = new Intent(getActivity(),haozuo.com.healthdoctor.view.custom.CustomDetailActivity.class);
                intent.putExtra("CustomerId", customerId);
                startActivity(intent);
            }
        });
        list_group_customlist.setAdapter(mGroupCustInfoAdapter);
        pull_to_refresh_layout.setOnRefreshListener(new PullListener());
        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
        mGroupCustomListPresenter.cancelRequest();
    }

    @Override
    public void setPresenter(GroupCustomListContract.IGroupCustomListPresenter presenter) {
        mGroupCustomListPresenter=presenter;
    }

    @Override
    public void refreshCustomAdapter(List<GroupCustInfoBean> dataList) {
        mGroupCustInfoAdapter.refresh(dataList);
        mGroupCustInfoAdapter.notifyDataSetChanged();
    }

    @Override
    public void refreshFinish(int status) {
        pull_to_refresh_layout.refreshFinish(status);
    }

    class PullListener implements PullToRefreshLayout.OnRefreshListener {

        @Override
        public void onRefresh() {
            mGroupCustomListPresenter.refreshCustomList();
        }

        @Override
        public void onLoadMore() {
            mGroupCustomListPresenter.loadmoreCustomList();
        }

    }

    class GroupCustInfoAdapter extends BaseAdapter {
        LayoutInflater myInflater;
        List<GroupCustInfoBean> dataSource;
        View.OnClickListener clickListener = null;

        public GroupCustInfoAdapter(Context context,View.OnClickListener onClickListener) {
            this.myInflater = LayoutInflater.from(context);
            dataSource =new ArrayList<>();
            clickListener = onClickListener;
        }

        public void refresh(List<GroupCustInfoBean> dataList){
            dataSource.clear();
            dataSource.addAll(dataList);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return dataSource.size();
        }

        @Override
        public Object getItem(int arg0) {
            return null;
        }

        @Override
        public long getItemId(int arg0) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = myInflater.inflate(R.layout.group_custinfo_item, parent, false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            GroupCustInfoBean groupCustInfoEntity = dataSource.get(position);
            holder.Cname.setText(groupCustInfoEntity.Cname);
            holder.Gender.setText(groupCustInfoEntity.Gender);
            holder.NickName.setText(groupCustInfoEntity.NickName);
            holder.Birthday.setText(groupCustInfoEntity.Birthday);
            holder.DoctorName.setText(String.valueOf(groupCustInfoEntity.DoctorId));

            holder.DoctorName.setTag(new Object[]{groupCustInfoEntity.CustId});
            holder.DoctorName.setOnClickListener(clickListener);

            return convertView;
        }

        class ViewHolder {
            @Bind(R.id.txt_Cname)
            public TextView Cname;

            @Bind(R.id.txt_Gender)
            public TextView Gender;

            @Bind(R.id.txt_NickName)
            public TextView NickName;

            @Bind(R.id.txt_Birthday)
            public TextView Birthday;

            @Bind(R.id.txt_DoctorName)
            public TextView DoctorName;

            public ViewHolder(View convertView) {
                ButterKnife.bind(this, convertView);
            }
        }
    }
}
