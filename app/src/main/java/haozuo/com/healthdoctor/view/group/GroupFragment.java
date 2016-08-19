package haozuo.com.healthdoctor.view.group;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.bean.DoctorGroupBean;
import haozuo.com.healthdoctor.contract.GroupContract;
import haozuo.com.healthdoctor.contract.IBasePresenter;
import haozuo.com.healthdoctor.view.base.AbstractView;
import haozuo.com.healthdoctor.view.custom.GroupCustomListActivity;

public class GroupFragment extends AbstractView implements GroupContract.IGroupView {
    Context mContext;
    View rootView;
    GroupContract.IGroupPresenter mGroupPresenter;

    @Bind(R.id.firstGroupName)
    TextView firstGroupName;
    @Bind(R.id.secondGroupName)
    TextView secondGroupName;
    @Bind(R.id.thirdGroupName)
    TextView thirdGroupName;
    @Bind(R.id.fourthGroupName)
    TextView fourthGroupName;
    @Bind(R.id.fifthGroupName)
    TextView fifthGroupName;
    @Bind(R.id.sixthGroupName)
    TextView sixthGroupName;

    public GroupFragment() {
    }

    @Override
    protected IBasePresenter getPresenter() {
        return mGroupPresenter;
    }

    public static GroupFragment newInstance() {
        GroupFragment fragment = new GroupFragment();
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("GroupFragment", "onResume");
        mGroupPresenter.start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = getContext();
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_group, container, false);
            ButterKnife.bind(this, rootView);
        }
        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
        mGroupPresenter.cancelRequest();
    }

    @Override
    public void setPresenter(GroupContract.IGroupPresenter presenter) {
        mGroupPresenter = presenter;
    }

    @Override
    public void setGroupInfo(final List<DoctorGroupBean> doctorGroupBeanList) {
        TextView[] GroupNameArray = {firstGroupName, secondGroupName, thirdGroupName, fourthGroupName, fifthGroupName, sixthGroupName};

        for (int i = 0; i < GroupNameArray.length; i++) {
            final int finalI = i;
            String doctorNum = "服务人数: " + doctorGroupBeanList.get(i).doctorNum;
            String groupName = doctorGroupBeanList.get(i).name;
            String groupContent = groupName + "\n" + doctorNum;
            GroupNameArray[i].setText(groupContent);
            GroupNameArray[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, GroupCustomListActivity.class);
                    intent.putExtra(GroupCustomListActivity.EXTRA_GROUP_ID, doctorGroupBeanList.get(finalI));
                    mContext.startActivity(intent);
                }
            });

        }
    }


}

