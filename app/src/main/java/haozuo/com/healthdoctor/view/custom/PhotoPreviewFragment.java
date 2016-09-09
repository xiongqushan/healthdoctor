package haozuo.com.healthdoctor.view.custom;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.view.base.BaseFragment;
import me.relex.photodraweeview.OnViewTapListener;
import me.relex.photodraweeview.PhotoDraweeView;

/**
 * Created by hzguest3 on 2016/8/24.
 */
public class PhotoPreviewFragment extends BaseFragment{
    private static String URL_ADDRESS = "URL_ADDRESS";
    private String mURL;
    private PhotoDraweeView photoDraweeView;

    public PhotoPreviewFragment(){};

    public static PhotoPreviewFragment newInstance(String Url){
        PhotoPreviewFragment photoPreviewFragment = new PhotoPreviewFragment();
        Bundle args = new Bundle();
        args.putString(URL_ADDRESS, Url);
        photoPreviewFragment.setArguments(args);
        return photoPreviewFragment;
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mURL = getArguments().getString(URL_ADDRESS);
        View view = inflater.inflate(R.layout.item_photo_preview, container, false);
        photoDraweeView = (PhotoDraweeView) view;
        WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);

        photoDraweeView.setPhotoUri(Uri.parse(mURL));
        photoDraweeView.setEnableDraweeMatrix(false);

        ImageRequest resRequest = ImageRequestBuilder.newBuilderWithSource(Uri.parse(mURL))
                .setResizeOptions(new ResizeOptions(wm.getDefaultDisplay().getWidth(),wm.getDefaultDisplay().getHeight()))
                .setAutoRotateEnabled(true) //根据metadata记录的方向 图片自动旋转
                .setProgressiveRenderingEnabled(true)//开启渐进式加载
                .build();

        PipelineDraweeControllerBuilder controller = Fresco.newDraweeControllerBuilder();
        controller.setOldController(photoDraweeView.getController());
        controller.setImageRequest(resRequest);
        controller.setLowResImageRequest(ImageRequest.fromUri(Uri.parse(mURL+"!small200")));
        controller.setControllerListener(new BaseControllerListener<ImageInfo>() {
            @Override
            public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                super.onFinalImageSet(id, imageInfo, animatable);
                if (imageInfo == null) {
                    return;
                }
                photoDraweeView.setEnableDraweeMatrix(true);
                photoDraweeView.update(imageInfo.getWidth(), imageInfo.getHeight());
                photoDraweeView.getHierarchy().setActualImageScaleType(ScalingUtils.ScaleType.FIT_CENTER);
            }
        });
        photoDraweeView.setController(controller.build());
        photoDraweeView.getHierarchy().setActualImageScaleType(ScalingUtils.ScaleType.CENTER_INSIDE);

        photoDraweeView.setOnViewTapListener(new OnViewTapListener() {
            @Override
            public void onViewTap(View view, float x, float y) {
                getActivity().finish();
            }
        });

        return view;
    }
}