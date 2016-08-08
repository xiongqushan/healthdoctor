package haozuo.com.healthdoctor.view.consult;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.bean.ConsultReplyBean;
import haozuo.com.healthdoctor.bean.DoctorBean;
import haozuo.com.healthdoctor.bean.GroupCustInfoBean;
import haozuo.com.healthdoctor.contract.ConsultDetailContract;
import haozuo.com.healthdoctor.manager.UserManager;
import haozuo.com.healthdoctor.util.DateUtil;
import haozuo.com.healthdoctor.view.base.AbstractView;
import haozuo.com.healthdoctor.view.threePart.common.FlowLayout;
import haozuo.com.healthdoctor.view.threePart.common.WindowResize;

public class ConsultDetailFragment extends AbstractView implements ConsultDetailContract.IConsultDetailView {
    Context mContext;
    View rootView;
    public ConsultDetailContract.IConsultDetailPresenter mConsultDetailPresenter;
    ConsultListAdapter mConsultListAdapter;
    List<GroupCustInfoBean> dataList;
    private String mURI;
    private static int mCustomerID;
    private static DoctorBean mDoctorEntity;

    @Bind(R.id.consult_detail_List) ListView consult_detail_List;
    public ConsultDetailFragment(){};

    public static ConsultDetailFragment newInstance(int customerID){
        ConsultDetailFragment fragment = new ConsultDetailFragment();
        mCustomerID = customerID;
        mDoctorEntity = UserManager.getInstance().getDoctorInfo();
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
        WindowResize.assistActivity(getActivity());
        mConsultListAdapter=new ConsultListAdapter(mContext);
        consult_detail_List.setAdapter(mConsultListAdapter);
        mConsultDetailPresenter.GetConsultReply(mCustomerID);

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

    @Override
    public void refreshCustomAdapter(List<ConsultReplyBean> dataList) {
        mConsultListAdapter.refresh(dataList);
//        mConsultListAdapter.notifyDataSetChanged();
    }

    class ConsultListAdapter extends BaseAdapter {
        LayoutInflater myInflater;
        List<ConsultReplyBean> dataSource;
        private String mCommitOn = "";
        private static final int TYPE_COSTUMER = 0;
        private static final int TYPE_DOCTOR = 1;
        private static final int TYPE_COUNT = 2;
        private int currentType;

        public ConsultListAdapter(Context context) {
            this.myInflater = LayoutInflater.from(context);
            dataSource =new ArrayList<>();
        }

        public void refresh(List<ConsultReplyBean> dataList){
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
        public int getItemViewType(int position){
            if (dataSource.get(position).IsDoctorReply == 0){
                return TYPE_COSTUMER;
            } else if (dataSource.get(position).IsDoctorReply == 1){
                return TYPE_DOCTOR;
            } else{
                return 100;
            }
        }

        @Override
        public int getViewTypeCount(){
            return TYPE_COUNT;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            currentType = getItemViewType(position);
            if (currentType == TYPE_COSTUMER){
                ViewHolderLeft holder = null;
                if (convertView == null){
                    convertView = myInflater.inflate(R.layout.fragment_consult_detail_item_left, parent, false);
                    holder = new ViewHolderLeft(convertView);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolderLeft) convertView.getTag();
                }
                ConsultReplyBean consultReplyEntity = dataSource.get(position);
                if (consultReplyEntity.PhotoUrl == null){
                    mURI = "res://haozuo.com.healthdoctor/"+R.drawable.default_photourl;
                }
                else {
                    mURI = consultReplyEntity.PhotoUrl;
                }
                Uri uri = Uri.parse(mURI);
                holder.drawee_consult_item_photo.setImageURI(uri);

                switch(consultReplyEntity.ConsultType){
                    case 1://纯文本
                        holder.txt_consult_item.setText(consultReplyEntity.Content);
                        holder.flowLayout_consult_photo.setVisibility(View.GONE);
                        break;
                    case 2://照片病例
                        holder.txt_consult_item.setVisibility(View.GONE);
                        String[] photoList = consultReplyEntity.AppendInfo.split(";");
                        for (String s : photoList) {
                            holder.flowLayout_consult_photo.removeAllViews();
                            SimpleDraweeView consult_photo = (SimpleDraweeView) LayoutInflater.from(mContext).inflate(R.layout.fragment_consult_detail_item_left_photo, holder.flowLayout_consult_photo, false);
                            Uri photoUri = Uri.parse(s);
                            consult_photo.setImageURI(photoUri);
                            holder.flowLayout_consult_photo.addView(consult_photo);
                        }
                        break;
                    case 3://体检异常项
                        holder.txt_consult_item.setText(consultReplyEntity.Content);
                        holder.txt_consult_item.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(mContext,"异常项转跳逻辑", Toast.LENGTH_SHORT).show();
                            }
                        });
                        holder.flowLayout_consult_photo.setVisibility(View.GONE);
                        break;
                    default:
                        break;
                }
                if(mCommitOn.equals( DateUtil.converTime(DateUtil.getStringToTimestamp(consultReplyEntity.CommitOn)))){
                    holder.txt_consult_commiton.setVisibility(View.GONE);
                }else{
                    mCommitOn = DateUtil.converTime(DateUtil.getStringToTimestamp(consultReplyEntity.CommitOn));
                    holder.txt_consult_commiton.setText(mCommitOn);
                }
            }else if (currentType == TYPE_DOCTOR){
                ViewHolderRight holder = null;
                if (convertView == null){
                    convertView = myInflater.inflate(R.layout.fragment_consult_detail_item_right, parent, false);
                    holder = new ViewHolderRight(convertView);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolderRight) convertView.getTag();
                }
                ConsultReplyBean consultReplyEntity = dataSource.get(position);
                Uri uri = Uri.parse(mDoctorEntity.PhotoUrl);
                holder.drawee_consult_item_photo.setImageURI(uri);
                holder.txt_consult_item.setText(consultReplyEntity.Content);
                if(mCommitOn.equals( DateUtil.converTime(DateUtil.getStringToTimestamp(consultReplyEntity.CommitOn)))){
                    holder.txt_consult_commiton.setVisibility(View.GONE);
                }else{
                    mCommitOn = DateUtil.converTime(DateUtil.getStringToTimestamp(consultReplyEntity.CommitOn));
                    holder.txt_consult_commiton.setText(mCommitOn);
                }
            }
            return convertView;
        }

        public class ViewHolderLeft {

            @Bind(R.id.txt_consult_commiton)
            TextView txt_consult_commiton;

            @Bind(R.id.drawee_consult_item_photo)
            SimpleDraweeView drawee_consult_item_photo;

            @Bind(R.id.txt_consult_item)
            TextView txt_consult_item;

            @Bind(R.id.flowLayout_consult_photo)
            FlowLayout flowLayout_consult_photo;

            @OnClick(R.id.flowLayout_consult_photo)
            public void test(View v){};

            public ViewHolderLeft(View convertView) {
                ButterKnife.bind(this, convertView);
            }
        }

        public class ViewHolderRight {

            @Bind(R.id.txt_consult_commiton)
            TextView txt_consult_commiton;

            @Bind(R.id.drawee_consult_item_photo)
            SimpleDraweeView drawee_consult_item_photo;

            @Bind(R.id.txt_consult_item)
            TextView txt_consult_item;

            public ViewHolderRight(View convertView) {
                ButterKnife.bind(this, convertView);
            }
        }

    }

}
