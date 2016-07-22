package haozuo.com.healthdoctor.view.custom;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.Bind;
import butterknife.ButterKnife;
import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.contract.AbsView;
import haozuo.com.healthdoctor.contract.CustomDetailContract;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CustomDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CustomDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CustomDetailFragment extends AbsView implements CustomDetailContract.ICustomDetailView{
    Context mContext;
    View rootView;
    CustomDetailContract.ICustomDetailPresenter mCustomDetailPresenter;

    public CustomDetailFragment(){

    }

    public static CustomDetailFragment newInstance() {
        CustomDetailFragment fragment = new CustomDetailFragment();
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        mCustomDetailPresenter.start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext=getContext();
        if(rootView==null){
            rootView= inflater.inflate(R.layout.fragment_group, container, false);
            ButterKnife.bind(this,rootView);
        }
        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
        mCustomDetailPresenter.cancelRequest();
    }
    @Override
    public void setPresenter(CustomDetailContract.ICustomDetailPresenter presenter) {
        mCustomDetailPresenter=presenter;
    }
}
