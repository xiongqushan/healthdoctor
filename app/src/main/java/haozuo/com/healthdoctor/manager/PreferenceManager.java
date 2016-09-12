package haozuo.com.healthdoctor.manager;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {
    /**
     * 保存Preference的name
     */
    public static final String PREFERENCE_NAME = "HZ_HEALTHDOCTOR_SHAREDPREFERENCES";
    private static SharedPreferences mSharedPreferences;
    private static PreferenceManager mPreferencemManager;
    private static SharedPreferences.Editor editor;

    private static String SHARED_KEY_JPUSH = "SHARED_KEY_JPUSH";
    private static String SHARED_KEY_DOCTOE_jOB = "SHARED_KEY_DOCTOE_jOB";
    private static String SHARED_KEY_DOCTOE_DEPT = "SHARED_KEY_DOCTOE_DEPT";

    private PreferenceManager(Context context) {
        mSharedPreferences = context.getSharedPreferences(PREFERENCE_NAME,
                Context.MODE_PRIVATE);
        editor = mSharedPreferences.edit();
    }

    public static synchronized void init(Context context) {
        if (mPreferencemManager == null) {
            mPreferencemManager = new PreferenceManager(context);
        }
    }

    /**
     * 单例模式，获取instance实例
     */
    public synchronized static PreferenceManager getInstance() {
        if (mPreferencemManager == null) {
            throw new RuntimeException("please init PreferenceManager first!");
        }
        return mPreferencemManager;
    }


    public boolean readJpush() {
        return mSharedPreferences.getBoolean(SHARED_KEY_JPUSH, true);
    }

    public void writeJpush(boolean state) {
        editor.putBoolean(SHARED_KEY_JPUSH, state);
        editor.commit();
    }


//    public String readDoctorJob() {
//        return mSharedPreferences.getString(SHARED_KEY_DOCTOE_jOB, null);
//    }
//
//    public void writeDoctorJob(String job) {
//        editor.putString(SHARED_KEY_DOCTOE_jOB, job);
//        editor.commit();
//    }
//
//    public String readDoctorDept() {
//        return mSharedPreferences.getString(SHARED_KEY_DOCTOE_DEPT, null);
//    }
//
//    public void writeDoctorDept(String dept) {
//        editor.putString(SHARED_KEY_DOCTOE_DEPT, dept);
//        editor.commit();
//    }
//
//    public void clear() {
//        editor.remove(SHARED_KEY_DOCTOE_DEPT).commit();
//        editor.remove(SHARED_KEY_DOCTOE_jOB).commit();
//    }


}
