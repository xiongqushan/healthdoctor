package haozuo.com.healthdoctor.view.consult;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.bean.ConsultItemBean;
import haozuo.com.healthdoctor.contract.ConsultContract;
import haozuo.com.healthdoctor.util.DateUtil;
import haozuo.com.healthdoctor.view.threePart.PullToRefresh.PullToRefreshLayout;


public class ConsultPandingFragment extends Fragment{
    Context mContext;
    View rootView;
    ConsultFragment mConsultFragment;
    ConsultContract.IConsultPresenter mConsultPresenter;
    ConsultListAdapter mConsultListAdapter;
    RadioGroup.OnCheckedChangeListener mOnCheckedChangeListener;
    View.OnClickListener mOnClicklistener;

    private int mFlag;

    @Bind(R.id.consult_pull_to_refresh_layout)PullToRefreshLayout consult_pull_to_refresh_layout;
    @Bind(R.id.consult_detail_List) ListView consult_detail_List;
    @Bind(R.id.consult_Tab) RadioGroup consult_Tab;

    public ConsultPandingFragment(){};

    public static ConsultPandingFragment newInstance(){
        ConsultPandingFragment fragment = new ConsultPandingFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext=getContext();
        mConsultFragment=(ConsultFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.frameContent);
        mFlag = 3;
        mOnClicklistener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConsultListAdapter.ViewHolder tag=( ConsultListAdapter.ViewHolder)v.getTag();
                ConsultItemBean customerItem = (ConsultItemBean)(((Object[])tag.Cphoto.getTag())[0]);
                Intent intent = new Intent(mContext,ConsultDetailActivity.class);
                intent.putExtra(ConsultDetailActivity.EXTRA_CONSULT_ITEM, customerItem);
                mContext.startActivity(intent);
            }
        };
        mConsultFragment.ConsultPresenter.refreshCustomList(mFlag); //首次加载全部咨询内容
        mConsultFragment.setOnPendingRefreshListener(new ConsultFragment.OnPendingPageListener() {
            @Override
            public void refreshConsultDetailList(List<ConsultItemBean> consultItemBeanList) {
                mConsultListAdapter.refresh(consultItemBeanList);
                mConsultListAdapter.notifyDataSetChanged();
            }

            @Override
            public void refreshFinish(int status) {
                consult_pull_to_refresh_layout.refreshFinish(status);
            }
        });

        mConsultPresenter = mConsultFragment.ConsultPresenter;
        if(rootView==null){
            rootView= inflater.inflate(R.layout.fragment_consult_panding_list, container, false);
            ButterKnife.bind(this,rootView);
        }

        mConsultListAdapter=new ConsultListAdapter(mContext, mOnClicklistener);

        mOnCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.btn_consult_All:
                        mFlag = 3;
                        mConsultFragment.ConsultPresenter.refreshCustomList(mFlag);//全部
                        break;
                    case R.id.btn_consult_Submit:
                        mFlag = 2;
                        mConsultFragment.ConsultPresenter.refreshCustomList(mFlag);//转入
                        break;
                    case R.id.btn_consult_Mine:
                        mFlag = 1;
                        mConsultFragment.ConsultPresenter.refreshCustomList(mFlag);//我的
                        break;
                }
            }
        };

        consult_detail_List.setAdapter(mConsultListAdapter);
        consult_Tab.setOnCheckedChangeListener(mOnCheckedChangeListener);
        consult_pull_to_refresh_layout.setOnRefreshListener(new PullListener());
        return rootView;
    }

    class ConsultListAdapter extends BaseAdapter {
        private LayoutInflater myInflater;
        List<ConsultItemBean> dataSource;
        private String Cphoto;

        public ConsultListAdapter(Context context, View.OnClickListener onClickListener) {
            this.myInflater = LayoutInflater.from(context);
            dataSource = new ArrayList<>();
            mOnClicklistener = onClickListener;
        }

        public void refresh(List<ConsultItemBean> dataList){
            dataSource=dataList;
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
                convertView = myInflater.inflate(R.layout.fragment_consult_panding_item, parent, false);
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
            if (doctorGroupEntity.PhotoUrl == null){
                Cphoto = "res://haozuo.com.healthdoctor.view.custom/"+R.drawable.default_photourl;
            }
            else {
                Cphoto = doctorGroupEntity.PhotoUrl;
            }
            Uri uri = Uri.parse(Cphoto);
            holder.Cphoto.setImageURI(uri);
            holder.Cname.setText(doctorGroupEntity.CustName);
            holder.ConsultContent.setText(doctorGroupEntity.ConsultTitele);
            holder.LastConsult.setText(DateUtil.str2Date(doctorGroupEntity.CommitOn,"yyyy-mm-dd mm:ss").toString());

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
            mConsultPresenter.refreshCustomList(mFlag);
        }

        @Override
        public void onLoadMore() {
            mConsultPresenter.loadmoreCustomList(mFlag);
        }

    }

}
