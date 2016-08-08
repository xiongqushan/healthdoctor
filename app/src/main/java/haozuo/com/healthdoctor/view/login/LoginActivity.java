package haozuo.com.healthdoctor.view.login;

import android.support.v4.app.FragmentManager;
import android.os.Bundle;

import javax.inject.Inject;

import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.ioc.DaggerLoginPresenterComponent;
import haozuo.com.healthdoctor.ioc.LoginPresenterModule;
import haozuo.com.healthdoctor.view.base.BaseActivity;
import haozuo.com.healthdoctor.presenter.LoginPresenter;
import haozuo.com.healthdoctor.util.ActivityUtils;

public class LoginActivity extends BaseActivity {
    @Inject
    LoginPresenter mLoginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FragmentManager fragmentManager=getSupportFragmentManager();
        LoginFragment loginFragment=(LoginFragment)fragmentManager.findFragmentById(R.id.frameContent);
        if(loginFragment==null){
            loginFragment=LoginFragment.newInstance();
            ActivityUtils.addFragmentToActivity(fragmentManager,loginFragment,R.id.frameContent);
        }

        DaggerLoginPresenterComponent.builder()
                .appComponent(getAppComponent())
                .loginPresenterModule(new LoginPresenterModule(loginFragment))
                .build()
                .inject(this);

    }

}
