package haozuo.com.healthdoctor.view.mine;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.bean.DoctorBean;
import haozuo.com.healthdoctor.bean.ReportParamsBean;
import haozuo.com.healthdoctor.manager.UserManager;
import haozuo.com.healthdoctor.view.base.BaseFragment;
import haozuo.com.healthdoctor.view.custom.CustomerReportActivity;
import haozuo.com.healthdoctor.view.home.HomeActivity;

/**
 * by zy  2016.08.15.
 */
public class MineFragment extends BaseFragment {
    @Bind(R.id.img_doctorphoto)
    SimpleDraweeView imgPhoto;
    @Bind(R.id.tv_doctorname)
    TextView tvName;
    private View rootView;

    @OnClick(R.id.layout_doctor)
    void clickLayoutoctor() {
        getActivity().startActivity(new Intent(getActivity(), DoctorInfoActivity.class));
    }

    @OnClick(R.id.layout_basedata)
    void clickLayoutBaseData() {
        Intent intent = new Intent(getActivity(), CustomerReportActivity.class);
        ReportParamsBean bean = new ReportParamsBean();
        bean.customerId = 138;
        bean.CheckUnitCode = "bjbr002";
        bean.WorkNo = "0000000081";
        intent.putExtra(CustomerReportActivity.REPORTPARAMSBEAN, bean);
        getActivity().startActivity(intent);
    }

    @OnClick(R.id.layout_set)
    void clickLayoutSet() {
        getActivity().startActivity(new Intent(getActivity(), SettingsActivity.class));
    }

    public MineFragment() {
    }

    public static MineFragment newInstance() {
        MineFragment fragment = new MineFragment();
        return fragment;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_mine, container, false);
            ButterKnife.bind(this, rootView);
            initView();
            registerCustomReceiver(HomeActivity.FINISHACTIVITY);
        }
        return rootView;
    }

    private void initView() {
        DoctorBean doctorInfo = UserManager.getInstance().getDoctorInfo();
        tvName.setText(doctorInfo.Name);
        String imgUrl = doctorInfo.PhotoUrl;
        if (imgUrl == null || imgUrl.equals("")) {
            imgUrl = "res://haozuo.com.healthdoctor.view.custom/" + R.drawable.default_photourl;
        }
        Uri uri = Uri.parse(imgUrl);
        imgPhoto.setImageURI(uri);
    }
    @Override
    protected void onReceiveBroadcast(String filterAction) {
        super.onReceiveBroadcast(filterAction);
        if (filterAction.equals(HomeActivity.FINISHACTIVITY)){
            getActivity().finish();
        }
    }

}

