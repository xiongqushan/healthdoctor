package haozuo.com.healthdoctor.view.mine;

import android.os.Bundle;

import javax.inject.Inject;

import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.contract.DoctorInfoContract;
import haozuo.com.healthdoctor.ioc.DaggerDoctorInfoComponent;
import haozuo.com.healthdoctor.ioc.DoctorInfoModule;
import haozuo.com.healthdoctor.presenter.DoctorInfoPresenter;
import haozuo.com.healthdoctor.util.ActivityUtils;
import haozuo.com.healthdoctor.view.base.BaseActivity;

/**
 * by zy 2016.08.24
 */
public class DoctorInfoActivity extends BaseActivity {


    @Inject
    DoctorInfoContract.IDoctorInfoView mIDoctorInfoView;
    @Inject
    DoctorInfoPresenter mDoctorInfoPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTranslucentStatus(R.color.main_color_blue);
        setContentView(R.layout.activity_content);
        setCustomerTitle("基本资料");

        DaggerDoctorInfoComponent.builder().appComponent(getAppComponent()).doctorInfoModule(new DoctorInfoModule()).build().inject(this);
        DoctorInfoFragment fragment = (DoctorInfoFragment) getSupportFragmentManager().findFragmentById(R.id.frameContent);
        if (fragment == null) {
            fragment = (DoctorInfoFragment) mIDoctorInfoView;
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.frameContent);
        }
    }
}
