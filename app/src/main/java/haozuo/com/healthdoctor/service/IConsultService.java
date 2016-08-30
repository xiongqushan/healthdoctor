package haozuo.com.healthdoctor.service;

import java.util.List;
import java.util.Map;

import haozuo.com.healthdoctor.bean.BaseBean;
import haozuo.com.healthdoctor.bean.ConsultDoneItemBean;
import haozuo.com.healthdoctor.bean.ConsultItemBean;
import haozuo.com.healthdoctor.bean.ConsultReplyBean;
import haozuo.com.healthdoctor.bean.FeedbackItemBean;
import haozuo.com.healthdoctor.bean.PageBean;
import haozuo.com.healthdoctor.bean.UsefulExpressionBean;
import haozuo.com.healthdoctor.framework.SysConfig;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by xiongwei1 on 2016/8/2.
 */
public interface IConsultService {
    @GET(SysConfig.CONTROLLER_PRE_API_CONSULT + "GetPendingAskData")
    Observable<BaseBean<PageBean<ConsultItemBean>>> getPendingAskData(@Query("doctorId") int doctorId, @Query("pageIndex") int pageIndex, @Query("pageSize") int pageSize, @Query("flag") int flag);

    @GET(SysConfig.CONTROLLER_PRE_API_CONSULT + "GetProcessedAskData")
    Observable<BaseBean<PageBean<ConsultDoneItemBean>>> GetProcessedAskData(@Query("doctorId") int doctorId, @Query("beginCommitOn") String beginCommitOn, @Query("endCommitOn") String endCommitOn, @Query("pageIndex") int pageIndex, @Query("pageSize") int pageSize);

    @GET(SysConfig.CONTROLLER_PRE_API_CONSULT + "GetFeedbackAskData")
    Observable<BaseBean<PageBean<FeedbackItemBean>>> GetFeedbackAskData(@Query("flag") int flag, @Query("doctorId") int doctorId, @Query("pageIndex") int pageIndex, @Query("pageSize") int pageSize);

    @GET(SysConfig.CONTROLLER_PRE_API_CONSULT + "GetAskReplyData")
    Observable<BaseBean<List<ConsultReplyBean>>> getConsultReplyData(@Query("customerId") int customerId, @Query("commitOn") String commitOn);

    @GET(SysConfig.CONTROLLER_PRE_API_CONSULT + "GetDefaultExpressions")
    Observable<BaseBean<List<UsefulExpressionBean>>> getUsefulExpression();

    @GET(SysConfig.CONTROLLER_PRE_API_CONSULT + "SearchExpressionsByKeyWord")
    Observable<BaseBean<List<UsefulExpressionBean>>> searchUsefulExpression(@Query("keyWord") String keyWord);

    @POST(SysConfig.CONTROLLER_PRE_API_CONSULT + "AddDoctorReply")
    Observable<BaseBean<Boolean>> addDoctorReply(@Body Map<String, Object> params);

}
