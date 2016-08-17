package haozuo.com.healthdoctor.view.consult;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.bean.FeedbackItemBean;
import haozuo.com.healthdoctor.util.DateUtil;
import haozuo.com.healthdoctor.util.UIHelper;
import haozuo.com.healthdoctor.view.threePart.PullToRefresh.PullToRefreshLayout;


public class FeedbackFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_CONSULTDONE";
    Context mContext;
    View rootView;
    ConsultFragment mConsultFragment;
    ListAdapter adapter;
    private int mFlag = 1; // TODO 1全部 2已反馈 3未反馈
    @Bind(R.id.consult_pull_to_refresh_layout)
    PullToRefreshLayout ptrLayout;
    @Bind(R.id.consult_detail_List)
    ListView mListView;

    public FeedbackFragment() {
    }

    public static FeedbackFragment newInstance(int flag) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, flag);
        FeedbackFragment fragment = new FeedbackFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void refreshFeedbackList(List<FeedbackItemBean> dataList) {
        adapter.refresh(dataList);
    }

    public void refreshFinish(int status) {
        ptrLayout.refreshFinish(status);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFlag = getArguments().getInt(ARG_PAGE, 1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            mContext = getActivity();
            mConsultFragment = (ConsultFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.content_consult);
            rootView = inflater.inflate(R.layout.fragment_consult_panding_list, container, false);
            ButterKnife.bind(this, rootView);
            adapter = new ListAdapter(mContext);
            mListView.setAdapter(adapter);
            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                    ConsultListAdapter.ViewHolder tag = (ConsultListAdapter.ViewHolder) v.getTag();
//                    ConsultItemBean customerItem = (ConsultItemBean) (((Object[]) tag.Cphoto.getTag())[0]);
//                    Intent intent = new Intent(mContext, ConsultDetailActivity.class);
//                    intent.putExtra(ConsultDetailActivity.EXTRA_CONSULT_ITEM, customerItem);
//                    mContext.startActivity(intent);
                    Toast.makeText(mContext, "test", Toast.LENGTH_SHORT).show();
                }
            });
            ptrLayout.setOnRefreshListener(new OnPTRListener());
            mConsultFragment.refreshFeedBackList(mFlag); //首次加载全部咨询内容
        }
        return rootView;
    }

    class ListAdapter extends BaseAdapter {
        private LayoutInflater myInflater;
        List<FeedbackItemBean> dataSource;
        private String Cphoto;

        public ListAdapter(Context context) {
            this.myInflater = LayoutInflater.from(context);
            dataSource = new ArrayList<>();
        }

        public void refresh(List<FeedbackItemBean> dataList) {
            dataSource.clear();
            dataSource.addAll(dataList);
            notifyDataSetChanged();
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = myInflater.inflate(R.layout.lvitem_consultdone_layout, null);
            }
            TextView tvTile = UIHelper.getAdapterView(convertView, R.id.tvTitle);
            TextView tvTime = UIHelper.getAdapterView(convertView, R.id.tvTime);
            TextView tvName = UIHelper.getAdapterView(convertView, R.id.tvName);
            tvTile.setText(dataSource.get(position).CustName);
            tvName.setText(dataSource.get(position).ReDoctor);
            tvName.setVisibility(View.VISIBLE);
            SimpleDraweeView img = UIHelper.getAdapterView(convertView, R.id.drawee_consult_Cphoto);
            Cphoto = dataSource.get(position).PhotoUrl;
            if (Cphoto == null || Cphoto.equals("")) {
                Cphoto = "res://haozuo.com.healthdoctor.view.custom/" + R.drawable.default_photourl;
            }
            tvTime.setText(DateUtil.converTime(DateUtil.getStringToTimestamp(dataSource.get(position).CommitOn)));
            Uri uri = Uri.parse(Cphoto);
            img.setImageURI(uri);
            return convertView;
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
        public int getCount() {
            return dataSource.size();
        }

    }

    class OnPTRListener implements PullToRefreshLayout.OnRefreshListener {

        @Override
        public void onRefresh() {
            mConsultFragment.refreshFeedBackList(mFlag);
        }

        @Override
        public void onLoadMore() {
            mConsultFragment.loadmoreFeedBackList(mFlag);
        }

    }

}
