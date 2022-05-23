package q.sdm.di.component;


import dagger.Component;
import q.sdm.di.module.FragmentModule;
import q.sdm.di.scope.FragmentScope;
import q.sdm.ui.main.account.AccountFragment;
import q.sdm.ui.main.cart.CartFragment;
import q.sdm.ui.main.home.HomeFragment;

@FragmentScope
@Component(modules = {FragmentModule.class},dependencies = AppComponent.class)
public interface FragmentComponent {

    void inject(HomeFragment fragment);

    void inject(CartFragment fragment);

    void inject(AccountFragment fragment);
}
