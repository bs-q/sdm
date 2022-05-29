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
import q.sdm.databinding.FragmentHomeBinding;
import q.sdm.di.component.FragmentComponent;
import q.sdm.ui.base.fragment.BaseFragment;
import q.sdm.ui.main.cart.adapter.CartAdapter;
import q.sdm.ui.main.cart.edit.CartEditActivity;

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
        if (lock) return;
        lock = true;
        myApplication().setEditProduct(productEntity);
        navigateToProductEdit();
        binding.getRoot().postDelayed(()->{
            lock = false;
        },650);
    }

    private void navigateToProductEdit(){
        Intent it = new Intent(requireContext(), CartEditActivity.class);
        startActivity(it);
        requireActivity().overridePendingTransition(R.anim.bottom_up, R.anim.nothing);

    }

    @Override
    public void deleteProduct(ProductEntity productEntity) {
        viewModel.deleteProduct(productEntity);
    }
}
