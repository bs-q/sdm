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
import q.sdm.ui.address.AddAddressViewModel;
import q.sdm.ui.base.activity.BaseActivity;
import q.sdm.ui.category.CategoryViewModel;
import q.sdm.ui.location.LocationViewModel;
import q.sdm.ui.login.LoginViewModel;
import q.sdm.ui.main.MainViewModel;
import q.sdm.ui.main.cart.edit.CartEditViewModel;
import q.sdm.ui.product.ProductDetailViewModel;
import q.sdm.ui.recovery.RecoveryViewModel;
import q.sdm.ui.recovery.complete.RecoveryCompleteViewModel;
import q.sdm.ui.register.RegisterViewModel;
import q.sdm.ui.register.complete.RegisterCompleteViewModel;
import q.sdm.ui.register.verify.VerifyViewModel;
import q.sdm.ui.search.SearchViewModel;
import q.sdm.ui.search.result.SearchResultViewModel;
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

    @Provides
    @ActivityScope
    RegisterCompleteViewModel provideRegisterCompleteViewModel(Repository repository, Context application) {
        Supplier<RegisterCompleteViewModel> supplier = () -> new RegisterCompleteViewModel(repository, (MVVMApplication) application);
        ViewModelProviderFactory<RegisterCompleteViewModel> factory = new ViewModelProviderFactory<>(RegisterCompleteViewModel.class, supplier);
        return new ViewModelProvider(activity, factory).get(RegisterCompleteViewModel.class);
    }

    @Provides
    @ActivityScope
    RecoveryViewModel provideRecoveryViewModel(Repository repository, Context application) {
        Supplier<RecoveryViewModel> supplier = () -> new RecoveryViewModel(repository, (MVVMApplication) application);
        ViewModelProviderFactory<RecoveryViewModel> factory = new ViewModelProviderFactory<>(RecoveryViewModel.class, supplier);
        return new ViewModelProvider(activity, factory).get(RecoveryViewModel.class);
    }

    @Provides
    @ActivityScope
    RecoveryCompleteViewModel provideRecoveryCompleteViewModel(Repository repository, Context application) {
        Supplier<RecoveryCompleteViewModel> supplier = () -> new RecoveryCompleteViewModel(repository, (MVVMApplication) application);
        ViewModelProviderFactory<RecoveryCompleteViewModel> factory = new ViewModelProviderFactory<>(RecoveryCompleteViewModel.class, supplier);
        return new ViewModelProvider(activity, factory).get(RecoveryCompleteViewModel.class);
    }

    @Provides
    @ActivityScope
    LocationViewModel provideLocationViewModel(Repository repository, Context application) {
        Supplier<LocationViewModel> supplier = () -> new LocationViewModel(repository, (MVVMApplication) application);
        ViewModelProviderFactory<LocationViewModel> factory = new ViewModelProviderFactory<>(LocationViewModel.class, supplier);
        return new ViewModelProvider(activity, factory).get(LocationViewModel.class);
    }

    @Provides
    @ActivityScope
    CategoryViewModel provideCategoryViewModel(Repository repository, Context application) {
        Supplier<CategoryViewModel> supplier = () -> new CategoryViewModel(repository, (MVVMApplication) application);
        ViewModelProviderFactory<CategoryViewModel> factory = new ViewModelProviderFactory<>(CategoryViewModel.class, supplier);
        return new ViewModelProvider(activity, factory).get(CategoryViewModel.class);
    }

    @Provides
    @ActivityScope
    ProductDetailViewModel provideProductDetailViewModel(Repository repository, Context application) {
        Supplier<ProductDetailViewModel> supplier = () -> new ProductDetailViewModel(repository, (MVVMApplication) application);
        ViewModelProviderFactory<ProductDetailViewModel> factory = new ViewModelProviderFactory<>(ProductDetailViewModel.class, supplier);
        return new ViewModelProvider(activity, factory).get(ProductDetailViewModel.class);
    }

    @Provides
    @ActivityScope
    SearchViewModel provideSearchViewModel(Repository repository, Context application) {
        Supplier<SearchViewModel> supplier = () -> new SearchViewModel(repository, (MVVMApplication) application);
        ViewModelProviderFactory<SearchViewModel> factory = new ViewModelProviderFactory<>(SearchViewModel.class, supplier);
        return new ViewModelProvider(activity, factory).get(SearchViewModel.class);
    }

    @Provides
    @ActivityScope
    SearchResultViewModel provideSearchResultViewModel(Repository repository, Context application) {
        Supplier<SearchResultViewModel> supplier = () -> new SearchResultViewModel(repository, (MVVMApplication) application);
        ViewModelProviderFactory<SearchResultViewModel> factory = new ViewModelProviderFactory<>(SearchResultViewModel.class, supplier);
        return new ViewModelProvider(activity, factory).get(SearchResultViewModel.class);
    }

    @Provides
    @ActivityScope
    CartEditViewModel provideCartEditViewModel(Repository repository, Context application) {
        Supplier<CartEditViewModel> supplier = () -> new CartEditViewModel(repository, (MVVMApplication) application);
        ViewModelProviderFactory<CartEditViewModel> factory = new ViewModelProviderFactory<>(CartEditViewModel.class, supplier);
        return new ViewModelProvider(activity, factory).get(CartEditViewModel.class);
    }

    @Provides
    @ActivityScope
    AddAddressViewModel provideAddAddressViewModel(Repository repository, Context application) {
        Supplier<AddAddressViewModel> supplier = () -> new AddAddressViewModel(repository, (MVVMApplication) application);
        ViewModelProviderFactory<AddAddressViewModel> factory = new ViewModelProviderFactory<>(AddAddressViewModel.class, supplier);
        return new ViewModelProvider(activity, factory).get(AddAddressViewModel.class);
    }
}
