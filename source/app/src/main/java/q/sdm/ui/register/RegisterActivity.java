package q.sdm.ui.register;

import static q.sdm.constant.Constants.VALID_EMAIL_ADDRESS_REGEX;

import q.sdm.R;
import q.sdm.BR;
import q.sdm.databinding.ActivityRegisterBinding;
import q.sdm.di.component.ActivityComponent;
import q.sdm.ui.base.activity.BaseActivity;
import q.sdm.ui.base.activity.BaseCallback;
import q.sdm.ui.login.LoginActivity;
import q.sdm.ui.register.verify.VerifyActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.databinding.Observable;

public class RegisterActivity extends BaseActivity<ActivityRegisterBinding,RegisterViewModel>
implements View.OnClickListener {
    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
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
        viewBinding.name.input.requestFocus();
    }
    private void listenFormFocus(){
        viewBinding.email.input.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus){
                viewModel.emailState.set(true);
                viewModel.passwordState.set(false);
                viewModel.nameState.set(false);
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
                viewModel.nameState.set(false);
            }
        });
        viewBinding.name.input.setOnFocusChangeListener((v,hasFocus)->{
            if (hasFocus){
                viewModel.passwordState.set(false);
                viewModel.emailState.set(false);
                viewModel.nameState.set(true);
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
            if (viewModel.password.get().trim().length()<8){
                viewModel.showErrorMessage("Mật khẩu phải có ít nhất 8 kí tự");
                return;
            }
            viewModel.register(new BaseCallback() {
                @Override
                public void doSuccess() {
                    navigateToLogin();
//                    navigateToVerify();
                }
            });
        } else if (v.getId() == R.id.login_btn){
            navigateToLogin();
        }
    }
    private void navigateToVerify(){
        Intent it = new Intent(this, VerifyActivity.class);
        startActivity(it);
        finish();
    }
    private void navigateToLogin(){
        Intent it = new Intent(this, LoginActivity.class);
        startActivity(it);
        finish();
    }
    private void register(){
        viewModel.register(new BaseCallback() {
            @Override
            public void doSuccess() {

            }
        });
    }
}
