package haozuo.com.healthdoctor.service;

import java.util.List;

import haozuo.com.healthdoctor.bean.BaseBean;
import haozuo.com.healthdoctor.bean.DoctorGroupBean;
import haozuo.com.healthdoctor.bean.TestGroupBean;
import haozuo.com.healthdoctor.framework.SysConfig;
import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by xiongwei1 on 2016/7/27.
 */
public interface IGroupService {
    @GET(SysConfig.CONTROLLER_PRE_API_USER + "GetCusGroupByDoctorId")
    Observable<BaseBean<List<DoctorGroupBean>>> getGroup(@Query("doctorId") int doctorId);

    @GET(SysConfig.CONTROLLER_PRE_API_USER + "DeleteCustomerGroup")
    Observable<BaseBean<Boolean>> DeleteGroup(@Query("customerId") int customerId,@Query("curGroupId") int groupId,@Query("operateBy") String operateBy);
}
