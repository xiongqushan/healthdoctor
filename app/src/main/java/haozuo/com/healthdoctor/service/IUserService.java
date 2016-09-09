package haozuo.com.healthdoctor.service;

import java.util.List;
import java.util.Map;

import haozuo.com.healthdoctor.bean.BasConstBean;
import haozuo.com.healthdoctor.bean.BaseBean;
import haozuo.com.healthdoctor.bean.CustomDetailBean;
import haozuo.com.healthdoctor.bean.DoctorBean;
import haozuo.com.healthdoctor.bean.GroupCustInfoBean;
import haozuo.com.healthdoctor.bean.PageBean;
import haozuo.com.healthdoctor.bean.ServiceDeptBean;
import haozuo.com.healthdoctor.framework.SysConfig;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by xiongwei1 on 2016/7/28.
 */
public interface IUserService {

    @GET(SysConfig.CONTROLLER_PRE_API_USER + "LoginSMSCode")
    Observable<BaseBean<Boolean>> getSMSCode(@Query("mobile") String mobile);

    @POST(SysConfig.CONTROLLER_PRE_API_USER + "Login")
    Observable<BaseBean<DoctorBean>> login(@Body Map<String, Object> params);

    @POST(SysConfig.CONTROLLER_PRE_API_USER + "LoginValidate")
    Observable<BaseBean<DoctorBean>> LoginValidate(@Body Map<String, Object> params);

    @GET(SysConfig.CONTROLLER_PRE_API_USER + "getGroupCustInfoList")
    Observable<BaseBean<PageBean<GroupCustInfoBean>>> getGroupCustInfoList(@Query("serviceDeptId") int serviceDeptId, @Query("doctorId") int doctorId, @Query("groupId") int groupId, @Query("customNameOrMobile") String customNameOrMobile, @Query("pageIndex") int pageIndex, @Query("pageSize") int pageSize);

    @GET(SysConfig.CONTROLLER_PRE_API_USER + "GetCusInfo")
    Observable<BaseBean<CustomDetailBean>> GetCusInfo(@Query("customerId") int customerId);

    @GET(SysConfig.CONTROLLER_PRE_API_USER + "GetBasConstList")
    Observable<BaseBean<List<BasConstBean>>> GetBasConstList();

    @GET(SysConfig.CONTROLLER_PRE_API_USER + "GetServiceDeptList")
    Observable<BaseBean<List<ServiceDeptBean>>> GetServiceDeptList();
}
