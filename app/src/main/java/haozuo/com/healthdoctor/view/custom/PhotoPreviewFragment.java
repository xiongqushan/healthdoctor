package haozuo.com.healthdoctor.view.custom;

import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;

import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.view.base.BaseFragment;
import me.relex.photodraweeview.OnPhotoTapListener;
import me.relex.photodraweeview.PhotoDraweeView;

/**
 * Created by hzguest3 on 2016/8/24.
 */
public class PhotoPreviewFragment extends BaseFragment{
    private static String URL_ADDRESS = "URL_ADDRESS";
    private String mURL;

    public PhotoPreviewFragment(){};

    public static PhotoPreviewFragment newInstance(String Url){
        PhotoPreviewFragment photoPreviewFragment = new PhotoPreviewFragment();
        Bundle args = new Bundle();
        args.putString(URL_ADDRESS, Url);
        photoPreviewFragment.setArguments(args);
        return photoPreviewFragment;
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mURL = getArguments().getString(URL_ADDRESS);
        View view = inflater.inflate(R.layout.item_photo_preview, container, false);
        final PhotoDraweeView photoDraweeView = (PhotoDraweeView) view;


        PipelineDraweeControllerBuilder controller = Fresco.newDraweeControllerBuilder();
        controller.setOldController(photoDraweeView.getController());
        controller.setImageRequest(ImageRequest.fromUri(Uri.parse(mURL)));
        controller.setLowResImageRequest(ImageRequest.fromUri(Uri.parse(mURL+"!small200")));
        controller.setControllerListener(new BaseControllerListener<ImageInfo>() {
            @Override
            public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                super.onFinalImageSet(id, imageInfo, animatable);
                if (imageInfo == null) {
                    return;
                }
                photoDraweeView.update(imageInfo.getWidth(), imageInfo.getHeight());
            }
        });
        photoDraweeView.setController(controller.build());
        photoDraweeView.setOnPhotoTapListener(new OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
                getActivity().finish();
            }
        });

        return view;
    }
}