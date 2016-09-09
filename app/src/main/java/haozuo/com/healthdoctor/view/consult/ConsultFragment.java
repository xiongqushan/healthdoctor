package haozuo.com.healthdoctor.view.consult;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.bean.ConsultDoneItemBean;
import haozuo.com.healthdoctor.bean.ConsultItemBean;
import haozuo.com.healthdoctor.bean.FeedbackItemBean;
import haozuo.com.healthdoctor.contract.ConsultContract;
import haozuo.com.healthdoctor.presenter.IBasePresenter;
import haozuo.com.healthdoctor.view.base.AbstractView;

/**
 * Created by hzguest3 on 2016/8/1.
 */
public class ConsultFragment extends AbstractView implements ConsultContract.IConsultView {
    Context mContext;
    View rootView;
    public ConsultContract.IConsultPresenter mConsultPresenter;
    @Bind(R.id.consult_Tab)
    TabLayout tabLayout;
    @Bind(R.id.consult_pager_pending)
    ViewPager pagerPending;
    @Bind(R.id.consult_pager_done)
    ViewPager pagerDone;
    @Bind(R.id.consult_pager_feedback)
    ViewPager pagerFeedback;
    @Bind(R.id.tv_msgcount)
    TextView tvMsgCount;
    @Bind(R.id.groupConsult)
    RadioGroup mRadioGroup;
    @Bind(R.id.rbPending)
    RadioButton rbPending;
    private SimpleFragmentPagerAdapter pendingAdapter;
    private DonePagerAdapter doneAdapter;
    private FeedBackPagerAdapter feedBackAdapter;

    public ConsultFragment() {
    }

    @Override
    protected IBasePresenter getPresenter() {
        return mConsultPresenter;
    }

    @Override
    protected View getRootView() {
        return rootView;
    }

    public static ConsultFragment newInstance() {
        ConsultFragment fragment = new ConsultFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = getContext();
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_consult, container, false);
            ButterKnife.bind(this, rootView);
            InitView();

            registerCustomReceiver(BROADFILTER_CONSULT_REPLAY);
        }
        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
        mConsultPresenter.cancelRequest();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void setPresenter(ConsultContract.IConsultPresenter presenter) {
        mConsultPresenter = presenter;
    }

    public void InitView() {
//        ConsultFragment fragment = new ConsultFragment();

//        ConsultFragment.SimpleFragmentPagerAdapter pagerAdapter =
//                fragment.new SimpleFragmentPagerAdapter(getChildFragmentManager());
        pendingAdapter = new SimpleFragmentPagerAdapter(getChildFragmentManager());
        pagerPending.setAdapter(pendingAdapter);
        tabLayout.setupWithViewPager(pagerPending);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                pagerFeedback.setVisibility(View.GONE);
                pagerPending.setVisibility(View.GONE);
                pagerDone.setVisibility(View.GONE);
                if (id == R.id.rbPending) {
                    pagerPending.setVisibility(View.VISIBLE);
                    tabLayout.setupWithViewPager(pagerPending);
                }
                if (id == R.id.rbDone) {
                    if (doneAdapter == null) {
                        doneAdapter = new DonePagerAdapter(getChildFragmentManager());
                        pagerDone.setAdapter(doneAdapter);
                    }
                    pagerDone.setVisibility(View.VISIBLE);
                    tabLayout.setupWithViewPager(pagerDone);
                }
                if (id == R.id.rbFeedback) {
                    if (feedBackAdapter == null) {
                        feedBackAdapter = new FeedBackPagerAdapter(getChildFragmentManager());
                        pagerFeedback.setAdapter(feedBackAdapter);
                    }
                    pagerFeedback.setVisibility(View.VISIBLE);
                    tabLayout.setupWithViewPager(pagerFeedback);
                }

            }
        });
    }


    private List<ConsultPandingFragment> pendingList = new ArrayList<ConsultPandingFragment>();

    public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {

        private String tabTitles[] = new String[]{"全部", "转入", "我的"};

        public SimpleFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
            pendingList.add(ConsultPandingFragment.newInstance(3));
            pendingList.add(ConsultPandingFragment.newInstance(2));
            pendingList.add(ConsultPandingFragment.newInstance(1));
        }

        @Override
        public Fragment getItem(int position) {
            return pendingList.get(position);
        }

        @Override
        public int getCount() {
            return pendingList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }

    }

    private List<ConsultDoneFragment> doneList = new ArrayList<ConsultDoneFragment>();

    public class DonePagerAdapter extends FragmentPagerAdapter {
        private String tabTitles[] = new String[]{"当天", "本周", "本月"};

        public DonePagerAdapter(FragmentManager fm) {
            super(fm);
            doneList.add(ConsultDoneFragment.newInstance(1));
            doneList.add(ConsultDoneFragment.newInstance(2));
            doneList.add(ConsultDoneFragment.newInstance(3));
        }

        @Override
        public Fragment getItem(int position) {
            return doneList.get(position);
        }

        @Override
        public int getCount() {
            return doneList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }

    }

    private List<FeedbackFragment> feedbackList = new ArrayList<FeedbackFragment>();

    public class FeedBackPagerAdapter extends FragmentPagerAdapter {
        private String tabTitles[] = new String[]{"全部", "已反馈", "未反馈"};

        public FeedBackPagerAdapter(FragmentManager fm) {
            super(fm);
            feedbackList.add(FeedbackFragment.newInstance(3));
            feedbackList.add(FeedbackFragment.newInstance(2));
            feedbackList.add(FeedbackFragment.newInstance(1));
        }

        @Override
        public Fragment getItem(int position) {
            return feedbackList.get(position);
        }

        @Override
        public int getCount() {
            return feedbackList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }

    }

    // TODO 实现view接口
    @Override
    public void refreshPendingPageList(List<ConsultItemBean> dataList, int flag, boolean isRefresh) {
        if (flag == 3) {
            pendingList.get(0).refreshConsultPendingList(dataList, isRefresh);
        }
        if (flag == 2) {
            pendingList.get(1).refreshConsultPendingList(dataList, isRefresh);
        }
        if (flag == 1) {
            pendingList.get(2).refreshConsultPendingList(dataList, isRefresh);
        }
    }

    @Override
    public void refreshFinish(int status, int flag, boolean isRefresh) {
        if (flag == 3) {
            pendingList.get(0).refreshFinish(status, isRefresh);
        }
        if (flag == 2) {
            pendingList.get(1).refreshFinish(status, isRefresh);
        }
        if (flag == 1) {
            pendingList.get(2).refreshFinish(status, isRefresh);
        }
    }

    @Override
    public void refreshConsultDonePageList(List<ConsultDoneItemBean> dataList, int flag, boolean isRefresh) {
        doneList.get(flag - 1).refreshConsultDoneList(dataList, isRefresh);
    }


    @Override
    public void refreshConsultDonePageFinish(int status, int flag, boolean isRefresh) {
        doneList.get(flag - 1).refreshFinish(status, isRefresh);
    }

    @Override
    public void refreshFeedbackPageList(List<FeedbackItemBean> dataList, int flag, boolean isRefresh) {
        if (flag == 1) {
            feedbackList.get(2).refreshFeedbackList(dataList, isRefresh);
        }
        if (flag == 2) {
            feedbackList.get(1).refreshFeedbackList(dataList, isRefresh);
        }
        if (flag == 3) {
            feedbackList.get(0).refreshFeedbackList(dataList, isRefresh);
        }

    }

    @Override
    public void refreshFeedbackPageFinish(int status, int flag, boolean isRefresh) {
        if (flag == 1) {
            feedbackList.get(2).refreshFinish(status, isRefresh);
        }
        if (flag == 2) {
            feedbackList.get(1).refreshFinish(status, isRefresh);
        }
        if (flag == 3) {
            feedbackList.get(0).refreshFinish(status, isRefresh);
        }
    }

    @Override
    public void updateMsgCounts(int newCount) {
        tvMsgCount.setText(newCount + "");
    }

    @Override//TODO 添加返回信息
    protected void onReceiveBroadcast(String filterAction, Intent intent) {
        super.onReceiveBroadcast(filterAction,intent );
        if (filterAction == BROADFILTER_CONSULT_REPLAY) {
            refreshCustomList(3, true);
            if (rbPending.isChecked()) {
                pagerPending.setCurrentItem(0);
            }
        }
    }

    public void refreshFeedBackList(int flag, boolean initData) {
        mConsultPresenter.refreshFeedBackList(flag, initData);
    }

    public void loadmoreFeedBackList(int flag, int pageIndex) {
        mConsultPresenter.loadmoreFeedBackList(flag, pageIndex);
    }

    public void refreshConsultDoneList(int flag, boolean initData) {
        mConsultPresenter.refreshConsultDoneList(flag, initData);
    }

    public void loadmoreConsultDoneList(int flag, int pageIndex) {
        mConsultPresenter.loadmoreConsultDoneList(flag, pageIndex);
    }

    public void refreshCustomList(int flag, boolean initData) {
        mConsultPresenter.refreshCustomList(flag, initData);
    }

    public void loadmoreCustomList(int flag, int pageIndex) {
        mConsultPresenter.loadmoreCustomList(flag, pageIndex);
    }

}
