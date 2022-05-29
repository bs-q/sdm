package q.sdm.ui.main.cart.edit;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.Observable;

import q.sdm.BR;
import q.sdm.R;
import q.sdm.data.model.api.response.product.ProductResponse;
import q.sdm.databinding.ActivityEditCartBinding;
import q.sdm.di.component.ActivityComponent;
import q.sdm.ui.base.activity.BaseActivity;
import q.sdm.ui.base.activity.BaseDbCallback;
import q.sdm.ui.base.activity.BaseRequestCallback;

public class CartEditActivity extends BaseActivity<ActivityEditCartBinding,CartEditViewModel>
implements View.OnClickListener {
    @Override
    public int getLayoutId() {
        return R.layout.activity_edit_cart;
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
        viewBinding.root.setClipToOutline(true);
        viewBinding.root.setClipChildren(true);

        viewBinding.aecSv.setClipToOutline(true);
        viewBinding.aecSv.setClipChildren(true);
        getProductDetail();
        setupProduct();
    }
    private void setupProduct(){
        viewModel.quantity.set(myApplication().getEditProduct().amount);
        viewModel.quantityString.set(String.valueOf(viewModel.quantity.get()));
        viewModel.quantityString.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                viewModel.quantity.set(Integer.valueOf(viewModel.quantityString.get()));
            }
        });
        viewBinding.aecQuantity.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //You can identify which key pressed buy checking keyCode value with KeyEvent.KEYCODE_
                if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (viewModel.quantityString.get().length()==1) return true;
                }
                return false;
            }
        });
    }
    private void getProductDetail(){
        viewModel.getProductDetail(myApplication().getEditProduct().getId(), new BaseRequestCallback<ProductResponse>() {
            @Override
            public void doSuccess(ProductResponse response) {
                viewBinding.setProduct(response);
                viewBinding.shimmer.setVisibility(View.INVISIBLE);
                viewBinding.shimmer.stopShimmer();
                viewBinding.root.setClipToOutline(true);
                viewBinding.root.setClipChildren(true);

                viewBinding.aecSv.setClipToOutline(true);
                viewBinding.aecSv.setClipChildren(true);
                viewBinding.executePendingBindings();
            }
        });
    }
    @Override
    public void onClick(View v) {
        if (v.getId() == viewBinding.plus.getId()){
            int quantity = Integer.parseInt(viewModel.quantityString.get());
            quantity += 1;
            viewModel.quantityString.set(String.valueOf(quantity));
        } else if (v.getId() == viewBinding.minus.getId()){
            if (viewModel.quantity.get() == 1) return;
            int quantity = Integer.parseInt(viewModel.quantityString.get());
            quantity -= 1;
            viewModel.quantityString.set(String.valueOf(quantity));
        } else if (v.getId() == viewBinding.aecUpdate.getId()){
            myApplication().getEditProduct().setAmount(viewModel.quantity.get());
            viewModel.updateProduct(myApplication().getEditProduct(), new BaseDbCallback<Long>() {
                @Override
                public void doSuccess(Long response) {
                    finish();
                    overridePendingTransition(R.anim.nothing, R.anim.bottom_down);
                }

                @Override
                public void doError(Throwable throwable) {

                }
            });
        } else if (v.getId() == viewBinding.close.getId()){
            finish();
            overridePendingTransition(R.anim.nothing, R.anim.bottom_down);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.nothing, R.anim.bottom_down);
    }
}
