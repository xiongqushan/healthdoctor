package haozuo.com.healthdoctor.bean;

import java.io.Serializable;

/**
 * Created by hzguest3 on 2016/8/22.
 */
public class ReportParamsBean implements Serializable{
    /**
     * CheckUnitCode : sample string 1
     * CheckUnitName : sample string 2
     * CustomerName : sample string 3
     * WorkNo : sample string 4
     * CheckDate : sample string 5
     */
    public int customerId;
    public String CheckUnitCode;
    public String CheckUnitName;
    public String CustomerName;
    public String WorkNo;
    public String CheckDate;
    private String formedDate;

    public String getFormedDate(){
        if (CheckDate.length()>=8){
            String year = CheckDate.substring(0,4);
            String month = CheckDate.substring(4,6);
            String day = CheckDate.substring(6,8);
            formedDate = year+"年"+month+"月"+day+"日" ;
        }
        return formedDate;
    }
}
