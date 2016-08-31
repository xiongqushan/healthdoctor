package haozuo.com.healthdoctor.view.consult;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
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
import haozuo.com.healthdoctor.util.DateUtil;
import haozuo.com.healthdoctor.view.threePart.PullToRefresh.PullToRefreshLayout;


public class ConsultPandingFragment extends Fragment {
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
    ListView consult_detail_List;

    public ConsultPandingFragment() {
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

    public void refreshFinish(int status, boolean isRefresh) {
        if (isRefresh) {
            ptrLayout.refreshFinish(status);
        } else {
            ptrLayout.loadmoreFinish(status);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("ConsultPandingFragment", "onResume");
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
            consult_detail_List.setAdapter(adapter);
            consult_detail_List.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
            if (dataList == null) return;
            if (isRefresh) dataSource.clear();
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
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.lvitem_consultpanding_layout, parent, false);
                holder.Cphoto = (SimpleDraweeView) convertView.findViewById(R.id.drawee_consult_Cphoto);
                holder.Cname = (TextView) convertView.findViewById(R.id.txt_consult_Cname);
                holder.LastConsult = (TextView) convertView.findViewById(R.id.txt_consult_LastConsult);
                holder.ConsultContent = (TextView) convertView.findViewById(R.id.txt_consult_ConsultContent);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            ConsultItemBean doctorGroupEntity = dataSource.get(position);
            if (doctorGroupEntity.PhotoUrl == null) {
                Cphoto = "res://haozuo.com.healthdoctor.view.custom/" + R.drawable.default_photourl;
            } else {
                Cphoto = doctorGroupEntity.PhotoUrl;
            }
            Uri uri = Uri.parse(Cphoto);
            holder.Cphoto.setImageURI(uri);
            holder.Cname.setText(doctorGroupEntity.CustName);
            holder.ConsultContent.setText(doctorGroupEntity.ConsultTitele);
            holder.LastConsult.setText(DateUtil.TimeFormatByWeek(dataSource.get(position).CommitOn, "yyyy-MM-dd HH:mm"));

            holder.Cphoto.setTag(new Object[]{doctorGroupEntity});
            return convertView;
        }

        public class ViewHolder {
            public SimpleDraweeView Cphoto;
            public TextView Cname;
            public TextView LastConsult;
            public TextView ConsultContent;
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
