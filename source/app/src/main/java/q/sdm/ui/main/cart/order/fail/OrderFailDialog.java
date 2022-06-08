package q.sdm.ui.main.cart.order.fail;

import static android.view.Gravity.CENTER;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import q.sdm.R;
import q.sdm.databinding.DialogChangeAvatarBinding;
import q.sdm.databinding.DialogOrderFailBinding;

public class OrderFailDialog extends DialogFragment{

    public OrderFailDialog(OrderFailDialogInterface dialogInterface) {
        this.dialogInterface = dialogInterface;
    }

    public interface OrderFailDialogInterface{
        void retry();
        void backToHome();
    }
    private final OrderFailDialogInterface dialogInterface;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        DialogOrderFailBinding orderFailBinding = DataBindingUtil.
                inflate(LayoutInflater.from(requireContext()), R.layout.dialog_order_fail,null,false);
        builder.setView(orderFailBinding.getRoot());
        orderFailBinding.backToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogInterface.backToHome();
            }
        });
        orderFailBinding.retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogInterface.retry();
            }
        });
        Dialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().setGravity(CENTER);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        return dialog;
    }

}
