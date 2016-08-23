package haozuo.com.healthdoctor.view.custom;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.bean.CustomDetailBean;
import haozuo.com.healthdoctor.contract.CustomDetailContract;
import haozuo.com.healthdoctor.contract.IBasePresenter;
import haozuo.com.healthdoctor.view.base.AbstractView;

public class CustomDetailFragment extends AbstractView implements CustomDetailContract.ICustomDetailView{
    Context mContext;
    View rootView;
    String Cphoto;
    CustomDetailContract.ICustomDetailPresenter mCustomDetailPresenter;
    @Bind(R.id.drawee_CPhoto) SimpleDraweeView CPhoto;
    @Bind(R.id.tv_CName) TextView CName;
    @Bind(R.id.tv_CGender) TextView CGender;
    @Bind(R.id.tv_CAge) TextView CAge;
    @Bind(R.id.tv_Cphone) TextView Cphone;
    @Bind(R.id.btn_go_into) ImageView btn_go_into;
    @Bind(R.id.lv_custom_report) ListView lv_custom_report;
    @Bind(R.id.gv_PhotoReport) GridView gv_PhotoReport;

    @OnClick(R.id.btn_show_Report)
    public void showReport(){
        if (lv_custom_report.getVisibility() == View.GONE){
            lv_custom_report.setVisibility(View.VISIBLE);
        }else {
            lv_custom_report.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.btn_show_PhotoReport)
    public void showPhotoReport(){
        if (gv_PhotoReport.getVisibility() == View.GONE){
            gv_PhotoReport.setVisibility(View.VISIBLE);
        }else {
            gv_PhotoReport.setVisibility(View.GONE);
        }
    }

    public CustomDetailFragment(){
    }

    @Override
    protected IBasePresenter getPresenter() {
        return mCustomDetailPresenter;
    }

    @Override
    protected View getRootView() {
        return rootView;
    }

    public static CustomDetailFragment newInstance() {
        CustomDetailFragment fragment = new CustomDetailFragment();
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
    public void changeRetryLayer(boolean isShow) {
        if (!isShow) {
            showRetryLayer(R.id.rLayout);
        } else {
            hideRetryLayer(R.id.rLayout);
        }
    }

    @Override
    public void InitView(CustomDetailBean custom) {
        final CustomDetailBean customInfo = custom;
        if (custom.PhotoUrl == null){
            Cphoto = "res://haozuo.com.healthdoctor.view.custom/"+R.drawable.default_photourl;
        }
        else {
            Cphoto = custom.PhotoUrl;
        }
        Uri uri = Uri.parse(Cphoto);
        CPhoto.setImageURI(uri);
        CName.setText(custom.Cname);
        CGender.setText(custom.Sex);
        CAge.setText(String.valueOf(custom.Age));
        Cphone.setText(custom.Mobile);
        btn_go_into.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CustomerInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(CustomerInfoActivity.EXTRA_CUSTOMER_INFO, customInfo);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }


}


