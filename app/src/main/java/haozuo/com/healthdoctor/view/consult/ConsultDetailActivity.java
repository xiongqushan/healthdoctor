package haozuo.com.healthdoctor.view.consult;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import java.io.Serializable;

import javax.inject.Inject;

import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.bean.ConsultItemBean;
import haozuo.com.healthdoctor.contract.ConsultDetailContract;
import haozuo.com.healthdoctor.ioc.ConsultDetailModule;
import haozuo.com.healthdoctor.ioc.DaggerConsultDetailComponent;
import haozuo.com.healthdoctor.presenter.ConsultDetailPresenter;
import haozuo.com.healthdoctor.util.ActivityUtils;
import haozuo.com.healthdoctor.view.base.BaseActivity;

public class ConsultDetailActivity extends BaseActivity {
    @Inject
    ConsultDetailPresenter mConsultPresenter;
    @Inject
    ConsultDetailContract.IConsultDetailView mIConsultDetailView;

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

        DaggerConsultDetailComponent.builder()
                .appComponent(getAppComponent())
                .consultDetailModule(new ConsultDetailModule(mCustId))
                .build()
                .inject(this);

        FragmentManager fragmentManager=getSupportFragmentManager();
        ConsultDetailFragment consultDetailfragment=(ConsultDetailFragment)fragmentManager.findFragmentById(R.id.frameContent);
        if(consultDetailfragment==null){
            consultDetailfragment=(ConsultDetailFragment)mIConsultDetailView;
            ActivityUtils.addFragmentToActivity(fragmentManager,consultDetailfragment,R.id.frameContent);
        }


    }
}
