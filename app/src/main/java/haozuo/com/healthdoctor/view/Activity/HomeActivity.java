package haozuo.com.healthdoctor.view.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.manager.UserManager;
import haozuo.com.healthdoctor.view.Fragment.FragmentTest2;
import haozuo.com.healthdoctor.view.Fragment.FragmentTest3;
import haozuo.com.healthdoctor.view.Fragment.GroupFragment;
import haozuo.com.healthdoctor.view.Interface.IHomeActivity;

public class HomeActivity extends BaseActivity implements IHomeActivity {
    private FragmentTabHost mTabHost;

    //定义一个布局
    private LayoutInflater mLayoutInflater;

    //定义数组来存放Fragment界面
    private Class mFragmentArray[] = {GroupFragment.class,FragmentTest2.class,FragmentTest3.class};

    //定义数组来存放按钮图片
    private int mImageViewArray[] = {R.drawable.tabhost_home_btn,R.drawable.tabhost_message_btn,R.drawable.tabhost_more_btn};

    private String tabSpecTextArray[] = { "主页", "咨询", "我的" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        if(!UserManager.getInstance().exist()){
            Intent intent=new Intent(this,LoginActivity.class);
            startActivity(intent);
            finishThis();
            return;
        }
        initView();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //super.onSaveInstanceState(outState);
    }

    @Override
    protected void onReceiveBroadcast(String filterAction) {

    }

    private void initView() {
        mLayoutInflater = LayoutInflater.from(this);

        // 找到TabHost
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.frameFragmentContainer);
        // 得到fragment的个数
        int count = mFragmentArray.length;
        for (int i = 0; i < count; i++) {
            // 给每个Tab按钮设置图标、文字和内容
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(tabSpecTextArray[i])
                    .setIndicator(getTabItemView(i));
            // 将Tab按钮添加进Tab选项卡中
            mTabHost.addTab(tabSpec, mFragmentArray[i], null);
            // 设置Tab按钮的背景
            mTabHost.getTabWidget().getChildAt(i)
                    .setBackgroundResource(R.drawable.tabhost_menu_bg);
        }
    }

    public void initTitle(){
        int currentTabSpecIndex=mTabHost.getCurrentTab();
        String title=tabSpecTextArray[currentTabSpecIndex];
        setCustomerTitle(title);
    }

    /**
     *
     * 给每个Tab按钮设置图标和文字
     */
    private View getTabItemView(int index) {
        View view = mLayoutInflater.inflate(R.layout.tabhost_menu_item, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.imgTabhostMenuIcon);
        imageView.setImageResource(mImageViewArray[index]);
        TextView textView = (TextView) view.findViewById(R.id.txtTabhostMenuDescriptin);
        textView.setText(tabSpecTextArray[index]);
        return view;
    }

    private long exitTime;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(HomeActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finishThis();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
