package haozuo.com.healthdoctor.view.custom;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

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
    String photoUri;
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
                int customerId = (int)(((Object[])v.getTag())[0]);
                Intent intent = new Intent(mContext,CustomDetailActivity.class);
                intent.putExtra("CustomerId", customerId);
                mContext.startActivity(intent);
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
            if (groupCustInfoEntity.PhotoUrl == null){
//                photoUri = "http://pic002.cnblogs.com/images/2011/103608/2011062022023456.jpg";
                photoUri = "res://haozuo.com.healthdoctor.view.custom/"+R.drawable.default_photourl;
            }
            else {
                photoUri = groupCustInfoEntity.PhotoUrl;
            }
            Uri uri = Uri.parse(photoUri);
            holder.CPhoto.setImageURI(uri);
            holder.Cname.setText(groupCustInfoEntity.Cname);
            holder.NickName.setText(groupCustInfoEntity.NickName);
            holder.CBirthday.setText(groupCustInfoEntity.Birthday);
            holder.Company.setText(groupCustInfoEntity.CompanyName);

            holder.CPhoto.setTag(new Object[]{groupCustInfoEntity.CustId});
            holder.CPhoto.setOnClickListener(clickListener);

            return convertView;
        }

        class ViewHolder {
            @Bind(R.id.CPhoto)
            public SimpleDraweeView CPhoto;

            @Bind(R.id.txt_Cname)
            public TextView Cname;

            @Bind(R.id.txt_NickName)
            public TextView NickName;

            @Bind(R.id.Company)
            public TextView Company;

            @Bind(R.id.CBirthday)
            public TextView CBirthday;

            public ViewHolder(View convertView) {
                ButterKnife.bind(this, convertView);
            }
        }
    }
}
