package haozuo.com.healthdoctor.view.custom;


import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import javax.inject.Inject;

import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.contract.CustomDetailContract;
import haozuo.com.healthdoctor.ioc.CustomDetailModule;
import haozuo.com.healthdoctor.ioc.DaggerCustomDetailComponent;
import haozuo.com.healthdoctor.util.ActivityUtils;
import haozuo.com.healthdoctor.view.base.BaseActivity;


public class CustomDetailActivity extends BaseActivity {
    @Inject
    CustomDetailContract.ICustomDetailView mICustomDetailView;

    public static String EXTRA_CUSTOMER_ID = "Customer_ID";
    public static String EXTRA_ACCOUNT_ID = "Account_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTranslucentStatus(R.color.main_color_blue);
        setContentView(R.layout.activity_custom_detail);

        int customerId = getIntent().getIntExtra(EXTRA_CUSTOMER_ID, 0);
        String accountId = getIntent().getStringExtra(EXTRA_ACCOUNT_ID);
        if (accountId.equals(null)) {
            accountId = "";
        }

        DaggerCustomDetailComponent.builder()
                .appComponent(getAppComponent())
                .customDetailModule(new CustomDetailModule(customerId, accountId))
                .build()
                .inject(this);

        FragmentManager fragmentManager = getSupportFragmentManager();
        CustomDetailFragment fragment = (CustomDetailFragment) fragmentManager.findFragmentById(R.id.frameContent);
        if (fragment == null) {
            fragment = (CustomDetailFragment) mICustomDetailView;
            ActivityUtils.addFragmentToActivity(fragmentManager, fragment, R.id.frameContent);
        }
        setTitleWithConsult("客户详情", customerId);
//        if (BuildConfig.DEBUG) {
//            ActivityUtils.getInstance().addActivity(this);
//        }
    }


}

