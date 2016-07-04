package haozuo.com.healthdoctor.view.login;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.presenter.LoginPresenter;
import haozuo.com.healthdoctor.util.ActivityUtils;

public class LoginActivity extends AppCompatActivity {
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
        mLoginPresenter=new LoginPresenter(loginFragment);
    }


}
