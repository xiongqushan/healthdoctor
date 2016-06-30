package haozuo.com.healthdoctor.view.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import haozuo.com.healthdoctor.R;

/**
 * Created by Administrator on 2016/6/30.
 */
public class CustDetailFragment extends BaseFragment {
    private String   text;
    private TextView tv = null;

    public CustDetailFragment(String text){
        super();
        this.text = text;
    }

    /**
     * 覆盖此函数，先通过inflater inflate函数得到view最后返回
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_custom_detail, container, false);
//        tv = (TextView)v.findViewById(R.id.viewPagerText);
//        tv.setText(text);
        return v;
    }
}
