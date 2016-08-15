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
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.bean.CustomDetailBean;
import haozuo.com.healthdoctor.bean.DoctorGroupBean;
import haozuo.com.healthdoctor.view.base.AbstractView;
import haozuo.com.healthdoctor.contract.CustomerInfoContract;
import haozuo.com.healthdoctor.view.threePart.common.FlowLayout;
import haozuo.com.healthdoctor.view.threePart.common.DrawableClickableTextView;

/**
 * A simple {@link Fragment} subclass.
 */

public class CustomerInfoFragment extends AbstractView implements CustomerInfoContract.ICustomerInfoView, DrawableClickableTextView.DrawableRightListener {
    Context mContext;
    View rootView;
    CustomerInfoContract.ICustomerInfoPresenter mPresenter;
    String photoUri;
    List<String> mTvNames = new ArrayList<String>();
    List<DoctorGroupBean> mGroups = new ArrayList<DoctorGroupBean>();

    @Bind(R.id.CPhoto)SimpleDraweeView CPhoto;
    @Bind(R.id.Cname)TextView Cname;
    @Bind(R.id.CAge)TextView CAge;
    @Bind(R.id.CHeight)TextView CHeight;
    @Bind(R.id.CPosition)TextView CPosition;
    @Bind(R.id.CMobile)TextView CMobile;
    @Bind(R.id.CCompany)TextView CCompany;
    @Bind(R.id.CConnect)TextView CConnect;
    @Bind(R.id.CConnectPhone)TextView CConnectPhone;
    @Bind(R.id.flow_layout)
    FlowLayout mFlowLayout;

    public CustomerInfoFragment(){
    }

    public static CustomerInfoFragment newInstance() {
        CustomerInfoFragment fragment = new CustomerInfoFragment();
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext=getContext();
        if(rootView==null){
            rootView= inflater.inflate(R.layout.fragment_customer_info, container, false);
            ButterKnife.bind(this,rootView);
        }
        mPresenter.start();
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
        if (customInfo.PhotoUrl == null){
//            photoUri = "http://pic002.cnblogs.com/images/2011/103608/2011062022023456.jpg";
            photoUri = "res://haozuo.com.healthdoctor.view.custom/"+R.drawable.default_photourl;
        }
        else {
            photoUri = customInfo.PhotoUrl;
        }
        Uri uri = Uri.parse(photoUri);
        CPhoto.setImageURI(uri);
        Cname.setText(customInfo.Cname);
        CHeight.setText(customInfo.Gender);
        CMobile.setText(customInfo.Mobile);
        CCompany.setText(customInfo.Company_Name);
        CConnect.setText(customInfo.Contact_Name);
        CConnectPhone.setText(customInfo.Contact_Mobile);
        mPresenter.InitGroupLabel();
    }

    public void addLabelView(final DoctorGroupBean groupBean) {
        String groupName = groupBean.name;

        DrawableClickableTextView tvGroupName = (DrawableClickableTextView) LayoutInflater.from(mContext).inflate(R.layout.fragment_customer_info_label, mFlowLayout, false);
        tvGroupName.setText(groupName);
        tvGroupName.setDrawableRightListener(new DrawableClickableTextView.DrawableRightListener() {
            @Override
            public void onDrawableRightClick(View view) {
                mPresenter.DeleteCustomerGroup(groupBean);
            }
        });

        mFlowLayout.addView(tvGroupName);
    }

    @Override
    public void refreshLabelView(List<DoctorGroupBean> mGroups) {
        mFlowLayout.removeAllViews();
        mPresenter.InitGroupLabel();
    }

}
