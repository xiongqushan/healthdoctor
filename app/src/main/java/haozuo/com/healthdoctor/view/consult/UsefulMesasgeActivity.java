package haozuo.com.healthdoctor.view.consult;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;

import java.io.Serializable;

import javax.inject.Inject;

import butterknife.OnClick;
import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.bean.ConsultReplyBean;
import haozuo.com.healthdoctor.contract.UsefulMessageContract;
import haozuo.com.healthdoctor.ioc.DaggerUsefulMessageComponent;
import haozuo.com.healthdoctor.ioc.UsefulMessageModule;
import haozuo.com.healthdoctor.presenter.UsefulMessagePresenter;
import haozuo.com.healthdoctor.util.ActivityUtils;
import haozuo.com.healthdoctor.view.base.BaseActivity;

public class UsefulMesasgeActivity extends BaseActivity {
    @Inject
    UsefulMessagePresenter mUsefulMessagePresenter;
    @Inject
    UsefulMessageContract.IUsefulMessageView mIUsefulMessageView;

    public static String LAST_CONSULT_CONTENT="LAST_CONSULT_CONTENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_useful_message);

        ConsultReplyBean consultReplyItem = (ConsultReplyBean) getIntent().getSerializableExtra(LAST_CONSULT_CONTENT);

        DaggerUsefulMessageComponent.builder()
                .appComponent(getAppComponent())
                .usefulMessageModule(new UsefulMessageModule(consultReplyItem))
                .build()
                .inject(this);

        FragmentManager fragmentManager=getSupportFragmentManager();
        UsefulMessageFragment userfulMessageFragment=(UsefulMessageFragment)fragmentManager.findFragmentById(R.id.frameContent);
        if(userfulMessageFragment==null){
            userfulMessageFragment=(UsefulMessageFragment)mIUsefulMessageView;
            ActivityUtils.addFragmentToActivity(fragmentManager,userfulMessageFragment,R.id.frameContent);
        }

        findViewById(R.id.btn_go_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
