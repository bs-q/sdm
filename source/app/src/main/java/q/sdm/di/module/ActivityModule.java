package q.sdm.di.module;

import android.content.Context;

import androidx.core.util.Supplier;
import androidx.lifecycle.ViewModelProvider;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import q.sdm.MVVMApplication;
import q.sdm.ViewModelProviderFactory;
import q.sdm.data.Repository;
import q.sdm.di.scope.ActivityScope;
import q.sdm.ui.base.activity.BaseActivity;
import q.sdm.ui.login.LoginViewModel;
import q.sdm.ui.main.MainViewModel;
import q.sdm.ui.register.RegisterViewModel;
import q.sdm.ui.register.verify.VerifyViewModel;
import q.sdm.utils.GetInfo;

@Module
public class ActivityModule {

    private BaseActivity<?, ?> activity;

    public ActivityModule(BaseActivity<?, ?> activity) {
        this.activity = activity;
    }

    @Named("access_token")
    @Provides
    @ActivityScope
    String provideToken(Repository repository){
        return repository.getToken();
    }

    @Named("device_id")
    @Provides
    @ActivityScope
    String provideDeviceId( Context applicationContext){
        return GetInfo.getAll(applicationContext);
    }


    @Provides
    @ActivityScope
    MainViewModel provideMainViewModel(Repository repository, Context application) {
        Supplier<MainViewModel> supplier = () -> new MainViewModel(repository, (MVVMApplication)application);
        ViewModelProviderFactory<MainViewModel> factory = new ViewModelProviderFactory<>(MainViewModel.class, supplier);
        return new ViewModelProvider(activity, factory).get(MainViewModel.class);
    }

    @Provides
    @ActivityScope
    LoginViewModel provideLoginViewModel(Repository repository, Context application) {
        Supplier<LoginViewModel> supplier = () -> new LoginViewModel(repository, (MVVMApplication) application);
        ViewModelProviderFactory<LoginViewModel> factory = new ViewModelProviderFactory<>(LoginViewModel.class, supplier);
        return new ViewModelProvider(activity, factory).get(LoginViewModel.class);
    }

    @Provides
    @ActivityScope
    RegisterViewModel provideRegisterViewModel(Repository repository, Context application) {
        Supplier<RegisterViewModel> supplier = () -> new RegisterViewModel(repository, (MVVMApplication) application);
        ViewModelProviderFactory<RegisterViewModel> factory = new ViewModelProviderFactory<>(RegisterViewModel.class, supplier);
        return new ViewModelProvider(activity, factory).get(RegisterViewModel.class);
    }

    @Provides
    @ActivityScope
    VerifyViewModel provideVerifyViewModel(Repository repository, Context application) {
        Supplier<VerifyViewModel> supplier = () -> new VerifyViewModel(repository, (MVVMApplication) application);
        ViewModelProviderFactory<VerifyViewModel> factory = new ViewModelProviderFactory<>(VerifyViewModel.class, supplier);
        return new ViewModelProvider(activity, factory).get(VerifyViewModel.class);
    }

}
