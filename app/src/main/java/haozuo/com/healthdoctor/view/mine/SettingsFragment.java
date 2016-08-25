package haozuo.com.healthdoctor.view.mine;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.OnClick;
import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.view.base.BaseFragment;


/**
 * by zy 2016.08.24
 */
public class SettingsFragment extends BaseFragment {
    @OnClick(R.id.layout_aboutus)
    void aboutusClick() {
    }

    @OnClick(R.id.layout_help)
    void helpClick() {
    }

    @OnClick(R.id.layout_feedback)
    void feedbackClick() {
    }

    @OnClick(R.id.layout_disclaimer)
    void disclaimerClick() {
    }

    @OnClick(R.id.layout_clearcache)
    void clearcacheClick() {
    }

    @OnClick(R.id.btnSignOut)
    void btnSignOut() {
    }

    private View rootView;

    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_settings, container, false);
            ButterKnife.bind(this, rootView);
        }
        return rootView;
    }


}
