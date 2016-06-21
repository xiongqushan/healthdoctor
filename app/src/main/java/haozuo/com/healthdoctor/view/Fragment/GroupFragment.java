package haozuo.com.healthdoctor.view.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;


import butterknife.Bind;
import butterknife.ButterKnife;
import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.bean.DoctorGroupBean;
import haozuo.com.healthdoctor.bean.GlobalShell;
import haozuo.com.healthdoctor.manager.UserManager;
import haozuo.com.healthdoctor.model.IUserModel;
import haozuo.com.healthdoctor.model.UserModel;
import haozuo.com.healthdoctor.presenter.GroupPresenterImpl;
import haozuo.com.healthdoctor.presenter.IGroupPresenter;
import haozuo.com.healthdoctor.view.Interface.IGroupFragment;
import haozuo.com.healthdoctor.view.UserInfoActivity;
import haozuo.com.healthdoctor.view.adapter.GroupAdapter;


public class GroupFragment extends Fragment implements IGroupFragment {
    @Bind(R.id.list_doctor_group)GridView list_doctor_group;

    View rootview;
    IGroupPresenter mIGroupPresenter;
    List<DoctorGroupBean> groupList ;
    GroupAdapter groupAdapter;
    public GroupFragment() {
        mIGroupPresenter=new GroupPresenterImpl(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View.OnClickListener onGroupItemClickListener =new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Object[]tag=(Object[])view.getTag();
                int groupId = (int) tag[0];
                Intent intent=new Intent(getContext(),UserInfoActivity.class);
                intent.putExtra("GroupId",groupId);
                startActivity(intent);
            }
        };
        groupList=new ArrayList<>();
        groupAdapter=new GroupAdapter(getContext(),groupList,onGroupItemClickListener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(rootview==null) {
            rootview = inflater.inflate(R.layout.fragment_group, container, false);
            ButterKnife.bind(this,rootview);
            list_doctor_group.setAdapter(groupAdapter);
            mIGroupPresenter.requestGroupList(UserManager.getInstance().getDoctorInfo().Id);
        }
        return rootview;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void handlerGetGroupList(GlobalShell<List<DoctorGroupBean>> result) {
        groupList.clear();
        groupList.addAll(result.Data);
        groupAdapter.notifyDataSetChanged();
    }
}
