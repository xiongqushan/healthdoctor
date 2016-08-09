package haozuo.com.healthdoctor.view.custom;


import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import javax.inject.Inject;

import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.ioc.CustomDetailPresenterModule;
import haozuo.com.healthdoctor.ioc.DaggerCustomDetailPresenterComponent;
import haozuo.com.healthdoctor.view.base.BaseActivity;
import haozuo.com.healthdoctor.presenter.CustomDetailPresenter;
import haozuo.com.healthdoctor.util.ActivityUtils;



public class CustomDetailActivity extends BaseActivity {
    @Inject
    CustomDetailPresenter mGroupPresenter;
    public static String EXTRA_CUSTOMER_ID="Customer_ID";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_detail);

    Bundle bundle = getIntent().getExtras();
    int customerId = bundle.getInt("CustomerId");

        FragmentManager fragmentManager=getSupportFragmentManager();
        CustomDetailFragment fragment=(CustomDetailFragment)fragmentManager.findFragmentById(R.id.frameContent);
        if(fragment==null){
            fragment=CustomDetailFragment.newInstance(customerId);
            ActivityUtils.addFragmentToActivity(fragmentManager,fragment,R.id.frameContent);
        }
        //CustomDetailPresenter mGroupPresenter=new CustomDetailPresenter(fragment, customerId);
        DaggerCustomDetailPresenterComponent.builder()
                .appComponent(getAppComponent())
                .customDetailPresenterModule(new CustomDetailPresenterModule(fragment,customerId))
                .build()
                .inject(this);
    }
}

