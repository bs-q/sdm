package q.sdm.ui.main.cart.receiver;

import android.content.Intent;
import android.view.View;

import androidx.databinding.ObservableField;

import kotlin.text.Regex;
import q.sdm.BR;
import q.sdm.R;
import q.sdm.databinding.SheetUpdateReceiverBinding;
import q.sdm.di.component.SheetComponent;
import q.sdm.ui.base.fragment.BaseSheet;
import q.sdm.ui.location.LocationActivity;

public class UpdateReceiverSheet extends BaseSheet<SheetUpdateReceiverBinding,UpdateReceiverViewModel> {
    public ObservableField<String> receiverName;
    public ObservableField<String> receiverPhone;
    public ObservableField<String> receiverAddress;
    public UpdateReceiverSheet(ObservableField<String> name,ObservableField<String> phone,ObservableField<String> address) {
        this.receiverName = name;
        this.receiverPhone = phone;
        this.receiverAddress = address;
    }

    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.sheet_update_receiver;
    }

    @Override
    protected void performDataBinding() {
        binding.setA(this);
        binding.setVm(viewModel);
        viewModel.receiverAddress.set(receiverAddress.get());
        viewModel.receiverPhone.set(receiverPhone.get());
        viewModel.receiverName.set(receiverName.get());
    }

    @Override
    protected void performDependencyInjection(SheetComponent buildComponent) {
        buildComponent.inject(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.receiverAddress.set(myApplication().getCustomerLocation().get());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() ==  binding.addressLayer.getId()) {
            navigateToAddressList();
        } else if (v.getId() == binding.updateBtn.getId()) {
            if (checkForm()) {
                receiverName.set(viewModel.receiverName.get());
                receiverAddress.set(viewModel.receiverAddress.get());
                receiverPhone.set(viewModel.receiverPhone.get());
                dismiss();
            }

        }
    }
    private boolean checkForm(){
        if (viewModel.receiverName.get().isEmpty()) {
            viewModel.showErrorMessage("Vui lòng nhập tên người nhận");
            return false;
        }
        if (viewModel.receiverPhone.get().isEmpty()) {
            viewModel.showErrorMessage("Vui lòng nhập số điện thoại người nhận");
            return false;
        }
        Regex regex = new  Regex("^[0-9]{10}$");
        if ( !regex.matches(viewModel.receiverPhone.get())) {
            viewModel.showErrorMessage("Số điện thoại không hợp lệ. Số điện thoại phải bao gồm 10 chữ số");
            return false;
        }
        return true;
    }
    private void navigateToAddressList(){
        Intent it = new Intent(requireContext(), LocationActivity.class);
        startActivity(it);
    }
}
