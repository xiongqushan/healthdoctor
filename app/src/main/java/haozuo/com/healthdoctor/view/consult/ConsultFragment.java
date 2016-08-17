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
    @Bind(R.id.consult_Tab)
    TabLayout tabLayout;
    @Bind(R.id.consult_pager_pending)
    ViewPager pagerPending;
    @Bind(R.id.consult_pager_done)
    ViewPager pagerDone;
    @Bind(R.id.consult_pager_feedback)
    ViewPager pagerFeedback;
    @Bind(R.id.tv_msgnum)
    TextView tvMsgNum;
    @Bind(R.id.groupConsult)
    RadioGroup mRadioGroup;
    private SimpleFragmentPagerAdapter pendingAdapter;
    private DonePagerAdapter doneAdapter;
    private FeedBackPagerAdapter feedBackAdapter;

    public ConsultFragment() {
    }

    public static ConsultFragment newInstance() {
        ConsultFragment fragment = new ConsultFragment();
        return fragment;
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = getContext();
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_consult, container, false);
            ButterKnife.bind(this, rootView);
            InitView();
        }
        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
        ConsultPresenter.cancelRequest();
    }

    @Override
    public void setPresenter(ConsultContract.IConsultPresenter presenter) {
        ConsultPresenter = presenter;
    }

    public void InitView() {
//        ConsultFragment fragment = new ConsultFragment();

//        ConsultFragment.SimpleFragmentPagerAdapter pagerAdapter =
//                fragment.new SimpleFragmentPagerAdapter(getChildFragmentManager());
        pendingAdapter = new SimpleFragmentPagerAdapter(getActivity().getSupportFragmentManager());
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
                        doneAdapter = new DonePagerAdapter(getActivity().getSupportFragmentManager());
                        pagerDone.setAdapter(doneAdapter);
                    }
                    pagerDone.setVisibility(View.VISIBLE);
                    tabLayout.setupWithViewPager(pagerDone);
                }
                if (id == R.id.rbFeedback) {
                    if (feedBackAdapter == null) {
                        feedBackAdapter = new FeedBackPagerAdapter(getActivity().getSupportFragmentManager());
                        pagerFeedback.setAdapter(feedBackAdapter);
                    }
                    pagerFeedback.setVisibility(View.VISIBLE);
                    tabLayout.setupWithViewPager(pagerFeedback);
                }

            }
        });
    }


    public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {
        private String tabTitles[] = new String[]{"全部", "转入", "我的"};

        public SimpleFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        private List<Fragment> fragList = new ArrayList<Fragment>() {
        };

        @Override
        public Fragment getItem(int position) {
            switch (position) {
//                case 1:
//                    return ConsultPandingFragment.newInstance(2);
//                case 2:
//                    return ConsultPandingFragment.newInstance(1);
//                default:
//                    return ConsultPandingFragment.newInstance(3);
                case 1:
                    return PageFragment.newInstance(2);
                case 2:
                    return PageFragment.newInstance(3);
                default:
                    return ConsultPandingFragment.newInstance(3);
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

    public class DonePagerAdapter extends FragmentPagerAdapter {
        private String tabTitles[] = new String[]{"当天", "本周", "本月"};

        public DonePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 1:
                    return PageFragment.newInstance(2);
                case 2:
                    return PageFragment.newInstance(3);
                default:
                    return ConsultDoneFragment.newInstance(3);
            }
//                case 1:
//                    return ConsultDoneFragment.newInstance(1);
//                case 2:
//                    return ConsultDoneFragment.newInstance(2);
//                default:
//                    return ConsultDoneFragment.newInstance(3);
//            }
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

    public class FeedBackPagerAdapter extends FragmentPagerAdapter {
        private String tabTitles[] = new String[]{"全部", "已反馈", "未反馈"};


        public FeedBackPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 1:
                    return PageFragment.newInstance(2);
                case 2:
                    return PageFragment.newInstance(3);
                default:
                    return FeedbackFragment.newInstance(1);
//                case 0:
//                    return FeedbackFragment.newInstance(1);
//                case 1:
//                    return FeedbackFragment.newInstance(2);
//                case 2:
//                    return FeedbackFragment.newInstance(3);
//                default:
//                    return FeedbackFragment.newInstance(1);
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

    // TODO 实现view接口
    @Override
    public void refreshPendingPageList(List<ConsultItemBean> consultItemBeanList) {
        if (mPendingPageListener != null) {
            mPendingPageListener.refreshConsultDetailList(consultItemBeanList);
        }
    }

    @Override
    public void refreshFinish(int status) {
        if (mPendingPageListener != null) {
            mPendingPageListener.refreshFinish(status);
        }
    }

    @Override
    public void refreshConsultDonePageList(List<ConsultDoneItemBean> dataList) {
        if (mConsultDonePageListener != null) {
            mConsultDonePageListener.refreshConsultDoneList(dataList);
        }
    }

    @Override
    public void refreshConsultDonePageFinish(int status) {
        if (mConsultDonePageListener != null) {
            mConsultDonePageListener.refreshFinish(status);
        }
    }

    @Override
    public void refreshFeedbackPageList(List<FeedbackItemBean> dataList) {
        if (mFeedBackPageListener != null) {
            mFeedBackPageListener.refreshFeedBackList(dataList);
        }
    }

    @Override
    public void refreshFeedbackPageFinish(int status) {
        if (mFeedBackPageListener != null) {
            mFeedBackPageListener.refreshFinish(status);
        }
    }

    // TODO 提供给二级片段的回调
    public interface OnPendingPageListener {
        void refreshConsultDetailList(List<ConsultItemBean> consultDetailBeanList);

        void refreshFinish(int status);
    }

    public void setOnPendingRefreshListener(OnPendingPageListener pendingPageListener) {
        mPendingPageListener = pendingPageListener;
    }

    private OnConsultDonePageListener mConsultDonePageListener;

    public interface OnConsultDonePageListener {
        void refreshConsultDoneList(List<ConsultDoneItemBean> dataList);

        void refreshFinish(int status);
    }

    public void setOnConsultDonePageListener(OnConsultDonePageListener consultDonePageListener) {
        mConsultDonePageListener = consultDonePageListener;
    }

    private OnFeedBackPageListener mFeedBackPageListener;

    public interface OnFeedBackPageListener {
        void refreshFeedBackList(List<FeedbackItemBean> dataList);

        void refreshFinish(int status);
    }

    public void setOnFeedBackPageListener(OnFeedBackPageListener feedBackPageListener) {
        mFeedBackPageListener = feedBackPageListener;
    }


    public void refreshFeedBackList(int flag) {
        ConsultPresenter.refreshFeedBackList(flag);
    }

    public void loadmoreFeedBackList(int flag) {
        ConsultPresenter.loadmoreFeedBackList(flag);
    }

    public void refreshConsultDoneList(int flag) {
        ConsultPresenter.refreshConsultDoneList(flag);
    }

    public void loadmoreConsultDoneList(int flag) {
        ConsultPresenter.loadmoreConsultDoneList(flag);
    }

    public void refreshCustomList(int flag) {
        ConsultPresenter.refreshCustomList(flag);
    }

    public void loadmoreCustomList(int flag) {
        ConsultPresenter.loadmoreCustomList(flag);
    }


}
