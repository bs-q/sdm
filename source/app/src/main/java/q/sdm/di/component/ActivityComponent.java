package q.sdm.di.component;

import dagger.Component;
import q.sdm.di.module.ActivityModule;
import q.sdm.di.scope.ActivityScope;
import q.sdm.ui.address.AddAddressActivity;
import q.sdm.ui.category.CategoryActivity;
import q.sdm.ui.category.product.CategoryProductActivity;
import q.sdm.ui.location.LocationActivity;
import q.sdm.ui.login.LoginActivity;
import q.sdm.ui.main.MainActivity;
import q.sdm.ui.main.account.detail.AccountDetailActivity;
import q.sdm.ui.main.account.email.UpdateEmailActivity;
import q.sdm.ui.main.account.name.UpdateNameActivity;
import q.sdm.ui.main.account.password.UpdatePasswordActivity;
import q.sdm.ui.main.cart.CartActivity;
import q.sdm.ui.main.cart.added.ProductAddedActivity;
import q.sdm.ui.main.cart.order.complete.OrderCompleteActivity;
import q.sdm.ui.main.cart.order.detail.OrderDetailActivity;
import q.sdm.ui.main.cart.order.history.OrderHistoryActivity;
import q.sdm.ui.product.ProductDetailActivity;
import q.sdm.ui.recovery.RecoveryActivity;
import q.sdm.ui.recovery.complete.RecoveryCompleteActivity;
import q.sdm.ui.recovery.input.InputPasswordActivity;
import q.sdm.ui.register.RegisterActivity;
import q.sdm.ui.register.complete.RegisterCompleteActivity;
import q.sdm.ui.register.verify.VerifyActivity;
import q.sdm.ui.search.SearchActivity;
import q.sdm.ui.search.result.SearchResultActivity;

@ActivityScope
@Component(modules = {ActivityModule.class}, dependencies = AppComponent.class)
public interface ActivityComponent {
    void inject(MainActivity activity);

    void inject(LoginActivity activity);

    void inject(RegisterActivity activity);

    void inject(VerifyActivity activity);

    void inject(RegisterCompleteActivity activity);

    void inject(RecoveryActivity activity);

    void inject(RecoveryCompleteActivity activity);

    void inject(LocationActivity activity);

    void inject(CategoryActivity activity);

    void inject(ProductDetailActivity activity);

    void inject(SearchActivity activity);

    void inject(SearchResultActivity activity);

    void inject(AddAddressActivity activity);

    void inject(AccountDetailActivity activity);

    void inject(ProductAddedActivity activity);

    void inject(CartActivity activity);

    void inject(UpdateNameActivity activity);

    void inject(UpdateEmailActivity activity);

    void inject(UpdatePasswordActivity activity);

    void inject(CategoryProductActivity activity);

    void inject(OrderCompleteActivity activity);

    void inject(OrderHistoryActivity activity);

    void inject(OrderDetailActivity activity);

    void inject(InputPasswordActivity activity);
}

