package haozuo.com.healthdoctor.view.custom;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.Bind;
import butterknife.ButterKnife;
import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.bean.GroupCustInfoBean;
import haozuo.com.healthdoctor.contract.AbsView;
import haozuo.com.healthdoctor.contract.CustomDetailContract;
import haozuo.com.healthdoctor.contract.CustomerInfoContract;

public class CustomDetailFragment extends AbsView implements CustomDetailContract.ICustomDetailView{
    private SimpleFragmentPagerAdapter pagerAdapter;
    Context mContext;
    View rootView;
    int mCustomerId;
    String Cphoto;
    CustomDetailContract.ICustomDetailPresenter mCustomDetailPresenter;
    @Bind(R.id.customerName) TextView customerName;
    @Bind(R.id.customerImg) SimpleDraweeView customerImg;
    @Bind(R.id.customDetail_Tab) TabLayout tabLayout;
    @Bind(R.id.customDetail_Vp) ViewPager viewPager;


    public CustomDetailFragment(){
    }

    public static CustomDetailFragment newInstance(int customerId) {
        CustomDetailFragment fragment = new CustomDetailFragment();
        fragment.mCustomerId = customerId;
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        mCustomDetailPresenter.start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext=getContext();
        if(rootView==null){
            rootView= inflater.inflate(R.layout.fragment_custom_detail, container, false);
            ButterKnife.bind(this,rootView);
        }
        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
        mCustomDetailPresenter.cancelRequest();
    }

    @Override
    public void setPresenter(CustomDetailContract.ICustomDetailPresenter presenter) {
        mCustomDetailPresenter=presenter;
    }

    @Override
    public void InitView(GroupCustInfoBean custom) {
        final GroupCustInfoBean customInfo = custom;
        if (custom.PhotoUrl == null){
            Cphoto = "http://pic002.cnblogs.com/images/2011/103608/2011062022023456.jpg";
        }
        else {
            Cphoto = custom.PhotoUrl;
        }
        Uri uri = Uri.parse(Cphoto);
        customerImg.setImageURI(uri);
        customerName.setText(custom.Cname);
        customerImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CustomerInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("CustomInfo", customInfo);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        CustomDetailFragment fragment = new CustomDetailFragment();
        CustomDetailFragment.SimpleFragmentPagerAdapter pagerAdapter = fragment.new SimpleFragmentPagerAdapter(getActivity().getSupportFragmentManager(), mContext);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
    }

    public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {

        final int PAGE_COUNT = 4;
        private String tabTitles[] = new String[]{"tab1","tab2","tab3","tab4"};
        private Context context;

        public SimpleFragmentPagerAdapter(FragmentManager fm, Context context) {
            super(fm);
            this.context = context;
        }

        @Override
        public Fragment getItem(int position) {
            return PageFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }
    }

}


