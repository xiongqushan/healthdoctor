package haozuo.com.healthdoctor.bean;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Created by xiongwei on 16/5/22.
 */
public class DoctorGroupBean implements Comparator<DoctorGroupBean>,Serializable{
    public int id;
    public int type;
    public String name;
    public int groupNum;
    public int doctorNum;

    public int compare(DoctorGroupBean o1, DoctorGroupBean o2) {
        if(o1.doctorNum > o2.doctorNum) {
            return -1;
        }
        else if(o1.doctorNum<o2.doctorNum) {
            return 1;
        }
        else return 0;
    }



}
