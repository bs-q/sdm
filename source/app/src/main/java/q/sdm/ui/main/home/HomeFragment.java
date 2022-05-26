package q.sdm.ui.main.home;

import android.content.Intent;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;

import java.util.List;

import q.sdm.BR;
import q.sdm.R;
import q.sdm.data.model.api.response.category.CategoryResponse;
import q.sdm.data.model.api.response.product.ProductResponse;
import q.sdm.data.model.db.ProductEntity;
import q.sdm.databinding.FragmentHomeBinding;
import q.sdm.di.component.FragmentComponent;
import q.sdm.ui.base.fragment.BaseFragment;
import q.sdm.ui.main.home.adapter.CategoryAdapter;
import q.sdm.ui.main.home.adapter.HomeAdapter;
import q.sdm.ui.product.ProductDetailActivity;

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
        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(),2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == 0){
                    return 2;
                }
                return 1;
            }
        });

        binding.rv.setLayoutManager(gridLayoutManager);
        binding.rv.setAdapter(homeAdapter);
        observeCart();
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
