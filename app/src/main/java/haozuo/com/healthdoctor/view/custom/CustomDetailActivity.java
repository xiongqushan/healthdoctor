package haozuo.com.healthdoctor.view.custom;


import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import javax.inject.Inject;

import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.contract.CustomDetailContract;
import haozuo.com.healthdoctor.ioc.CustomDetailModule;
import haozuo.com.healthdoctor.ioc.DaggerCustomDetailComponent;
import haozuo.com.healthdoctor.view.base.BaseActivity;
import haozuo.com.healthdoctor.presenter.CustomDetailPresenter;
import haozuo.com.healthdoctor.util.ActivityUtils;



public class CustomDetailActivity extends BaseActivity {
    @Inject
    CustomDetailPresenter mGroupPresenter;
    @Inject
    CustomDetailContract.ICustomDetailView mICustomDetailView;

    public static String EXTRA_CUSTOMER_ID = "Customer_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_detail);

        Bundle bundle = getIntent().getExtras();
        int customerId = bundle.getInt("CustomerId");

        DaggerCustomDetailComponent.builder()
                .appComponent(getAppComponent())
                .customDetailModule(new CustomDetailModule(customerId))
                .build()
                .inject(this);

        FragmentManager fragmentManager = getSupportFragmentManager();
        CustomDetailFragment fragment = (CustomDetailFragment) fragmentManager.findFragmentById(R.id.frameContent);
        if (fragment == null) {
            fragment = (CustomDetailFragment) mICustomDetailView;
            ActivityUtils.addFragmentToActivity(fragmentManager, fragment, R.id.frameContent);
        }
    }
}

