package haozuo.com.healthdoctor.manager;

import android.app.Activity;
import android.content.SharedPreferences;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.ref.SoftReference;
import java.util.HashSet;
import java.util.Hashtable;

import haozuo.com.healthdoctor.bean.DoctorBean;
import haozuo.com.healthdoctor.framework.HZApplication;
import haozuo.com.healthdoctor.util.StringUtil;

/**
 * Created by xiongwei1 on 2016/6/6.
 */
public class UserManager  {
    private static final String SP_NAME = "User";
    private static final String USER_INFO_KEY = "User_Info_Key";
    private static UserManager _instance;
    private SharedPreferences sharedPreferences;
    private SoftReference<DoctorBean> _currentEntity;
    DoctorBean _doctorEntity;
    private UserManager() {
        if (null == sharedPreferences) {
            sharedPreferences = HZApplication.shareApplication().getSharedPreferences(SP_NAME,Activity.MODE_PRIVATE);
        }
    }

    public static UserManager getInstance() {
        if (_instance == null) {

            _instance = new UserManager();
        }
        return _instance;
    }

    public void setDoctorInfo(DoctorBean doctorEntity) throws CloneNotSupportedException{
        try {
            DoctorBean doctorBean= (DoctorBean) doctorEntity.clone();
            _currentEntity=new SoftReference<DoctorBean>(doctorBean);
            doctorBean=null;
            SharedPreferences.Editor sharedata = sharedPreferences.edit();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(bos);
            //将对象序列化写入byte缓存
            os.writeObject(_currentEntity.get());
            String bytesToHexString = StringUtil.bytesToHexString(bos.toByteArray());
            sharedata.putString(USER_INFO_KEY, bytesToHexString);
            sharedata.apply();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public DoctorBean getDoctorInfo(){
        DoctorBean doctorBeanValue=null;
        if(_currentEntity!=null){
            doctorBeanValue=_currentEntity.get();
        }
        if(doctorBeanValue==null){
            try {
                if (sharedPreferences.contains(USER_INFO_KEY)) {
                    String localData = sharedPreferences.getString(USER_INFO_KEY, "");
                    if(!StringUtil.isEmpty(localData)){
                        byte[] stringToBytes = StringUtil.StringToBytes(localData);
                        ByteArrayInputStream bis=new ByteArrayInputStream(stringToBytes);
                        ObjectInputStream is=new ObjectInputStream(bis);
                        //返回反序列化得到的对象
                        Object localObject = is.readObject();
                        DoctorBean doctorBean=(DoctorBean)localObject;
                        _currentEntity=new SoftReference<DoctorBean>(doctorBean);
                        doctorBean=null;
                        doctorBeanValue=_currentEntity.get();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return doctorBeanValue;
    }

    public boolean exist(){
        return getDoctorInfo()!=null;
    }

    public void clear(){
        sharedPreferences.edit().remove(USER_INFO_KEY).commit();
    }

}
