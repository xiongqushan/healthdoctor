package haozuo.com.healthdoctor.view.mine;

import android.os.Bundle;

import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.view.base.BaseActivity;

public class StatementActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTranslucentStatus(R.color.main_color_blue);
        setContentView(R.layout.activity_statement);
        setCustomerTitle("免责声明");
    }
}
