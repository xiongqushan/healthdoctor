package haozuo.com.healthdoctor.view.custom;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import javax.inject.Inject;

import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.bean.ReportParamsBean;
import haozuo.com.healthdoctor.contract.CustomerReportContract;
import haozuo.com.healthdoctor.ioc.CustomerReportModule;
import haozuo.com.healthdoctor.ioc.DaggerCustomerReportComponent;
import haozuo.com.healthdoctor.presenter.CustomerReportPresenter;
import haozuo.com.healthdoctor.util.ActivityUtils;
import haozuo.com.healthdoctor.view.base.BaseActivity;


public class CustomerReportActivity extends BaseActivity {
    public static final String REPORTPARAMSBEAN = "REPORTPARAMSBEAN_SERIALIZABLE";
    @Inject
    CustomerReportContract.ICustomerReportView mIReportView;
    @Inject
    CustomerReportPresenter mIReportPresenter;

    @Override
    protected void setCustomerTitle(String title) {
        super.setCustomerTitle(title);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTranslucentStatus(R.color.main_color_blue);
        setContentView(R.layout.activity_customer_report);
        ReportParamsBean bean = (ReportParamsBean) getIntent().getSerializableExtra(REPORTPARAMSBEAN);
        DaggerCustomerReportComponent.builder()
                .appComponent(getAppComponent())
                .customerReportModule(new CustomerReportModule(bean))
                .build()
                .inject(this);

        FragmentManager fragmentManager = getSupportFragmentManager();
        CustomerReportFragment fragment = (CustomerReportFragment) fragmentManager.findFragmentById(R.id.frameContent);
        if (fragment == null) {
            fragment = (CustomerReportFragment) mIReportView;
            ActivityUtils.addFragmentToActivity(fragmentManager, fragment, R.id.frameContent);
        }
    }
}
