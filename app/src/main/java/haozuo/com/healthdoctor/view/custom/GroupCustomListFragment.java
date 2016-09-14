package haozuo.com.healthdoctor.view.custom;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.bean.GroupCustInfoBean;
import haozuo.com.healthdoctor.contract.GroupCustomListContract;
import haozuo.com.healthdoctor.presenter.IBasePresenter;
import haozuo.com.healthdoctor.util.UIHelper;
import haozuo.com.healthdoctor.view.base.AbstractView;
import haozuo.com.healthdoctor.view.threePart.PullToRefresh.PullToRefreshLayout;
import haozuo.com.healthdoctor.view.threePart.PullToRefresh.PullableListView;
import haozuo.com.healthdoctor.view.threePart.common.DrawableClickableEditText;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressWarnings("ResourceType")
public class GroupCustomListFragment extends AbstractView implements GroupCustomListContract.IGroupCustomListView {
    Context mContext;
    View rootView;
    GroupCustomListContract.IGroupCustomListPresenter mGroupCustomListPresenter;
    GroupCustInfoAdapter mGroupCustInfoAdapter;
    String photoUri;
    String customNameOrMobile;
    @Bind(R.id.list_group_customlist)
    PullableListView list_group_customlist;
    @Bind(R.id.pull_to_refresh_layout)
    PullToRefreshLayout pull_to_refresh_layout;

    public GroupCustomListFragment() {
    }

    @Override
    protected IBasePresenter getPresenter() {
        return mGroupCustomListPresenter;
    }

    @Override
    protected View getRootView() {
        return rootView;
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
        mContext = getContext();
        customNameOrMobile = "";
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.lv_group_custom, container, false);
            ButterKnife.bind(this, rootView);
        }
        mGroupCustInfoAdapter = new GroupCustInfoAdapter(mContext, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GroupCustInfoAdapter.ViewHolder tag = (GroupCustInfoAdapter.ViewHolder) v.getTag();
                int customerId = (int) (((Object[]) tag.CPhoto.getTag())[0]);
                String accountId = (String) (((Object[]) tag.CPhoto.getTag())[1]);
                Intent intent = new Intent(mContext, CustomDetailActivity.class);
                intent.putExtra(CustomDetailActivity.EXTRA_CUSTOMER_ID, customerId);
                intent.putExtra(CustomDetailActivity.EXTRA_ACCOUNT_ID, accountId);
                mContext.startActivity(intent);
            }
        });
        list_group_customlist.setAdapter(mGroupCustInfoAdapter);
        pull_to_refresh_layout.setOnRefreshListener(new PullListener());

        final DrawableClickableEditText et_TitleBar_search = (DrawableClickableEditText) getActivity().findViewById(R.id.et_TitleBar_search);
        //搜索框按钮监听
        et_TitleBar_search.setDrawableLeftListener(new DrawableClickableEditText.DrawableLeftListener() {
            @Override
            public void onDrawableLeftClick(View view) {
                customNameOrMobile = et_TitleBar_search.getText().toString();
                mGroupCustomListPresenter.refreshCustomList(customNameOrMobile, true);
            }
        });
        //搜索框取消按钮监听
        et_TitleBar_search.setDrawableRightListener(new DrawableClickableEditText.DrawableRightListener() {
            @Override
            public void onDrawableRightClick(View view) {
                et_TitleBar_search.setText("");
                customNameOrMobile = et_TitleBar_search.getText().toString();
                mGroupCustomListPresenter.refreshCustomList(customNameOrMobile, true);
                getActivity().findViewById(R.id.txt_TitleBar_title).setVisibility(View.VISIBLE);
                getActivity().findViewById(R.id.btn_search).setVisibility(View.VISIBLE);
                getActivity().findViewById(R.id.et_TitleBar_search).setVisibility(View.GONE);
            }
        });
        //搜索框回车监听
        et_TitleBar_search.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm.isActive()) {
                        imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                        customNameOrMobile = et_TitleBar_search.getText().toString();
                        mGroupCustomListPresenter.refreshCustomList(customNameOrMobile, true);
                    }
                    return true;
                }
                return false;
            }
        });

        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
        mGroupCustomListPresenter.cancelRequest();
    }

    @Override
    public void setPresenter(GroupCustomListContract.IGroupCustomListPresenter presenter) {
        mGroupCustomListPresenter = presenter;
    }

    @Override
    public void refreshCustomAdapter(List<GroupCustInfoBean> dataList) {
        if (dataList.size() == 0) {
            showRetryLayer(R.id.rLayout, getString(R.string.customList_search_fail));
        } else {
            hideRetryLayer(R.id.rLayout);
        }
        mGroupCustInfoAdapter.refresh(dataList);
        mGroupCustInfoAdapter.notifyDataSetChanged();
    }

    @Override
    public void refreshFinish(int status, boolean isRefresh) {
        if (isRefresh) {

            pull_to_refresh_layout.refreshFinish(status);
        } else {
            pull_to_refresh_layout.loadmoreFinish(status);
        }
        if (status == PullToRefreshLayout.SUCCEED) {
            playSuccessSound();
        }
    }

    class PullListener implements PullToRefreshLayout.OnRefreshListener {

        @Override
        public void onRefresh() {
            mGroupCustomListPresenter.refreshCustomList(customNameOrMobile, false);
        }

        @Override
        public void onLoadMore() {
            mGroupCustomListPresenter.loadmoreCustomList(customNameOrMobile);
        }
    }

    class GroupCustInfoAdapter extends BaseAdapter {
        LayoutInflater myInflater;
        List<GroupCustInfoBean> dataSource;
        View.OnClickListener clickListener = null;

        public GroupCustInfoAdapter(Context context, View.OnClickListener onClickListener) {
            this.myInflater = LayoutInflater.from(context);
            dataSource = new ArrayList<>();
            clickListener = onClickListener;
        }

        public void refresh(List<GroupCustInfoBean> dataList) {
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
                convertView = myInflater.inflate(R.layout.lvitem_group_custinfo, parent, false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
                convertView.setOnClickListener(clickListener);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            GroupCustInfoBean groupCustInfoEntity = dataSource.get(position);

            UIHelper.setFrescoURL(holder.CPhoto, photoUri
                    , "res://haozuo.com.healthdoctor.view.custom/" + R.drawable.user_default_url);
            holder.Cname.setText(groupCustInfoEntity.Cname);
            holder.NickName.setText(groupCustInfoEntity.NickName);
            if (groupCustInfoEntity.Birthday != null) {
                holder.CBirthday.setText(groupCustInfoEntity.Birthday.split("T")[0]);
            }
            holder.Company.setText(groupCustInfoEntity.CompanyName);

            holder.CPhoto.setTag(new Object[]{groupCustInfoEntity.CustId, groupCustInfoEntity.AccountId});

            return convertView;
        }

        public class ViewHolder {
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