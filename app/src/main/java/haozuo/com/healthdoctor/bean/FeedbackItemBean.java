package haozuo.com.healthdoctor.bean;

import java.io.Serializable;

/**
 * by zy on 2016.08.16.
 */
public class FeedbackItemBean implements Serializable {

    /**
     * ROWNUMBER : 1
     * CustId : 2
     * CustName : sample string 3
     * Nickname : sample string 4
     * PhotoUrl : sample string 5
     * ConsultTitele : sample string 7
     * Score: 8
     * DoctorId: 9
     * ReDoctorId: 10
     * ReDoctor: sample string 11
     * CommitOn : 2016-08-02T14:36:07.0179946+08:00
     */

    public int ROWNUMBER;
    public int Score;
    public int CustId;
    public int DoctorId;
    public int ReDoctorId;
    public String ReDoctor;
    public String CustName;
    public String Nickname;
    public String PhotoUrl;
    public String ConsultTitele;
    public String CommitOn;
}
