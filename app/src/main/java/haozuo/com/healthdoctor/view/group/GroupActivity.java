package haozuo.com.healthdoctor.view.group;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import java.util.List;

import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.contract.BaseActivity;
import haozuo.com.healthdoctor.manager.UserManager;
import haozuo.com.healthdoctor.presenter.GroupPresenter;
import haozuo.com.healthdoctor.util.ActivityUtils;
import haozuo.com.healthdoctor.view.Fragment.*;

public class GroupActivity extends BaseActivity {
    private FragmentTabHost mTabHost;

    //定义一个布局
    private LayoutInflater mLayoutInflater;

    //定义数组来存放Fragment界面
    private Class mFragmentArray[] = {GroupFragment.class,FragmentTest2.class,FragmentTest3.class};

    //定义数组来存放按钮图片
    private int mImageViewArray[] = {R.drawable.tabhost_home_btn,R.drawable.tabhost_message_btn,R.drawable.tabhost_more_btn};

    private String tabSpecTextArray[] = { "a", "咨询", "我的" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        boolean isLogin=checkLogin();
        if(!isLogin){
            return;
        }
        initView();
        FragmentManager fragmentManager=getSupportFragmentManager();
        List<Fragment>a= fragmentManager.getFragments();
        android.app.Fragment aa=getFragmentManager().findFragmentByTag(tabSpecTextArray[0]);

        GroupFragment aaaa= (GroupFragment)getSupportFragmentManager().findFragmentById(R.id.frameContent);
        GroupFragment groupFragment=(GroupFragment)fragmentManager.findFragmentByTag(tabSpecTextArray[0]);
        if(groupFragment==null){
            groupFragment=GroupFragment.newInstance();
            ActivityUtils.addFragmentToActivity(fragmentManager,groupFragment,R.id.frameContent);
        }
         GroupPresenter mGroupPresenter=new GroupPresenter(groupFragment);
    }

    boolean checkLogin(){
        if(!UserManager.getInstance().exist()){
            Intent intent=new Intent(this, haozuo.com.healthdoctor.view.login.LoginActivity.class);
            startActivity(intent);
            return false;
        }
        return true;
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
}
