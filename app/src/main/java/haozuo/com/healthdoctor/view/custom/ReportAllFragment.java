package haozuo.com.healthdoctor.view.custom;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.util.UIHelper;

/**
 * by zy 2016.08.22
 */
public class ReportAllFragment extends Fragment {

    @Bind(R.id.listView_report_all)
    ListView mListView;

    private View rootView;
//    private List<String> dataList = new ArrayList<String>();

    public ReportAllFragment() {
        // Required empty public constructor
    }

    public static ReportAllFragment newInstance() {
        ReportAllFragment fragment = new ReportAllFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_report_all, container, false);
            ButterKnife.bind(this, rootView);
            initView();
        }
        return rootView;
    }

    private void initView() {
        ListAdapter adapter = new ListAdapter();
        mListView.setAdapter(adapter);
    }

    class ListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 15;
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
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = LayoutInflater.from(getActivity()).inflate(R.layout.lvitem_reportall_layout, null);
            }
            TextView tvTitle = UIHelper.getAdapterView(view, R.id.tvTitle);
            TextView tvSubtitle = UIHelper.getAdapterView(view, R.id.tvSubtitle);
            return view;
        }
    }

}
