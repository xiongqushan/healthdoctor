package haozuo.com.healthdoctor.view.login;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.contract.LoginContract;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements LoginContract.ILoginView{
    View rootView;
    LoginContract.ILoginPresenter mILoginPresenter;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        mILoginPresenter.start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(rootView==null){
            rootView= inflater.inflate(R.layout.fragment_login, container, false);
            ButterKnife.bind(this,rootView);
        }
        return rootView;
    }

    @Override
    public void loginSuccess() {

    }

    @Override
    public void loginError(String msg) {

    }

    @Override
    public void setPresenter(LoginContract.ILoginPresenter presenter) {
        mILoginPresenter=presenter;
    }
}
