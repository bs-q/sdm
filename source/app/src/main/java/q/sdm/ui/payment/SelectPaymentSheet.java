package q.sdm.ui.payment;

import android.view.View;

import androidx.databinding.ObservableInt;

import q.sdm.BR;
import q.sdm.R;
import q.sdm.databinding.SheetSelectPaymentBinding;
import q.sdm.di.component.SheetComponent;
import q.sdm.ui.base.fragment.BaseSheet;

public class SelectPaymentSheet extends BaseSheet<SheetSelectPaymentBinding,SelectPaymentViewModel> {

    public ObservableInt selectedPayment;

    public SelectPaymentSheet(ObservableInt selectedPayment){
        this.selectedPayment = selectedPayment;
    }

    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.sheet_select_payment;
    }

    @Override
    protected void performDataBinding() {
        binding.setA(this);
        binding.setVm(viewModel);
        if (selectedPayment.get() == 1) {
            viewModel.type.set(true);
        } else {
            viewModel.type.set(false);
        }
    }

    @Override
    protected void performDependencyInjection(SheetComponent buildComponent) {
        buildComponent.inject(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == binding.cash.getId()){
            viewModel.type.set(true);
        } else if (v.getId() == binding.card.getId()){
            viewModel.type.set(false);
        } else if (v.getId() == binding.updateBtn.getId()){
            selectedPayment.set(viewModel.type.get() ? 1 : 2);
            dismiss();
        }
    }
}
