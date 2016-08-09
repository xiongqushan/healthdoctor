package haozuo.com.healthdoctor.view.consult;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import java.io.Serializable;

import javax.inject.Inject;

import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.bean.ConsultItemBean;
import haozuo.com.healthdoctor.ioc.ConsultDetailPresenterModule;
import haozuo.com.healthdoctor.ioc.DaggerConsultDetailPresenterComponent;
import haozuo.com.healthdoctor.presenter.ConsultDetailPresenter;
import haozuo.com.healthdoctor.util.ActivityUtils;
import haozuo.com.healthdoctor.view.base.BaseActivity;

public class ConsultDetailActivity extends BaseActivity {
    @Inject
    ConsultDetailPresenter mConsultPresenter;

    public static String EXTRA_CONSULT_ITEM="CONSULT_ITEM";
    private String mCustName;
    private int mCustId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consult_detail);
        Serializable obj= getIntent().getSerializableExtra(EXTRA_CONSULT_ITEM);
        if(obj!=null){
            ConsultItemBean doctorGroupBean = (ConsultItemBean) obj;
            mCustName = doctorGroupBean.CustName;
            mCustId = doctorGroupBean.CustId;
        }
        setCustomerTitle(mCustName);

        FragmentManager fragmentManager=getSupportFragmentManager();
        ConsultDetailFragment consultDetailfragment=(ConsultDetailFragment)fragmentManager.findFragmentById(R.id.frameContent);
        if(consultDetailfragment==null){
            consultDetailfragment=ConsultDetailFragment.newInstance(mCustId);
            ActivityUtils.addFragmentToActivity(fragmentManager,consultDetailfragment,R.id.frameContent);
        }

        DaggerConsultDetailPresenterComponent.builder()
                .appComponent(getAppComponent())
                .consultDetailPresenterModule(new ConsultDetailPresenterModule(consultDetailfragment))
                .build()
                .inject(this);
    }
}

