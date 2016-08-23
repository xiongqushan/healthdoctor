package haozuo.com.healthdoctor.view.custom;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.bean.ReportDetailBean;
import haozuo.com.healthdoctor.util.UIHelper;
import haozuo.com.healthdoctor.view.threePart.common.ChildListView;

/**
 * by zy 2016.08.22.
 */
public class ReportDetailFragment extends Fragment {

    @Bind(R.id.listView_report_detail)
    ListView mListView;
    private ListAdapter adapter;
    private View rootView;
    private List<ReportDetailBean.DepartmentInfoVM> dataList = new ArrayList<ReportDetailBean.DepartmentInfoVM>();

    public ReportDetailFragment() {
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
            initView();
        }
        return rootView;
    }

    private void initView() {
        adapter = new ListAdapter();
        mListView.setAdapter(adapter);
    }

    public void updateUI(ReportDetailBean bean) {
        dataList = bean.DepartmentCheck;
        adapter.notifyDataSetChanged();
    }

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
                view = LayoutInflater.from(getActivity()).inflate(R.layout.lvitem_reportdetail_layout, null);
            }
            TextView tvMainItem = UIHelper.getAdapterView(view, R.id.tvMainItem);
            TextView tvCheckItem = UIHelper.getAdapterView(view, R.id.tvCheckItem);
            tvMainItem.setText(dataList.get(position).DepartmentName);
            StringBuffer CheckItem = new StringBuffer();
            for (int i = 0; i < dataList.get(position).CheckItems.size() - 1; i++) {
                CheckItem.append(dataList.get(position).CheckItems.get(i).CheckItemName + "、");
            }
            CheckItem.append(dataList.get(position).CheckItems.get(dataList.get(position).CheckItems.size() - 1).CheckItemName);
            tvCheckItem.setText(CheckItem.toString());
            initChildList(view, position);
            return view;
        }

        private void initChildList(View view, int position) {
            ChildListView mChildList = UIHelper.getAdapterView(view, R.id.listView_report_detail_child);
            List<ReportDetailBean.CheckResult> childData = new ArrayList<ReportDetailBean.CheckResult>();
            for (int i = 0; i < dataList.get(position).CheckItems.size(); i++) {
                childData.addAll(dataList.get(position).CheckItems.get(i).CheckResults);
            }
            ChildListAdapter childAdapter = new ChildListAdapter(childData);
            mChildList.setAdapter(childAdapter);
        }

        class ChildListAdapter extends BaseAdapter {
            private List<ReportDetailBean.CheckResult> childData;

            public ChildListAdapter(List<ReportDetailBean.CheckResult> childData) {
                this.childData = childData;
            }

            @Override
            public int getCount() {
                return childData.size();
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
                tvTitle.setText(childData.get(position).CheckIndexName);
                return view;
            }

        }

    }


}
