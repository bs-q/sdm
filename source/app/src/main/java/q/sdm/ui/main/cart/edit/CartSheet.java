package q.sdm.ui.main.cart.edit;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.Observable;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import q.sdm.BR;
import q.sdm.R;
import q.sdm.data.model.api.response.product.ProductResponse;
import q.sdm.databinding.ActivityEditCartBinding;
import q.sdm.di.component.SheetComponent;
import q.sdm.ui.base.activity.BaseDbCallback;
import q.sdm.ui.base.activity.BaseRequestCallback;
import q.sdm.ui.base.fragment.BaseSheet;

public class CartSheet extends BaseSheet<ActivityEditCartBinding,CartSheetViewModel> {

    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit_cart;
    }

    @Override
    protected void performDataBinding() {
        binding.setA(this);
        binding.setVm(viewModel);
        binding.root.setClipToOutline(true);
        binding.root.setClipChildren(true);

        binding.aecSv.setClipToOutline(true);
        binding.aecSv.setClipChildren(true);
        binding.executePendingBindings();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getProductDetail();
        setupProduct();
    }

    private void setupProduct(){
        viewModel.quantity.set(myApplication().getEditProduct().amount);
        viewModel.quantityString.set(String.valueOf(viewModel.quantity.get()));
        viewModel.quantityString.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                Integer amount = Integer.valueOf(viewModel.quantityString.get());
                if (amount>myApplication().getEditProduct().quantityInStock) {
                    viewModel.quantity.set(myApplication().getEditProduct().quantityInStock);
                    viewModel.quantityString.set(String.valueOf(myApplication().getEditProduct().quantityInStock));
                    binding.aecQuantity.clearFocus();
                } else {
                    viewModel.quantity.set(amount);
                }
            }
        });
        binding.aecQuantity.setOnKeyListener(new View.OnKeyListener() {
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
                binding.setProduct(response);
                binding.shimmer.setVisibility(View.INVISIBLE);
                binding.shimmer.stopShimmer();
                binding.root.setClipToOutline(true);
                binding.root.setClipChildren(true);

                binding.aecSv.setClipToOutline(true);
                binding.aecSv.setClipChildren(true);
                binding.executePendingBindings();
            }
        });
    }
    @Override
    public void onClick(View v) {
        if (v.getId() == binding.plus.getId()){
            int quantity = Integer.parseInt(viewModel.quantityString.get());
            quantity += 1;
            viewModel.quantityString.set(String.valueOf(quantity));
        } else if (v.getId() == binding.minus.getId()){
            if (viewModel.quantity.get() == 1) return;
            int quantity = Integer.parseInt(viewModel.quantityString.get());
            quantity -= 1;
            viewModel.quantityString.set(String.valueOf(quantity));
        } else if (v.getId() == binding.aecUpdate.getId()){
            myApplication().getEditProduct().setAmount(viewModel.quantity.get());
            viewModel.updateProduct(myApplication().getEditProduct(), new BaseDbCallback<Long>() {
                @Override
                public void doSuccess(Long response) {
                   dismiss();
                }

                @Override
                public void doError(Throwable throwable) {

                }
            });
        } 
    }
    @Override
    protected void performDependencyInjection(SheetComponent buildComponent) {
        buildComponent.inject(this);
    }
}
