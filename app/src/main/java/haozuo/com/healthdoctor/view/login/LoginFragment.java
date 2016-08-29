package haozuo.com.healthdoctor.view.login;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.presenter.IBasePresenter;
import haozuo.com.healthdoctor.contract.LoginContract;
import haozuo.com.healthdoctor.contract.LoginContract.ILoginView;
import haozuo.com.healthdoctor.view.base.AbstractView;
import haozuo.com.healthdoctor.view.home.HomeActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends AbstractView implements ILoginView {
    View rootView;
    LoginContract.ILoginPresenter mILoginPresenter;

    @Bind(R.id.txt_GetCode)
    TextView txt_GetCode;
    @Bind(R.id.btn_login)
    Button btn_login;
    @Bind(R.id.edit_mobile)
    EditText edit_mobile;
    @Bind(R.id.edit_code)
    EditText edit_code;

    public LoginFragment() {
    }

    @Override
    protected IBasePresenter getPresenter() {
        return mILoginPresenter;
    }

    @Override
    protected View getRootView() {
        return rootView;
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
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_login, container, false);
            ButterKnife.bind(this, rootView);
            edit_mobile.setText("15601815186");
            txt_GetCode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String mobile = edit_mobile.getText().toString();
                    mILoginPresenter.requestLoginSMS(mobile);
                }
            });

            btn_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String mobile = edit_mobile.getText().toString();
                    final String code = edit_code.getText().toString();
                    mILoginPresenter.requestLoginWithPassWord(mobile, code);
                }
            });
        }
        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
        mILoginPresenter.cancelRequest();
    }

    @Override
    public void setSMSButtonEnableStatus(boolean isEnabled) {
        txt_GetCode.setEnabled(isEnabled);
    }

    @Override
    public void toHomeActivity() {
        Context context = getContext();
        Intent intent = new Intent(context, HomeActivity.class);
        context.startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void setPresenter(LoginContract.ILoginPresenter presenter) {
        mILoginPresenter = presenter;
    }

}
