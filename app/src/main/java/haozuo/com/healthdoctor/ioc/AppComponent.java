package haozuo.com.healthdoctor.ioc;

import android.content.Context;

import com.squareup.okhttp.OkHttpClient;

import javax.inject.Singleton;

import dagger.Component;
import haozuo.com.healthdoctor.framework.HZApplication;
import haozuo.com.healthdoctor.service.IConsultService;
import haozuo.com.healthdoctor.service.IGroupService;
import haozuo.com.healthdoctor.service.IReportService;
import haozuo.com.healthdoctor.service.ISystemService;
import haozuo.com.healthdoctor.service.IUserService;

/**
 * Created by xiongwei1 on 2016/8/5.
 */

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    HZApplication getApplication();

    Context getContext();

    IUserService getUserService();

    IGroupService getGroupService();

    IConsultService getConsultService();

    IReportService getReportService();

    ISystemService getSystemService();

    OkHttpClient getOkHttpClient();
}
