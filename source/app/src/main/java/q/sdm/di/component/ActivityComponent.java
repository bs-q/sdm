package q.sdm.di.component;

import dagger.Component;
import q.sdm.di.module.ActivityModule;
import q.sdm.di.scope.ActivityScope;
import q.sdm.ui.category.CategoryActivity;
import q.sdm.ui.location.LocationActivity;
import q.sdm.ui.login.LoginActivity;
import q.sdm.ui.main.MainActivity;
import q.sdm.ui.product.ProductDetailActivity;
import q.sdm.ui.recovery.RecoveryActivity;
import q.sdm.ui.recovery.complete.RecoveryCompleteActivity;
import q.sdm.ui.register.RegisterActivity;
import q.sdm.ui.register.complete.RegisterCompleteActivity;
import q.sdm.ui.register.verify.VerifyActivity;

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
}

