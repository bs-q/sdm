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
import q.sdm.di.scope.SheetScope;
import q.sdm.ui.base.fragment.BaseSheet;
import q.sdm.ui.main.account.AccountViewModel;
import q.sdm.ui.main.cart.CartViewModel;
import q.sdm.ui.main.cart.added.edit.ProductAddedEditViewModel;
import q.sdm.ui.main.cart.edit.CartSheetViewModel;
import q.sdm.ui.main.home.HomeViewModel;
import q.sdm.utils.GetInfo;

@Module
public class SheetModule {

    private BaseSheet<?, ?> fragment;

    public SheetModule(BaseSheet<?, ?> fragment) {
        this.fragment = fragment;
    }

    @Named("access_token")
    @Provides
    @SheetScope
    String provideToken(Repository repository) {
        return repository.getToken();
    }

    @Named("device_id")
    @Provides
    @SheetScope
    String provideDeviceId( Context applicationContext){
        return GetInfo.getAll(applicationContext);
    }
    @Provides
    @SheetScope
    CartSheetViewModel provideCartSheetViewModel(Repository repository, Context application) {
        Supplier<CartSheetViewModel> supplier = () -> new CartSheetViewModel(repository, (MVVMApplication)application);
        ViewModelProviderFactory<CartSheetViewModel> factory = new ViewModelProviderFactory<>(CartSheetViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(CartSheetViewModel.class);
    }

    @Provides
    @SheetScope
    ProductAddedEditViewModel provideProductAddedEditViewModel(Repository repository, Context application) {
        Supplier<ProductAddedEditViewModel> supplier = () -> new ProductAddedEditViewModel(repository, (MVVMApplication)application);
        ViewModelProviderFactory<ProductAddedEditViewModel> factory = new ViewModelProviderFactory<>(ProductAddedEditViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(ProductAddedEditViewModel.class);
    }
    
}
