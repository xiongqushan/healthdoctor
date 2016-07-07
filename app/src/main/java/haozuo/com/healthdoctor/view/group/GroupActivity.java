package haozuo.com.healthdoctor.view.group;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.presenter.GroupPresenter;
import haozuo.com.healthdoctor.util.ActivityUtils;
import haozuo.com.healthdoctor.view.Fragment.*;

public class GroupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        FragmentManager fragmentManager=getSupportFragmentManager();
        GroupFragment groupFragment=(GroupFragment)fragmentManager.findFragmentById(R.id.frameContent);
        if(groupFragment==null){
            groupFragment=GroupFragment.newInstance();
            ActivityUtils.addFragmentToActivity(fragmentManager,groupFragment,R.id.frameContent);
        }
         GroupPresenter mGroupPresenter=new GroupPresenter(groupFragment);
    }
}
