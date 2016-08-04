package haozuo.com.healthdoctor.view.consult;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.bean.ConsultItemBean;
import haozuo.com.healthdoctor.bean.GroupCustInfoBean;
import haozuo.com.healthdoctor.contract.ConsultContract;
import haozuo.com.healthdoctor.contract.ConsultDetailContract;
import haozuo.com.healthdoctor.util.CustomDialog;
import haozuo.com.healthdoctor.view.base.AbstractView;
import haozuo.com.healthdoctor.view.custom.PageFragment;

public class ConsultDetailFragment extends AbstractView implements ConsultDetailContract.IConsultDetailView {
    Context mContext;
    View rootView;
    public ConsultDetailContract.IConsultDetailPresenter mConsultDetailPresenter;
    ConsultListAdapter mConsultListAdapter;
    List<GroupCustInfoBean> dataList;
//    private LinearLayout.LayoutParams mEditareaParams;
//
//    @Bind(R.id.message_edittxt) EditText message_edittxt;
//    @Bind(R.id.edit_area) RelativeLayout edit_area;
    @Bind(R.id.consult_detail_List) ListView consult_detail_List;
    public ConsultDetailFragment(){};

    public static ConsultDetailFragment newInstance(){
        ConsultDetailFragment fragment = new ConsultDetailFragment();
        return fragment;
    }

    @Override
    public void onResume(){
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext=getContext();
        if(rootView==null){
            rootView= inflater.inflate(R.layout.fragment_consult_detail, container, false);
            ButterKnife.bind(this,rootView);
        }
        AndroidBug5497Workaround.assistActivity(getActivity());
        mConsultListAdapter=new ConsultListAdapter(mContext);
        consult_detail_List.setAdapter(mConsultListAdapter);

//        mConsultListAdapter.refresh(dataList);

//        mEditareaParams = (LinearLayout.LayoutParams) edit_area.getLayoutParams();
//        message_edittxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus){
//                    mEditareaParams.height = 144;
//                    edit_area.setLayoutParams(mEditareaParams);
//                }else {
//                    mEditareaParams.height = 48;
//                    edit_area.setLayoutParams(mEditareaParams);
//                }
//            }
//        });
        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
        mConsultDetailPresenter.cancelRequest();
    }

    @Override
    public void setPresenter(ConsultDetailContract.IConsultDetailPresenter presenter) {
        mConsultDetailPresenter=presenter;
    }

    class ConsultListAdapter extends BaseAdapter {
        LayoutInflater myInflater;
        List<GroupCustInfoBean> dataSource;

        public ConsultListAdapter(Context context) {
            this.myInflater = LayoutInflater.from(context);
            dataSource =new ArrayList<>();
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
//                if (DoctorApply){}
                convertView = myInflater.inflate(R.layout.fragment_consult_detail_item_left, parent, false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            GroupCustInfoBean groupCustInfoEntity = dataSource.get(position);

            return convertView;
        }

        public class ViewHolder {

            public ViewHolder(View convertView) {
                ButterKnife.bind(this, convertView);
            }
        }
    }

}
