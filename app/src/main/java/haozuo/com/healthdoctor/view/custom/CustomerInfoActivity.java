package haozuo.com.healthdoctor.view.custom;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import javax.inject.Inject;

import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.bean.CustomDetailBean;
import haozuo.com.healthdoctor.bean.GroupCustInfoBean;
import haozuo.com.healthdoctor.ioc.CustomerInfoPresenterModule;
import haozuo.com.healthdoctor.ioc.DaggerCustomerInfoPresenterComponent;
import haozuo.com.healthdoctor.view.base.BaseActivity;
import haozuo.com.healthdoctor.presenter.CustomerInfoPresenter;
import haozuo.com.healthdoctor.util.ActivityUtils;

public class CustomerInfoActivity extends BaseActivity {
    @Inject
    CustomerInfoPresenter mGroupPresenter;
    public static String EXTRA_CUSTOMER_INFO="Custom_Info";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_info);

        Bundle bundle = getIntent().getExtras();


        CustomDetailBean customInfo = (CustomDetailBean) bundle.getSerializable(EXTRA_CUSTOMER_INFO);
        setCustomerTitle("基本资料—"+customInfo.Cname);

        FragmentManager fragmentManager=getSupportFragmentManager();
        CustomerInfoFragment fragment=(CustomerInfoFragment)fragmentManager.findFragmentById(R.id.frameContent);
        if(fragment==null){
            fragment=CustomerInfoFragment.newInstance(customInfo);
            ActivityUtils.addFragmentToActivity(fragmentManager,fragment,R.id.frameContent);
        }
        //CustomerInfoPresenter mGroupPresenter=new CustomerInfoPresenter(CustomInfo, fragment, CustomInfo.Id);
        DaggerCustomerInfoPresenterComponent.builder()
                .appComponent(getAppComponent())
                .customerInfoPresenterModule(new CustomerInfoPresenterModule(fragment,customInfo))
                .build()
                .inject(this);
    }
}
