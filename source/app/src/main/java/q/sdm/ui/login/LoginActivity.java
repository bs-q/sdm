package q.sdm.ui.login;

import static q.sdm.constant.Constants.VALID_EMAIL_ADDRESS_REGEX;

import q.sdm.R;
import q.sdm.BR;
import q.sdm.data.model.api.response.account.ProfileResponse;
import q.sdm.databinding.ActivityLoginBinding;
import q.sdm.di.component.ActivityComponent;
import q.sdm.ui.base.activity.BaseActivity;
import q.sdm.ui.base.activity.BaseCallback;
import q.sdm.ui.base.activity.BaseRequestCallback;
import q.sdm.ui.base.activity.BaseViewModel;
import q.sdm.ui.base.activity.MessageWrapper;
import q.sdm.ui.main.MainActivity;
import q.sdm.ui.recovery.RecoveryActivity;
import q.sdm.ui.register.RegisterActivity;
import q.sdm.utils.ErrorUtils;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.databinding.BindingAdapter;
import androidx.databinding.Observable;

import java.util.Objects;

public class LoginActivity extends BaseActivity<ActivityLoginBinding,LoginViewModel>
implements View.OnClickListener {
    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
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
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        viewBinding.cl.setLayoutParams(new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height));
        listenFormFocus();
        profile();
    }
    private void profile(){
        viewModel.profile(new BaseRequestCallback<ProfileResponse>() {
            @Override
            public void doSuccess(ProfileResponse profileResponse) {
                navigateToMainScreen();
            }

            @Override
            public void doError(Throwable throwable, BaseViewModel viewModel) {
                BaseRequestCallback.super.doError(throwable,viewModel);
                setupForm();
            }
        });

    }

    private void setupForm(){
        viewBinding.email.input.requestFocus();
    }
    private void listenFormFocus(){
        viewBinding.email.input.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus){
                viewModel.emailState.set(true);
                viewModel.passwordState.set(false);
            }

        });
        viewModel.email.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                viewModel.valid.set(viewModel.email.get().trim().length() == 10);
            }
        });
        viewBinding.password.input.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus){
                viewModel.passwordState.set(true);
                viewModel.emailState.set(false);
            }
        });
        viewModel.toggle.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                viewBinding.password.input.postDelayed(()->{
                    viewBinding.password.input.setSelection(viewModel.password.get().length());
                    viewBinding.password.input.setTypeface(null, Typeface.BOLD);
                },100);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.toggle){
            viewModel.toggle.set(!viewModel.toggle.get());
        } else if (v.getId() == R.id.register_btn){
            navigateToRegister();
        } else if (v.getId() == R.id.forgot_pwd){
            navigateToForgetPassword();
        } else if (v.getId() == R.id.login_btn){
            doLogin();
        }
    }
    private void doLogin(){
        viewModel.login(new BaseRequestCallback<ProfileResponse>() {
            @Override
            public void doSuccess(ProfileResponse profileResponse) {
                viewModel.hideLoading();
                navigateToMainScreen();
            }

            @Override
            public void doFail(String message, String code) {
                viewModel.hideLoading();
                viewModel.showErrorMessage(ErrorUtils.getMessageFromError(code));
            }
        });
    }
    private void navigateToRegister(){
        Intent it = new Intent(this, RegisterActivity.class);
        startActivity(it);
    }
    private void navigateToForgetPassword(){
        Intent it = new Intent(this, RecoveryActivity.class);
        startActivity(it);
    }
    private void navigateToMainScreen(){
        Intent it = new Intent(this, MainActivity.class);
        startActivity(it);
        finish();
    }

    @BindingAdapter("layout_height")
    public static void setLayoutHeight(View view, int height) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = height;
        view.setLayoutParams(layoutParams);
    }
}
