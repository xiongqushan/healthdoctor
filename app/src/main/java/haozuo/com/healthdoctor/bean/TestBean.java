package haozuo.com.healthdoctor.bean;

/**
 * Created by xiongwei1 on 2016/7/26.
 */
public class TestBean {
    public int code;

    public IpInfo data;

    public static class IpInfo {
        public String country;
        public String country_id;
        public String area;
        public String area_id;
        public String ip;
    }
}
