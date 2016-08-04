package haozuo.com.healthdoctor.view.login;

import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.view.base.BaseActivity;
import haozuo.com.healthdoctor.presenter.LoginPresenter;
import haozuo.com.healthdoctor.util.ActivityUtils;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        FragmentManager fragmentManager=getSupportFragmentManager();
        LoginFragment loginFragment=(LoginFragment)fragmentManager.findFragmentById(R.id.frameContent);
        if(loginFragment==null){
            loginFragment=LoginFragment.newInstance();
            ActivityUtils.addFragmentToActivity(fragmentManager,loginFragment,R.id.frameContent);
        }
        LoginPresenter mLoginPresenter=new LoginPresenter(loginFragment,this);

        haozuo.com.healthdoctor.manager.UserManager.getInstance(this);
    }

}
