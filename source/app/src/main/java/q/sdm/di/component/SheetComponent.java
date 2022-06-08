package q.sdm.di.component;

import dagger.Component;
import q.sdm.di.module.SheetModule;
import q.sdm.di.scope.SheetScope;
import q.sdm.ui.main.account.request.RequestPasswordSheet;
import q.sdm.ui.main.cart.added.edit.ProductAddedEditSheet;
import q.sdm.ui.main.cart.edit.CartSheet;
import q.sdm.ui.main.cart.receiver.UpdateReceiverSheet;
import q.sdm.ui.payment.SelectPaymentSheet;

@SheetScope
@Component(modules = {SheetModule.class},dependencies = AppComponent.class)
public interface SheetComponent {
    void inject(CartSheet cartSheet);

    void inject(ProductAddedEditSheet sheet);

    void inject(RequestPasswordSheet sheet);

    void inject(UpdateReceiverSheet sheet);

    void inject(SelectPaymentSheet sheet);
}
