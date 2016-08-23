package haozuo.com.healthdoctor.service;

import java.util.List;

import haozuo.com.healthdoctor.bean.BaseBean;
import haozuo.com.healthdoctor.bean.ReportDetailBean;
import haozuo.com.healthdoctor.bean.ReportParamsBean;
import haozuo.com.healthdoctor.framework.SysConfig;
import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by hzguest3 on 2016/8/22.
 */
public interface IReportService {

    @GET(SysConfig.CONTROLLER_PRE_API_REPORT + "GetReportParams")
    Observable<BaseBean<List<ReportParamsBean>>> getReportParams(@Query("tag") String tag, @Query("customerId") int customerId);

    @GET(SysConfig.CONTROLLER_PRE_API_REPORT + "GetHealthReport")
    Observable<BaseBean<ReportDetailBean>> GetHealthReport(@Query("tag") String tag, @Query("customerId") int customerId,
                                                                 @Query("checkCode") String checkCode, @Query("workNo") String workNo);

}
