package q.sdm.ui.main.home;

import android.content.Intent;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.List;

import q.sdm.BR;
import q.sdm.R;
import q.sdm.data.model.api.response.category.CategoryResponse;
import q.sdm.data.model.api.response.product.ProductResponse;
import q.sdm.data.model.db.ProductEntity;
import q.sdm.databinding.FragmentHomeBinding;
import q.sdm.di.component.FragmentComponent;
import q.sdm.ui.base.activity.BaseRequestCallback;
import q.sdm.ui.base.fragment.BaseFragment;
import q.sdm.ui.main.home.adapter.CategoryAdapter;
import q.sdm.ui.main.home.adapter.HomeAdapter;
import q.sdm.ui.product.ProductDetailActivity;
import q.sdm.ui.search.SearchActivity;

public class HomeFragment extends BaseFragment<FragmentHomeBinding,HomeViewModel> implements CategoryAdapter.CategoryAdapterClick, HomeAdapter.HomeAdapterClick {

    private HomeAdapter homeAdapter;

    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void performDataBinding() {
        binding.setA(this);
        binding.setVm(viewModel);
        homeAdapter = new HomeAdapter(this,this);
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(),2);
//        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//            @Override
//            public int getSpanSize(int position) {
//                if (position == 0){
//                    return 2;
//                }
//                return 1;
//            }
//        });
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);

        binding.rv.setLayoutManager(staggeredGridLayoutManager);
        binding.rv.setAdapter(homeAdapter);

        observeCart();
        getCategory();
        getProductsFirstTime();
    }
    private void getCategory(){
        viewModel.getCategories(new BaseRequestCallback<List<CategoryResponse>>() {
            @Override
            public void doSuccess(List<CategoryResponse> response) {
                homeAdapter.updateCategory(response);
            }
        });
    }
    private void getProductsFirstTime(){
        viewModel.getProducts(10, 0, new BaseRequestCallback<List<ProductResponse>>() {
            @Override
            public void doSuccess(List<ProductResponse> response) {
                homeAdapter.updateProducts(response);
            }
        });
    }
    private void observeCart(){
        viewModel.observeProductsInCart();
        viewModel.productEntityLiveData.observe(this, new Observer<List<ProductEntity>>() {
            @Override
            public void onChanged(List<ProductEntity> productEntities) {
                myApplication().getTotalItemInCart().set(productEntities.size());
            }
        });
    }

    @Override
    protected void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == binding.searchScan.search.getId()){
            navigateToSearchActivity();
        }
    }

    private void navigateToSearchActivity(){
        Intent it = new Intent(requireContext(), SearchActivity.class);
        startActivity(it);
    }

    @Override
    public void categoryItemClick(CategoryResponse categoryResponse) {

    }

    @Override
    public void productItemClick(ProductResponse productResponse) {
        navigateToProductDetail(productResponse);
    }

    private void navigateToProductDetail(ProductResponse productResponse) {
        Intent it = new Intent(requireContext(), ProductDetailActivity.class);
        myApplication().setProductDetailItem(productResponse);
        startActivity(it);
    }
}
