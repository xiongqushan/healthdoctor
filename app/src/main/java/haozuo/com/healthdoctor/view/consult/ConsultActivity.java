package haozuo.com.healthdoctor.view.consult;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import javax.inject.Inject;

import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.ioc.ConsultPresenterModule;
import haozuo.com.healthdoctor.ioc.DaggerConsultPresenterComponent;
import haozuo.com.healthdoctor.presenter.ConsultPresenter;
import haozuo.com.healthdoctor.util.ActivityUtils;
import haozuo.com.healthdoctor.view.base.BaseActivity;
import haozuo.com.healthdoctor.view.custom.CustomDetailFragment;

public class ConsultActivity extends BaseActivity {
    @Inject
    ConsultPresenter mConsultPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consult);
        setCustomerTitle("咨询");
        hideGoBackBtn();

        FragmentManager fragmentManager=getSupportFragmentManager();
        ConsultFragment fragment=(ConsultFragment)fragmentManager.findFragmentById(R.id.frameContent);
        if(fragment==null){
            fragment=ConsultFragment.newInstance();
            ActivityUtils.addFragmentToActivity(fragmentManager,fragment,R.id.frameContent);
        }
        DaggerConsultPresenterComponent.builder()
                .appComponent(getAppComponent())
                .consultPresenterModule(new ConsultPresenterModule(fragment))
                .build()
                .inject(this);
    }

    @Override
    public void onResume(){
        super.onResume();
        initTabhostMenu();
    }

}
