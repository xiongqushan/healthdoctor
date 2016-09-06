package haozuo.com.healthdoctor.ioc;

import dagger.Module;
import dagger.Provides;
import haozuo.com.healthdoctor.contract.WelcomeContract;
import haozuo.com.healthdoctor.view.welcome.WelcomeActivity;


@Module
public class WelcomeModule {

    private WelcomeActivity activity;

    public WelcomeModule() {
    }

    public WelcomeModule(WelcomeActivity activity) {
        this.activity = activity;
    }

    @Provides
    @ScopeType.ActivityScope
    WelcomeContract.IWelcomeView provideWelcomeView() {
        return activity;
    }
}
