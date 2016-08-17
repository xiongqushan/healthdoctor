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
import haozuo.com.healthdoctor.bean.ConsultDoneItemBean;
import haozuo.com.healthdoctor.util.UIHelper;
import haozuo.com.healthdoctor.view.threePart.PullToRefresh.PullToRefreshLayout;


public class ConsultDoneFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_CONSULTDONE";
    Context mContext;
    View rootView;
    ConsultFragment mConsultFragment;
    ListAdapter adapter;
    private int mFlag = 3; // TODO  1当天  2本周 3本月

    @Bind(R.id.consult_pull_to_refresh_layout)
    PullToRefreshLayout ptrLayout;
    @Bind(R.id.consult_detail_List)
    ListView mListView;

    public ConsultDoneFragment() {
    }

    public static ConsultDoneFragment newInstance(int flag) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, flag);
        ConsultDoneFragment fragment = new ConsultDoneFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void refreshConsultDoneList(List<ConsultDoneItemBean> dataList) {
        adapter.refresh(dataList);
    }

    public void refreshFinish(int status) {
        ptrLayout.refreshFinish(status);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFlag = getArguments().getInt(ARG_PAGE, 3);
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
            mConsultFragment.refreshConsultDoneList(mFlag); //首次加载全部咨询内容
        }
        return rootView;
    }

    class ListAdapter extends BaseAdapter {
        private LayoutInflater myInflater;
        List<ConsultDoneItemBean> dataSource;
        private String Cphoto;

        public ListAdapter(Context context) {
            this.myInflater = LayoutInflater.from(context);
            dataSource = new ArrayList<>();
        }

        public void refresh(List<ConsultDoneItemBean> dataList) {
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
            SimpleDraweeView img = UIHelper.getAdapterView(convertView, R.id.drawee_consult_Cphoto);
            Cphoto = "res://haozuo.com.healthdoctor.view.custom/" + R.drawable.default_photourl;
            Uri uri = Uri.parse(Cphoto);
            img.setImageURI(uri);
            //tvTime.setText(DateUtil.converTime(DateUtil.getStringToTimestamp(doctorGroupEntity.CommitOn)));
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
            mConsultFragment.refreshConsultDoneList(mFlag);
        }

        @Override
        public void onLoadMore() {
            mConsultFragment.loadmoreConsultDoneList(mFlag);
        }

    }

}
