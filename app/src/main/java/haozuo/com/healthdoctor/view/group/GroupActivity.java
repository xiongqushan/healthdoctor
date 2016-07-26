package haozuo.com.healthdoctor.view.group;

import android.content.Intent;
import android.net.Uri;
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

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.List;

import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.bean.TestBean;
import haozuo.com.healthdoctor.contract.BaseActivity;
import haozuo.com.healthdoctor.contract.ITestService;
import haozuo.com.healthdoctor.manager.UserManager;
import haozuo.com.healthdoctor.presenter.GroupPresenter;
import haozuo.com.healthdoctor.util.ActivityUtils;
import haozuo.com.healthdoctor.view.login.LoginActivity;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class GroupActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        setCustomerTitle("客户分组");
        boolean isLogin=checkLogin();
        if(!isLogin){
            return;
        }
        FragmentManager fragmentManager=getSupportFragmentManager();
        GroupFragment groupFragment=(GroupFragment)fragmentManager.findFragmentById(R.id.frameContent);
        if(groupFragment==null){
            groupFragment=GroupFragment.newInstance();
            ActivityUtils.addFragmentToActivity(fragmentManager,groupFragment,R.id.frameContent);
        }
         GroupPresenter mGroupPresenter=new GroupPresenter(groupFragment);




        /*
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ip.taobao.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        ITestService testService = retrofit.create(ITestService.class);
        testService.getIp("63.223.108.42")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<TestBean>() {
                    @Override
                    public void onCompleted() {
                        String a="";
                    }

                    @Override
                    public void onError(Throwable e) {
                        String a="";
                    }

                    @Override
                    public void onNext(TestBean testBean) {
                        String a= testBean.toString();
                    }
                });
                */

    }

    boolean checkLogin() {
        if (!UserManager.getInstance().exist()) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            return false;
        }
        return true;
    }


}
