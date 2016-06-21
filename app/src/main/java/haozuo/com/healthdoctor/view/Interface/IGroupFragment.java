package haozuo.com.healthdoctor.view.Interface;

import java.util.List;

import haozuo.com.healthdoctor.bean.DoctorGroupBean;
import haozuo.com.healthdoctor.bean.GlobalShell;

/**
 * Created by xiongwei1 on 2016/6/20.
 */
public interface IGroupFragment {
    void handlerGetGroupList(GlobalShell<List<DoctorGroupBean>> result);
}
