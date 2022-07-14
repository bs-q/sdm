package q.sdm.ui.main.cart.added;

import q.sdm.R;
import q.sdm.BR;
import q.sdm.data.model.db.ProductEntity;
import q.sdm.databinding.ActivityProductAddedBinding;
import q.sdm.di.component.ActivityComponent;
import q.sdm.ui.base.activity.BaseActivity;
import q.sdm.ui.main.cart.CartActivity;
import q.sdm.ui.main.cart.adapter.CartAdapter;
import q.sdm.ui.main.cart.added.adapter.ProductAddedAdapter;
import q.sdm.ui.main.cart.added.edit.ProductAddedEditSheet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.List;

public class ProductAddedActivity extends BaseActivity<ActivityProductAddedBinding,ProductAddedViewModel>
        implements View.OnClickListener, ProductAddedAdapter.ProductAddedAdapterCallback {

    private ProductAddedAdapter productAddedAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_product_added;
    }

    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    public void performDependencyInjection(ActivityComponent buildComponent) {
        buildComponent.inject(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding.setA(this);
        viewBinding.setVm(viewModel);
        viewBinding.setTotal(viewModel.total);
        viewBinding.setAmount(viewModel.amount);
        setupCartAdapter();
    }
    private void setupCartAdapter(){
        productAddedAdapter = new ProductAddedAdapter(this);
        viewModel.observeProductsInCart();
        viewModel.productEntityLiveData.observe(this, new Observer<List<ProductEntity>>() {
            @Override
            public void onChanged(List<ProductEntity> productEntities) {
                productAddedAdapter.productEntities = productEntities;
                double total = productEntities.stream().mapToDouble(o -> o.price*(1-(double)(o.sale/100))*o.amount).reduce(0, Double::sum);
                viewModel.total.set(total);
                viewModel.amount.set(productEntities.size());
                productAddedAdapter.notifyDataSetChanged();
            }
        });
        viewBinding.rv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        viewBinding.rv.setAdapter(productAddedAdapter);
    }

    @Override
    public void onClick(View v) {
        if (viewModel.productEntityLiveData.getValue().isEmpty()){
            viewModel.showErrorMessage("Bạn không có sản phẩm nào trong giỏ hàng");
            return;
        }
        if (v.getId() == viewBinding.cartLayer.getId()) {
            navigateToEditAdded();
        } else if (v.getId() == viewBinding.checkout.getId()){
            navigateToCheckout();
        }
    }
    private void navigateToEditAdded(){
        ProductAddedEditSheet productAddedEditSheet = new ProductAddedEditSheet();
        productAddedEditSheet.show(getSupportFragmentManager(),"EDIT_ADDED_PRODUCT");
    }
    public void navigateToCheckout(){
        Intent it = new Intent(this, CartActivity.class);
        startActivity(it);
        finish();
    }

    @Override
    public void delete(ProductEntity productEntity) {
        viewModel.deleteProduct(productEntity);
    }
}
