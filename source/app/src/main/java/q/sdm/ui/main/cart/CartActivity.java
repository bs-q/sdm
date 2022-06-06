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
import q.sdm.databinding.FragmentCartBinding;
import q.sdm.di.component.ActivityComponent;
import q.sdm.di.component.FragmentComponent;
import q.sdm.ui.base.activity.BaseActivity;
import q.sdm.ui.base.fragment.BaseFragment;
import q.sdm.ui.location.LocationActivity;
import q.sdm.ui.main.cart.adapter.CartAdapter;
import q.sdm.ui.main.cart.edit.CartSheet;

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
                double total = productEntities.stream().mapToDouble(o -> o.price*o.amount).reduce(0, Double::sum);
                viewModel.total.set(total);
                viewModel.totalAndVat.set(total);
                cartAdapter.notifyDataSetChanged();
            }
        });
        cartAdapter.callback = this;
        viewBinding.fcRv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        viewBinding.fcRv.setAdapter(cartAdapter);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fc_payment) {
            navigateToSelectPayment();
        } else if (v.getId() == R.id.fc_checkout) {
            checkout();
        } else if (v.getId() == viewBinding.fcChangeAddress.getId()){
            navigateToAddressList();
        }
    }
    private void navigateToAddressList(){
        Intent it = new Intent(this, LocationActivity.class);
        startActivity(it);
    }

    private void navigateToSelectPayment(){

    }
    private void checkout(){

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
