package haozuo.com.healthdoctor.view.custom;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.bean.ReportDetailBean;
import haozuo.com.healthdoctor.util.StringUtil;
import haozuo.com.healthdoctor.util.UIHelper;

/**
 * by zy 2016.08.22
 */
public class ReportBadFragment extends Fragment {
    @Bind(R.id.listView_report_bad)
    ListView mListView;
    private View rootView;

    private TextView tvReportWarn;
    private ListAdapter adapter;
    private View header;

    public ReportBadFragment() {
        // Required empty public constructor
    }

    public static ReportBadFragment newInstance() {
        ReportBadFragment fragment = new ReportBadFragment();
        return fragment;
    }

    public void updateUI(ReportDetailBean bean) {
        dataList.addAll(bean.AnomalyCheckResult);
        adapter.notifyDataSetChanged();
        tvReportWarn.setText(bean.ReportInfoVM.CheckUnitName + "友情提醒，您可能存在以下" + dataList.size() + "条异常项");
        if (dataList.size() == 0) {
            header.findViewById(R.id.img_header_reportbad).setVisibility(View.GONE);
            header.findViewById(R.id.line_header_reportbad).setVisibility(View.GONE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_report_bad, container, false);
            ButterKnife.bind(this, rootView);
            initView();
        }
        return rootView;
    }


    private void initView() {
        header = LayoutInflater.from(getActivity()).inflate(R.layout.headerview_reportbadlv_layout, null);
        tvReportWarn = (TextView) header.findViewById(R.id.tvReportWarn);
        mListView.addHeaderView(header);
        adapter = new ListAdapter();
        mListView.setAdapter(adapter);
    }

    List<ReportDetailBean.CheckResult> dataList = new ArrayList<ReportDetailBean.CheckResult>();

    class ListAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return dataList.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = LayoutInflater.from(getActivity()).inflate(R.layout.lvitem_reportbad_layout, null);
            }
            TextView tvTitle = UIHelper.getAdapterView(view, R.id.tvTitle);
            TextView tvSubtitle = UIHelper.getAdapterView(view, R.id.tvSubtitle);
            TextView tvValue = UIHelper.getAdapterView(view, R.id.tvResultValue);
            TextView tvUnit = UIHelper.getAdapterView(view, R.id.tvUnit);
            TextView lineCenter = UIHelper.getAdapterView(view, R.id.lineCenter);
            LinearLayout footer = UIHelper.getAdapterView(view, R.id.footer_layout);
            if (position + 1 == dataList.size()) {
                footer.setVisibility(View.VISIBLE);
            } else {
                footer.setVisibility(View.GONE);
            }
            tvTitle.setText(dataList.get(position).CheckIndexName);
//            tvValue.setTextColor(Color.parseColor("#FFFF0000"));
            boolean unitEmpty = StringUtil.isTrimEmpty(dataList.get(position).Unit);
            if (unitEmpty) {
                tvSubtitle.setTextColor(Color.parseColor("#FF666666"));
                tvValue.setVisibility(View.GONE);
                tvUnit.setVisibility(View.GONE);
                lineCenter.setVisibility(View.GONE);
                String resultValue = dataList.get(position).ResultValue;
                if (resultValue.equals("")) resultValue = "正常";
                tvSubtitle.setText(resultValue);
            } else {
                tvSubtitle.setTextColor(Color.parseColor("#FFB2B2B2"));
                tvValue.setVisibility(View.VISIBLE);
                tvUnit.setVisibility(View.VISIBLE);
                lineCenter.setVisibility(View.VISIBLE);
                boolean subEmpty = StringUtil.isTrimEmpty(dataList.get(position).TextRef);
                if (subEmpty) {
                    tvSubtitle.setText("");
                } else {
                    tvSubtitle.setText("参考范围 : " + dataList.get(position).TextRef);
                }
                tvValue.setText(dataList.get(position).ResultValue);
                tvUnit.setText("单位 : " + dataList.get(position).Unit);
            }
            return view;
        }
    }
}
