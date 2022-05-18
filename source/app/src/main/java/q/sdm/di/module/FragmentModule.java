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
import q.sdm.di.scope.FragmentScope;
import q.sdm.ui.base.fragment.BaseFragment;
import q.sdm.ui.main.news.AccountViewModel;
import q.sdm.ui.main.revenue.RevenueViewModel;
import q.sdm.ui.main.setting.SettingViewModel;
import q.sdm.ui.main.store.StoreViewModel;
import q.sdm.utils.GetInfo;

@Module
public class FragmentModule {

    private BaseFragment<?, ?> fragment;

    public FragmentModule(BaseFragment<?, ?> fragment) {
        this.fragment = fragment;
    }

    @Named("access_token")
    @Provides
    @FragmentScope
    String provideToken(Repository repository) {
        return repository.getToken();
    }

    @Named("device_id")
    @Provides
    @FragmentScope
    String provideDeviceId( Context applicationContext){
        return GetInfo.getAll(applicationContext);
    }

    @Provides
    @FragmentScope
    StoreViewModel provideStoreViewModel(Repository repository, Context application) {
        Supplier<StoreViewModel> supplier = () -> new StoreViewModel(repository, (MVVMApplication)application);
        ViewModelProviderFactory<StoreViewModel> factory = new ViewModelProviderFactory<>(StoreViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(StoreViewModel.class);

    }

    @Provides
    @FragmentScope
    RevenueViewModel provideRevenueViewModel(Repository repository, Context application) {
        Supplier<RevenueViewModel> supplier = () -> new RevenueViewModel(repository, (MVVMApplication)application);
        ViewModelProviderFactory<RevenueViewModel> factory = new ViewModelProviderFactory<>(RevenueViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(RevenueViewModel.class);
    }

    @Provides
    @FragmentScope
    AccountViewModel provideNewsViewModel(Repository repository, Context application) {
        Supplier<AccountViewModel> supplier = () -> new AccountViewModel(repository, (MVVMApplication)application);
        ViewModelProviderFactory<AccountViewModel> factory = new ViewModelProviderFactory<>(AccountViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(AccountViewModel.class);
    }
    @Provides
    @FragmentScope
    SettingViewModel provideSettingViewModel(Repository repository, Context application) {
        Supplier<SettingViewModel> supplier = () -> new SettingViewModel(repository, (MVVMApplication)application);
        ViewModelProviderFactory<SettingViewModel> factory = new ViewModelProviderFactory<>(SettingViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(SettingViewModel.class);
    }

}
