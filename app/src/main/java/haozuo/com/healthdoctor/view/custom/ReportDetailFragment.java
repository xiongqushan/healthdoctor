package haozuo.com.healthdoctor.view.custom;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import haozuo.com.healthdoctor.R;

/**
 * by zy 2016.08.22.
 */
public class ReportDetailFragment extends Fragment {


    private View rootView;

    public ReportDetailFragment() {
        // Required empty public constructor
    }


    public static ReportDetailFragment newInstance() {
        ReportDetailFragment fragment = new ReportDetailFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_report_detail, container, false);
            ButterKnife.bind(this, rootView);
        }
        return rootView;
    }

}
