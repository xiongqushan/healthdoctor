package haozuo.com.healthdoctor.service;

import java.util.List;

import haozuo.com.healthdoctor.bean.BaseBean;
import haozuo.com.healthdoctor.bean.ConsultItem;
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
    Observable<BaseBean<PageBean<ConsultItem>>> getPendingAskData(@Query("doctorId") int doctorId, @Query("pageIndex") int pageIndex, @Query("pageSize") int pageSize, @Query("flag") int flag);
}
