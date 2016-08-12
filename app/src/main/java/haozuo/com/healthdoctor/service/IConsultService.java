package haozuo.com.healthdoctor.service;

import java.util.List;

import haozuo.com.healthdoctor.bean.BaseBean;
import haozuo.com.healthdoctor.bean.ConsultItemBean;
import haozuo.com.healthdoctor.bean.ConsultReplyBean;
import haozuo.com.healthdoctor.bean.PageBean;
import haozuo.com.healthdoctor.framework.SysConfig;
import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by xiongwei1 on 2016/8/2.
 */
public interface IConsultService {
    @GET(SysConfig.CONTROLLER_PRE_API_CONSULT + "GetPendingAskData")
    Observable<BaseBean<PageBean<ConsultItemBean>>> getPendingAskData(@Query("tag") String tag,@Query("doctorId") int doctorId, @Query("pageIndex") int pageIndex, @Query("pageSize") int pageSize, @Query("flag") int flag);

    @GET(SysConfig.CONTROLLER_PRE_API_CONSULT + "GetAskReplyData")
    Observable<BaseBean<List<ConsultReplyBean>>> getConsultReplyData(@Query("tag") String tag,@Query("customerId") int customerId, @Query("commitOn") String commitOn);
}
