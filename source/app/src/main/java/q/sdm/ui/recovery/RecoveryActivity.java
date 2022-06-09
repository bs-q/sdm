package q.sdm.ui.recovery;

import static q.sdm.constant.Constants.VALID_EMAIL_ADDRESS_REGEX;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.Observable;

import q.sdm.BR;
import q.sdm.R;
import q.sdm.data.model.api.response.account.ResetPasswordResponse;
import q.sdm.databinding.ActivityRecoveryPasswordBinding;
import q.sdm.di.component.ActivityComponent;
import q.sdm.ui.base.activity.BaseActivity;
import q.sdm.ui.base.activity.BaseRequestCallback;
import q.sdm.ui.base.activity.MessageWrapper;
import q.sdm.ui.register.verify.VerifyActivity;

public class RecoveryActivity extends BaseActivity<ActivityRecoveryPasswordBinding,RecoveryViewModel>
implements View.OnClickListener {
    @Override
    public int getLayoutId() {
        return R.layout.activity_recovery_password;
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
        viewModel.email.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                viewModel.validEmail.set(VALID_EMAIL_ADDRESS_REGEX.matcher(
                        viewModel.email.get()
                ).find());
            }
        });
    }

    @Override
    public void onClick(View v) {
        recoveryPassword();
    }

    private void recoveryPassword(){
        viewModel.requestRecovery(new BaseRequestCallback<ResetPasswordResponse>() {
            @Override
            public void doSuccess(ResetPasswordResponse response) {
                Intent it = new Intent(RecoveryActivity.this, VerifyActivity.class);
                myApplication().setIdHash(response.getIdHash());
                myApplication().setEmail(viewModel.email.get());
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
