package haozuo.com.healthdoctor.view.custom;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.bean.CustomBean;
import haozuo.com.healthdoctor.bean.GroupCustInfoBean;
import haozuo.com.healthdoctor.contract.AbsView;
import haozuo.com.healthdoctor.contract.CustomerInfoContract;

/**
 * A simple {@link Fragment} subclass.
 */
public class CustomerInfoFragment extends AbsView implements CustomerInfoContract.ICustomerInfoView {
    Context mContext;
    View rootView;
    int mCustomerId;
    CustomerInfoContract.ICustomerInfoPresenter mPresenter;


    public CustomerInfoFragment(){
    }

    public static CustomerInfoFragment newInstance(int customerId) {
        CustomerInfoFragment fragment = new CustomerInfoFragment();
        fragment.mCustomerId = customerId;
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
    public void InitView(GroupCustInfoBean custom) {

    }

}
