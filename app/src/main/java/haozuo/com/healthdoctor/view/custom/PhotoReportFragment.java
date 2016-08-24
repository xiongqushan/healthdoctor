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
        SimpleDraweeView simpleDraweeView = (SimpleDraweeView) view;
//        if (mURL == "") {
//            mURL = "res://haozuo.com.healthdoctor/" + R.drawable.default_photourl;
//        }
        Uri uri = Uri.parse(mURL);
        simpleDraweeView.setImageURI(uri);

        return view;
    }
}
