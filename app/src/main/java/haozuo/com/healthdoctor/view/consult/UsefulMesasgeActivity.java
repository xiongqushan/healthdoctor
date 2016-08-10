package haozuo.com.healthdoctor.view.consult;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import javax.inject.Inject;

import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.ioc.DaggerUsefulMessagePresenterComponent;
import haozuo.com.healthdoctor.ioc.UsefulMessagePresenterModule;
import haozuo.com.healthdoctor.presenter.CustomDetailPresenter;
import haozuo.com.healthdoctor.presenter.UsefulMessagePresenter;
import haozuo.com.healthdoctor.util.ActivityUtils;
import haozuo.com.healthdoctor.view.base.BaseActivity;
import haozuo.com.healthdoctor.view.custom.CustomDetailFragment;

public class UsefulMesasgeActivity extends BaseActivity {
    @Inject
    UsefulMessagePresenter mUsefulMessagePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_useful_mesasge);

        FragmentManager fragmentManager=getSupportFragmentManager();
        UsefulMessageFragment userfulMessageFragment=(UsefulMessageFragment)fragmentManager.findFragmentById(R.id.frameContent);
        if(userfulMessageFragment==null){
            userfulMessageFragment=UsefulMessageFragment.newInstance();
            ActivityUtils.addFragmentToActivity(fragmentManager,userfulMessageFragment,R.id.frameContent);
        }

        DaggerUsefulMessagePresenterComponent.builder()
                .appComponent(getAppComponent())
                .usefulMessagePresenterModule(new UsefulMessagePresenterModule(userfulMessageFragment))
                .build()
                .inject(this);
    }

}
