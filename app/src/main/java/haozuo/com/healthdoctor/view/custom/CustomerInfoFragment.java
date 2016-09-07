package haozuo.com.healthdoctor.view.custom;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.bean.CustomDetailBean;
import haozuo.com.healthdoctor.bean.DoctorGroupBean;
import haozuo.com.healthdoctor.contract.CustomerInfoContract;
import haozuo.com.healthdoctor.presenter.IBasePresenter;
import haozuo.com.healthdoctor.util.UIHelper;
import haozuo.com.healthdoctor.view.base.AbstractView;
import haozuo.com.healthdoctor.view.threePart.common.DrawableClickableTextView;
import haozuo.com.healthdoctor.view.threePart.common.FlowLayout;

/**
 * A simple {@link Fragment} subclass.
 */

public class CustomerInfoFragment extends AbstractView implements CustomerInfoContract.ICustomerInfoView, DrawableClickableTextView.DrawableRightListener {
    Context mContext;
    View rootView;
    CustomerInfoContract.ICustomerInfoPresenter mPresenter;
    private CustomDetailBean mCustomInfo;
    String photoUri;
    List<String> mTvNames = new ArrayList<String>();
    List<DoctorGroupBean> mGroups = new ArrayList<DoctorGroupBean>();

    @Bind(R.id.CPhoto)SimpleDraweeView CPhoto;
    @Bind(R.id.Cname)TextView Cname;
    @Bind(R.id.CAge)TextView CAge;
    @Bind(R.id.CGender)TextView CGender;
    @Bind(R.id.CPosition)TextView CPosition;
    @Bind(R.id.CMobile)TextView CMobile;
    @Bind(R.id.CCompany)TextView CCompany;
    @Bind(R.id.CConnect)TextView CConnect;
    @Bind(R.id.CConnectPhone)TextView CConnectPhone;
    @Bind(R.id.flow_layout)
    FlowLayout mFlowLayout;

    public CustomerInfoFragment(){
    }

    @Override
    protected IBasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    protected View getRootView() {
        return rootView;
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
    public void onDrawableRightClick(View view) {}

    @Override
    public void InitView(CustomDetailBean customInfo) {
        mCustomInfo = customInfo;
        UIHelper.setFrescoURL(CPhoto,mCustomInfo.PhotoUrl,
                "res://haozuo.com.healthdoctor.view.custom/"+R.drawable.user_default_url);
        Cname.setText(customInfo.Cname);
        CGender.setText(customInfo.GetSex());
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
                if (mCustomInfo.GroupIdList.size() == 1){
                    Toast.makeText(mContext,"该客户至少需要保留一个分组", Toast.LENGTH_SHORT).show();
                    return;
                }
                mPresenter.DeleteCustomerGroup(groupBean);
            }
        });

        mFlowLayout.addView(tvGroupName);
    }

    @Override
    public void refreshLabelView(CustomDetailBean customInfo) {
        mCustomInfo = customInfo;
        mFlowLayout.removeAllViews();
        mPresenter.InitGroupLabel();
    }

}
