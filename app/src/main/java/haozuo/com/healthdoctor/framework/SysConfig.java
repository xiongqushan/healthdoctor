package haozuo.com.healthdoctor.framework;

/**
 * Created by xiongwei on 16/5/19.
 */
public class SysConfig {
    public static final String CURRENT_BASE_VERSION = "V1";
    public static final String CURRENT_VERSION_TEXT = "V1.0";
    public static final int CURRENT_VERSION_VALUE = 10;
    //    public static final String[] BASE_API=new String[]{"http://hc.ihaozhuo.com:90","HZ_API_V2","1!2@3#4$5%6^"};//pro
//    public static final String[] BASE_API = new String[]{"http://hz75thbd2:803", "HZ_API_V2", "1!2@3#4$5%6^"};//xw
    public static final String[] BASE_API=new String[]{"http://hz75thbd2:19949","HZ_API_V2","1!2@3#4$5%6^"};//xwdebugg

    public static final String CONTROLLER_PRE_API_USER = "api/" + CURRENT_BASE_VERSION + "_User/";
    public static final String CONTROLLER_PRE_API_CONSULT = "api/" + CURRENT_BASE_VERSION + "_Consult/";
}
