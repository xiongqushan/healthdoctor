package haozuo.com.healthdoctor.view.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.bean.GlobalShell;
import haozuo.com.healthdoctor.bean.DoctorBean;
import haozuo.com.healthdoctor.manager.UserManager;
import haozuo.com.healthdoctor.presenter.ILoginPresenter;
import haozuo.com.healthdoctor.presenter.LoginPresenterImpl;
import haozuo.com.healthdoctor.view.Interface.ILoginActivity;
import haozuo.com.healthdoctor.view.MainActivity;

/**
 * Created by xiongwei1 on 2016/6/3.
 */
public class LoginActivity extends BaseActivity implements ILoginActivity{
    @Bind(R.id.txt_GetCode)TextView txt_GetCode;
    @Bind(R.id.btn_login)TextView btn_login;
    @Bind(R.id.edit_mobile)EditText edit_mobile;
    @Bind(R.id.edit_code)EditText edit_code;
    ILoginPresenter mILoginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mILoginPresenter=new LoginPresenterImpl(this);
        edit_mobile.setInputType(EditorInfo.TYPE_CLASS_PHONE);

        if(UserManager.getInstance().exist()){
            Intent intent=new Intent(this,MainActivity.class);
            startActivity(intent);
        }
        //Intent intent = new Intent(this, CaptureActivity.class);
        //startActivityForResult(intent,CaptureActivity.REQUEST_LOGIN_CODE);

        edit_mobile.setText("15601815186");
        txt_GetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
                String mobile=edit_mobile.getText().toString().trim();
                mILoginPresenter.requestLoginSMS(mobile);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
                String mobile=edit_mobile.getText().toString().trim();
                int code=Integer.parseInt(edit_code.getText().toString().trim());
                mILoginPresenter.requestLoginWithSMSCode(mobile,code);
            }
        });
    }


    @Override
    protected void onReceiveBroadcast(String filterAction) {

    }

    @Override
    protected void onStop() {
        super.onStop();
        mILoginPresenter.cancelRequest();
    }

    @Override
    public void handlerLogin(GlobalShell<DoctorBean> result) {
        if(result.Code>0) {
            hideDialog();
            UserManager.getInstance().setDoctorInfo(result.Data);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        else{
            hideDialog(result.Message);
        }
    }

    @Override
    public void handlerGetSMSCode(GlobalShell<Boolean> result) {
        if(result.Code>0 && result.Data) {
            hideDialog("验证码获取成功！");
        }
        else{
            hideDialog(result.Message);
        }
    }
}
