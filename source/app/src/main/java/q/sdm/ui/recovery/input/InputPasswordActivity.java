package q.sdm.ui.recovery.input;

import static q.sdm.constant.Constants.VALID_EMAIL_ADDRESS_REGEX;

import q.sdm.R;
import q.sdm.BR;
import q.sdm.data.model.api.response.account.UpdatePasswordResponse;
import q.sdm.databinding.LayoutActivityInputNewPasswordBinding;
import q.sdm.di.component.ActivityComponent;
import q.sdm.ui.base.activity.BaseActivity;
import q.sdm.ui.base.activity.BaseRequestCallback;
import q.sdm.ui.login.LoginActivity;
import q.sdm.ui.recovery.complete.RecoveryCompleteActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.Observable;

public class InputPasswordActivity extends BaseActivity<LayoutActivityInputNewPasswordBinding,InputPasswordViewModel>
implements View.OnClickListener {

    @Override
    public int getLayoutId() {
        return R.layout.layout_activity_input_new_password;
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
        setupForm();
    }
    private void setupForm(){
        viewModel.password.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                viewModel.validPassword.set(viewModel.password.get().trim().length()>=8);
            }
        });
    }

    @Override
    public void onClick(View v) {
        viewModel.updatePassword(new BaseRequestCallback<UpdatePasswordResponse>() {
            @Override
            public void doSuccess(UpdatePasswordResponse response) {
                Intent it = new Intent(InputPasswordActivity.this, RecoveryCompleteActivity.class);
                startActivity(it);
                finish();
            }

            @Override
            public void doFail(String message, String code) {
                BaseRequestCallback.super.doFail(message, code);
                viewModel.showErrorMessage(message);
            }
        });
    }
}
