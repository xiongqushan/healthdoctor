package haozuo.com.healthdoctor.view.Fragment;

import android.app.Activity;
import android.support.v4.app.Fragment;

import haozuo.com.healthdoctor.view.Activity.BaseActivity;

/**
 * Created by xiongwei1 on 2016/6/22.
 */
public class BaseFragment extends Fragment {
    protected BaseActivity shareCurrentActivity(){
        BaseActivity currentActivity=(BaseActivity)getActivity();
        return currentActivity;
    }
}
