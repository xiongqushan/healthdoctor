package haozuo.com.healthdoctor.view.consult;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.bean.ConsultReplyBean;
import haozuo.com.healthdoctor.contract.UsefulMessageContract;
import haozuo.com.healthdoctor.view.base.AbstractView;

/**
 * Created by hzguest3 on 2016/8/9.
 */
public class UsefulMessageFragment extends AbstractView implements UsefulMessageContract.IUsefulMessageView {
    Context mContext;
    View rootView;
    private UsefulMessageContract.IUsefulMessagePresenter mIUsefulMessagePresenter;
    private static ConsultReplyBean mConsultReplyBean;

    @Bind(R.id.txt_reportdetail_content)TextView txt_reportdetail_content;

    public UsefulMessageFragment(){};

    public static UsefulMessageFragment newInstance(@NonNull ConsultReplyBean consultReplyItem) {
        UsefulMessageFragment fragment = new UsefulMessageFragment();
        mConsultReplyBean = consultReplyItem;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext=getContext();
        if(rootView==null){
            rootView= inflater.inflate(R.layout.fragment_useful_message_list, container, false);
            ButterKnife.bind(this,rootView);
        }
        setConsultContent();

        return rootView;
    }

    @Override
    public void setPresenter(UsefulMessageContract.IUsefulMessagePresenter presenter) {
        mIUsefulMessagePresenter = presenter;
    }

    public void setConsultContent(){
        switch(mConsultReplyBean.ConsultType){
            case 1:case 3:
                txt_reportdetail_content.setText(mConsultReplyBean.Content);
                break;
            case 2:
                break;
            case 4:
                break;
            default:
                break;

        }
    }
}
