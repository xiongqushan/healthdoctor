package haozuo.com.healthdoctor.view.mine;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import haozuo.com.healthdoctor.R;

/**
 * by zy 2016.08.24
 */
public class DoctorInfoFragment extends Fragment {


    private View rootView;

    public DoctorInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_doctor_info, container, false);

        }
        return rootView;
    }

}
