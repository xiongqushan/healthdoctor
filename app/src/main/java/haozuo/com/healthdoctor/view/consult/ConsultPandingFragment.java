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
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.bean.ConsultDetailBean;
import haozuo.com.healthdoctor.contract.ConsultContract;
import haozuo.com.healthdoctor.view.group.GroupActivity;


public class ConsultPandingFragment extends Fragment{
    Context mContext;
    View rootView;
    ConsultContract.IConsultPresenter mConsultPresenter;
    ConsultListAdapter mConsultListAdapter;
    RadioGroup.OnCheckedChangeListener mOnCheckedChangeListener;

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
        ConsultFragment consultFragment=(ConsultFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.frameContent);
        consultFragment.ConsultPresenter.getConsultList();
        consultFragment.setOnPendingRefreshListener(new ConsultFragment.OnPendingPageListener() {
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

        mOnCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.consult_All:
                        Toast.makeText(mContext,"111",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.consult_Submit:
                        Toast.makeText(mContext,"222",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.consult_Mine:
                        Toast.makeText(mContext,"333",Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };

        consult_detail_List.setAdapter(mConsultListAdapter);
        consult_Tab.setOnCheckedChangeListener(mOnCheckedChangeListener);
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
