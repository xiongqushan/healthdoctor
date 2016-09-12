package haozuo.com.healthdoctor.ioc;

import dagger.Module;
import dagger.Provides;
import haozuo.com.healthdoctor.contract.WelcomeContract;
import haozuo.com.healthdoctor.view.welcome.WelcomeFragment;


@Module
public class WelcomeModule {
    int currVersion;

    public WelcomeModule() {
    }

    public WelcomeModule(int currVersion) {
        this.currVersion = currVersion;
    }


    @Provides
    @ScopeType.ActivityScope
    WelcomeContract.IWelcomeView provideWelcomeView() {
        return WelcomeFragment.getInstance();
    }


    @Provides
    @ScopeType.ActivityScope
    int provideCurrVersion() {
        return currVersion;
    }
}
