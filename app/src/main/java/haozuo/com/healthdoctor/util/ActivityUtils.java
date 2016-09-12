package haozuo.com.healthdoctor.util;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by xiongwei1 on 2016/7/4.
 */
public class ActivityUtils {

        private List<Activity> mActivities = new ArrayList<>();
   // public Activity mActivities;
    private static ActivityUtils instance;

    public static ActivityUtils getInstance() {
        if (instance == null) {
            Log.e("instance", "instance");
            instance = new ActivityUtils();
        }
        return instance;
    }

    public void addActivity(Activity activity) {
        mActivities.add(activity);
    }
//    public void addActivity(Activity activity) {
//        mActivities = activity;
//    }

    /**
     * The {@code fragment} is added to the container view with id {@code frameId}. The operation is
     * performed by the {@code fragmentManager}.
     */
    public static void addFragmentToActivity(@NonNull FragmentManager fragmentManager,
                                             @NonNull Fragment fragment, int frameId) {
        checkNotNull(fragmentManager);
        checkNotNull(fragment);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment);
        transaction.commit();
    }
}
