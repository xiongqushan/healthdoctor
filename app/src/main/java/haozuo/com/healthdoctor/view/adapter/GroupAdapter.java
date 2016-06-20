package haozuo.com.healthdoctor.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.bean.DoctorGroupBean;

/**
 * Created by xiongwei1 on 2016/6/7.
 */
public class GroupAdapter extends BaseAdapter{
    private LayoutInflater myInflater;
    List<DoctorGroupBean>dataSource;
    View.OnClickListener clickListener=null;
    public GroupAdapter(Context context, List<DoctorGroupBean> dataList, View.OnClickListener onClickListener){
        this.myInflater = LayoutInflater.from(context);
        dataSource=dataList;
        clickListener=onClickListener;
    }
    @Override
    public int getCount(){return dataSource.size();}
    @Override
    public Object getItem(int arg0){return null;}
    @Override
    public long getItemId(int arg0){return 0;}
    @Override
    public View getView(int position,View convertView, ViewGroup parent){
        ViewHolder holder = null;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = myInflater.inflate(R.layout.doctor_group_item,parent,false);
            holder.txt_group_item_name=(TextView) convertView.findViewById(R.id.txt_group_item_name);
            holder.txt_group_item_doctorNum=(TextView) convertView.findViewById(R.id.txt_group_item_doctorNum);
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder)convertView.getTag();
        }
        DoctorGroupBean doctorGroupEntity=dataSource.get(position);
        holder.txt_group_item_name.setText(doctorGroupEntity.name);
        holder.txt_group_item_doctorNum.setText(doctorGroupEntity.doctorNum);

        holder.txt_group_item_name.setTag(new Object[]{doctorGroupEntity.id});
        holder.txt_group_item_name.setOnClickListener(clickListener);
        return convertView;
    }

    public class ViewHolder{
        public TextView txt_group_item_name;
        public TextView txt_group_item_doctorNum;
    }
}