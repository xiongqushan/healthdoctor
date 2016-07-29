package haozuo.com.healthdoctor.view.custom;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.bean.GroupCustInfoBean;
import haozuo.com.healthdoctor.view.base.BaseActivity;
import haozuo.com.healthdoctor.presenter.CustomerInfoPresenter;
import haozuo.com.healthdoctor.util.ActivityUtils;

public class CustomerInfoActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_info);

        Bundle bundle = getIntent().getExtras();

        GroupCustInfoBean CustomInfo = (GroupCustInfoBean) bundle.getSerializable("CustomInfo");

        setCustomerTitle("基本资料—"+CustomInfo.Cname);

        FragmentManager fragmentManager=getSupportFragmentManager();
        CustomerInfoFragment fragment=(CustomerInfoFragment)fragmentManager.findFragmentById(R.id.frameContent);
        if(fragment==null){
            fragment=CustomerInfoFragment.newInstance(CustomInfo);
            ActivityUtils.addFragmentToActivity(fragmentManager,fragment,R.id.frameContent);
        }
        CustomerInfoPresenter mGroupPresenter=new CustomerInfoPresenter(CustomInfo, fragment);
    }
}
