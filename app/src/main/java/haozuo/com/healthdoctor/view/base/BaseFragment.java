package haozuo.com.healthdoctor.view.base;

import android.support.v4.app.Fragment;

import haozuo.com.healthdoctor.framework.HZApplication;

/**
 * Created by xiongwei1 on 2016/8/1.
 */
public class BaseFragment extends Fragment {
    @Override
    public void onDestroy() {
        super.onDestroy();
        HZApplication.shareApplication().getRefWatcher().watch(this);
    }
}
