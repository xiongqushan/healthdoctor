package haozuo.com.healthdoctor.manager;

import android.app.Activity;
import android.content.SharedPreferences;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.ref.SoftReference;
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
//    private DoctorBean doctorBean;
    private Hashtable< String,SoftReference<DoctorBean>> doctorBeanRefs;

    private SharedPreferences sharedPreferences;
    private SoftReference<DoctorBean> _currentEntity;

    private UserManager() {
        if (null == sharedPreferences) {
            sharedPreferences = HZApplication.shareApplication().getSharedPreferences(SP_NAME,Activity.MODE_PRIVATE);
//            doctorBeanRefs = new Hashtable< String,SoftReference<DoctorBean>>();
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
            DoctorBean _doctorEntity = (DoctorBean) doctorEntity.clone();
            _currentEntity=new SoftReference<DoctorBean>(_doctorEntity);
//            doctorBeanRefs.put("TEST",_currentEntity);
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

    public DoctorBean getDoctorInfo(){
//        if(_currentEntity==null){
        if(1==1){
            try {
                if (sharedPreferences.contains(USER_INFO_KEY)) {
                    String localData = sharedPreferences.getString(USER_INFO_KEY, "");
                    if(!StringUtil.isEmpty(localData)){
                        byte[] stringToBytes = StringUtil.StringToBytes(localData);
                        ByteArrayInputStream bis=new ByteArrayInputStream(stringToBytes);
                        ObjectInputStream is=new ObjectInputStream(bis);
                        //返回反序列化得到的对象
                        Object localObject = is.readObject();
                        DoctorBean localInfo=(DoctorBean)localObject;
                        _currentEntity=new SoftReference<DoctorBean>(localInfo);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(_currentEntity!=null) {
//            return doctorBeanRefs.get("TEST").get();
            return _currentEntity.get();

        }
        return null;
    }

    public boolean exist(){
        return getDoctorInfo()!=null;
    }

    public void clear(){
        sharedPreferences.edit().remove(USER_INFO_KEY).commit();
    }

}
