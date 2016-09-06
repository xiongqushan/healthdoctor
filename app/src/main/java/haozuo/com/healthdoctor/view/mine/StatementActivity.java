package haozuo.com.healthdoctor.view.mine;

import android.os.Bundle;
import android.webkit.WebView;

import haozuo.com.healthdoctor.BuildConfig;
import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.framework.HZApplication;
import haozuo.com.healthdoctor.view.base.BaseActivity;

public class StatementActivity extends BaseActivity {

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTranslucentStatus(R.color.main_color_blue);
        setContentView(R.layout.activity_statement);
        setCustomerTitle("免责声明");

        mWebView = (WebView) findViewById(R.id.webView);
        mWebView.loadUrl("https://www.baidu.com/");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (BuildConfig.DEBUG) {
            HZApplication.shareApplication().getRefWatcher().watch(this);
            HZApplication.shareApplication().getRefWatcher().watch(mWebView);
        }

    }
}
