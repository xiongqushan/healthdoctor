package haozuo.com.healthdoctor.view.custom;

import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.view.base.BaseActivity;
import haozuo.com.healthdoctor.view.threePart.common.MultiTouchViewPager;
import me.relex.circleindicator.CircleIndicator;
import me.relex.photodraweeview.OnPhotoTapListener;
import me.relex.photodraweeview.OnViewTapListener;
import me.relex.photodraweeview.PhotoDraweeView;

/**
 * Created by hzguest3 on 2016/8/24.
 */
public class PhotoPreviewActivity extends BaseActivity {
    public static String EXTRA_URL_LIST = "EXTRA_URL_LIST";
    public static String EXTRA_PAGER_INDEX = "EXTRA_PAGER_INDEX";
    private List<String> mPhotoUrlList;
    private int mIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

//        mPhotoUrlList = (List<String>) getIntent().getSerializableExtra(EXTRA_URL_LIST);
        String PhotoUrl = (String)getIntent().getStringExtra(EXTRA_URL_LIST);
        mIndex= (int)getIntent().getIntExtra(EXTRA_PAGER_INDEX,0);
        mPhotoUrlList = (List<String>) Arrays.asList(PhotoUrl.split(","));

        if (mPhotoUrlList.size()>1){
            PhotoListPreview();
        }else if (mPhotoUrlList.size()==1){
            SinglePhotoPreview();
        }
    }

    public void PhotoListPreview(){
        setContentView(R.layout.activity_photoreport);
        MultiTouchViewPager vp = (MultiTouchViewPager) findViewById(R.id.view_pager);
        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
//        vp.setAdapter(new DraweePagerAdapter(mPhotoUrlList));
        indicator.setViewPager(vp);
        vp.setCurrentItem(mIndex);
    }

    public void SinglePhotoPreview(){
        setContentView(R.layout.item_photo_preview);
        PhotoDraweeView mPhotoDraweeView = (PhotoDraweeView) findViewById(R.id.drawee_photoreport);
        mPhotoDraweeView.setPhotoUri(Uri.parse(mPhotoUrlList.get(0)));
    }

//    public class DraweePagerAdapter extends PagerAdapter {
//        private List<String> mPhotoUrlList;
//
//        public DraweePagerAdapter (List<String> PhotoUrlList){
//            mPhotoUrlList = PhotoUrlList;
//        };
//
//        @Override public int getCount() {
//            return mPhotoUrlList.size();
//        }
//
//        @Override public boolean isViewFromObject(View view, Object object) {
//            return view == object;
//        }
//
//        @Override public void destroyItem(ViewGroup container, int position, Object object) {
//            container.removeView((View) object);
//        }
//
//        @Override public Object instantiateItem(ViewGroup viewGroup, int position) {
//            final PhotoDraweeView photoDraweeView = new PhotoDraweeView(viewGroup.getContext());
//            GenericDraweeHierarchyBuilder builder =
//                    new GenericDraweeHierarchyBuilder(getResources());
//            GenericDraweeHierarchy hierarchy = builder
//                    .setFadeDuration(300)
//                    .setProgressBarImage(getDrawable(R.drawable.loading), ScalingUtils.ScaleType.CENTER_INSIDE)
//                    .setFailureImage(getDrawable(R.drawable.failure_pic), ScalingUtils.ScaleType.CENTER_INSIDE)
//                    .build();
//
//            PipelineDraweeControllerBuilder controller = Fresco.newDraweeControllerBuilder();
//            controller.setUri(Uri.parse(mPhotoUrlList.get(position)));
////            controller.setImageRequest(ImageRequest.fromUri(Uri.parse(mPhotoUrlList.get(position))));//原图加载
////            controller.setLowResImageRequest(ImageRequest.fromUri(Uri.parse(mPhotoUrlList.get(position)+"!small200")));//低分辨率图片加载
////            controller.setTapToRetryEnabled(true);//允许点击重试
//            controller.setOldController(photoDraweeView.getController());
//            controller.setTapToRetryEnabled(true);
//
//            controller.setControllerListener(new BaseControllerListener<ImageInfo>() {
//                @Override
//                public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
//                    super.onFinalImageSet(id, imageInfo, animatable);
//                    if (imageInfo == null) {
//                        return;
//                    }
//                    photoDraweeView.update(imageInfo.getWidth(), imageInfo.getHeight());
//                }
//            });
//            photoDraweeView.setController(controller.build());
//            photoDraweeView.setHierarchy(hierarchy);
//
//            try {
//                viewGroup.addView(photoDraweeView, ViewGroup.LayoutParams.MATCH_PARENT,
//                        ViewGroup.LayoutParams.MATCH_PARENT);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            return photoDraweeView;
//        }
//    }

    public class DraweePagerAdapter extends PagerAdapter {

        public DraweePagerAdapter(){};

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return false;
        }
    }
}