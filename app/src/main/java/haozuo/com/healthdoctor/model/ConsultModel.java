package haozuo.com.healthdoctor.model;

import haozuo.com.healthdoctor.bean.BaseBean;
import haozuo.com.healthdoctor.bean.ConsultItem;
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

    public void GetGroupCustInfoList(int doctorId,int pageIndex,int pageSize,int flag, final OnHandlerResultListener<GlobalShell<PageBean<ConsultItem>>> callbackListener){
        IConsultService consultService=createService(IConsultService.class);
        consultService.getPendingAskData(doctorId,pageIndex,pageSize,flag)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseBean<PageBean<ConsultItem>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        GlobalShell<PageBean<ConsultItem>> entity=new GlobalShell<PageBean<ConsultItem>>(e.getMessage());
                        callbackListener.handlerResult(entity);
                    }

                    @Override
                    public void onNext(BaseBean<PageBean<ConsultItem>> resultBean) {
                        GlobalShell<PageBean<ConsultItem>> entity=null;
                        if(resultBean.state>0) {
                            PageBean<ConsultItem> result = resultBean.Data;
                            entity=new GlobalShell<PageBean<ConsultItem>>(result);
                        }
                        else{
                            entity=new GlobalShell<PageBean<ConsultItem>>(resultBean.message);
                        }
                        callbackListener.handlerResult(entity);
                    }
                });
    }
}
