package q.sdm.ui.main.account.detail.dialog;

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

public class CaptureImageDialog extends DialogFragment implements View.OnClickListener {

    public CaptureImageDialog(CaptureImageDialogInterface dialogInterface) {
        this.dialogInterface = dialogInterface;
    }

    public interface CaptureImageDialogInterface{
        void captureImage();
        void loadImage();
    }
    private final CaptureImageDialogInterface dialogInterface;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        DialogChangeAvatarBinding dialogChangeAvatarBinding = DataBindingUtil.
                inflate(LayoutInflater.from(requireContext()), R.layout.dialog_change_avatar,null,false);
        dialogChangeAvatarBinding.setDialog(this);
        builder.setView(dialogChangeAvatarBinding.getRoot());
        Dialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().setGravity(CENTER);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        return dialog;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.album:
                dialogInterface.loadImage();
                dismiss();
                break;
            case R.id.camera:
                dialogInterface.captureImage();
                dismiss();
                break;
            default:
                break;
        }
    }
}
