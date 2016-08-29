package haozuo.com.healthdoctor.view.mine;

import android.os.Bundle;

import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.util.ActivityUtils;
import haozuo.com.healthdoctor.view.base.BaseActivity;

/**
 * by zy 2016.08.24
 */
public class DoctorInfoActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTranslucentStatus(R.color.main_color_blue);
        setContentView(R.layout.activity_content);
        setCustomerTitle("基本资料");
        DoctorInfoFragment fragment = (DoctorInfoFragment) getSupportFragmentManager().findFragmentById(R.id.frameContent);
        if (fragment == null) {
            fragment = new DoctorInfoFragment();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.frameContent);
        }
    }
}
