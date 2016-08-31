package haozuo.com.healthdoctor.manager;

import android.app.Activity;
import android.content.SharedPreferences;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import haozuo.com.healthdoctor.bean.UsefulExpressionBean;
import haozuo.com.healthdoctor.framework.HZApplication;
import haozuo.com.healthdoctor.util.StringUtil;

/**
 * Created by hzguest3 on 2016/8/26.
 */
public class UsefulMessageManager {
    private static UsefulMessageManager _instance;
    private static final String DEFAULT_EXPRESSION_KEY = "Default_Expression_Key";
    private static List<UsefulExpressionBean> _usefulExpressionEntity;

    private UsefulMessageManager() {}

    public static UsefulMessageManager getInstance() {
        if (_instance == null) {
            _instance = new UsefulMessageManager();
        }
        return _instance;
    }

    public void setDefaultExpression(List<UsefulExpressionBean> usefulExpressionEntity){
        _usefulExpressionEntity=usefulExpressionEntity;
    }

    public List<UsefulExpressionBean> getDefaultExpression(){
        return  _usefulExpressionEntity;
    }
}
