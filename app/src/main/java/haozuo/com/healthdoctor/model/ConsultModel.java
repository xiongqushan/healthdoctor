package haozuo.com.healthdoctor.model;

import android.support.annotation.NonNull;

import com.squareup.okhttp.OkHttpClient;

import javax.inject.Inject;
import java.util.List;
import haozuo.com.healthdoctor.bean.BaseBean;
import haozuo.com.healthdoctor.bean.ConsultItemBean;
import haozuo.com.healthdoctor.bean.ConsultReplyBean;
import haozuo.com.healthdoctor.bean.GlobalShell;
import haozuo.com.healthdoctor.bean.PageBean;
import haozuo.com.healthdoctor.bean.UsefulExpressionBean;
import haozuo.com.healthdoctor.listener.OnHandlerResultListener;
import haozuo.com.healthdoctor.service.IConsultService;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xiongwei1 on 2016/8/2.
 */
public class ConsultModel extends AbstractModel {
    IConsultService mIConsultService;
    @Inject
    public ConsultModel(@NonNull OkHttpClient okHttpClient, @NonNull IConsultService iConsultService){
        super(okHttpClient);
        mIConsultService=iConsultService;
    }

    public void GetGroupCustInfoList(int doctorId,int pageIndex,int pageSize,int flag, final OnHandlerResultListener<GlobalShell<PageBean<ConsultItemBean>>> callbackListener){
        mIConsultService.getPendingAskData(requestTag(),doctorId,pageIndex,pageSize,flag)
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

    public void GetConsultReplyList(int customerId,String commitOn, final OnHandlerResultListener<GlobalShell<List<ConsultReplyBean>>> callbackListener){
        mIConsultService.getConsultReplyData(requestTag(),customerId,commitOn)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseBean<List<ConsultReplyBean>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        GlobalShell<List<ConsultReplyBean>> entity=new GlobalShell<List<ConsultReplyBean>>(e.getMessage());
                        callbackListener.handlerResult(entity);
                    }

                    @Override
                    public void onNext(BaseBean<List<ConsultReplyBean>> resultBean) {
                        GlobalShell<List<ConsultReplyBean>> entity=null;
                        if(resultBean.state>0) {
                            List<ConsultReplyBean> result = resultBean.Data;
                            entity=new GlobalShell<List<ConsultReplyBean>>(result);
                        }
                        else{
                            entity=new GlobalShell<List<ConsultReplyBean>>(resultBean.message);
                        }
                        callbackListener.handlerResult(entity);
                    }
                });
    }

    public void getUsefulExpression(final OnHandlerResultListener<GlobalShell<List<UsefulExpressionBean>>> callbackListener){
        mIConsultService.getUsefulExpression(requestTag())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseBean<List<UsefulExpressionBean>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        GlobalShell<List<UsefulExpressionBean>> entity=new GlobalShell<List<UsefulExpressionBean>>(e.getMessage());
                        callbackListener.handlerResult(entity);
                    }

                    @Override
                    public void onNext(BaseBean<List<UsefulExpressionBean>> resultBean) {
                        GlobalShell<List<UsefulExpressionBean>> entity=null;
                        if(resultBean.state>0) {
                            List<UsefulExpressionBean> result = resultBean.Data;
                            entity=new GlobalShell<List<UsefulExpressionBean>>(result);
                        }
                        else{
                            entity=new GlobalShell<List<UsefulExpressionBean>>(resultBean.message);
                        }
                        callbackListener.handlerResult(entity);
                    }
                });
    }

    public void searchUsefulExpression(String keyWord,final OnHandlerResultListener<GlobalShell<List<UsefulExpressionBean>>> callbackListener){
        mIConsultService.searchUsefulExpression(requestTag(),keyWord)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseBean<List<UsefulExpressionBean>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        GlobalShell<List<UsefulExpressionBean>> entity=new GlobalShell<List<UsefulExpressionBean>>(e.getMessage());
                        callbackListener.handlerResult(entity);
                    }

                    @Override
                    public void onNext(BaseBean<List<UsefulExpressionBean>> resultBean) {
                        GlobalShell<List<UsefulExpressionBean>> entity=null;
                        if(resultBean.state>0) {
                            List<UsefulExpressionBean> result = resultBean.Data;
                            entity=new GlobalShell<List<UsefulExpressionBean>>(result);
                        }
                        else{
                            entity=new GlobalShell<List<UsefulExpressionBean>>(resultBean.message);
                        }
                        callbackListener.handlerResult(entity);
                    }
                });
    }
}
