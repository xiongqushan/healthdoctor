package haozuo.com.healthdoctor.manager;

import android.app.Activity;
import android.content.SharedPreferences;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import haozuo.com.healthdoctor.bean.DoctorBean;
import haozuo.com.healthdoctor.bean.DoctorGroupBean;
import haozuo.com.healthdoctor.bean.UsefulExpressionBean;
import haozuo.com.healthdoctor.framework.HZApplication;
import haozuo.com.healthdoctor.util.StringUtil;

/**
 * Created by xiongwei1 on 2016/6/6.
 */
public class UserManager {
    private static final String SP_NAME = "User";
    private static final String USER_INFO_KEY = "User_Info_Key";
    private static final String DEFAULT_EXPRESSION_KEY = "Default_Expression_Key";
    private static UserManager _instance;
    private static SharedPreferences sharedPreferences;
    private static DoctorBean _currentEntity;
    private static List<DoctorGroupBean> _currentGroupEntity;
    private static List<UsefulExpressionBean> _usefulExpressionEntity;

    public static Activity testActivity;

    private UserManager() {
        if (null == sharedPreferences) {
            sharedPreferences = HZApplication.shareApplication().getSharedPreferences(SP_NAME, Activity.MODE_PRIVATE);
        }
    }

    public static UserManager getInstance() {
        if (_instance == null) {
            _instance = new UserManager();
        }
        return _instance;
    }

    public void setDoctorInfo(DoctorBean doctorEntity) {
        try {
            _currentEntity = doctorEntity;
            // 保存对象
            SharedPreferences.Editor sharedata = sharedPreferences.edit();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(bos);
            //将对象序列化写入byte缓存
            os.writeObject(doctorEntity);
            String bytesToHexString = StringUtil.bytesToHexString(bos.toByteArray());
            sharedata.putString(USER_INFO_KEY, bytesToHexString);
            sharedata.apply();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setGroupInfo(List<DoctorGroupBean> GroupEntity) {
        try {
            _currentGroupEntity = GroupEntity;
            // 保存对象
            SharedPreferences.Editor sharedata = sharedPreferences.edit();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(bos);
            //将对象序列化写入byte缓存
            os.writeObject(GroupEntity);
            String bytesToHexString = StringUtil.bytesToHexString(bos.toByteArray());
            sharedata.putString(USER_INFO_KEY, bytesToHexString);
            sharedata.apply();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setDefaultExpression(List<UsefulExpressionBean> usefulExpressionEntity) {
        try {
            _usefulExpressionEntity = usefulExpressionEntity;
            // 保存对象
            SharedPreferences.Editor sharedata = sharedPreferences.edit();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(bos);
            //将对象序列化写入byte缓存
            os.writeObject(usefulExpressionEntity);
            String bytesToHexString = StringUtil.bytesToHexString(bos.toByteArray());
            sharedata.putString(DEFAULT_EXPRESSION_KEY, bytesToHexString);
            sharedata.apply();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public DoctorBean getDoctorInfo() {
        if (_currentEntity == null) {
            try {
                if (sharedPreferences.contains(USER_INFO_KEY)) {
                    String localData = sharedPreferences.getString(USER_INFO_KEY, "");
                    if (!StringUtil.isEmpty(localData)) {
                        byte[] stringToBytes = StringUtil.StringToBytes(localData);
                        ByteArrayInputStream bis = new ByteArrayInputStream(stringToBytes);
                        ObjectInputStream is = new ObjectInputStream(bis);
                        //返回反序列化得到的对象
                        Object localObject = is.readObject();
                        DoctorBean localInfo = (DoctorBean) localObject;
                        _currentEntity = localInfo;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return _currentEntity;
    }

    public List<DoctorGroupBean> getGroupInfo() {
        if (_currentGroupEntity == null) {
            try {
                if (sharedPreferences.contains(USER_INFO_KEY)) {
                    String localData = sharedPreferences.getString(USER_INFO_KEY, "");
                    if (!StringUtil.isEmpty(localData)) {
                        byte[] stringToBytes = StringUtil.StringToBytes(localData);
                        ByteArrayInputStream bis = new ByteArrayInputStream(stringToBytes);
                        ObjectInputStream is = new ObjectInputStream(bis);
                        //返回反序列化得到的对象
                        Object localObject = is.readObject();
                        List<DoctorGroupBean> localInfo = (List<DoctorGroupBean>) localObject;
                        _currentGroupEntity = localInfo;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return _currentGroupEntity;
    }

    public List<UsefulExpressionBean> getDefaultExpression() {
        if (_usefulExpressionEntity == null) {
            try {
                if (sharedPreferences.contains(DEFAULT_EXPRESSION_KEY)) {
                    String localData = sharedPreferences.getString(DEFAULT_EXPRESSION_KEY, "");
                    if (!StringUtil.isEmpty(localData)) {
                        byte[] stringToBytes = StringUtil.StringToBytes(localData);
                        ByteArrayInputStream bis = new ByteArrayInputStream(stringToBytes);
                        ObjectInputStream is = new ObjectInputStream(bis);
                        //返回反序列化得到的对象
                        Object localObject = is.readObject();
                        List<UsefulExpressionBean> localInfo = (List<UsefulExpressionBean>) localObject;
                        _usefulExpressionEntity = localInfo;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return _usefulExpressionEntity;
    }


    public boolean exist() {
        return getDoctorInfo() != null;
    }

}
