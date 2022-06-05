package q.sdm.ui.product;

import q.sdm.R;
import q.sdm.BR;
import q.sdm.data.model.api.response.product.ProductResponse;
import q.sdm.data.model.db.ProductEntity;
import q.sdm.databinding.ActivityProductDetailBinding;
import q.sdm.di.component.ActivityComponent;
import q.sdm.ui.base.activity.BaseActivity;
import q.sdm.ui.base.activity.BaseDbCallback;
import q.sdm.ui.base.activity.BaseRequestCallback;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;

import org.w3c.dom.Text;

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
                productEntity.setThumbnail(myApplication().getProductDetailItem().getProductImage());
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
        getProductDetail();
    }

    private void getProductDetail(){
        viewModel.getProductDetail(myApplication().getProductDetailItem().getId(), new BaseRequestCallback<ProductResponse>() {
            @Override
            public void doSuccess(ProductResponse response) {
                viewBinding.setProduct(response);
                viewBinding.shimmer.setVisibility(View.INVISIBLE);
                viewBinding.shimmer.stopShimmer();
                viewBinding.executePendingBindings();
            }
        });
    }

    @BindingAdapter("html_text")
    public static void setHtmlText(WebView view, String source) {
        if (source == null) return;;
        view.loadDataWithBaseURL(null,source, "text/html; charset=utf-8", "UTF-8", null);
    }
}
