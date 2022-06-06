package q.sdm.ui.category.product;

import q.sdm.R;
import q.sdm.BR;
import q.sdm.data.model.api.response.product.ProductResponse;
import q.sdm.databinding.ActivityProductByCategoryBinding;
import q.sdm.di.component.ActivityComponent;
import q.sdm.ui.base.activity.BaseActivity;
import q.sdm.ui.base.activity.BaseRequestCallback;
import q.sdm.ui.product.ProductDetailActivity;
import q.sdm.ui.search.adapter.ProductAdapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;

import java.util.List;

public class CategoryProductActivity extends BaseActivity<ActivityProductByCategoryBinding,CategoryProductViewModel>
        implements View.OnClickListener, ProductAdapter.ProductAdapterClick {

    private ProductAdapter productAdapter;


    @Override
    public int getLayoutId() {
        return R.layout.activity_product_by_category;
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
        viewBinding.setCategory(myApplication().getSelectedCategory().getCategoryName());
        setupAdapter();
    }
    private void setupAdapter(){
        productAdapter = new ProductAdapter(this);
        viewBinding.rv.setLayoutManager(new GridLayoutManager(this,2));
        viewBinding.rv.setAdapter(productAdapter);
        getProductsByCategory();
    }
    private void getProductsByCategory(){
        viewModel.showLoading();
        viewModel.getProductsByCategory(myApplication().getSelectedCategory().getId(), new BaseRequestCallback<List<ProductResponse>>() {
            @Override
            public void doSuccess(List<ProductResponse> response) {
                if (response != null) {
                    productAdapter.updateProducts(response);
                }
                viewModel.hideLoading();
            }
        });
    }

    @Override
    protected void onDestroy() {
        myApplication().setSelectedCategory(null);
        super.onDestroy();
    }

    @Override
    public void productItemClick(ProductResponse productResponse) {
        myApplication().setProductDetailItem(productResponse);
        navigateToProductDetail();
    }

    private void navigateToProductDetail(){
        Intent it = new Intent(this, ProductDetailActivity.class);
        startActivity(it);
    }
    @Override
    public void onClick(View v) {

    }
}
