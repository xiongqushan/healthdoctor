package haozuo.com.healthdoctor.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import butterknife.Bind;
import butterknife.ButterKnife;
import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.bean.GroupCustInfoBean;

/**
 * Created by hzguest3 on 2016/6/12.
 */
public class GroupCustInfoAdapter extends BaseAdapter{
    LayoutInflater myInflater;
    List<GroupCustInfoBean> dataList;
    View.OnClickListener clickListener=null;
    public GroupCustInfoAdapter(Context context, List<GroupCustInfoBean> dataSource, View.OnClickListener onClickListener){
        this.myInflater = LayoutInflater.from(context);
        dataList = dataSource;
        clickListener=onClickListener;
    }
    @Override
    public int getCount(){return dataList.size();}
    @Override
    public Object getItem(int arg0){return null;}
    @Override
    public long getItemId(int arg0){return 0;}

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder holder = null;
        if (convertView == null){
            convertView = myInflater.inflate(R.layout.group_custinfo_item,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder)convertView.getTag();
        }

        GroupCustInfoBean groupCustInfoEntity = dataList.get(position);
        holder.Cname.setText(groupCustInfoEntity.Cname);
        holder.Gender.setText(groupCustInfoEntity.Gender);
        holder.NickName.setText(groupCustInfoEntity.NickName);
        holder.Birthday.setText(groupCustInfoEntity.Birthday);
        holder.DoctorName.setText(String.valueOf(groupCustInfoEntity.DoctorId));

        holder.DoctorName.setTag(new Object[]{groupCustInfoEntity});
        holder.DoctorName.setOnClickListener(clickListener);

        return convertView;
    }

    static class ViewHolder{
        @Bind(R.id.txt_Cname)
        public TextView Cname;

        @Bind(R.id.txt_Gender)
        public TextView Gender;

        @Bind(R.id.txt_NickName)
        public TextView NickName;

        @Bind(R.id.txt_Birthday)
        public TextView Birthday;

        @Bind(R.id.txt_DoctorName)
        public TextView DoctorName;

        public ViewHolder(View convertView){
            ButterKnife.bind(this, convertView);
        }
    }
}
