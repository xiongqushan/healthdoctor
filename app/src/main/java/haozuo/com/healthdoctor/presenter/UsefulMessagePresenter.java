package haozuo.com.healthdoctor.presenter;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import haozuo.com.healthdoctor.bean.GlobalShell;
import haozuo.com.healthdoctor.bean.UsefulExpressionBean;
import haozuo.com.healthdoctor.model.IBaseModel;
import haozuo.com.healthdoctor.view.IBaseView;
import haozuo.com.healthdoctor.contract.UsefulMessageContract;
import haozuo.com.healthdoctor.listener.OnHandlerResultListener;
import haozuo.com.healthdoctor.manager.UserManager;
import haozuo.com.healthdoctor.model.ConsultModel;

/**
 * Created by hzguest3 on 2016/8/9.
 */
public class UsefulMessagePresenter extends AbstractPresenter implements UsefulMessageContract.IUsefulMessagePresenter{
    UsefulMessageContract.IUsefulMessageView mIUsefulMessageView;
    Context mContext;
    ConsultModel mConsultModel;

    @Inject
    public UsefulMessagePresenter(@NonNull UsefulMessageContract.IUsefulMessageView iUsefulMessageView,@NonNull Context context, @NonNull ConsultModel consultModel){
        mIUsefulMessageView = iUsefulMessageView;
        mConsultModel = consultModel;
        mContext = context;
        mIUsefulMessageView.setPresenter(this);
    }

    @Override
    public IBaseView getBaseView() {
        return mIUsefulMessageView;
    }

    @Override
    public IBaseModel[] getBaseModelList() {
        return new IBaseModel[]{mConsultModel};
    }

    @Override
    public void start() {
        getDefaultUsefulExpression();
    }

//    @Override
    public void getDefaultUsefulExpression() {
        if (UserManager.getInstance().getDefaultExpression() != null){
            mIUsefulMessageView.refreshUsefulMessageAdapter(UserManager.getInstance().getDefaultExpression());
        }
        else {
            mIUsefulMessageView.showDialog();
            mConsultModel.getUsefulExpression(new OnHandlerResultListener<GlobalShell<List<UsefulExpressionBean>>>() {
                @Override
                public void handlerResult(GlobalShell<List<UsefulExpressionBean>> resultData) {
                    if (resultData.LogicSuccess){
                        UserManager.getInstance().setDefaultExpression(resultData.Data);
                        mIUsefulMessageView.hideDialog();
                        mIUsefulMessageView.refreshUsefulMessageAdapter(resultData.Data);
                    }else {
                        mIUsefulMessageView.hideDialog();
                    }
                }
            });
        }

    }

    @Override
    public void searchUsefulExpression(String keyword) {
        mIUsefulMessageView.showDialog();
        mConsultModel.searchUsefulExpression(keyword,new OnHandlerResultListener<GlobalShell<List<UsefulExpressionBean>>>() {
            @Override
            public void handlerResult(GlobalShell<List<UsefulExpressionBean>> resultData) {
                if (resultData.LogicSuccess){
                    mIUsefulMessageView.hideDialog();
                    mIUsefulMessageView.refreshUsefulMessageAdapter(resultData.Data);
                }else {
                    mIUsefulMessageView.hideDialog();
                }
            }
        });
    }
}
