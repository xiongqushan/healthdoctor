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
    View.OnClickListener mOnClicklistener;
    private int mFlag;// TODO 3全部 2转入 1我的

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


    public void refreshConsultPendingList(List<ConsultItemBean> dataList) {
        adapter.refresh(dataList);
    }

    public void refreshFinish(int status) {
        ptrLayout.refreshFinish(status);
        ptrLayout.loadmoreFinish(status);
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
            mOnClicklistener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ConsultListAdapter.ViewHolder tag = (ConsultListAdapter.ViewHolder) v.getTag();
                    ConsultItemBean customerItem = (ConsultItemBean) (((Object[]) tag.Cphoto.getTag())[0]);
                    Intent intent = new Intent(mContext, ConsultDetailActivity.class);
                    intent.putExtra(ConsultDetailActivity.EXTRA_CONSULT_ITEM, customerItem);
                    mContext.startActivity(intent);
                }
            };

            rootView = inflater.inflate(R.layout.fragment_consult_panding_list, container, false);
            ButterKnife.bind(this, rootView);
            adapter = new ConsultListAdapter(mOnClicklistener);
            Log.e("adapterObject" + mFlag, adapter.toString());
            consult_detail_List.setAdapter(adapter);
            ptrLayout.setOnRefreshListener(new PullListener());
            mConsultFragment.refreshCustomList(mFlag); //首次加载全部咨询内容
        }
        return rootView;
    }


    class ConsultListAdapter extends BaseAdapter {
        private String Cphoto;
        List<ConsultItemBean> dataSource;

        public ConsultListAdapter(View.OnClickListener onClickListener) {
            mOnClicklistener = onClickListener;
            dataSource = new ArrayList<>();
        }

        public void refresh(List<ConsultItemBean> dataList) {
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
            return arg0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_consult_panding_item, parent, false);
                holder.Cphoto = (SimpleDraweeView) convertView.findViewById(R.id.drawee_consult_Cphoto);
                holder.Cname = (TextView) convertView.findViewById(R.id.txt_consult_Cname);
                holder.LastConsult = (TextView) convertView.findViewById(R.id.txt_consult_LastConsult);
                holder.ConsultContent = (TextView) convertView.findViewById(R.id.txt_consult_ConsultContent);
                convertView.setTag(holder);
                convertView.setOnClickListener(mOnClicklistener);
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
            holder.LastConsult.setText(DateUtil.converTime(DateUtil.getStringToTimestamp(doctorGroupEntity.CommitOn)));

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
            Log.e("onRefresh mFlag", mFlag + "");
            mConsultFragment.refreshCustomList(mFlag);
        }

        @Override
        public void onLoadMore() {
            Log.e("onLoadMore mFlag", mFlag + "");
            mConsultFragment.loadmoreCustomList(mFlag);
        }

    }

}
