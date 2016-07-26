package haozuo.com.healthdoctor.contract;

import haozuo.com.healthdoctor.bean.BaseBean;
import haozuo.com.healthdoctor.bean.TestBean;
import haozuo.com.healthdoctor.bean.TestGroupBean;
import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by xiongwei1 on 2016/7/26.
 */
public interface ITestService {

    @GET("service/getIpInfo.php")
    Observable<TestBean> getIp(@Query("ip")String ip);

    @GET("v1_User/GetCusGroupByDoctorId")
    Observable<BaseBean<TestGroupBean>>getGroup(@Query("doctorId")int doctorId);
}
