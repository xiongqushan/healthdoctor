package haozuo.com.healthdoctor.view.custom;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.bean.ReportDetailBean;
import haozuo.com.healthdoctor.contract.CustomerReportContract;
import haozuo.com.healthdoctor.presenter.IBasePresenter;
import haozuo.com.healthdoctor.view.base.AbstractView;

/**
 * by zy  2016.08.19
 */
public class CustomerReportFragment extends AbstractView implements CustomerReportContract.ICustomerReportView {
    @Bind(R.id.tabLayout)
    TabLayout mTabLayout;
    @Bind(R.id.viewPager)
    ViewPager mViewPager;

    private View rootVeiw;
    private List<Fragment> fragmentList = new ArrayList();
    CustomerReportContract.ICustomerReportPresenter mIReportPresenter;


    public CustomerReportFragment() {

    }

    @Override
    protected IBasePresenter getPresenter() {
        return mIReportPresenter;
    }

    @Override
    protected View getRootView() {
        return rootVeiw;
    }

    public static CustomerReportFragment newInstance() {
        CustomerReportFragment fragment = new CustomerReportFragment();
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootVeiw == null) {
            rootVeiw = inflater.inflate(R.layout.fragment_customer_report, container, false);
            ButterKnife.bind(this, rootVeiw);
            initView();
            mIReportPresenter.start();
        }
        return rootVeiw;
    }

    private void initView() {
        PagerAdapter pagerAdapter = new ReportPagerAdapter(getChildFragmentManager());
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setOffscreenPageLimit(4);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void setPresenter(CustomerReportContract.ICustomerReportPresenter presenter) {
        mIReportPresenter = presenter;
    }

    @Override
    public void onStop() {
        super.onStop();
        mIReportPresenter.cancelRequest();
    }

    @Override
    public void changeRetryLayer(boolean isShow) {
        if (isShow) {
            showRetryLayer(R.id.layerLayout);
        } else {
            hideRetryLayer(R.id.layerLayout);
        }
    }

    @Override
    public void updateChildUI(ReportDetailBean bean) {
        ReportBaseFragment base = (ReportBaseFragment) fragmentList.get(0);
        ReportAllFragment all = (ReportAllFragment) fragmentList.get(1);
        ReportBadFragment bad = (ReportBadFragment) fragmentList.get(2);
        ReportDetailFragment detail = (ReportDetailFragment) fragmentList.get(3);
        base.updateUI(bean);
        all.updateUI(bean);
        bad.updateUI(bean);
        detail.updateUI(bean);
    }


    class ReportPagerAdapter extends FragmentPagerAdapter {
        private List<String> titleList = new ArrayList();

        public ReportPagerAdapter(FragmentManager fm) {
            super(fm);
            fragmentList.add(new ReportBaseFragment());
            fragmentList.add(new ReportAllFragment());
            fragmentList.add(new ReportBadFragment());
            fragmentList.add(new ReportDetailFragment());
            titleList.add("概况");
            titleList.add("汇总");
            titleList.add("异常");
            titleList.add("详情");
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return titleList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titleList.get(position);
        }
    }
}
