package haozuo.com.healthdoctor.view.Fragment;

import android.content.Intent;
import android.os.Bundle;
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
import haozuo.com.healthdoctor.presenter.GroupPresenterImpl;
import haozuo.com.healthdoctor.presenter.IGroupPresenter;
import haozuo.com.healthdoctor.view.Activity.GroupCustomListActivity;
import haozuo.com.healthdoctor.view.Activity.HomeActivity;
import haozuo.com.healthdoctor.view.Interface.IGroupFragment;
import haozuo.com.healthdoctor.view.adapter.GroupAdapter;


public class GroupFragment extends BaseFragment implements IGroupFragment {
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
        ((HomeActivity)shareCurrentActivity()).initTitle();
        View.OnClickListener onGroupItemClickListener =new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Object[]tag=(Object[])view.getTag();
                int groupId = (int) tag[0];
                Intent intent=new Intent(getContext(),GroupCustomListActivity.class);
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
            rootview = inflater.inflate(R.layout.fragment_group_2, container, false);
            ButterKnife.bind(this,rootview);
            list_doctor_group.setAdapter(groupAdapter);
            shareCurrentActivity().showDialog();
            mIGroupPresenter.requestGroupList(UserManager.getInstance().getDoctorInfo().Id);
        }
        return rootview;
    }

    @Override
    public void onStop() {
        super.onStop();
        mIGroupPresenter.cancelRequest();
        shareCurrentActivity().hideDialog();
    }

    @Override
    public void handlerGetGroupList(GlobalShell<List<DoctorGroupBean>> result) {
        if(result.LogicSuccess) {
            groupList.clear();
            groupList.addAll(result.Data);
            groupAdapter.notifyDataSetChanged();
            shareCurrentActivity().hideDialog();
        }
        else{
            shareCurrentActivity().hideDialog(result.Message);
        }
    }
}
