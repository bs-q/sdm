package q.sdm.ui.main.cart;

import android.content.Intent;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.List;

import q.sdm.BR;
import q.sdm.R;
import q.sdm.data.model.api.response.account.ProfileResponse;
import q.sdm.data.model.db.ProductEntity;
import q.sdm.databinding.FragmentCartBinding;
import q.sdm.di.component.FragmentComponent;
import q.sdm.ui.base.fragment.BaseFragment;
import q.sdm.ui.main.cart.adapter.CartAdapter;
import q.sdm.ui.main.cart.edit.CartSheet;

public class CartFragment extends BaseFragment<FragmentCartBinding, CartViewModel> implements CartAdapter.CartAdapterCallback {

    CartAdapter cartAdapter;

    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_cart;
    }

    @Override
    protected void performDataBinding() {
        ProfileResponse profileResponse = new ProfileResponse();
        profileResponse.setCustomerFullName("QUAN");
        profileResponse.setCustomerAddress("ABC duong D xa E");
        binding.setA(this);
        binding.setVm(viewModel);
        binding.setProfile(profileResponse);
        binding.executePendingBindings();
        setupCartAdapter();
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
        binding.fcRv.setLayoutManager(new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false));
        binding.fcRv.setAdapter(cartAdapter);
    }

    @Override
    protected void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fc_payment) {
            navigateToSelectPayment();
        } else if (v.getId() == R.id.fc_checkout) {
            checkout();
        }
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
//        binding.getRoot().postDelayed(()->{
//            lock = false;
//        },650);
    }
    private void openSheet(){
        CartSheet cartSheet = new CartSheet();
        cartSheet.show(getChildFragmentManager(),"CART_SHEET");
    }

    @Override
    public void deleteProduct(ProductEntity productEntity) {
        viewModel.deleteProduct(productEntity);
    }
}
