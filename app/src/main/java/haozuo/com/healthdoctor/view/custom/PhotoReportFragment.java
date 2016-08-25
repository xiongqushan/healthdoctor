package haozuo.com.healthdoctor.view.custom;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.contract.IBasePresenter;
import haozuo.com.healthdoctor.view.base.AbstractView;
import haozuo.com.healthdoctor.view.base.BaseFragment;
import lib.lhh.fiv.library.FrescoZoomImageView;

/**
 * Created by hzguest3 on 2016/8/24.
 */
public class PhotoReportFragment extends BaseFragment{
    private static String mURL;

    public PhotoReportFragment(){};

    public static PhotoReportFragment newInstance(String Url){
        PhotoReportFragment photoReportFragment = new PhotoReportFragment();
        mURL = Url;
        return photoReportFragment;
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_photo_preview, container, false);
        FrescoZoomImageView frescoImageView = (FrescoZoomImageView) view;
        frescoImageView.loadView(mURL+"!small200",mURL,R.color.black);
//        frescoImageView.setTapToRetryEnabled(true);//设置点击重新加载
//        frescoImageView.setImageURI(Uri.parse(mURL));
        frescoImageView.setOnDraweeClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        return view;
    }
}