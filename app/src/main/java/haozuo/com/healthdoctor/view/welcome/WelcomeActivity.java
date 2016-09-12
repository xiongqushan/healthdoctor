package haozuo.com.healthdoctor.view.welcome;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.WindowManager;

import javax.inject.Inject;

import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.contract.WelcomeContract;
import haozuo.com.healthdoctor.ioc.DaggerWelcomeComponent;
import haozuo.com.healthdoctor.ioc.WelcomeModule;
import haozuo.com.healthdoctor.presenter.WelcomePresenter;
import haozuo.com.healthdoctor.util.ActivityUtils;
import haozuo.com.healthdoctor.util.UHealthUtils;
import haozuo.com.healthdoctor.view.base.BaseActivity;

public class WelcomeActivity extends BaseActivity {

    @Inject
    WelcomePresenter mWelcomePresenter;
    @Inject
    WelcomeContract.IWelcomeView mIWelcomeView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setTranslucentStatus(0);
        setContentView(R.layout.activity_welcome);
        DaggerWelcomeComponent.builder()
                .welcomeModule(new WelcomeModule(UHealthUtils.getCurrVersion(getApplicationContext())))
                .appComponent(getAppComponent())
                .build()
                .inject(this);
        WelcomeFragment WelcomeFragment = (WelcomeFragment) getSupportFragmentManager().findFragmentById(R.id.frameContent);
        if (WelcomeFragment == null) {
            WelcomeFragment = (WelcomeFragment) mIWelcomeView;
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), WelcomeFragment, R.id.frameContent);
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }


}
