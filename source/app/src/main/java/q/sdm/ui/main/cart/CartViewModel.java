package q.sdm.ui.main.cart;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableDouble;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.databinding.ObservableLong;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.paypal.checkout.PayPalCheckout;
import com.paypal.checkout.config.CheckoutConfig;
import com.paypal.checkout.config.Environment;
import com.paypal.checkout.config.SettingsConfig;
import com.paypal.checkout.createorder.CreateOrder;
import com.paypal.checkout.createorder.CreateOrderActions;
import com.paypal.checkout.createorder.CurrencyCode;
import com.paypal.checkout.createorder.OrderIntent;
import com.paypal.checkout.createorder.ShippingPreference;
import com.paypal.checkout.createorder.UserAction;
import com.paypal.checkout.order.Amount;
import com.paypal.checkout.order.AppContext;
import com.paypal.checkout.order.Order;
import com.paypal.checkout.order.PurchaseUnit;
import com.paypal.checkout.order.Shipping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import q.sdm.BuildConfig;
import q.sdm.MVVMApplication;
import q.sdm.data.Repository;
import q.sdm.data.model.api.request.order.CreateOrderRequest;
import q.sdm.data.model.api.response.product.ProductResponse;
import q.sdm.data.model.db.ProductEntity;
import q.sdm.ui.base.activity.BaseDbCallback;
import q.sdm.ui.base.activity.BaseRequestCallback;
import q.sdm.ui.base.activity.BaseViewModel;
import q.sdm.ui.base.fragment.BaseFragmentViewModel;
import timber.log.Timber;

public class CartViewModel extends BaseViewModel {
    public ObservableDouble total = new ObservableDouble(0);
    public ObservableDouble vat = new ObservableDouble(0);
    public ObservableDouble reduce = new ObservableDouble(0);
    public ObservableField<String> sale = new ObservableField<>("0");
    public ObservableInt selectedPayment = new ObservableInt(1);

    public ObservableDouble totalAndVat = new ObservableDouble(0);
    public ObservableField<String> receiverName = new ObservableField<>("");
    public ObservableField<String> receiverPhone = new ObservableField<>("");
    public ObservableField<String> receiverAddress = new ObservableField<>("");
    LiveData<List<ProductEntity>> productEntityLiveData;
    public MutableLiveData<String> message = new MutableLiveData<String>();

    public CartViewModel(Repository repository, MVVMApplication application) {
        super(repository, application);
        setupPaypal();
    }

    private void setupPaypal(){
        PayPalCheckout.setConfig(new CheckoutConfig(application
                ,"AWzq9Wc1ueNGbydGK3PXkXYqaWiN_qHLxT6P7yJqQ79FGSc9jfjF0z3uiRvk60k_lg73ZRY0rWELlcq0"
                , Environment.SANDBOX
                ,"q.sdm://paypalpay"
                ,CurrencyCode.USD
                ,UserAction.PAY_NOW));
        PayPalCheckout.registerCallbacks(approval -> {
            message.postValue("ss");
        },()->{
            message.postValue("cc");
        },errorInfo -> {
            message.postValue("er");

        });
    }
    public void createPaypalOrder(){
        PayPalCheckout.startCheckout(new CreateOrder() {
            @Override
            public void create(@NonNull CreateOrderActions createOrderActions) {
                ArrayList<PurchaseUnit> purchaseUnits = new ArrayList<>();
                purchaseUnits.add(
                        new PurchaseUnit.Builder()
                                .amount(
                                        new Amount.Builder()
                                                .currencyCode(CurrencyCode.USD)
                                                .value("22")
                                                .build()
                                )
                                .build()
                );

                Order order = new Order.Builder().intent(OrderIntent.CAPTURE)
                        .appContext(
                        new AppContext.Builder().userAction(UserAction.PAY_NOW).shippingPreference(ShippingPreference.NO_SHIPPING).build())
                        .purchaseUnitList(purchaseUnits).build();
                createOrderActions.create(order,orderId ->{

                });
            }
        });

    }

    public void observeProductsInCart() {
        productEntityLiveData = repository.getSqliteService().loadAllProductToLiveData();
    }

    public void deleteProduct(ProductEntity productEntity) {
        compositeDisposable.add(repository.getSqliteService().deleteProduct(productEntity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe());
    }

    public void updateCart(BaseDbCallback<Boolean> callback) {
        showLoading();
        compositeDisposable.add(repository.getApiService().getNukeProducts().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(
                        response -> {
                            syncProduct(response.getData().getData(),callback);
                            hideLoading();
                        }, throwable -> {
                            Timber.d(throwable);
                        }
                ));
    }
    public void syncProduct(List<ProductResponse> productResponses,BaseDbCallback<Boolean> callback){
        List<Long> productIds = productEntityLiveData.getValue().stream().map(o->o.id).collect(Collectors.toList());
        List<ProductResponse> filterList = productResponses.stream().filter(o->productIds.contains(o.getId())).collect(Collectors.toList());
        List<ProductEntity> currentCart = productEntityLiveData.getValue();
        List<Long> outOfStockList = new ArrayList<>();
        if (filterList.size()!=currentCart.size()) return;
        for (int i = 0; i < filterList.size(); i++) {
            if (filterList.get(i).getQuantityInStock()==0){
                outOfStockList.add(currentCart.get(i).getId());
            }
            else if (currentCart.get(i).amount>filterList.get(i).getQuantityInStock()){
                currentCart.get(i).setAmount(filterList.get(i).getQuantityInStock());
            }
            currentCart.get(i).setSale(filterList.get(i).getSaleoff());
        }
        updateAllProduct(currentCart,callback);
        deleteAllProduct(outOfStockList);
    }
    public void updateAllProduct(List<ProductEntity> productEntities, BaseDbCallback<Boolean> callback) {
        compositeDisposable.add(repository.getSqliteService().insertAllProduct(productEntities)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> {
                            if (response) {
                                callback.doSuccess(response);
                            }
                            hideLoading();
                        },throwable -> {
                            hideLoading();
                            callback.doError(throwable);
                        }
                ));
    }
    public void deleteAllProduct(List<Long> productEntities) {
        compositeDisposable.add(repository.getSqliteService().deleteListProduct(productEntities)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> {
                            if (response) {
                                Timber.d("delete product");
                            }
                        },throwable -> {
                           Timber.d(throwable);
                        }
                ));
    }

    public void createOrder(BaseRequestCallback<Boolean> callback) {
        CreateOrderRequest request = new CreateOrderRequest();
        request.setReceiverName(receiverName.get());
        request.setReceiverPhone(receiverPhone.get());
        request.setOrdersAddress(receiverAddress.get());
        request.createOrderDetail(productEntityLiveData.getValue());
        request.setPaymentMethod(selectedPayment.get());
        compositeDisposable.add(repository.getApiService().createOrder(request).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(
                        response -> {
                            if (response.isResult()) {
                                callback.doSuccess(true);
                            } else {
                                callback.doFail(response.getMessage(), response.getCode());
                            }
                        }, throwable -> {
                            callback.doError(throwable, this);
                        }
                ));
    }
    public void nukeProduct(){
        compositeDisposable.add(repository.getSqliteService().nukeProducts()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread()).subscribe());
    }
}
