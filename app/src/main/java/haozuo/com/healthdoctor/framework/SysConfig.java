package haozuo.com.healthdoctor.framework;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import haozuo.com.healthdoctor.bean.ExpressionConst;

/**
 * Created by xiongwei on 16/5/19.
 */
public class SysConfig {
    public static final String CURRENT_BASE_VERSION = "V1";
    public static final String CURRENT_VERSION_TEXT = "V1.0";
    public static final int CURRENT_VERSION_VALUE = 10;
    public static final String[] BASE_API = new String[]{"http://hc.ihaozhuo.com:90", "HZ_API_V2", "1!2@3#4$5%6^"};//pro
    //public static final String[] BASE_API = new String[]{"http://hz75thbd2:803", "HZ_API_V2", "1!2@3#4$5%6^"};//xw
    //public static final String[] BASE_API = new String[]{"http://hz75thbd2:19949", "HZ_API_V2", "1!2@3#4$5%6^"};//xwdebugg
    //public static final String[] BASE_API = new String[]{"http://hzswvajgs01:91", "HZ_API_V2", "1!2@3#4$5%6^"};//xwdebugg


    public static final String CONTROLLER_PRE_API_USER = "api/" + CURRENT_BASE_VERSION + "_User/";
    public static final String CONTROLLER_PRE_API_CONSULT = "api/" + CURRENT_BASE_VERSION + "_Consult/";
    public static final String CONTROLLER_PRE_API_REPORT = "api/" + CURRENT_BASE_VERSION + "_Report/";
    public static final String CONTROLLER_PRE_API_SYSTEM = "api/" + CURRENT_BASE_VERSION + "_System/";

    public static final int CONNECT_TIMEOUT = 15;
    public static final int WRITE_TIMEOUT = 15;
    public static final int READ_TIMEOUT = 40;

    public static List<ExpressionConst> getExpressionConstList() {
        List<ExpressionConst> ExpressionConstList = new ArrayList<>();
        List<String> ExpressionContent = new ArrayList(Arrays.asList("您好！", "感谢您的支持。", "祝健康！"));
        List<Integer> ExpressionPosition = new ArrayList(Arrays.asList(-1, 1, 1));
        for (int i = 0; i < 3; i++) {
            ExpressionConst expressionConst = new ExpressionConst();
            expressionConst.ID = i;
            expressionConst.Content = ExpressionContent.get(i);
            expressionConst.Postion = ExpressionPosition.get(i);
            ExpressionConstList.add(expressionConst);
        }
        return ExpressionConstList;
    }
}
