package haozuo.com.healthdoctor.view.mine;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.bean.BasConstBean;
import haozuo.com.healthdoctor.bean.DoctorBean;
import haozuo.com.healthdoctor.bean.ServiceDeptBean;
import haozuo.com.healthdoctor.contract.DoctorInfoContract;
import haozuo.com.healthdoctor.manager.UserManager;
import haozuo.com.healthdoctor.presenter.IBasePresenter;
import haozuo.com.healthdoctor.util.StringUtil;
import haozuo.com.healthdoctor.view.base.AbstractView;

/**
 * by zy 2016.08.24
 */
public class DoctorInfoFragment extends AbstractView implements DoctorInfoContract.IDoctorInfoView {
    @Bind(R.id.tvDoctorName)
    TextView tvName;
    @Bind(R.id.tvDoctorSkill)
    TextView tvSkill;
    @Bind(R.id.tvDoctorJob)
    TextView tvJob;
    @Bind(R.id.tvDoctorWorkUnit)
    TextView tvWorkUnit;
    @Bind(R.id.tvDoctorExplain)
    TextView tvExplain;

    DoctorInfoContract.IDoctorInfoPresenter mIDoctorInfoPresenter;

    private View rootView;
    private DoctorBean doctorInfo;

    public DoctorInfoFragment() {
        // Required empty public constructor
    }

    public static DoctorInfoFragment getInstance() {
        DoctorInfoFragment fragment = new DoctorInfoFragment();
        return fragment;
    }

    @Override
    protected IBasePresenter getPresenter() {
        return mIDoctorInfoPresenter;
    }

    @Override
    protected View getRootView() {
        return rootView;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_doctor_info, container, false);
            ButterKnife.bind(this, rootView);
            doctorInfo = UserManager.getInstance().getDoctorInfo();
            initView();
            mIDoctorInfoPresenter.start();
        }
        return rootView;
    }

    private void initView() {

        tvName.setText(doctorInfo.Name);
        tvSkill.setText(doctorInfo.Expertise);
        tvExplain.setText(doctorInfo.Introduce);

    }

    @Override
    public void updateUI(List<BasConstBean> listJob, List<ServiceDeptBean> listDept) {
        String job = "";
        String dept = "";
        for (int i = 0; i < listJob.size(); i++) {
            if (listJob.get(i).Code.equals(doctorInfo.Position)) {
                job = listJob.get(i).Name;
                break;
            }
        }
        if (StringUtil.isTrimEmpty(job)) job = "暂无";
        for (int i = 0; i < listDept.size(); i++) {
            if (listDept.get(i).Id == (doctorInfo.Dept)) {
                dept = listDept.get(i).Name;
                break;
            }
        }
        if (StringUtil.isTrimEmpty(dept)) dept = "暂无";
        tvJob.setText(job);
        tvWorkUnit.setText(dept);
    }

    @Override
    public void changeRetryLayer(boolean isShow) {
        if (isShow) {
//            showRetryLayer(R.id.rLayout,true,getString(R.string.connect_fail));
            showRetryLayer(R.id.rLayout);
        } else {
            hideRetryLayer(R.id.rLayout);
        }
    }

    @Override
    public void setPresenter(DoctorInfoContract.IDoctorInfoPresenter presenter) {
        mIDoctorInfoPresenter = presenter;
    }
}
