package haozuo.com.healthdoctor.view.consult;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import javax.inject.Inject;

import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.contract.ConsultContract;
import haozuo.com.healthdoctor.ioc.ConsultModule;
import haozuo.com.healthdoctor.ioc.DaggerConsultComponent;
import haozuo.com.healthdoctor.presenter.ConsultPresenter;
import haozuo.com.healthdoctor.util.ActivityUtils;
import haozuo.com.healthdoctor.view.base.BaseActivity;

public class ConsultActivity extends BaseActivity {
    @Inject
    ConsultPresenter mConsultPresenter;
    @Inject
    ConsultContract.IConsultView mIConsultView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consult);
        setCustomerTitle("咨询");
        hideGoBackBtn();

        DaggerConsultComponent.builder()
                .appComponent(getAppComponent())
                .consultModule(new ConsultModule())
                .build()
                .inject(this);

        FragmentManager fragmentManager = getSupportFragmentManager();
        ConsultFragment fragment = (ConsultFragment) fragmentManager.findFragmentById(R.id.frameContent);
        if (fragment == null) {
            fragment = (ConsultFragment) mIConsultView;
            ActivityUtils.addFragmentToActivity(fragmentManager, fragment, R.id.frameContent);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        initTabhostMenu();
    }


}
