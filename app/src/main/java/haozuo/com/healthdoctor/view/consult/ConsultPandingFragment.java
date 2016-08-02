package haozuo.com.healthdoctor.view.consult;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.bean.ConsultDetailBean;
import haozuo.com.healthdoctor.contract.ConsultContract;


public class ConsultPandingFragment extends Fragment{
    Context mContext;
    View rootView;
    ConsultContract.IConsultPresenter mConsultPresenter;
    ConsultListAdapter mConsultListAdapter;

    @Bind(R.id.consult_detail_List) ListView consult_detail_List;

    public ConsultPandingFragment(){};

    public static ConsultPandingFragment newInstance(){
        ConsultPandingFragment fragment = new ConsultPandingFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext=getContext();
        ConsultFragment consultFragment=(ConsultFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.frameContent);
        consultFragment.attachPendingPageEvent(new ConsultFragment.OnPendingRefreshListener() {
            @Override
            public void refreshConsultDetailList(List<ConsultDetailBean> consultDetailBeanList) {
                mConsultListAdapter.refresh(consultDetailBeanList);
            }
        });

        mConsultPresenter = consultFragment.ConsultPresenter;
        if(rootView==null){
            rootView= inflater.inflate(R.layout.fragment_consult_panding_list, container, false);
            ButterKnife.bind(this,rootView);
        }

        mConsultListAdapter=new ConsultListAdapter(mContext, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int groupId=(int)(((Object[])v.getTag())[0]);
                Intent intent=new Intent(mContext, ConsultPandingFragment.class);
                intent.putExtra("CustomerID",groupId);
                mContext.startActivity(intent);
            }
        });
        consult_detail_List.setAdapter(mConsultListAdapter);

        return rootView;
    }

    class ConsultListAdapter extends BaseAdapter {
        private LayoutInflater myInflater;
        List<ConsultDetailBean> dataSource;
        View.OnClickListener clickListener = null;

        public ConsultListAdapter(Context context, View.OnClickListener onClickListener) {
            this.myInflater = LayoutInflater.from(context);
            dataSource = new ArrayList<>();
            clickListener = onClickListener;
        }

        public void refresh(List<ConsultDetailBean> dataList){
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
                holder.Cphoto = (TextView) convertView.findViewById(R.id.consult_Cphoto);
                holder.Cname = (TextView) convertView.findViewById(R.id.consult_Cname);
                holder.LastConsult = (TextView) convertView.findViewById(R.id.consult_LastConsult);
                holder.ConsultContent = (TextView) convertView.findViewById(R.id.consult_ConsultContent);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            ConsultDetailBean doctorGroupEntity = dataSource.get(position);
            holder.Cname.setText(doctorGroupEntity.Cname);
            holder.Cphoto.setText(doctorGroupEntity.Cphoto);
            holder.ConsultContent.setText(doctorGroupEntity.CommitContent);
            holder.LastConsult.setText(doctorGroupEntity.LastCommit);

            holder.Cphoto.setTag(new Object[]{doctorGroupEntity.ID});
            holder.Cphoto.setOnClickListener(clickListener);
            return convertView;
        }

        public class ViewHolder {
            public TextView Cphoto;
            public TextView Cname;
            public TextView LastConsult;
            public TextView ConsultContent;
        }
    }

}
