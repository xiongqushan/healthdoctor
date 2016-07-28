package haozuo.com.healthdoctor.view.custom;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.bean.CustomDetailBean;
import haozuo.com.healthdoctor.bean.DoctorGroupBean;
import haozuo.com.healthdoctor.bean.GroupCustInfoBean;
import haozuo.com.healthdoctor.contract.AbsView;
import haozuo.com.healthdoctor.contract.CustomerInfoContract;
import haozuo.com.healthdoctor.manager.UserManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class CustomerInfoFragment extends AbsView implements CustomerInfoContract.ICustomerInfoView, TextViewExtend.DrawableRightListener {
    Context mContext;
    View rootView;
    CustomDetailBean mCustomInfo;
    CustomerInfoContract.ICustomerInfoPresenter mPresenter;
    String photoUri;
    List<String> mTvNames = new ArrayList<String>();
    HashMap<String,Object> mGroups = new HashMap<String,Object>();

    @Bind(R.id.CPhoto) SimpleDraweeView CPhoto;
    @Bind(R.id.Cname)TextView Cname;
    @Bind(R.id.CAge)TextView CAge;
    @Bind(R.id.CHeight)TextView CHeight;
    @Bind(R.id.CPosition)TextView CPosition;
    @Bind(R.id.CMobile)TextView CMobile;
    @Bind(R.id.CCompany)TextView CCompany;
    @Bind(R.id.CConnect)TextView CConnect;
    @Bind(R.id.CConnectPhone)TextView CConnectPhone;
    @Bind(R.id.flow_layout)
    CustomerInfoFlowLayout mCustomerInfoFlowLayout;

    public CustomerInfoFragment(){
    }

    public static CustomerInfoFragment newInstance(CustomDetailBean customInfo) {
        CustomerInfoFragment fragment = new CustomerInfoFragment();
        fragment.mCustomInfo = customInfo;
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext=getContext();
        if(rootView==null){
            rootView= inflater.inflate(R.layout.fragment_customer_info, container, false);
            ButterKnife.bind(this,rootView);
        }

        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
        mPresenter.cancelRequest();
    }

    @Override
    public void setPresenter(CustomerInfoContract.ICustomerInfoPresenter presenter) {
        mPresenter=presenter;
    }

    @Override
    public void onDrawableRightClick(View view) {
    }

    @Override
    public void InitView(CustomDetailBean customInfo) {
        mCustomInfo = customInfo;
        if (customInfo.PhotoUrl == null){
            photoUri = "http://pic002.cnblogs.com/images/2011/103608/2011062022023456.jpg";
        }
        else {
            photoUri = customInfo.PhotoUrl;
        }
        Uri uri = Uri.parse(photoUri);
        CPhoto.setImageURI(uri);
        Cname.setText(customInfo.Cname);
//        CAge.setText(customInfo.Age);
        CHeight.setText(customInfo.Gender);
//        CPosition.setText(customInfo.Career);
        CMobile.setText(customInfo.Mobile);
        CCompany.setText(customInfo.Company_Name);
        CConnect.setText(customInfo.Contact_Name);
        CConnectPhone.setText(customInfo.Contact_Mobile);
        InitGroupLabel();
    }

    public void InitGroupLabel() {
        //遍历标签名称数组
        List<DoctorGroupBean> GroupInfo =(List<DoctorGroupBean>) UserManager.getInstance().getGroupInfo();
        mGroups.clear();
        for (int s: mCustomInfo.GroupIdList){
            for (int i=0; i<GroupInfo.size();i++){
                if (s == GroupInfo.get(i).id){
                    mGroups.put(GroupInfo.get(i).name, GroupInfo.get(i).id);
                }
            }
        }
        for (Map.Entry entry : mGroups.entrySet()) {
            String groupName = entry.getKey().toString();
            int groupId = Integer.parseInt(entry.getValue().toString());
            addLabelView(groupName,groupId);
        }

    }

    public void addLabelView(String groupName, final int groupId) {
//        加载TextView并设置名称，并设置名称
        TextViewExtend tvGroupName = (TextViewExtend) LayoutInflater.from(mContext).inflate(R.layout.fragment_customer_info_label, mCustomerInfoFlowLayout, false);
        tvGroupName.setText(groupName);
        tvGroupName.setDrawableRightListener(new TextViewExtend.DrawableRightListener() {
            @Override
            public void onDrawableRightClick(View view) {
                mPresenter.DeleteCustomerGroup(groupId);
            }
        });

//        把TextView加入流式布局
        mCustomerInfoFlowLayout.addView(tvGroupName);
    }

}
