package haozuo.com.healthdoctor.view.login;

import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.WindowManager;

import javax.inject.Inject;

import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.contract.LoginContract;
import haozuo.com.healthdoctor.ioc.DaggerLoginPresenterComponent;
import haozuo.com.healthdoctor.ioc.LoginPresenterModule;
import haozuo.com.healthdoctor.view.base.BaseActivity;
import haozuo.com.healthdoctor.presenter.LoginPresenter;
import haozuo.com.healthdoctor.util.ActivityUtils;


public class LoginActivity extends BaseActivity {
    @Inject
    LoginPresenter mLoginPresenter;
    @Inject
    LoginContract.ILoginView mLoginFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);


        DaggerLoginPresenterComponent.builder()
                .appComponent(getAppComponent())
                .loginPresenterModule(new LoginPresenterModule())
                .build()
                .inject(this);

        FragmentManager fragmentManager=getSupportFragmentManager();
        LoginFragment loginFragment=(LoginFragment)fragmentManager.findFragmentById(R.id.frameContent);
        if(loginFragment==null){
            loginFragment=(LoginFragment) mLoginFragment;
            ActivityUtils.addFragmentToActivity(fragmentManager,loginFragment,R.id.frameContent);
        }
    }


}
