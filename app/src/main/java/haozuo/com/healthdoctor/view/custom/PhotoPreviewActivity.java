package haozuo.com.healthdoctor.view.custom;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.view.base.BaseActivity;
import me.relex.circleindicator.CircleIndicator;

/**
 * Created by hzguest3 on 2016/8/24.
 */
public class PhotoPreviewActivity extends BaseActivity {
    public static String EXTRA_URL_LIST = "EXTRA_URL_LIST";
    private List<String> mPhotoUrlList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_photoreport);

//        mPhotoUrlList = (List<String>) getIntent().getSerializableExtra(EXTRA_URL_LIST);
        String PhotoUrl = (String)getIntent().getStringExtra(EXTRA_URL_LIST);
        mPhotoUrlList = (List<String>) Arrays.asList(PhotoUrl.split(","));

        FragmentManager fragmentManager = getSupportFragmentManager();
        PhotoPreviewAdapter photoPreviewAdapter =new PhotoPreviewAdapter(fragmentManager, mPhotoUrlList);
        ViewPager vp =(ViewPager) findViewById(R.id.vp_photopreview);
        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        vp.setAdapter(photoPreviewAdapter);
        indicator.setViewPager(vp);
//        vp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }

    class PhotoPreviewAdapter extends FragmentPagerAdapter {
        private List<PhotoReportFragment> feedbackList = new ArrayList<PhotoReportFragment>();
        public PhotoPreviewAdapter(FragmentManager fm, List<String> PhotoUrlList) {
            super(fm);
            for (int i=0;i<PhotoUrlList.size();i++){
                feedbackList.add(PhotoReportFragment.newInstance(PhotoUrlList.get(i)));
            }
        }

        @Override
        public Fragment getItem(int position) {
            return feedbackList.get(position);
        }

        @Override
        public int getCount() {
            return feedbackList.size();
        }
    }
}
