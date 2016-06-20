package haozuo.com.healthdoctor.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.bean.GroupCustInfoBean;

/**
 * Created by hzguest3 on 2016/6/12.
 */
public class GroupCustInfoAdapter extends BaseAdapter{
    LayoutInflater myInflater;
    List<GroupCustInfoBean> dataList;
    public GroupCustInfoAdapter(Context context, List<GroupCustInfoBean> dataSource){
        this.myInflater = LayoutInflater.from(context);
        dataList = dataSource;
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
            holder = new ViewHolder();
            convertView = myInflater.inflate(R.layout.group_custinfo_item,parent,false);
            holder.Cname = (TextView)convertView.findViewById(R.id.Cname);
            holder.Gender=(TextView) convertView.findViewById(R.id.Gender);
            holder.NickName=(TextView) convertView.findViewById(R.id.NickName);
            holder.Age=(TextView) convertView.findViewById(R.id.Age);
            holder.Birthday=(TextView) convertView.findViewById(R.id.Birthday);
            holder.CompanyName=(TextView) convertView.findViewById(R.id.CompanyName);
            holder.DoctorName=(TextView) convertView.findViewById(R.id.DoctorName);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder)convertView.getTag();
        }

        GroupCustInfoBean groupCustInfoEntity = dataList.get(position);
        holder.Cname.setText(groupCustInfoEntity.Cname);
        holder.Gender.setText(groupCustInfoEntity.Gender);
        holder.NickName.setText(groupCustInfoEntity.NickName);
        holder.Age.setText(groupCustInfoEntity.Age);
        holder.Birthday.setText(groupCustInfoEntity.Birthday);
        holder.CompanyName.setText(groupCustInfoEntity.CompanyName);
//        holder.DoctorName.setText(groupCustInfoEntity.DoctorName);

        return convertView;
    }

    public class ViewHolder{
        public TextView Cname;
        public TextView Gender;
        public TextView NickName;
        public TextView Age;
        public TextView Birthday;
        public TextView CompanyName;
        public TextView DoctorName;
    }
}
