package haozuo.com.healthdoctor.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by hzguest3 on 2016/7/27.
 */
public class CustomDetailBean implements Serializable{
    public int Id;
    public String Cname;
    public int Gender;
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
//    public int[] GroupIdList;
    public List<Integer> GroupIdList;
    public int DoctorID;
    private String Sex;
    private int Age;

    public String GetSex(){
        switch(Gender){
            case 0 :
                return "男";
            case 1:
                return "女";
            default:
                return "未知";
        }
    }

    public int GetAge(){
        if (Certificate_Code != null){
            String birth_year = Certificate_Code.substring(6, 10);
            String birth_month = Certificate_Code.substring(11, 12);
            String birth_day = Certificate_Code.substring(13, 14);
            String birthday = birth_year+"-"+birth_month+"-"+birth_day;
            return getAgeBaseDate(birthday);
        }else if (Birthday != null){
            return getAgeBaseDate(Birthday.split("T")[0]);
        }
        return 0;
    }

    public int getAgeBaseDate(String birthday) {
        if (birthday == null) {
            return 0;
        }
        int age = 0;
        Date now = new Date();
        SimpleDateFormat format_y = new SimpleDateFormat("yyyy");
        SimpleDateFormat format_M = new SimpleDateFormat("MM");
        String birth_year = birthday.split("-")[0];
        String this_year = format_y.format(now);
        String birth_month = birthday.split("-")[1];
        String this_month = format_M.format(now);

        age = Integer.parseInt(this_year) - Integer.parseInt(birth_year);

        if (this_month.compareTo(birth_month) < 0) age -= 1;
        if (age < 0) age = 0;
        return age;
    }

};


