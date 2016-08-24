package haozuo.com.healthdoctor.view.mine;

import android.os.Bundle;
import android.view.Window;

import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.util.ActivityUtils;
import haozuo.com.healthdoctor.view.base.BaseActivity;

/**
 * by zy 2016.08.24
 */
public class SettingsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTranslucentStatus(R.color.main_color_blue);
        setContentView(R.layout.activity_content);
        setCustomerTitle("设置");
        SettingsFragment fragment = (SettingsFragment) getSupportFragmentManager().findFragmentById(R.id.frameContent);
        if (fragment == null) {
            fragment = new SettingsFragment();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.frameContent);
        }
    }
}
