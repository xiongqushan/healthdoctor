package haozuo.com.healthdoctor.view.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.OnClick;
import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.view.custom.CustomerReportActivity;

/**
 * by zy  2016.08.15.
 */
public class MineFragment extends Fragment {
    private View rootView;

    //    @Bind(R.id.layout_basedata)
//    LinearLayout layout_basedata;
    @OnClick(R.id.layout_basedata)
    void clickLayoutBaseData() {
        getActivity().startActivity(new Intent(getActivity(), BaseinfoActivity.class));
    }

    @OnClick(R.id.layout_set)
    void clickLayoutSet() {
        getActivity().startActivity(new Intent(getActivity(), CustomerReportActivity.class));
    }

    public MineFragment() {
    }

    public static MineFragment newInstance() {
        MineFragment fragment = new MineFragment();
        return fragment;
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("MineFragment", "onPause");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("MineFragment", "onResume");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_mine, container, false);
            ButterKnife.bind(this, rootView);
        }
        return rootView;
    }

}
