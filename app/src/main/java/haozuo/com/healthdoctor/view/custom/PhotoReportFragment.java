package haozuo.com.healthdoctor.view.custom;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.view.base.BaseFragment;
import lib.lhh.fiv.library.FrescoZoomImageView;

/**
 * Created by hzguest3 on 2016/8/24.
 */
public class PhotoReportFragment extends BaseFragment{
    private static String URL_ADDRESS = "URL_ADDRESS";
    private String mURL;

    public PhotoReportFragment(){};

    public static PhotoReportFragment newInstance(String Url){
        PhotoReportFragment photoReportFragment = new PhotoReportFragment();
        Bundle args = new Bundle();
        args.putString(URL_ADDRESS, Url);
        photoReportFragment.setArguments(args);
        return photoReportFragment;
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mURL = getArguments().getString(URL_ADDRESS);

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