package haozuo.com.healthdoctor.view.mine;

import android.os.Bundle;
import android.util.Log;
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
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl("https://www.baidu.com/");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (BuildConfig.DEBUG) {
            Log.e("mWebView", mWebView.toString() + "");
            HZApplication.shareApplication().getRefWatcher().watch(this);
            HZApplication.shareApplication().getRefWatcher().watch(mWebView);
        }

    }
}
