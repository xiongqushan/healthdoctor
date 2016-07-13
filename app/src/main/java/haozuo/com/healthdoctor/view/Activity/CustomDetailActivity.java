package haozuo.com.healthdoctor.view.Activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.view.Fragment.CustDetailFragment;
import haozuo.com.healthdoctor.view.adapter.CustDetailFragAdapter;

public class CustomDetailActivity extends haozuo.com.healthdoctor.view.Activity.BaseActivity {

    List<Fragment> fragmentList = new ArrayList<Fragment>();
    List<String>   titleList    = new ArrayList<String>();

    @Bind(R.id.customerName)TextView customerName;
    @Bind(R.id.customerImg) SimpleDraweeView customerImg;
    @Bind(R.id.viewPager) ViewPager vp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_detail);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        int customerId = bundle.getInt("CustomerId");
        String Cname = bundle.getString("CustomerName");
        String Nickname = bundle.getString("CustomerNickname");
        //String Cphoto = bundle.getString("CustomerPhoto");
        String Cphoto = "http://pic002.cnblogs.com/images/2011/103608/2011062022023456.jpg";
        Uri uri = Uri.parse(Cphoto);
        customerImg.setImageURI(uri);
        customerName.setText(Cname+"("+Nickname+")");

        GenericDraweeHierarchy hierarchy = customerImg.getHierarchy(); //头像占位
        hierarchy.setPlaceholderImage(R.drawable.ic_launcher);

        //fragmentList.add(new CustDetailFragment("页面1"));
        //fragmentList.add(new CustDetailFragment("页面2"));
        //fragmentList.add(new CustDetailFragment("页面3"));
        titleList.add("title 1 ");
        titleList.add("title 2 ");
        titleList.add("title 3 ");
        vp.setAdapter(new CustDetailFragAdapter(getSupportFragmentManager(), fragmentList, titleList));

    }

    @Override
    protected void onReceiveBroadcast(String filterAction) {

    }

}


