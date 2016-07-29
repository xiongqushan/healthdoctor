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

import butterknife.Bind;
import butterknife.ButterKnife;
import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.bean.GroupCustInfoBean;
import haozuo.com.healthdoctor.view.base.AbstractView;
import haozuo.com.healthdoctor.contract.CustomerInfoContract;

/**
 * A simple {@link Fragment} subclass.
 */
public class CustomerInfoFragment extends AbstractView implements CustomerInfoContract.ICustomerInfoView {
    Context mContext;
    View rootView;
    GroupCustInfoBean mCustomInfo;
    CustomerInfoContract.ICustomerInfoPresenter mPresenter;
    String photoUri;

    @Bind(R.id.CPhoto) SimpleDraweeView CPhoto;
    @Bind(R.id.Cname)TextView Cname;
    @Bind(R.id.CAge)TextView CAge;
    @Bind(R.id.CHeight)TextView CHeight;
    @Bind(R.id.CPosition)TextView CPosition;
    @Bind(R.id.CMobile)TextView CMobile;
    @Bind(R.id.CCompany)TextView CCompany;
    @Bind(R.id.CConnect)TextView CConnect;
    @Bind(R.id.CConnectPhone)TextView CConnectPhone;

    public CustomerInfoFragment(){
    }

    public static CustomerInfoFragment newInstance(GroupCustInfoBean customInfo) {
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
    public void InitView(GroupCustInfoBean customInfo) {
        if (customInfo.PhotoUrl == null){
            photoUri = "http://pic002.cnblogs.com/images/2011/103608/2011062022023456.jpg";
        }
        else {
            photoUri = customInfo.PhotoUrl;
        }
        Uri uri = Uri.parse(photoUri);
        CPhoto.setImageURI(uri);
        Cname.setText(customInfo.Cname);
        CAge.setText(customInfo.Age);
//        CHeight.setText(customInfo.Age);
        CPosition.setText(customInfo.Career);
        CMobile.setText(customInfo.Mobile);
        CCompany.setText(customInfo.CompanyName);
        CConnect.setText(customInfo.Contact_Name);
        CConnectPhone.setText(customInfo.Contact_Mobile);
    }

}
