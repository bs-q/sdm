package q.sdm.ui.login;

import static q.sdm.constant.Constants.VALID_EMAIL_ADDRESS_REGEX;

import q.sdm.R;
import q.sdm.BR;
import q.sdm.databinding.ActivityLoginBinding;
import q.sdm.di.component.ActivityComponent;
import q.sdm.ui.base.activity.BaseActivity;
import q.sdm.ui.base.activity.BaseCallback;
import q.sdm.ui.recovery.RecoveryActivity;
import q.sdm.ui.register.RegisterActivity;

import android.content.Intent;
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
        setupForm();
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
                viewModel.valid.set(VALID_EMAIL_ADDRESS_REGEX.matcher(
                        viewModel.email.get()
                ).find());
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
        viewModel.login(new BaseCallback() {
            @Override
            public void doSuccess() {
                finish();
            }
        });
    }
    private void navigateToRegister(){
        Intent it = new Intent(this, RegisterActivity.class);
        startActivity(it);
        finish();
    }
    private void navigateToForgetPassword(){
        Intent it = new Intent(this, RecoveryActivity.class);
        startActivity(it);
    }

    @BindingAdapter("layout_height")
    public static void setLayoutHeight(View view, int height) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = height;
        view.setLayoutParams(layoutParams);
    }
}
