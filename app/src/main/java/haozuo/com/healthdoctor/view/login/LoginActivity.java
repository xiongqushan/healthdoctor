package haozuo.com.healthdoctor.view.login;

import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.WindowManager;

import javax.inject.Inject;

import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.contract.LoginContract;
import haozuo.com.healthdoctor.ioc.DaggerLoginComponent;
import haozuo.com.healthdoctor.ioc.LoginModule;
import haozuo.com.healthdoctor.view.base.BaseActivity;
import haozuo.com.healthdoctor.presenter.LoginPresenter;
import haozuo.com.healthdoctor.util.ActivityUtils;


public class LoginActivity extends BaseActivity {
    @Inject
    LoginPresenter mLoginPresenter;
    @Inject
    LoginContract.ILoginView mLoginView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        DaggerLoginComponent.builder()
                .appComponent(getAppComponent())
                .loginModule(new LoginModule())
                .build()
                .inject(this);

        FragmentManager fragmentManager=getSupportFragmentManager();
        LoginFragment loginFragment=(LoginFragment)fragmentManager.findFragmentById(R.id.frameContent);
        if(loginFragment==null){
            loginFragment=(LoginFragment) mLoginView;
            ActivityUtils.addFragmentToActivity(fragmentManager,loginFragment,R.id.frameContent);
        }
    }


}
