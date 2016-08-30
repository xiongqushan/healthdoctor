package haozuo.com.healthdoctor.view.mine;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.bean.DoctorBean;
import haozuo.com.healthdoctor.manager.UserManager;

/**
 * by zy 2016.08.24
 */
public class DoctorInfoFragment extends Fragment {
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


    private View rootView;

    public DoctorInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_doctor_info, container, false);
            ButterKnife.bind(this, rootView);
            initView();
        }
        return rootView;
    }

    private void initView() {
        DoctorBean doctorInfo = UserManager.getInstance().getDoctorInfo();
        tvName.setText(doctorInfo.Name);
        tvSkill.setText(doctorInfo.Expertise);
        tvJob.setText(doctorInfo.RoleFlag);
        tvWorkUnit.setText(doctorInfo.Dept + "");
        tvExplain.setText(doctorInfo.Introduce);

    }

}
