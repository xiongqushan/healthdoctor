package haozuo.com.healthdoctor.view.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.view.Fragment.FragmentTest1;
import haozuo.com.healthdoctor.view.Fragment.FragmentTest2;
import haozuo.com.healthdoctor.view.Fragment.FragmentTest3;

public class HomeActivity extends FragmentActivity {
    private FragmentTabHost mTabHost;

    //定义一个布局
    private LayoutInflater mLayoutInflater;

    //定义数组来存放Fragment界面
    private Class mFragmentArray[] = {FragmentTest1.class,FragmentTest2.class,FragmentTest3.class};

    //定义数组来存放按钮图片
    private int mImageViewArray[] = {R.drawable.tabhost_home_btn,R.drawable.tabhost_message_btn,R.drawable.tabhost_more_btn};

    private String mTextArray[] = { "首页", "消息", "更多" };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
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
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(mTextArray[i])
                    .setIndicator(getTabItemView(i));
            // 将Tab按钮添加进Tab选项卡中
            mTabHost.addTab(tabSpec, mFragmentArray[i], null);
            // 设置Tab按钮的背景
            mTabHost.getTabWidget().getChildAt(i)
                    .setBackgroundResource(R.drawable.tabhost_menu_bg);
        }
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
        textView.setText(mTextArray[index]);

        return view;
    }
}
