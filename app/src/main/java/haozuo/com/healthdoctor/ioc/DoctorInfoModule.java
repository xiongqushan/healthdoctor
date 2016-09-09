package haozuo.com.healthdoctor.ioc;

import dagger.Module;
import dagger.Provides;
import haozuo.com.healthdoctor.contract.DoctorInfoContract;
import haozuo.com.healthdoctor.view.mine.DoctorInfoFragment;


/**
 * Created by zhangzhongyao on 2016/9/8.
 */

@Module
public class DoctorInfoModule {
    @ScopeType.ActivityScope
    @Provides
    DoctorInfoContract.IDoctorInfoView provideDoctorInfoView() {
        return DoctorInfoFragment.getInstance();
    }
}
