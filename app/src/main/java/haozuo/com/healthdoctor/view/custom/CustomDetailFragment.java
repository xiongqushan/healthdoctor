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

import org.w3c.dom.Text;

import butterknife.Bind;
import butterknife.ButterKnife;
import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.bean.CustomBean;
import haozuo.com.healthdoctor.contract.AbsView;
import haozuo.com.healthdoctor.contract.CustomDetailContract;

public class CustomDetailFragment extends AbsView implements CustomDetailContract.ICustomDetailView{
    Context mContext;
    View rootView;
    int mCustomerId;
    CustomDetailContract.ICustomDetailPresenter mCustomDetailPresenter;
    @Bind(R.id.tv_testname) TextView tv_testname;

    public CustomDetailFragment(){
    }

    public static CustomDetailFragment newInstance(int customerId) {
        CustomDetailFragment fragment = new CustomDetailFragment();
        fragment.mCustomerId = customerId;
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        mCustomDetailPresenter.start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext=getContext();
        if(rootView==null){
            rootView= inflater.inflate(R.layout.fragment_custom_detail, container, false);
            ButterKnife.bind(this,rootView);
        }
        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
        mCustomDetailPresenter.cancelRequest(); 
    }

    @Override
    public void setPresenter(CustomDetailContract.ICustomDetailPresenter presenter) {
        mCustomDetailPresenter=presenter;
    }

    @Override
    public void InitView(CustomBean custom) {
        tv_testname.setText(custom.Name);
    }
}
