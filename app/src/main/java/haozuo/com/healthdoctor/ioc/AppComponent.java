package haozuo.com.healthdoctor.ioc;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import haozuo.com.healthdoctor.framework.HZApplication;
import haozuo.com.healthdoctor.model.ConsultModel;
import haozuo.com.healthdoctor.model.GroupModel;
import haozuo.com.healthdoctor.model.ReportModel;
import haozuo.com.healthdoctor.model.SystemModel;
import haozuo.com.healthdoctor.model.UserModel;

/**
 * Created by xiongwei1 on 2016/8/5.
 */

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    HZApplication getApplication();

    Context getContext();

//    IUserService getUserService();
//
//    IGroupService getGroupService();
//
//    IConsultService getConsultService();
//
//    IReportService getReportService();
//
//    ISystemService getSystemService();

    ConsultModel getConsultModel();

    GroupModel getGroupModel();

    ReportModel getReportModel();

    UserModel getUserModel();

    SystemModel getSystemModel();

//    OkHttpClient getOkHttpClient();

}
