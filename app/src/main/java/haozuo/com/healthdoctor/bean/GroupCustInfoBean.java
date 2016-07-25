package haozuo.com.healthdoctor.bean;

/**
 * Created by xiongwei1 on 2016/6/12.
 */
public class GroupCustInfoBean {
    public int CustId; // 客户ID
    public String AccountId;  // Description: app客户登录账号
    public String Cname;
    public String Age;
    public String Mobile ; // 电话号码
    public String NickName ; // 昵称
    public String Gender;
    public String Birthday;
    public String CompanyName; // COMPANY_NAME
    public int DoctorId; // 健管师ID
    public int ServiceDeptId;// Description: FK: SERVICE_DEPT.ID
    public String ServiceDeptName; // 服务机构名称
    public int GroupInfoId;// Description: FK: GROUP_INFO.ID
    public String PhotoUrl;
}
