package haozuo.com.healthdoctor.view.consult;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.contract.ConsultContract;


public class ConsultDetailFragment extends Fragment {
    Context mContext;
    View rootView;
    ConsultContract.IConsultPresenter mConsultPresenter;

    public ConsultDetailFragment(){};

    public static ConsultDetailFragment newInstance(){
        ConsultDetailFragment fragment = new ConsultDetailFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext=getContext();
        ConsultFragment consultFragment=(ConsultFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.frameContent);
        mConsultPresenter = consultFragment.ConsultPresenter;
        if(rootView==null){
            rootView= inflater.inflate(R.layout.fragment_consult_list, container, false);
            ButterKnife.bind(this,rootView);
        }
        return rootView;
    }

}
