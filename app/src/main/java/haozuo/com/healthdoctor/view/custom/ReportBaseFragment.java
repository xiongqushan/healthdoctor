package haozuo.com.healthdoctor.view.custom;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import haozuo.com.healthdoctor.R;

/**
 * by zy 2016.08.22.
 */
public class ReportBaseFragment extends Fragment {

    @Bind(R.id.tvName)
    TextView tvName;
    @Bind(R.id.tvSex)
    TextView tvSex;
    @Bind(R.id.tvAge)
    TextView tvAge;
    @Bind(R.id.tvReportCenter)
    TextView tvReportCenter;
    @Bind(R.id.tvReportDate)
    TextView tvReportDate;
    @Bind(R.id.tvReportCode)
    TextView tvReportCode;
    @Bind(R.id.tvWorkUnit)
    TextView tvWorkUnit;
    private View rootView;

    public ReportBaseFragment() {
    }

    public static ReportBaseFragment newInstance() {
        ReportBaseFragment fragment = new ReportBaseFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_report_base, container, false);
            ButterKnife.bind(this, rootView);
        }
        return rootView;
    }

}
