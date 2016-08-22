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
import haozuo.com.healthdoctor.contract.CustomerReportContract;
import haozuo.com.healthdoctor.contract.IBasePresenter;
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


    public CustomerReportFragment() {

    }

    @Override
    protected IBasePresenter getPresenter() {
        return null;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootVeiw == null) {
            rootVeiw = inflater.inflate(R.layout.fragment_customer_report, container, false);
            ButterKnife.bind(this, rootVeiw);
            initView();
        }
        return rootVeiw;
    }

    private void initView() {
        PagerAdapter pagerAdapter = new ReportPagerAdapter(getChildFragmentManager());
        mViewPager.setAdapter(pagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
    }

    @Override
    public void setPresenter(CustomerReportContract.ICustomerReportPresenter presenter) {
    }


    class ReportPagerAdapter extends FragmentPagerAdapter {
        private List<String> titleList = new ArrayList();

        public ReportPagerAdapter(FragmentManager fm) {
            super(fm);
            fragmentList.add(new ReportBaseFragment());
            fragmentList.add(new ReportAllFragment());
            fragmentList.add(new ReportBadFragment());
            fragmentList.add(new ReportDetailFragment());
            titleList.add("基本信息");
            titleList.add("体检汇总");
            titleList.add("体检异常");
            titleList.add("体检详情");
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titleList.get(position);
        }
    }
}