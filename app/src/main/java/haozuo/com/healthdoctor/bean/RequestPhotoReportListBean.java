package haozuo.com.healthdoctor.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hzguest3 on 2016/8/22.
 */
public class RequestPhotoReportListBean implements Serializable{
    /**
     * NickName : sample string 1
     * CreateTime : 2.1
     * Content : sample string 3
     * HealthCompanyName : sample string 4
     * Date : 1970-01-01
     * ImageUrlList : ["sample string 3"]
     */

    public String NickName;
    public double CreateTime;
    public String Content;
    public String HealthCompanyName;
    public String Date;
    public List<String> ImageUrlList;
}
