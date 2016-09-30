package haozuo.com.healthdoctor.view.consult;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.bean.ConsultItemBean;
import haozuo.com.healthdoctor.presenter.IBasePresenter;
import haozuo.com.healthdoctor.util.DateUtil;
import haozuo.com.healthdoctor.util.UIHelper;
import haozuo.com.healthdoctor.view.base.AbstractView;
import haozuo.com.healthdoctor.view.threePart.PullToRefresh.PullToRefreshLayout;


public class ConsultPandingFragment extends AbstractView {
    public static final String ARG_PAGE = "ARG_PAGE_CONSULTPANDINGFRAGMENT";
    Context mContext;
    View rootView;
    private ConsultFragment mConsultFragment;
    private ConsultListAdapter adapter;
    private int mFlag;// TODO 3全部 2转入 1我的
    private int pagerIndex = 1;
    private boolean initData = true;
    @Bind(R.id.consult_pull_to_refresh_layout)
    PullToRefreshLayout ptrLayout;
    @Bind(R.id.consult_detail_List)
    ListView mListView;

    public ConsultPandingFragment() {
    }

    @Override
    protected IBasePresenter getPresenter() {
        return null;
    }

    @Override
    protected View getRootView() {
        return null;
    }

//    public ConsultPandingFragment(int tab) {
//        this.mFlag = tab;
//    }

    //    public static ConsultPandingFragment newInstance() {
//        ConsultPandingFragment fragment = new ConsultPandingFragment();
//        return fragment;
//    }
//
    public static ConsultPandingFragment newInstance(int flag) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, flag);
        ConsultPandingFragment fragment = new ConsultPandingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void refreshConsultPendingList(List<ConsultItemBean> dataList, boolean isRefresh) {
        pagerIndex++;
        adapter.refresh(dataList, isRefresh);
    }

    //删除上一条已经回复的内容
    public void removeConsultPendingList(int Id) {
        for (ConsultItemBean customBean :dataSource){
            if (customBean.CustId == Id){
                dataSource.remove(customBean);
                adapter.notifyDataSetChanged();
                return;
            }
        }
    }

    public void refreshFinish(int status, boolean isRefresh) {
        if (isRefresh) {
            ptrLayout.refreshFinish(status);
            if (status == PullToRefreshLayout.SUCCEED) {
                playSuccessSound();
            }
        } else {
            ptrLayout.loadmoreFinish(status);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFlag = getArguments().getInt(ARG_PAGE, 3);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            mContext = getContext();
            mConsultFragment = (ConsultFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.content_consult);
            rootView = inflater.inflate(R.layout.fragment_consult_panding_list, container, false);
            ButterKnife.bind(this, rootView);
            adapter = new ConsultListAdapter();
            mListView.setAdapter(adapter);
            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    Intent intent = new Intent(mContext, ConsultDetailActivity.class);
                    intent.putExtra(ConsultDetailActivity.EXTRA_CONSULT_ITEM, dataSource.get(position).CustId);
                    mContext.startActivity(intent);
                }
            });
            ptrLayout.setOnRefreshListener(new PullListener());
            mConsultFragment.refreshCustomList(mFlag, initData); //首次加载全部咨询内容
            initData = false;
        }
        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    private List<ConsultItemBean> dataSource;

    class ConsultListAdapter extends BaseAdapter {
        private String Cphoto;

        public ConsultListAdapter() {
            dataSource = new ArrayList<>();
        }

        public void refresh(List<ConsultItemBean> dataList, boolean isRefresh) {
            if (dataList == null) {
                dataSource.clear();
                notifyDataSetChanged();
                return;
            }
            if (isRefresh) {
                dataSource.clear();
            }
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
            return arg0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.lvitem_consultpending_layout, null);
            }
            SimpleDraweeView img = UIHelper.getAdapterView(convertView, R.id.drawee_consult_Cphoto);
            TextView tvTitle = UIHelper.getAdapterView(convertView, R.id.tvTitle);
            TextView tvTime = UIHelper.getAdapterView(convertView, R.id.tvTime);
            TextView tvSubtitle = UIHelper.getAdapterView(convertView, R.id.tvSubtitle);
            ConsultItemBean doctorGroupEntity = dataSource.get(position);
            UIHelper.setFrescoURL(img, Cphoto
                    , "res://haozuo.com.healthdoctor.view.custom/" + R.drawable.user_default_url);
            tvTitle.setText(doctorGroupEntity.CustName);
            tvSubtitle.setText(doctorGroupEntity.ConsultTitele);
            tvTime.setText(DateUtil.TimeFormatByWeek(dataSource.get(position).CommitOn, "yyyy-MM-dd HH:mm"));
            return convertView;
        }
    }

    class PullListener implements PullToRefreshLayout.OnRefreshListener {

        @Override
        public void onRefresh() {
            pagerIndex = 1;
            mConsultFragment.refreshCustomList(mFlag, initData);
        }

        @Override
        public void onLoadMore() {
            mConsultFragment.loadmoreCustomList(mFlag, pagerIndex);
        }

    }

}
