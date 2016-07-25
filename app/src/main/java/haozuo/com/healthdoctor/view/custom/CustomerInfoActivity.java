package haozuo.com.healthdoctor.view.custom;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.contract.BaseActivity;
import haozuo.com.healthdoctor.presenter.CustomerInfoPresenter;
import haozuo.com.healthdoctor.util.ActivityUtils;

public class CustomerInfoActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_info);

        setCustomerTitle("客户详情");

        Bundle bundle = getIntent().getExtras();

        int customerId = bundle.getInt("CustomerId");

        FragmentManager fragmentManager=getSupportFragmentManager();
        CustomerInfoFragment fragment=(CustomerInfoFragment)fragmentManager.findFragmentById(R.id.frameContent);
        if(fragment==null){
            fragment=CustomerInfoFragment.newInstance(customerId);
            ActivityUtils.addFragmentToActivity(fragmentManager,fragment,R.id.frameContent);
        }
        CustomerInfoPresenter mGroupPresenter=new CustomerInfoPresenter(customerId, fragment);
    }
}
