
package haozuo.com.healthdoctor.view.custom;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.bean.CustomDetailBean;
import haozuo.com.healthdoctor.bean.ReportParamsBean;
import haozuo.com.healthdoctor.bean.RequestPhotoReportListBean;
import haozuo.com.healthdoctor.contract.CustomDetailContract;
import haozuo.com.healthdoctor.presenter.IBasePresenter;
import haozuo.com.healthdoctor.util.UIHelper;
import haozuo.com.healthdoctor.view.base.AbstractView;
import haozuo.com.healthdoctor.view.threePart.common.ChildGridView;
import haozuo.com.healthdoctor.view.threePart.common.ChildListView;

public class CustomDetailFragment extends AbstractView implements CustomDetailContract.ICustomDetailView {
    Context mContext;
    View rootView;
    String Cphoto;
    CustomDetailContract.ICustomDetailPresenter mCustomDetailPresenter;
    private ReportParamsAdapter mReportParamsAdapter;
    private PhotoReportAdapter mPhotoReportAdapter;
    private CustomDetailBean mCustomInfo;
    private List<RequestPhotoReportListBean> dataSource;
    @Bind(R.id.drawee_CPhoto)
    SimpleDraweeView CPhoto;
    @Bind(R.id.tv_CName)
    TextView CName;
    @Bind(R.id.tv_CGender)
    TextView CGender;
    @Bind(R.id.tv_CAge)
    TextView CAge;
    @Bind(R.id.tv_Cphone)
    TextView Cphone;
    @Bind(R.id.btn_go_into)
    ImageView btn_go_into;
    @Bind(R.id.lv_custom_report)
    ChildListView lv_custom_report;
    @Bind(R.id.gv_PhotoReport)
    ChildGridView gv_PhotoReport;

    @OnClick(R.id.btn_show_Report)
    public void showReport() {
        if (lv_custom_report.getVisibility() == View.GONE) {
            lv_custom_report.setVisibility(View.VISIBLE);
        } else {
            lv_custom_report.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.btn_show_PhotoReport)
    public void showPhotoReport() {
        if (gv_PhotoReport.getVisibility() == View.GONE) {
            gv_PhotoReport.setVisibility(View.VISIBLE);
        } else {
            gv_PhotoReport.setVisibility(View.GONE);
        }
    }

    public CustomDetailFragment() {
    }

    @Override
    protected IBasePresenter getPresenter() {
        return mCustomDetailPresenter;
    }

    @Override
    protected View getRootView() {
        return rootView;
    }

    public static CustomDetailFragment newInstance() {
        CustomDetailFragment fragment = new CustomDetailFragment();
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            mContext = getContext();
            rootView = inflater.inflate(R.layout.fragment_custom_detail, container, false);
            ButterKnife.bind(this, rootView);
            mReportParamsAdapter = new ReportParamsAdapter(mContext);
            lv_custom_report.setAdapter(mReportParamsAdapter);
            lv_custom_report.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    ReportParamsBean bean = mReportParamsAdapter.getDataSource().get(position);
                    bean.customerId = getActivity().getIntent().getIntExtra(CustomDetailActivity.EXTRA_CUSTOMER_ID, 0);
                    Intent intent = new Intent(getActivity(), CustomerReportActivity.class);
                    intent.putExtra(CustomerReportActivity.REPORTPARAMSBEAN, bean);
                    startActivity(intent);
                }
            });
            mPhotoReportAdapter = new PhotoReportAdapter(mContext);
            gv_PhotoReport.setAdapter(mPhotoReportAdapter);
            gv_PhotoReport.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    startActivity(new Intent(mContext, PhotoPreviewActivity.class)
                            .putExtra(PhotoPreviewActivity.EXTRA_URL_LIST, dataSource.get(position).Content));
                    getActivity().overridePendingTransition(R.anim.photopreview_enter_anim, R.anim.photopreview_exit_anim);
                }
            });
            mCustomDetailPresenter.start();
        }
        registerCustomReceiver(BROADFILTER_CUSTOM_DELETEGROUP);
        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
        mCustomDetailPresenter.cancelRequest();
    }

    @Override
    public void setPresenter(CustomDetailContract.ICustomDetailPresenter presenter) {
        mCustomDetailPresenter = presenter;
    }

    @Override
    public void changeRetryLayer(boolean isShow) {
        if (!isShow) {
            showRetryLayer(R.id.rLayout);
//            showRetryLayer(R.id.rLayout, true, getString(R.string.connect_fail));
        } else {
            hideRetryLayer(R.id.rLayout);
        }
    }

    @Override
    public void RefreshReportParams(List<ReportParamsBean> ReportParamList) {
        mReportParamsAdapter.RefreshReportParams(ReportParamList);
    }

    @Override
    public void RefreshPhotoReport(List<RequestPhotoReportListBean> RequestPhotoReportList) {
        mPhotoReportAdapter.RefreshPhotoReportAdapter(RequestPhotoReportList);
    }

    @Override
    public void InitView(CustomDetailBean custom) {
        mCustomInfo = custom;
        UIHelper.setFrescoURL(CPhoto, custom.PhotoUrl,
                "res://haozuo.com.healthdoctor.view.custom/" + R.drawable.user_default_url);
        CName.setText(custom.Cname);
        CGender.setText(custom.GetSex());
        CAge.setText(custom.GetAge());
        Cphone.setText(custom.Mobile);
        btn_go_into.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CustomerInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(CustomerInfoActivity.EXTRA_CUSTOMER_INFO, mCustomInfo);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onReceiveBroadcast(String filterAction, Intent intent) {
        if (filterAction.equals(BROADFILTER_CUSTOM_DELETEGROUP)) {
            mCustomInfo = (CustomDetailBean) intent.getSerializableExtra(CustomerInfoFragment.DELETED_GROUP_ID);
            InitView(mCustomInfo);
        }
    }

    class ReportParamsAdapter extends BaseAdapter {
        private LayoutInflater mInflater;
        private List<ReportParamsBean> dataSource;

        public ReportParamsAdapter(Context context) {
            this.mInflater = LayoutInflater.from(context);
            dataSource = new ArrayList<>();
        }

        public List<ReportParamsBean> getDataSource() {
            return dataSource;
        }

        public void RefreshReportParams(List<ReportParamsBean> dataList) {
            dataSource.clear();
            dataSource.addAll(dataList);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return dataSource.size();
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
                convertView = mInflater.inflate(R.layout.lvitem_custom_report, null);
            }
            TextView tv_CName = UIHelper.getAdapterView(convertView, R.id.tv_CName);
            TextView tv_CGender = UIHelper.getAdapterView(convertView, R.id.tv_CGender);
            TextView tv_CAge = UIHelper.getAdapterView(convertView, R.id.tv_CAge);
            TextView tv_DeptName = UIHelper.getAdapterView(convertView, R.id.tv_DeptName);
            TextView tv_ReportDate = UIHelper.getAdapterView(convertView, R.id.tv_ReportDate);
            TextView tv_ReportCode = UIHelper.getAdapterView(convertView, R.id.tv_ReportCode);
            TextView tv_CCompany = UIHelper.getAdapterView(convertView, R.id.tv_CCompany);

            ReportParamsBean ReportParamsEntity = dataSource.get(position);

            tv_CName.setText(mCustomInfo.Cname);
            tv_CGender.setText(mCustomInfo.GetSex());
            tv_CAge.setText(mCustomInfo.GetAge());
            tv_DeptName.setText(ReportParamsEntity.CheckUnitName);
//            tv_ReportDate.setText(ReportParamsEntity.CheckDate);
            tv_ReportDate.setText(ReportParamsEntity.getFormedDate());
            tv_ReportCode.setText(ReportParamsEntity.CheckUnitCode);
            tv_CCompany.setText(ReportParamsEntity.WorkNo);

            return convertView;
        }
    }

        class PhotoReportAdapter extends BaseAdapter {
        private LayoutInflater mInflater;
//        private List<RequestPhotoReportListBean> dataSource;

        public PhotoReportAdapter(Context context) {
            this.mInflater = LayoutInflater.from(context);
            dataSource = new ArrayList<>();
        }

        public void RefreshPhotoReportAdapter(List<RequestPhotoReportListBean> dataList) {
            dataSource.clear();
            dataSource.addAll(dataList);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return dataSource.size();
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
                convertView = mInflater.inflate(R.layout.gvitem_photoreport, null);
            }
            TextView tv_Count = UIHelper.getAdapterView(convertView, R.id.tv_Count);
            TextView tv_PhotoReport_Date = UIHelper.getAdapterView(convertView, R.id.tv_PhotoReport_Date);
            TextView tv_PhotoReport_Dept = UIHelper.getAdapterView(convertView, R.id.tv_PhotoReport_Dept);

            final RequestPhotoReportListBean RequestPhotoReportEntity = dataSource.get(position);
            tv_Count.setText("共" + RequestPhotoReportEntity.ImageUrlList.size() + "张");
            tv_PhotoReport_Date.setText(RequestPhotoReportEntity.Date);
            tv_PhotoReport_Dept.setText(RequestPhotoReportEntity.HealthCompanyName);

            return convertView;
        }

    }

}


