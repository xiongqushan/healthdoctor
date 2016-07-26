package haozuo.com.healthdoctor.model;

import java.util.List;

import haozuo.com.healthdoctor.bean.DoctorBean;
import haozuo.com.healthdoctor.bean.DoctorGroupBean;
import haozuo.com.healthdoctor.bean.GlobalShell;
import haozuo.com.healthdoctor.bean.GroupCustInfoBean;
import haozuo.com.healthdoctor.bean.PageBean;
import haozuo.com.healthdoctor.contract.BaseModel;
import haozuo.com.healthdoctor.listener.OnHandlerResultListener;

/**
 * Created by Administrator on 2016/6/18.
 */
public interface IUserModel extends BaseModel {

    public void GetSMSCode(String tag, String mobile, final OnHandlerResultListener<GlobalShell<Boolean>> callbackListener);

    public void Login(String tag,String mobile,int smsCode, final OnHandlerResultListener<GlobalShell<DoctorBean>> callbackListener);

    public void GetGroup(String tag,int doctorId, final OnHandlerResultListener<GlobalShell<List<DoctorGroupBean>>> callbackListener);

    public void GetGroupCustInfoList(String tag,int serviceDeptId,int groupId,int doctorId,int pageIndex,int pageSize, final OnHandlerResultListener<GlobalShell<PageBean<GroupCustInfoBean>>> callbackListener);

    public void GetUserDetail(String tag,int customerId, final OnHandlerResultListener<GlobalShell<GroupCustInfoBean>> callbackListener);

}
