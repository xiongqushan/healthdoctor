package haozuo.com.healthdoctor.bean;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Created by xiongwei1 on 2016/6/1.
 */

public class DoctorBean implements Cloneable, Serializable {
    public String Account;
    public int Doctor_ID;
    public String Name;
    public String Position;
    public String Expertise;
    public int Dept;
    public String Introduce;
    public String RoleFlag;
    public String PhotoUrl;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}

