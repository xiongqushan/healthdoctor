package haozuo.com.healthdoctor.view.custom;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import haozuo.com.healthdoctor.contract.CustomerInfoContract;
import haozuo.com.healthdoctor.presenter.IBasePresenter;
import haozuo.com.healthdoctor.util.CustomDialog;
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
    String photoUri;
    List<String> mTvNames = new ArrayList<String>();
    List<DoctorGroupBean> mGroups = new ArrayList<DoctorGroupBean>();

    private CustomDetailBean mCustomInfo;
    public static String DELETED_GROUP_ID = "DELETED_GROUP_ID";

    @Bind(R.id.CPhoto)
    SimpleDraweeView CPhoto;
    @Bind(R.id.Cname)
    TextView Cname;
    @Bind(R.id.CSex)
    TextView CSex;
    @Bind(R.id.CuserID)
    TextView CuserID;
    @Bind(R.id.CAge)
    TextView CAge;
    @Bind(R.id.CHeight)
    TextView CHeight;
    @Bind(R.id.CPosition)
    TextView CPosition;
    @Bind(R.id.CMobile)
    TextView CMobile;
    @Bind(R.id.CCompany)
    TextView CCompany;
    @Bind(R.id.CConnect)
    TextView CConnect;
    @Bind(R.id.CConnectPhone)
    TextView CConnectPhone;
    @Bind(R.id.flow_layout)
    FlowLayout mFlowLayout;

    public CustomerInfoFragment() {
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
        mContext = getContext();
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_customer_info, container, false);
            ButterKnife.bind(this, rootView);
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
        mPresenter = presenter;
    }

    @Override
    public void onDrawableRightClick(View view) {
    }

    @Override
    public void InitView(@NonNull CustomDetailBean customInfo) {
        mCustomInfo = customInfo;

        UIHelper.setFrescoURL(CPhoto, customInfo.PhotoUrl,
                "res://haozuo.com.healthdoctor.view.custom/" + R.drawable.user_default_url);
        Cname.setText(customInfo.Cname);
        CSex.setText(customInfo.GetSex());
        CAge.setText(customInfo.GetAge());
        CuserID.setText(customInfo.Certificate_Code);
//        CHeight.setText(customInfo.GetSex());
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
                if (mCustomInfo.GroupIdList.size() == 1) {
//                    Toast.makeText(mContext, "该客户至少需要保留一个分组", Toast.LENGTH_SHORT).show();
                    showConfirmDialog("该客户至少需要保留一个分组！", new CustomDialog.OnDialogListener() {
                        @Override
                        public void OnDialogConfirmListener() {

                        }
                    });
                    return;
                }
                showConfirmDialog("确认将该客户从"+groupBean.name+"中删除吗？", new CustomDialog.OnDialogListener() {
                    @Override
                    public void OnDialogConfirmListener() {
                        mPresenter.DeleteCustomerGroup(groupBean);
                    }
                });
            }
        });

        mFlowLayout.addView(tvGroupName);
    }

    @Override
    public void refreshLabelView(CustomDetailBean customInfo) {
        mCustomInfo = customInfo;
        mFlowLayout.removeAllViews();
        mPresenter.InitGroupLabel();
        sendCustomBroadcast(BROADFILTER_CUSTOM_DELETEGROUP, customInfo);
    }

    public synchronized void sendCustomBroadcast(String activeName, CustomDetailBean customInfo) {
        Intent intent = new Intent();
        intent.setAction(activeName);
        intent.putExtra(DELETED_GROUP_ID, customInfo);
        getActivity().sendBroadcast(intent);
    }
}
