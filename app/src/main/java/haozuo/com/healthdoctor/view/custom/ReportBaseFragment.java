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
import haozuo.com.healthdoctor.bean.ReportDetailBean;

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

    public void updateUI(ReportDetailBean bean) {
        ((CustomerReportActivity) getActivity()).setCustomerTitle(bean.ReportInfoVM.CustomerName);
        tvName.setText(bean.ReportInfoVM.CustomerName);
        if (bean.ReportInfoVM.Sex == 0) {
            tvSex.setText("女");
        } else {
            tvSex.setText("男");
        }
        tvAge.setText(bean.ReportInfoVM.Age + "");
        tvReportCenter.setText(bean.ReportInfoVM.CheckUnitName);
        tvReportDate.setText(bean.ReportInfoVM.ReportDate);
        tvReportCode.setText(bean.ReportInfoVM.WorkNo);
//        tvWorkUnit.setText(bean.ReportInfoVM.WorkNo);
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
