package q.sdm.ui.search.result;

import q.sdm.R;
import q.sdm.BR;
import q.sdm.data.model.api.response.product.ProductResponse;
import q.sdm.databinding.ActivitySearchResultBinding;
import q.sdm.di.component.ActivityComponent;
import q.sdm.ui.base.activity.BaseActivity;
import q.sdm.ui.product.ProductDetailActivity;
import q.sdm.ui.search.adapter.ProductAdapter;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;

public class SearchResultActivity extends BaseActivity<ActivitySearchResultBinding,SearchResultViewModel> implements ProductAdapter.ProductAdapterClick {

    private ProductAdapter productAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_search_result;
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
        setupAdapter();
    }

    private void setupAdapter(){
        productAdapter = new ProductAdapter(this);
        productAdapter.updateProducts(myApplication().getSearchResults());
        viewBinding.asrRv.setLayoutManager(new GridLayoutManager(this,2));
        viewBinding.asrRv.setAdapter(productAdapter);
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
}
