package haozuo.com.healthdoctor.bean;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

/**
 * Created by hzguest3 on 2016/8/4.
 */
public class ConsultReplyBean implements Comparator<ConsultReplyBean>,Serializable{

    public int Id;
    public int CustomerId;
    public int ReDoctorId;
    public String CommitOn;
    public String Content;
    public int IsDoctorReply;
    public int ConsultType;
    public String AppendInfo;
    public String PhotoUrl;
    public String StrGuid;
    public int DeliverState;

    @Override
    public int compare(ConsultReplyBean lhs, ConsultReplyBean rhs) {
        try
        {
            Date _date1 = (new SimpleDateFormat("yyyy-MM-dd")).parse(lhs.CommitOn);
            Date _date2 = (new SimpleDateFormat("yyyy-MM-dd")).parse(rhs.CommitOn);

            if (_date1.compareTo(_date2) > 0)
                return 1;
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return -1;
    }
}
