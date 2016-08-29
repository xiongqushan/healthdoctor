package haozuo.com.healthdoctor.manager;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Dictionary;

import haozuo.com.healthdoctor.framework.HZApplication;

/**
 * Created by xiongwei1 on 2016/6/6.
 */
public class InternalStorageManager {
    private static final String FILE_NAME = "HZ_Interal_File";
    private static final String USER_INFO_KEY = "User_Info_Key";
    private static InternalStorageManager _instance;
    private static Dictionary<String,Object> _currentDictionary;
    private InternalStorageManager() {

    }

    public static InternalStorageManager getInstance() {
        if (_instance == null) {
            _instance = new InternalStorageManager();
        }
        return _instance;
    }

    void save(String content) throws IOException {
        File file = new File(HZApplication.shareApplication().getFilesDir(), FILE_NAME);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(content.getBytes());
        fos.close();
    }

    String getFile() throws IOException {
        File file = new File(HZApplication.shareApplication().getFilesDir(), FILE_NAME);
        if (file.exists()) {
            FileInputStream fis = HZApplication.shareApplication().openFileInput(FILE_NAME);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] data = new byte[1024];
            int len = -1;
            while ((len = fis.read(data)) != -1) {
                baos.write(data, 0, len);
            }
            return new String(baos.toByteArray());
        }
        return null;
    }
}
