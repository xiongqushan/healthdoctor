package haozuo.com.healthdoctor.bean;

import android.text.format.DateUtils;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by hzguest3 on 2016/7/27.
 */
public class CustomDetailBean implements Serializable{
    public int Id;
    public String Cname;
    public static String Gender;
    public String Birthday;
    public String Certificate_Code;
    public String Mobile;
    public String Email;
    public String Address;
    public String Nation;
    public String Contact_Mobile;
    public String Contact_Relation;
    public String Contact_Name;
    public String Account_Id;
    public String Nickname;
    public String PhotoUrl;
    public String Company_Name;
    public String Cust_Code;
    public int[] GroupIdList;
    public int DoctorID;
    public int Age;
    public String Sex = GenderConvert(this.Gender);

    public static String GenderConvert(String Gender){
        switch(Gender){
            case "0" :
                return "男";
            case "1":
                return "女";
            default:
                return "未知";
        }
    }

//    public int ComputeAge(){
//        if (Certificate_Code == null){
//            if (Birthday == null){
//                return 0;
//            }else {
//                getAge(DateUtils.str2Date(Birthday));
//
//                return 0;
//            }
//        }else {
//            return 0;
//        }
//    };
//
//    public static int getAge(Date birthDay) throws Exception
//    {
//        Calendar cal = Calendar.getInstance();
//
//        if (cal.before(birthDay))
//        {
//            throw new IllegalArgumentException(
//                    "BIRTHDAY IS LATER THAN TODAY");
//        }
//        int yearNow = cal.get(Calendar.YEAR);
//        int monthNow = cal.get(Calendar.MONTH);
//        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
//        cal.setTime(birthDay);
//
//        int yearBirth = cal.get(Calendar.YEAR);
//        int monthBirth = cal.get(Calendar.MONTH);
//        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
//
//        int age = yearNow - yearBirth;
//
//        if (monthNow <= monthBirth)
//        {
//            if (monthNow == monthBirth)
//            {
//                if (dayOfMonthNow < dayOfMonthBirth)
//                    age--;
//            }
//            else
//            {
//                age--;
//            }
//        }
//        return age;
//    }
};


