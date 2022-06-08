package q.sdm.ui.main.cart.added.edit;

import android.view.View;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.List;
import java.util.Timer;

import q.sdm.BR;
import q.sdm.R;
import q.sdm.data.model.db.ProductEntity;
import q.sdm.databinding.SheetEditProductAddedBinding;
import q.sdm.di.component.SheetComponent;
import q.sdm.ui.base.activity.BaseDbCallback;
import q.sdm.ui.base.fragment.BaseSheet;
import q.sdm.ui.main.cart.added.ProductAddedActivity;
import q.sdm.ui.main.cart.added.edit.adapter.ProductAddedEditAdapter;
import timber.log.Timber;

public class ProductAddedEditSheet extends BaseSheet<SheetEditProductAddedBinding,ProductAddedEditViewModel> implements ProductAddedEditAdapter.Refresh {

    private ProductAddedEditAdapter productAddedAdapter;


    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.sheet_edit_product_added;
    }

    @Override
    protected void performDataBinding() {
        binding.setA(this);
        binding.setVm(viewModel);
        binding.setTotal(viewModel.total);
        binding.setAmount(viewModel.amount);
        setupCartAdapter();
    }
    private void setupCartAdapter(){
        productAddedAdapter = new ProductAddedEditAdapter();
        productAddedAdapter.callback = this;
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
        binding.rv.setLayoutManager(new LinearLayoutManager(requireActivity(),LinearLayoutManager.VERTICAL,false));
        binding.rv.setAdapter(productAddedAdapter);
    }

    @Override
    protected void performDependencyInjection(SheetComponent buildComponent) {
        buildComponent.inject(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == binding.close.getId()){
            dismiss();
        } else if (v.getId() == binding.checkout.getId()) {
            updateProduct();
        }
    }
    private void updateProduct(){
        viewModel.showLoading();
        viewModel.updateAllProduct(productAddedAdapter.productEntities, new BaseDbCallback<Boolean>() {
            @Override
            public void doSuccess(Boolean response) {
                viewModel.hideLoading();
                dismiss();
                ((ProductAddedActivity)requireActivity()).navigateToCheckout();
            }

            @Override
            public void doError(Throwable throwable) {
                Timber.d(throwable);
            }
        });
    }

    @Override
    public void refreshList() {
        double total = productAddedAdapter.productEntities.stream().mapToDouble(o -> o.price*(1-(double)(o.sale/100))*o.amount).reduce(0, Double::sum);
        viewModel.total.set(total);

    }
}
