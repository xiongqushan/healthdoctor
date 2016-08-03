package haozuo.com.healthdoctor.model;

import haozuo.com.healthdoctor.bean.BaseBean;
import haozuo.com.healthdoctor.bean.ConsultItemBean;
import haozuo.com.healthdoctor.bean.GlobalShell;
import haozuo.com.healthdoctor.bean.PageBean;
import haozuo.com.healthdoctor.listener.OnHandlerResultListener;
import haozuo.com.healthdoctor.service.IConsultService;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xiongwei1 on 2016/8/2.
 */
public class ConsultModel extends AbstractModel {
    public static ConsultModel createInstance(){
        return new ConsultModel();
    }

    public void GetGroupCustInfoList(int doctorId,int pageIndex,int pageSize,int flag, final OnHandlerResultListener<GlobalShell<PageBean<ConsultItemBean>>> callbackListener){
        IConsultService consultService=createService(IConsultService.class);
        consultService.getPendingAskData(doctorId,pageIndex,pageSize,flag)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseBean<PageBean<ConsultItemBean>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        GlobalShell<PageBean<ConsultItemBean>> entity=new GlobalShell<PageBean<ConsultItemBean>>(e.getMessage());
                        callbackListener.handlerResult(entity);
                    }

                    @Override
                    public void onNext(BaseBean<PageBean<ConsultItemBean>> resultBean) {
                        GlobalShell<PageBean<ConsultItemBean>> entity=null;
                        if(resultBean.state>0) {
                            PageBean<ConsultItemBean> result = resultBean.Data;
                            entity=new GlobalShell<PageBean<ConsultItemBean>>(result);
                        }
                        else{
                            entity=new GlobalShell<PageBean<ConsultItemBean>>(resultBean.message);
                        }
                        callbackListener.handlerResult(entity);
                    }
                });
    }
}
