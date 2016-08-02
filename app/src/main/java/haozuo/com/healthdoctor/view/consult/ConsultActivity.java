package haozuo.com.healthdoctor.view.consult;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.presenter.ConsultPresenter;
import haozuo.com.healthdoctor.util.ActivityUtils;
import haozuo.com.healthdoctor.view.base.BaseActivity;
import haozuo.com.healthdoctor.view.custom.CustomDetailFragment;

public class ConsultActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consult);
        setCustomerTitle("咨询");

        FragmentManager fragmentManager=getSupportFragmentManager();
        ConsultFragment fragment=(ConsultFragment)fragmentManager.findFragmentById(R.id.frameContent);
        if(fragment==null){
            fragment=ConsultFragment.newInstance();
            ActivityUtils.addFragmentToActivity(fragmentManager,fragment,R.id.frameContent);
        }
        ConsultPresenter mConsultPresenter=new ConsultPresenter(fragment) ;
    }

    @Override
    public void onResume(){
        super.onResume();
        initTabhostMenu();
    }

}
