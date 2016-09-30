package haozuo.com.healthdoctor.view.custom;


import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.bean.ReportDetailBean;
import haozuo.com.healthdoctor.util.StringUtil;
import haozuo.com.healthdoctor.util.UIHelper;
import haozuo.com.healthdoctor.view.threePart.common.ChildListView;
import retrofit.http.HEAD;

/**
 * by zy 2016.08.22.
 */
public class ReportDetailFragment extends Fragment {

    @Bind(R.id.listView_report_detail)
    ListView mListView;
    @Bind(R.id.listView_report_detail_hide)
    ListView lvHide;
    @Bind(R.id.divider_report_detail)
    View dividerList;


    @Bind(R.id.imgShowHideList)
    ImageView imgShowHideList;

    @OnClick(R.id.imgShowHideList)
    void showHideList() {
        if (lvHide.getVisibility() == View.GONE) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    lvHide.setVisibility(View.VISIBLE);
                    dividerList.setVisibility(View.VISIBLE);
                }
            }, 800);
            showListAnima();
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    lvHide.setVisibility(View.GONE);
                    dividerList.setVisibility(View.GONE);
                }
            }, 800);
            hideListAnima();
        }
    }

    private void showListAnima() {
        if (mAnimaRotateShow == null) {
            mAnimaRotateShow = AnimationUtils.loadAnimation(getActivity(), R.anim.rotating_show);
        }
        imgShowHideList.startAnimation(mAnimaRotateShow);
    }

    private void hideListAnima() {
        if (mAnimaRotateHide == null) {
            mAnimaRotateHide = AnimationUtils.loadAnimation(getActivity(), R.anim.rotating_hide);
        }
        imgShowHideList.startAnimation(mAnimaRotateHide);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private Animation mAnimaRotateShow = null;
    private Animation mAnimaRotateHide = null;

    private ListAdapter adapter;
    private View rootView;
    private List<ReportDetailBean.DepartmentInfoVM> dataList = new ArrayList<ReportDetailBean.DepartmentInfoVM>();
    private ListHideAdapter adapterHide;

    public ReportDetailFragment() {
    }


    public static ReportDetailFragment newInstance() {
        ReportDetailFragment fragment = new ReportDetailFragment();
        return fragment;
    }

    public void updateUI(ReportDetailBean bean) {
        dataList = bean.DepartmentCheck;
        if (dataList.size() == 0) {
            mListView.setVisibility(View.INVISIBLE);
            return;
        }
        adapter.notifyDataSetChanged();
        adapterHide.refresh(bean.DepartmentCheck);
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
        View header = LayoutInflater.from(getActivity()).inflate(R.layout.headerview_reportdetail_layout, null);
        mListView.addHeaderView(header);
        adapter = new ListAdapter();
        mListView.setAdapter(adapter);
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            private int position = 0;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (position != firstVisibleItem) {
                    position = firstVisibleItem;
                    if (firstVisibleItem - mListView.getHeaderViewsCount() > -1)
                        adapterHide.setSelectedPosition(firstVisibleItem - mListView.getHeaderViewsCount());
                }

            }
        });

        adapterHide = new ListHideAdapter();
        lvHide.setAdapter(adapterHide);
        lvHide.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapterHide.setSelectedPosition(position);
                mListView.setSelection(position + mListView.getHeaderViewsCount());
            }
        });
    }

    class ListHideAdapter extends BaseAdapter {
        private List<ReportDetailBean.DepartmentInfoVM> list = new ArrayList<ReportDetailBean.DepartmentInfoVM>();
        private int selectedPosition = 0;

        public void setSelectedPosition(int position) {
            selectedPosition = position;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return list.size();
        }

        public void refresh(List<ReportDetailBean.DepartmentInfoVM> list) {
            this.list.addAll(list);
            notifyDataSetChanged();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getActivity()).inflate(
                        R.layout.lvitem_report_detail_hide_layout, null);
            }
            TextView tvName = UIHelper.getAdapterView(convertView, R.id.tvName);
            TextView tvMark = UIHelper.getAdapterView(convertView, R.id.tvMark);
            tvName.setText(list.get(position).DepartmentName);
            if (selectedPosition == position) {
                tvName.setTextColor(Color.parseColor("#FF3AB4F1"));
                tvMark.setBackgroundColor(Color.parseColor("#FF3AB4F1"));
            } else {
                tvName.setTextColor(Color.parseColor("#FF282828"));
                tvMark.setBackgroundColor(Color.parseColor("#FFFFFFFF"));

            }
            return convertView;
        }
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
//            TextView tvCheckItem = UIHelper.getAdapterView(view, R.id.tvCheckItem);
            LinearLayout layoutFooter = UIHelper.getAdapterView(view, R.id.layoutFooter);

            if (position + 1 == dataList.size()) {
                layoutFooter.setVisibility(View.VISIBLE);
            } else {
                layoutFooter.setVisibility(View.GONE);
            }
            tvMainItem.setText(dataList.get(position).DepartmentName);
//            StringBuffer CheckItem = new StringBuffer();
//            for (int i = 0; i < dataList.get(position).CheckItems.size() - 1; i++) {
//                CheckItem.append(dataList.get(position).CheckItems.get(i).CheckItemName + "、");
//            }
//            CheckItem.append(dataList.get(position).CheckItems.get(dataList.get(position).CheckItems.size() - 1).CheckItemName);
//            tvCheckItem.setText(CheckItem.toString());
            initChildList(view, position);
            return view;
        }

        private void initChildList(View view, int position) {
            ChildListView mChildList = UIHelper.getAdapterView(view, R.id.listView_report_detail_child);
            List<ReportDetailBean.CheckItem> childData = dataList.get(position).CheckItems;
//            for (int i = 0; i < dataList.get(position).CheckItems.size(); i++) {
//                childData.addAll(dataList.get(position).CheckItems.get(i).CheckResults);
//            }
            ChildListAdapter childAdapter = new ChildListAdapter(childData);
            mChildList.setAdapter(childAdapter);
        }

        class ChildListAdapter extends BaseAdapter {
            private List<ReportDetailBean.CheckItem> childData;

            public ChildListAdapter(List<ReportDetailBean.CheckItem> childData) {
                this.childData = childData;
            }

            @Override
            public int getCount() {
                if (childData == null) {
                    return 0;
                }
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
                    view = LayoutInflater.from(getActivity()).inflate(R.layout.lvitem_reportdetail_child_layout, null);
                }
                TextView tvCheckItem = UIHelper.getAdapterView(view, R.id.tvCheckItem);
                tvCheckItem.setText(childData.get(position).CheckItemName);
                initSunList(view, position);
                return view;
            }

            private void initSunList(View view, int position) {
                ChildListView sunList = UIHelper.getAdapterView(view, R.id.listView_report_detail_sun);
                List<ReportDetailBean.CheckResult> sunData = childData.get(position).CheckResults;
                SunListAdapter sunAdapter = new SunListAdapter(sunData);
                sunList.setAdapter(sunAdapter);
            }

            class SunListAdapter extends BaseAdapter {
                private List<ReportDetailBean.CheckResult> sunData;

                public SunListAdapter(List<ReportDetailBean.CheckResult> sunData) {
                    this.sunData = sunData;
                }

                @Override
                public int getCount() {
                    if (sunData == null) {
                        return 0;
                    }
                    return sunData.size();
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
                        view = LayoutInflater.from(getActivity()).inflate(R.layout.lvitem_reportdetail_sun_layout, null);
                    }
                    TextView tvTitle = UIHelper.getAdapterView(view, R.id.tvTitle);
                    TextView tvSubtitle = UIHelper.getAdapterView(view, R.id.tvSubtitle);
                    TextView tvValue = UIHelper.getAdapterView(view, R.id.tvResultValue);
                    TextView tvUnit = UIHelper.getAdapterView(view, R.id.tvUnit);
                    tvTitle.setText(sunData.get(position).CheckIndexName);
                    if (sunData.get(position).IsAnomaly) {
                        tvTitle.setTextColor(Color.parseColor("#FFFA7981"));
                        tvValue.setTextColor(Color.parseColor("#FFFA7981"));
                    } else {
                        tvTitle.setTextColor(Color.parseColor("#FF666666"));
                        tvValue.setTextColor(Color.parseColor("#FF666666"));
                    }
                    boolean unitEmpty = StringUtil.isTrimEmpty(sunData.get(position).Unit);
                    if (unitEmpty) {
                        tvValue.setVisibility(View.GONE);
                        tvUnit.setVisibility(View.GONE);
                        String resultValue = sunData.get(position).ResultValue;
                        if (resultValue.equals("")) resultValue = "正常";
                        tvSubtitle.setText(resultValue);
                    } else {
                        tvValue.setVisibility(View.VISIBLE);
                        tvUnit.setVisibility(View.VISIBLE);
                        boolean subEmpty = StringUtil.isTrimEmpty(sunData.get(position).TextRef);
                        if (subEmpty) {
                            tvSubtitle.setText("");
                        } else {
                            tvSubtitle.setText("参考范围 : " + sunData.get(position).TextRef);
                        }
                        tvValue.setText(sunData.get(position).ResultValue);
                        tvUnit.setText("单位 : " + sunData.get(position).Unit);
                    }
                    return view;
                }

            }

        }


    }


}
