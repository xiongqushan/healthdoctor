package haozuo.com.healthdoctor.view.custom;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.view.base.BaseActivity;

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
        setContentView(R.layout.fragment_photoreport);

        mPhotoUrlList = (List<String>) getIntent().getSerializableExtra(EXTRA_URL_LIST);

        FragmentManager fragmentManager = getSupportFragmentManager();
        PhotoPreviewAdapter photoPreviewAdapter =new PhotoPreviewAdapter(fragmentManager, mPhotoUrlList);
        ViewPager vp =(ViewPager) findViewById(R.id.vp_photopreview);
        vp.setAdapter(photoPreviewAdapter);
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
