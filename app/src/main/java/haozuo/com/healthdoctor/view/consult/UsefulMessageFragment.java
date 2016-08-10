package haozuo.com.healthdoctor.view.consult;

import haozuo.com.healthdoctor.contract.UsefulMessageContract;
import haozuo.com.healthdoctor.view.base.AbstractView;

/**
 * Created by hzguest3 on 2016/8/9.
 */
public class UsefulMessageFragment extends AbstractView implements UsefulMessageContract.IUsefulMessageView {
    private UsefulMessageContract.IUsefulMessagePresenter mIUsefulMessagePresenter;

    public static UsefulMessageFragment newInstance() {
        UsefulMessageFragment fragment = new UsefulMessageFragment();
        return fragment;
    }

    @Override
    public void setPresenter(UsefulMessageContract.IUsefulMessagePresenter presenter) {
        mIUsefulMessagePresenter = presenter;
    }
}
