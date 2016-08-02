package haozuo.com.healthdoctor.view.group;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.bean.DoctorGroupBean;
import haozuo.com.healthdoctor.contract.AbsView;
import haozuo.com.healthdoctor.contract.GroupContract;
import haozuo.com.healthdoctor.util.DisplayUtil;
import haozuo.com.healthdoctor.view.custom.GroupCustomListActivity;

public class GroupFragment extends AbsView implements GroupContract.IGroupView{
    Context mContext;
    View rootView;
    GroupContract.IGroupPresenter mGroupPresenter;
//    GroupAdapter mGroupAdapter;

//    @Bind(R.id.list_group)GridView list_group;
    @Bind(R.id.firstGroup)LinearLayout firstGroup;
    @Bind(R.id.secondGroup)LinearLayout secondGroup;
    @Bind(R.id.thirdGroup)LinearLayout thirdGroup;
    @Bind(R.id.fourthGroup)LinearLayout fourthGroup;
    @Bind(R.id.fifthGroup)LinearLayout fifthGroup;
    @Bind(R.id.sixthGroup)LinearLayout sixthGroup;

    @Bind(R.id.firstGroupName)TextView firstGroupName;
    @Bind(R.id.secondGroupName)TextView secondGroupName;
    @Bind(R.id.thirdGroupName)TextView thirdGroupName;
    @Bind(R.id.fourthGroupName)TextView fourthGroupName;
    @Bind(R.id.fifthGroupName)TextView fifthGroupName;
    @Bind(R.id.sixthGroupName)TextView sixthGroupName;

    @Bind(R.id.firstGroupCount)TextView firstGroupCount;
    @Bind(R.id.secondGroupCount)TextView secondGroupCount;
    @Bind(R.id.thirdGroupCount)TextView thirdGroupCount;
    @Bind(R.id.fourthGroupCount)TextView fourthGroupCount;
    @Bind(R.id.fifthGroupCount)TextView fifthGroupCount;
    @Bind(R.id.sixthGroupCount)TextView sixthGroupCount;


    public GroupFragment(){

    }

    public static GroupFragment newInstance() {
        GroupFragment fragment = new GroupFragment();
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        mGroupPresenter.start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext=getContext();
        if(rootView==null){
            rootView= inflater.inflate(R.layout.fragment_group, container, false);
            ButterKnife.bind(this,rootView);
        }
//        mGroupAdapter=new GroupAdapter(mContext, new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int groupId=(int)(((Object[])v.getTag())[0]);
//                Intent intent=new Intent(mContext, GroupCustomListActivity.class);
//                intent.putExtra(GroupCustomListActivity.EXTRA_GROUP_ID,groupId);
//                mContext.startActivity(intent);
//            }
//        });
//        list_group.setAdapter(mGroupAdapter);
        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
        mGroupPresenter.cancelRequest();
    }

    @Override
    public void setPresenter(GroupContract.IGroupPresenter presenter) {
        mGroupPresenter=presenter;
    }

//    @Override
//    public void refreshGroupAdapter(List<DoctorGroupBean> doctorGroupBeanList) {
//        mGroupAdapter.refresh(doctorGroupBeanList);
//    }

    @Override
    public void setGroupInfo(List<DoctorGroupBean> doctorGroupBeanList) {

        LinearLayout[] mLayoutList = {firstGroup,secondGroup,thirdGroup,fourthGroup,fifthGroup,sixthGroup};
        TextView[] GroupNameArray = {firstGroupName,secondGroupName,thirdGroupName,fourthGroupName,fifthGroupName,sixthGroupName};
        TextView[] GroupCountArray = {firstGroupCount,secondGroupCount,thirdGroupCount,fourthGroupCount,fifthGroupCount,sixthGroupCount};

        for (int i=0;i<doctorGroupBeanList.size();i++){
            String groupNum = "服务人数"+doctorGroupBeanList.get(i).groupNum;
            String groupName = doctorGroupBeanList.get(i).name;
            final int groupId=doctorGroupBeanList.get(i).id;
//            TextView groupNumTV = new TextView(mContext);
//            TextView groupNameTV = new TextView(mContext);
//            groupNumTV.setText(groupNum);
//            groupNameTV.setText(groupName);
//            groupNumTV.setGravity(Gravity.CENTER);
//            groupNameTV.setGravity(Gravity.CENTER);

            GroupNameArray[i].setText(groupName);
            GroupCountArray[i].setText(groupNum);
            GroupNameArray[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(mContext, GroupCustomListActivity.class);
                    intent.putExtra(GroupCustomListActivity.EXTRA_GROUP_ID,groupId);
                    mContext.startActivity(intent);
                }
            });
            DisplayMetrics dm =getResources().getDisplayMetrics();
            int w_screen = dm.widthPixels;
            int h_screen = dm.heightPixels;
            android.view.ViewGroup.LayoutParams layoutParams = mLayoutList[i].getLayoutParams();
            layoutParams.width=DisplayUtil.px2dip(mContext,w_screen-60);
            mLayoutList[i].setLayoutParams(layoutParams);
        }
    }


//    class GroupAdapter extends BaseAdapter {
//        private LayoutInflater myInflater;
//        List<DoctorGroupBean> dataSource;
//        View.OnClickListener clickListener = null;
//
//        public GroupAdapter(Context context, View.OnClickListener onClickListener) {
//            this.myInflater = LayoutInflater.from(context);
//            dataSource = new ArrayList<>();
//            clickListener = onClickListener;
//        }
//
//        public void refresh(List<DoctorGroupBean> dataList){
//            dataSource=dataList;
//            notifyDataSetChanged();
//        }
//
//        @Override
//        public int getCount() {
//            return dataSource.size();
//        }
//
//        @Override
//        public Object getItem(int arg0) {
//            return null;
//        }
//
//        @Override
//        public long getItemId(int arg0) {
//            return arg0;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            ViewHolder holder = null;
//            if (convertView == null) {
//                holder = new ViewHolder();
//                convertView = myInflater.inflate(R.layout.doctor_group_item, parent, false);
//                holder.txt_group_item_name = (TextView) convertView.findViewById(R.id.txt_group_item_name);
//                holder.txt_group_item_doctorNum = (TextView) convertView.findViewById(R.id.txt_group_item_doctorNum);
//                convertView.setTag(holder);
//            } else {
//                holder = (ViewHolder) convertView.getTag();
//            }
//            DoctorGroupBean doctorGroupEntity = dataSource.get(position);
//            holder.txt_group_item_name.setText(doctorGroupEntity.name);
//            holder.txt_group_item_doctorNum.setText(doctorGroupEntity.doctorNum);
//
//            holder.txt_group_item_name.setTag(new Object[]{doctorGroupEntity.id});
//            holder.txt_group_item_name.setOnClickListener(clickListener);
//            return convertView;
//        }
//
//        public class ViewHolder {
//            public TextView txt_group_item_name;
//            public TextView txt_group_item_doctorNum;
//        }
//    }
}

