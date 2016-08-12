package haozuo.com.healthdoctor.view.consult;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.bean.ConsultItemBean;
import haozuo.com.healthdoctor.contract.ConsultContract;
import haozuo.com.healthdoctor.view.base.AbstractView;
import haozuo.com.healthdoctor.view.threePart.common.PageFragment;

/**
 * Created by hzguest3 on 2016/8/1.
 */
public class ConsultFragment extends AbstractView implements ConsultContract.IConsultView {
    Context mContext;
    View rootView;
    public ConsultContract.IConsultPresenter ConsultPresenter;
    private OnPendingPageListener mPendingPageListener;
    @Bind(R.id.consult_Tab) TabLayout tabLayout;
    @Bind(R.id.consult_Vp) ViewPager viewPager;

    public ConsultFragment(){};

    public static ConsultFragment newInstance(){
        ConsultFragment fragment = new ConsultFragment();
        return fragment;
    }

    @Override
    public void onResume(){
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext=getContext();
        if(rootView==null){
            rootView= inflater.inflate(R.layout.fragment_consult, container, false);
            ButterKnife.bind(this,rootView);
        }
        InitView();
        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
        ConsultPresenter.cancelRequest();
    }

    @Override
    public void setPresenter(ConsultContract.IConsultPresenter presenter) {
        ConsultPresenter=presenter;
    }

    public void InitView() {
        ConsultFragment fragment = new ConsultFragment();
//        ConsultFragment.SimpleFragmentPagerAdapter pagerAdapter = fragment.new SimpleFragmentPagerAdapter(getActivity().getSupportFragmentManager());
        ConsultFragment.SimpleFragmentPagerAdapter pagerAdapter = fragment.new SimpleFragmentPagerAdapter(getChildFragmentManager());

        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
    }

    public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {
        private String tabTitles[] = new String[]{"待处理","已处理","问题反馈"};
        private List<Fragment> fragList = new ArrayList<Fragment>(){};
        public SimpleFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 1:
                    return PageFragment.newInstance(2);
                case 2:
                    return PageFragment.newInstance(3);
                default:
                    return ConsultPandingFragment.newInstance();
            }
        }

        @Override
        public int getCount() {
            return tabTitles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }

    }

    public void setOnPendingRefreshListener(OnPendingPageListener pendingPageListener) {
        mPendingPageListener = pendingPageListener;
    }


    @Override
    public void RefreshPendingPageList(List<ConsultItemBean> consultItemBeanList) {
        if(mPendingPageListener!=null){
            mPendingPageListener.refreshConsultDetailList(consultItemBeanList);
        }
    }

    @Override
    public void refreshFinish(int status) {
        if (mPendingPageListener!=null){
            mPendingPageListener.refreshFinish(status);
        }
    }

    public interface OnPendingPageListener {
        void refreshConsultDetailList(List<ConsultItemBean> consultDetailBeanList);

        void refreshFinish(int status);
    }

}
