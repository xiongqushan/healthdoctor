package haozuo.com.healthdoctor.view.custom;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        PagerAdapter pagerAdapter = new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return null;
            }

            @Override
            public int getCount() {
                return 4;
            }
        };
        mViewPager.setAdapter(pagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
    }

    @Override
    public void setPresenter(CustomerReportContract.ICustomerReportPresenter presenter) {
    }
}
