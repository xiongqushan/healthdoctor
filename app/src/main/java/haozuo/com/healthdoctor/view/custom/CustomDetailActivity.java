package haozuo.com.healthdoctor.view.custom;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.facebook.drawee.generic.GenericDraweeHierarchy;

import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.contract.BaseActivity;
import haozuo.com.healthdoctor.presenter.CustomDetailPresenter;
import haozuo.com.healthdoctor.util.ActivityUtils;

public class CustomDetailActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_detail);
        setCustomerTitle("客户详情");

        Bundle bundle = getIntent().getExtras();

        int customerId = bundle.getInt("CustomerId");

        FragmentManager fragmentManager=getSupportFragmentManager();
        CustomDetailFragment fragment=(CustomDetailFragment)fragmentManager.findFragmentById(R.id.frameContent);
        if(fragment==null){
            fragment = new CustomDetailFragment();
            fragment=fragment.newInstance(customerId);
            ActivityUtils.addFragmentToActivity(fragmentManager,fragment,R.id.frameContent);
        }
        CustomDetailPresenter mGroupPresenter=new CustomDetailPresenter(fragment);
    }
}
