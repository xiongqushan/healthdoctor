package haozuo.com.healthdoctor.manager;

import android.app.Activity;
import android.content.SharedPreferences;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import haozuo.com.healthdoctor.bean.DoctorGroupBean;
import haozuo.com.healthdoctor.framework.HZApplication;
import haozuo.com.healthdoctor.util.StringUtil;

/**
 * Created by hzguest3 on 2016/8/26.
 */
public class GroupInfoManager {
    private static final String SP_NAME = "GROUP";
    private static final String GROUP_INFO_KEY = "GROUP_INFO_KEY";
    private static GroupInfoManager _instance;
    private SharedPreferences sharedPreferences;

    private static List<DoctorGroupBean> _currentGroupEntity;

    private GroupInfoManager() {
        if (null == sharedPreferences) {
            sharedPreferences = HZApplication.shareApplication().getSharedPreferences(SP_NAME, Activity.MODE_PRIVATE);
        }
    }

    public static GroupInfoManager getInstance() {
        if (_instance == null) {
            _instance = new GroupInfoManager();
        }
        return _instance;
    }

    public void setGroupInfo(List<DoctorGroupBean> GroupEntity){
        try {
            _currentGroupEntity=GroupEntity;
            // 保存对象
            SharedPreferences.Editor sharedata =sharedPreferences.edit();
            ByteArrayOutputStream bos=new ByteArrayOutputStream();
            ObjectOutputStream os=new ObjectOutputStream(bos);
            //将对象序列化写入byte缓存
            os.writeObject(GroupEntity);
            String bytesToHexString = StringUtil.bytesToHexString(bos.toByteArray());
            sharedata.putString(GROUP_INFO_KEY, bytesToHexString);
            sharedata.apply();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<DoctorGroupBean> getGroupInfo(){
        if(_currentGroupEntity==null){
            try {
                if (sharedPreferences.contains(GROUP_INFO_KEY)) {
                    String localData = sharedPreferences.getString(GROUP_INFO_KEY, "");
                    if(!StringUtil.isEmpty(localData)){
                        byte[] stringToBytes = StringUtil.StringToBytes(localData);
                        ByteArrayInputStream bis=new ByteArrayInputStream(stringToBytes);
                        ObjectInputStream is=new ObjectInputStream(bis);
                        //返回反序列化得到的对象
                        Object localObject = is.readObject();
                        List<DoctorGroupBean> localInfo=(List<DoctorGroupBean>)localObject;
                        _currentGroupEntity=localInfo;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return  _currentGroupEntity;
    }

    public boolean exist(){
        return getGroupInfo()!=null;
    }


    public void clear(){
        sharedPreferences.edit().remove(GROUP_INFO_KEY).commit();
        _currentGroupEntity=null;
    }
}
