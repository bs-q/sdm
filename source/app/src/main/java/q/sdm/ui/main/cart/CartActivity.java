package q.sdm.ui.main.cart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.List;

import q.sdm.BR;
import q.sdm.R;
import q.sdm.data.model.api.response.account.ProfileResponse;
import q.sdm.data.model.db.ProductEntity;
import q.sdm.databinding.DialogOrderFailBinding;
import q.sdm.databinding.FragmentCartBinding;
import q.sdm.di.component.ActivityComponent;
import q.sdm.di.component.FragmentComponent;
import q.sdm.ui.base.activity.BaseActivity;
import q.sdm.ui.base.activity.BaseDbCallback;
import q.sdm.ui.base.activity.BaseRequestCallback;
import q.sdm.ui.base.fragment.BaseFragment;
import q.sdm.ui.location.LocationActivity;
import q.sdm.ui.main.MainActivity;
import q.sdm.ui.main.cart.adapter.CartAdapter;
import q.sdm.ui.main.cart.edit.CartSheet;
import q.sdm.ui.main.cart.order.complete.OrderCompleteActivity;
import q.sdm.ui.main.cart.order.fail.OrderFailDialog;
import q.sdm.ui.main.cart.receiver.UpdateReceiverSheet;
import timber.log.Timber;

public class CartActivity extends BaseActivity<FragmentCartBinding, CartViewModel> implements CartAdapter.CartAdapterCallback, View.OnClickListener {

    CartAdapter cartAdapter;

    @Override
    public int getBindingVariable() {
        return BR.vm;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding.setA(this);
        viewBinding.setVm(viewModel);
        viewBinding.setProfile(myApplication().getProfileResponse());
        viewModel.receiverAddress.set(myApplication().getCustomerLocation().get());
        viewModel.receiverName.set(myApplication().getProfileResponse().getCustomerFullName());
        viewModel.receiverPhone.set(myApplication().getProfileResponse().getCustomerPhone());
        viewBinding.executePendingBindings();
        setupCartAdapter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_cart;
    }

    @Override
    public void performDependencyInjection(ActivityComponent buildComponent) {
        buildComponent.inject(this);
    }

    private void setupCartAdapter(){
        cartAdapter = new CartAdapter();
        viewModel.observeProductsInCart();
        viewModel.productEntityLiveData.observe(this, new Observer<List<ProductEntity>>() {
            @Override
            public void onChanged(List<ProductEntity> productEntities) {
                cartAdapter.productEntities = productEntities;
                double total = productEntities.stream().mapToDouble(o -> o.price*((1-o.sale/100))*o.amount).reduce(0, Double::sum);
                viewModel.total.set(total);
                viewModel.reduce.set(total*myApplication().getProfileResponse().getSaleOff()/100);
                viewModel.sale.set(String.valueOf(myApplication().getProfileResponse().getSaleOff()));
                viewModel.vat.set((total+viewModel.reduce.get())*0.1);
                viewModel.totalAndVat.set(total+viewModel.vat.get());
                cartAdapter.notifyDataSetChanged();
            }
        });
        cartAdapter.callback = this;
        viewBinding.fcRv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        viewBinding.fcRv.setAdapter(cartAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.updateCart(new BaseDbCallback<Boolean>() {
            @Override
            public void doSuccess(Boolean response) {
                Timber.d("Update cart");
            }

            @Override
            public void doError(Throwable throwable) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fc_payment) {
            navigateToSelectPayment();
        } else if (v.getId() == R.id.checkout_btn) {
            checkout();
        } else if (v.getId() == viewBinding.fcChangeAddress.getId()){
            navigateToUpdateReceiver();
        }
    }

    private void navigateToUpdateReceiver(){
        UpdateReceiverSheet updateReceiverSheet = new UpdateReceiverSheet(viewModel.receiverName, viewModel.receiverPhone,viewModel.receiverAddress);
        updateReceiverSheet.show(getSupportFragmentManager(),"RECEIVER");
    }

    private void navigateToSelectPayment(){

    }
    private void checkout(){
        if (viewModel.receiverAddress.get().equals("...")) {
            viewModel.showErrorMessage("Vui lòng cập nhật địa chỉ giao hàng để tiếp tục đặt hàng");
            return;
        }
        viewModel.showLoading();
        viewModel.createOrder(new BaseRequestCallback<Boolean>() {
            @Override
            public void doSuccess(Boolean response) {
                viewModel.hideLoading();
                viewModel.nukeProduct();
                Intent it = new Intent(CartActivity.this, OrderCompleteActivity.class);
                startActivity(it);
            }

            @Override
            public void doFail(String message, String code) {
                BaseRequestCallback.super.doFail(message, code);
                viewModel.hideLoading();
                OrderFailDialog dialogOrderFailBinding = new OrderFailDialog(new OrderFailDialog.OrderFailDialogInterface() {
                    @Override
                    public void retry() {
                        checkout();
                    }

                    @Override
                    public void backToHome() {
                        Intent it = new Intent(CartActivity.this, MainActivity.class);
                        it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(it);
                    }
                });
                dialogOrderFailBinding.show(getSupportFragmentManager(),"FAIL");
            }
        });
    }

    boolean lock = false;
    @Override
    public void productCallback(ProductEntity productEntity) {
        myApplication().setEditProduct(productEntity);
        openSheet();
        return;
//        if (lock) return;
//        lock = true;
//        navigateToProductEdit();
//        viewBinding.getRoot().postDelayed(()->{
//            lock = false;
//        },650);
    }
    private void openSheet(){
        CartSheet cartSheet = new CartSheet();
        cartSheet.show(getSupportFragmentManager(),"CART_SHEET");
    }

    @Override
    public void deleteProduct(ProductEntity productEntity) {
        viewModel.deleteProduct(productEntity);
    }
}
