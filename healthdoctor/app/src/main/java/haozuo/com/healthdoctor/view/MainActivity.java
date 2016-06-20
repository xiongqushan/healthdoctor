package haozuo.com.healthdoctor.view;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import haozuo.com.healthdoctor.R;
import haozuo.com.healthdoctor.view.Activity.BaseActivity;
import haozuo.com.healthdoctor.view.adapter.GroupAdapter;
import haozuo.com.healthdoctor.dispatcher.BaseDispatcher;
import haozuo.com.healthdoctor.bean.DoctorGroupBean;
import haozuo.com.healthdoctor.manager.UserManager;
import haozuo.com.healthdoctor.view.Activity.LoginActivity;

public class MainActivity extends BaseActivity {
    @Bind(R.id.list_doctor_group)GridView list_doctor_group;

    List<DoctorGroupBean> groupList ;
    GroupAdapter groupAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        if(!UserManager.getInstance().exist()){
            Intent intent=new Intent(this,LoginActivity.class);
            startActivity(intent);
            return;
        }

        View.OnClickListener onGroupItemClickListener =new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Object[]tag=(Object[])view.getTag();
                int groupId = (int) tag[0];
                Intent intent=new Intent(MainActivity.this,UserInfoActivity.class);
                intent.putExtra("GroupId",groupId);
                startActivity(intent);
            }
        };

        groupList=new ArrayList<>();
        groupAdapter=new GroupAdapter(MainActivity.this,groupList,onGroupItemClickListener);
        list_doctor_group.setAdapter(groupAdapter);

        Map<String, Object> params = new HashMap<>();
        int doctorId = UserManager.getInstance().getDoctorInfo().Id;
        params.put("doctorId", doctorId);
        showDialog();
        //shareControllerInstance(UserDispatcher.class).sendMessage(BaseDispatcher.WHAT_USER_GETGROUP,params);
    }



    @Override
    protected void onReceiveBroadcast(String filterAction) {
    }

    private void handeleGetGroupInfo(Message msg) {
        BaseDispatcher.RequestResult requestResult = (BaseDispatcher.RequestResult) msg.obj;
        if(requestResult.LogicSuccess) {
            List<DoctorGroupBean> dataList = (List<DoctorGroupBean>) requestResult.ResultData;
            groupList.clear();
            groupList.addAll(dataList);
            groupAdapter.notifyDataSetChanged();
        }
        hideDialog();
    }

    @Override
    protected void onStop(){
        super.onStop();
        //shareControllerInstance(UserDispatcher.class).cancelRequest(new int[]{BaseDispatcher.WHAT_USER_GetSMSCode,BaseDispatcher.WHAT_USER_LOGIN});
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String result = data.getExtras().getString("code");
        String a=result;
    }

    private long exitTime;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
