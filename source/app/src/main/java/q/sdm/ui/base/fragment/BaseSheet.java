package q.sdm.ui.base.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.Observable;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ViewDataBinding;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import javax.inject.Inject;
import javax.inject.Named;

import q.sdm.MVVMApplication;
import q.sdm.R;
import q.sdm.di.component.DaggerFragmentComponent;
import q.sdm.di.component.DaggerSheetComponent;
import q.sdm.di.component.FragmentComponent;
import q.sdm.di.component.SheetComponent;
import q.sdm.di.module.SheetModule;
import q.sdm.utils.DialogUtils;
import timber.log.Timber;

public abstract class BaseSheet  <B extends ViewDataBinding,V extends BaseSheetViewModel> extends BottomSheetDialogFragment
        implements View.OnClickListener {
    @Named("device_id")
    @Inject
    protected String deviceId;

    protected B binding;
    @Inject
    protected V viewModel;

    private Dialog progressDialog;


    @Named("access_token")
    @Inject
    protected String token;

    private View view;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override public void onShow(DialogInterface dialogInterface) {
                BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) dialogInterface;
                setupFullHeight(bottomSheetDialog);
            }
        });
        return dialog;

    }
    private void setupFullHeight(BottomSheetDialog bottomSheetDialog) {
        FrameLayout bottomSheet = (FrameLayout) bottomSheetDialog.findViewById(R.id.design_bottom_sheet);
        BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
        ViewGroup.LayoutParams layoutParams = bottomSheet.getLayoutParams();
        bottomSheet.setBackgroundResource(android.R.color.transparent);
        int windowHeight = getWindowHeight();
        if (layoutParams != null) {
            layoutParams.height = (int) (windowHeight*0.95);
        }
        bottomSheet.setLayoutParams(layoutParams);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    private int getWindowHeight() {
        // Calculate window height for fullscreen use
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null){
            performDependencyInjection(getBuildComponent());
            binding = DataBindingUtil.inflate(inflater,getLayoutId(),container,false);
            binding.setVariable(getBindingVariable(),viewModel);
            performDataBinding();
            viewModel.setToken(token);
            viewModel.mErrorMessage.observe(getViewLifecycleOwner(),toastMessage -> {
                if (toastMessage!=null){
                    toastMessage.showMessage(requireContext());
                }
            });
            viewModel.mIsLoading.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback(){

                @Override
                public void onPropertyChanged(Observable sender, int propertyId) {
                    if(((ObservableBoolean)sender).get()){
                        Timber.d("<*>Show progress");
                        showProgressbar(getResources().getString(R.string.msg_loading));
                    }else{
                        Timber.d("<*>Hide progress");
                        hideProgress();
                    }
                }
            });
            view = binding.getRoot();
        }
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public abstract int getBindingVariable();

    protected abstract int getLayoutId();

    protected abstract void performDataBinding();

    protected abstract void performDependencyInjection(SheetComponent buildComponent);

    private SheetComponent getBuildComponent(){

        return DaggerSheetComponent.builder()
                .appComponent(((MVVMApplication) requireActivity().getApplication()).getAppComponent())
                .sheetModule(new SheetModule(this))
                .build();
    }

    public void showProgressbar(String msg){
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
        progressDialog = DialogUtils.createDialogLoading(requireContext(), msg);
        progressDialog.show();
    }

    public void hideProgress() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    public MVVMApplication myApplication(){
        return (MVVMApplication) requireActivity().getApplication();
    }
}

