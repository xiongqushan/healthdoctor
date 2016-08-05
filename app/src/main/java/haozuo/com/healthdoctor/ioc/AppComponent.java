package haozuo.com.healthdoctor.ioc;

import javax.inject.Singleton;

import dagger.Component;
import haozuo.com.healthdoctor.framework.HZApplication;

/**
 * Created by xiongwei1 on 2016/8/5.
 */

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    HZApplication getApplication();
}
