package haozuo.com.healthdoctor.service;

import haozuo.com.healthdoctor.bean.BaseBean;
import haozuo.com.healthdoctor.bean.UpdateInfoBean;
import haozuo.com.healthdoctor.framework.SysConfig;
import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by zhangzhongyao on 2016/9/5.
 */
public interface ISystemService {
    @GET(SysConfig.CONTROLLER_PRE_API_SYSTEM + "GetVersionStatus")
    Observable<BaseBean<UpdateInfoBean>> GetVersionStatus(@Query("currentVersionCode") int currentVersionCode);
}
