package q.sdm.ui.product;

import q.sdm.R;
import q.sdm.BR;
import q.sdm.data.model.db.ProductEntity;
import q.sdm.databinding.ActivityProductDetailBinding;
import q.sdm.di.component.ActivityComponent;
import q.sdm.ui.base.activity.BaseActivity;
import q.sdm.ui.base.activity.BaseDbCallback;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

public class ProductDetailActivity extends BaseActivity<ActivityProductDetailBinding,ProductDetailViewModel>
implements View.OnClickListener {
    @Override
    public int getLayoutId() {
        return R.layout.activity_product_detail;
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
    public void onClick(View v) {
        updateProduct();
    }
    private void updateProduct(){
        viewModel.findProduct(new BaseDbCallback<ProductEntity>() {
            @Override
            public void doSuccess(ProductEntity response) {
                response.amount+=1;
                viewModel.updateProduct(response, new BaseDbCallback<Long>() {
                    @Override
                    public void doSuccess(Long response) {
                        viewModel.showSuccessMessage("Đã thêm vào giỏ hàng");
                        finish();
                    }
                    @Override
                    public void doError(Throwable throwable) {
                    }
                });
            }

            @Override
            public void doError(Throwable throwable) {
                ProductEntity productEntity = new ProductEntity();
                productEntity.setId(myApplication().getProductDetailItem().getId());
                productEntity.setName(myApplication().getProductDetailItem().getProductName());
                productEntity.setAmount(1);
                productEntity.setPrice(myApplication().getProductDetailItem().getProductPrice());
                viewModel.updateProduct(productEntity, new BaseDbCallback<Long>() {
                    @Override
                    public void doSuccess(Long response) {
                        viewModel.showSuccessMessage("Đã thêm vào giỏ hàng");
                        finish();
                    }
                    @Override
                    public void doError(Throwable throwable) {

                    }
                });
            }
        });
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding.setA(this);
        viewBinding.setVm(viewModel);
        viewBinding.setProduct(myApplication().getProductDetailItem());
    }
}
